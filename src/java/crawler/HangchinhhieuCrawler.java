package crawler;

import dao.KeyboardDAO;
import dao.LaptopDAO;
import dao.MouseDAO;
import dao.ProductDAO;
import dto.KeyboardDTO;
import dto.LaptopDTO;
import dto.MouseDTO;
import dto.ProductDTO;
import dto.ProductType;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import utility.CommonUtilities;
import utility.XMLUtilities;

/**
 *
 * @author dangxuananh1997
 */
public class HangchinhhieuCrawler {

    private final String siteUrl = "https://hangchinhhieu.vn";
    private final String laptopPath = "/collections/laptop";
    private final String mousePath = "/collections/chuot";
    private final String keyboardPath = "/collections/ban-phim";
    private final String headsetPath = "/collections/tai-nghe";
    
    ProductDAO productDAO = new ProductDAO();
    LaptopDAO laptopDAO = new LaptopDAO();
    MouseDAO mouseDAO = new MouseDAO();
    KeyboardDAO keyboardDAO = new KeyboardDAO();
            
    private List<ProductDTO> productList = new LinkedList<>();
    private boolean isPause = false;
    private int pausePosition = 0;
    
    public HangchinhhieuCrawler() {
    }
    
    public void pauseCrawler() {
        isPause = true;
    }
    
    public void crawl() throws SQLException, NamingException {
        System.out.println("HANGCHINHHIEU - CRAWLING");
        
        this.isPause = false;
        
        // get Laptops, Mouses, Keyboards
        this.productList.addAll(getAllDraftProducts(siteUrl + laptopPath, ProductType.LAPTOP));
        this.productList.addAll(getAllDraftProducts(siteUrl + mousePath, ProductType.MOUSE));
        this.productList.addAll(getAllDraftProducts(siteUrl + keyboardPath, ProductType.KEYBOARD));
        
        List<ProductDTO> existingProductList = productDAO.getAllProduct();
        List<String> existingHashCode = new LinkedList<>();
        for (ProductDTO product : existingProductList) {
            existingHashCode.add(product.getHashCode());
        }
        
        for (int i = pausePosition; i < productList.size(); i++) {
            if (isPause) {
                System.out.println("\nHANGCHINHHIEU - PAUSED");
                break;
            } else {
                this.pausePosition++;
                ProductDTO product = productList.get(i);
                if (!existingHashCode.contains(product.getHashCode())) {
                    System.out.print(".");
                    String tableDomString = getInfoTableDomString(product.getProductLink());
                    product = productDAO.addProduct(product);
                    switch (product.getProductType()) {
                        case LAPTOP:
                            LaptopDTO laptop = parseLaptop(tableDomString, product);
                            laptopDAO.addLaptop(laptop);
                            break;
                        case MOUSE:
                            MouseDTO mouse = parseMouse(tableDomString, product);
                            mouseDAO.addMouse(mouse);
                            break;
                        case KEYBOARD:
                            KeyboardDTO keyboard = parseKeyboard(tableDomString, product);
                            keyboardDAO.addKeyboard(keyboard);
                            break;
                        default:
                            break;
                    }      
                }
            }
        }
        
        System.out.println("\nHANGCHINHHIEU - FINISHED");
    }
    
    private String getPaginationDomString(String url) {
        try {
            BufferedReader reader = XMLUtilities.getBufferedReaderFromURL(url);
            String line;
            String document = "";
            boolean isStart = false;
            while ((line = reader.readLine()) != null) {
                if (line.contains("<ul class=\"pagination\">")) {
                    isStart = true;
                }
                // replace entity &-; with a-z
                if (isStart && line.contains("&") && line.contains(";")) {
                    line = line.replaceAll("&", "a").replaceAll(";", "z");
                }
                // close tag ul
                if (isStart && line.contains("</ul>")) {
                    line = line.substring(0, line.indexOf("</ul>")) + "</ul>";
                    document += line.trim();
                    break;
                }
                if (isStart && !line.trim().isEmpty()) {
                    document += line.trim();
                }
            }
            return document;
        } catch (IOException ex) {
            Logger.getLogger(HangchinhhieuCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    private int getLastPageNumber(String url) {
        int pageNum = 1;
        try {
            String domString = getPaginationDomString(url);
            if (!domString.isEmpty()) {
                Document document = XMLUtilities.parseStringToDom(domString);
                XPath xPath = XMLUtilities.getXPath();
                String lastPageNum = (String) xPath.evaluate("//li[last()-1]/a", document, XPathConstants.STRING);
                if (lastPageNum != null) {
                    pageNum = Integer.parseInt(lastPageNum);
                }
            }
        } catch (NumberFormatException | XPathExpressionException ex) {
            Logger.getLogger(HangchinhhieuCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pageNum;
    }
    
    private String getProductListDomString(String url) {
        try {
            BufferedReader reader = XMLUtilities.getBufferedReaderFromURL(url);
            String line;
            String document = "";
            boolean isStart = false;
            while ((line = reader.readLine()) != null) {
                if (line.contains("<section id=\"insCollectionPage\">")) {
                    isStart = true;
                }
                // replace entity &-; with a-z
                if (isStart && line.contains("&") && line.contains(";")) {
                    line = line.replaceAll("&", "a").replaceAll(";", "z");
                }
                // add img closing tag
                if (isStart && line.contains("<img")) {
                    line = CommonUtilities.addCloseTagToLine(line, "img");
                }
                // remove data-price with '<' line
                if (isStart && line.contains("data-price")) {
                    line = "";
                }
                if (isStart && !line.trim().isEmpty()) {
                    document += line.trim();
                }
                if (isStart && line.contains("</section>")) {
                    break;
                }
            }
            return document;
        } catch (IOException ex) {
            Logger.getLogger(HangchinhhieuCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    private List<ProductDTO> getAllDraftProducts(String url, ProductType productType) {
        List<ProductDTO> productArray = new LinkedList<>();
        try {
            int lastPageNumber = getLastPageNumber(url);
            for (int page = 1; page <= lastPageNumber; page++) {
                String domString = getProductListDomString(url + "?page=" + page);
                if (!domString.isEmpty()) {
                    Document document = XMLUtilities.parseStringToDom(domString);
                    XPath xPath = XMLUtilities.getXPath();
                    NodeList productList = (NodeList) xPath.evaluate("//*[@id=\"pd_collection\"]/ul/li", document, XPathConstants.NODESET);
                    if (productList != null) {
                        for (int p = 0; p < productList.getLength(); p++) {
                            Node productNode = productList.item(p);
                            
                            Node productLinkNode = (Node) xPath.evaluate(".//a[@class=\"productName\"]", productNode, XPathConstants.NODE);
                            String productLink = productLinkNode.getAttributes().getNamedItem("href").getTextContent().trim();
                            String productName = productLinkNode.getAttributes().getNamedItem("title").getTextContent().trim();
                            String productPrice = (String) xPath.evaluate(".//p[@class=\"pdPrice\"]/span", productNode, XPathConstants.STRING);
                            Node productImageNode = (Node) xPath.evaluate(".//div[@class=\"image-product\"]/a/img", productNode, XPathConstants.NODE);
                            String productImage = productImageNode.getAttributes().getNamedItem("src").getTextContent().trim();
                            
                            productLink = siteUrl + productLink;
                            
                            ProductDTO product = new ProductDTO(productType, productName, productImage, CommonUtilities.convertPriceHangchinhhieu(productPrice), productLink);
                            productArray.add(product);
                        }
                    }
                }
            }
        } catch (XPathExpressionException | DOMException ex) {
            Logger.getLogger(HangchinhhieuCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return productArray;
    }
    
    private String getInfoTableDomString(String url) {
        try {
            BufferedReader reader = XMLUtilities.getBufferedReaderFromURL(url);
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("<table")) {
                    int openTagPos = line.indexOf("<table");
                    int closeTagPos = line.indexOf("</table>") > 0
                            ? line.indexOf("</table>")
                            : line.length();
                    line = line.substring(openTagPos, closeTagPos) + "</table>";
                    
                    // replace entity &nbsp; with space
                    if (line.contains("&nbsp;")) {
                        line = line.replaceAll("&nbsp;", " ");
                    }
                    
                    // add br closing tag
                    if (line.contains("<br")) {
                        line = CommonUtilities.addCloseTagToLine(line, "br");
                    }
                    
                    if (closeTagPos > 0) {
                        return line.trim();
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(HangchinhhieuCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    private Map<String, String> getInfoTableMap(String tableDomString) {
        Map<String, String> table = new HashMap<>();
        try {
            XMLStreamReader reader = XMLUtilities.parseStringToXMLStreamReader(tableDomString);
            String tmpKey = null;
            while (reader.hasNext()) {
                int cursor = reader.next();
                if (cursor == XMLStreamReader.START_ELEMENT) {
                    String tagName = reader.getLocalName();
                    if (tagName.equals("td")) {
                        // traverse until meet text node
                        while (!reader.hasText() && reader.hasNext()) {
                            reader.next();
                        }
                        if (reader.hasNext()) {
                            if (tmpKey == null) {
                                tmpKey = reader.getText().trim();
                            } else {
                                table.put(tmpKey, reader.getText().trim());
                                tmpKey = null;
                            }
                        }
                    }
                }
            }
        } catch (XMLStreamException ex) {
            Logger.getLogger(HangchinhhieuCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return table;
    }
    
    private LaptopDTO parseLaptop(String tableDomString, ProductDTO product) {
        try {
            Map<String, String> table = getInfoTableMap(tableDomString);
            String cpu = table.get("CPU");
            String gpu = table.get("Card đồ họa");
            String ram = table.get("RAM");
            String hardDrive = table.get("Ổ cứng");
            String monitor = table.get("Màn hình");
            String ports = table.get("Cổng giao tiếp");
            String lan = table.get("Chuẩn LAN");
            String wireless = table.get("Chuẩn WIFI") + ", " + table.get("Bluetooth");
            LaptopDTO laptop = new LaptopDTO(cpu, gpu, ram, hardDrive, monitor, ports, lan, wireless, product);
            return laptop;
        } catch (Exception ex) {
            Logger.getLogger(HangchinhhieuCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private MouseDTO parseMouse(String tableDomString, ProductDTO product) {
        try {
            Map<String, String> table = getInfoTableMap(tableDomString);
            String weight = table.get("Cân nặng");
            String maxDPI = table.get("DPI (Tối thiểu / Tối đa)");
            String led = table.get("LED");
            String numberOfButton = table.get("Số nút");
            MouseDTO mouse = new MouseDTO(weight, maxDPI, led, numberOfButton, product);
            return mouse;
        } catch (NumberFormatException ex) {
            Logger.getLogger(HangchinhhieuCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private KeyboardDTO parseKeyboard(String tableDomString, ProductDTO product) {
        try {
            Map<String, String> table = getInfoTableMap(tableDomString);
            String numberOfKey = table.get("Số nút");
            String pressForce = table.get("Lực nhấn");
            String distance = table.get("Khoảng cách hành trình");
            String led = table.get("Đền nền");
            String weight = table.get("Trọng lượng");
            String size = table.get("Kích thước");
            String switches = table.get("Switch");
            KeyboardDTO keyboard = new KeyboardDTO(numberOfKey, pressForce, distance, led, weight, size, switches, product);
            return keyboard;
        } catch (NumberFormatException ex) {
            Logger.getLogger(HangchinhhieuCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
