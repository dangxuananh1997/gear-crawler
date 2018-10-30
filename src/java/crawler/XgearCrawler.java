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
public class XgearCrawler implements CrawlerInterface {
    
    private final String siteUrl = "https://xgear.vn";
    private final String[] laptopPath = {
        "/danh-muc/laptop-msi",
        "/danh-muc/laptop-asus",
        "/danh-muc/laptop-dell",
        "/danh-muc/laptop-acer",
    };
    private final String mousePath = "/danh-muc/mouse";
    private final String keyboardPath = "/danh-muc/keyboard";
    
    ProductDAO productDAO = new ProductDAO();
    LaptopDAO laptopDAO = new LaptopDAO();
    MouseDAO mouseDAO = new MouseDAO();
    KeyboardDAO keyboardDAO = new KeyboardDAO();
            
    private List<ProductDTO> productList = new LinkedList<>();
    private boolean isPause = false;
    private int pausePosition = 0;
    
    public XgearCrawler() {
    }

    @Override
    public void crawl() throws SQLException, NamingException {
        if (!this.isPause) {
            System.out.println("XGEAR - CRAWLING");
            this.isPause = false;

            // get Laptops, Mouses, Keyboards
            for (String path : laptopPath) {
                this.productList.addAll(getAllDraftProducts(siteUrl + path, ProductType.LAPTOP));
            }
            this.productList.addAll(getAllDraftProducts(siteUrl + mousePath, ProductType.MOUSE));
            this.productList.addAll(getAllDraftProducts(siteUrl + keyboardPath, ProductType.KEYBOARD));

            List<ProductDTO> existingProductList = productDAO.getAllProduct();
            List<String> existingHashCode = new LinkedList<>();
            for (ProductDTO product : existingProductList) {
                existingHashCode.add(product.getHashCode());
            }

            for (int i = pausePosition; i < productList.size(); i++) {
                try {
                    if (isPause) {
                        System.out.println("XGEAR - PAUSED");
                        break;
                    } else {
                        this.pausePosition++;
                        ProductDTO product = productList.get(i);
                        if (!existingHashCode.contains(product.getHashCode())) {
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
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            System.out.println("XGEAR - FINISHED");
        } else {
            System.out.println("XGEAR - PAUSED");
        }
    }
    
    @Override
    public void pause() {
        if (!this.isPause) {
            this.isPause = true;
            System.out.println("HANGCHINHHIEU - PAUSED");
        }
    }
    
    private String getPaginationDomString(String url) {
        try {
            BufferedReader reader = XMLUtilities.getBufferedReaderFromURL(url);
            String line;
            String document = "";
            boolean isStart = false;
            while ((line = reader.readLine()) != null) {
                if (line.contains("<ul class='page-numbers'>")) {
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
            Logger.getLogger(XgearCrawler.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(XgearCrawler.class.getName()).log(Level.SEVERE, null, ex);
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
                if (line.contains("class=\"products-loop")) {
                    isStart = true;
                }
                // replace entity &#8363; with đ
                if (isStart && line.contains("&#8363;")) {
                    line = line.replaceAll("&#8363;", "đ");
                }
                // replace entity &-; with a-z
                if (isStart && line.contains("&") && line.contains(";")) {
                    line = line.replaceAll("&", "a").replaceAll(";", "z");
                }
                // add img closing tag
                if (isStart && line.contains("<img")) {
                    line = CommonUtilities.addCloseTagToLine(line, "img");
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
            Logger.getLogger(XgearCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    private List<ProductDTO> getAllDraftProducts(String url, ProductType productType) {
        List<ProductDTO> productArray = new LinkedList<>();
        try {
            int lastPageNumber = getLastPageNumber(url);
            for (int page = 1; page <= lastPageNumber; page++) {
                String domString = getProductListDomString(url + "/page/" + page);
                if (!domString.isEmpty()) {
                    Document document = XMLUtilities.parseStringToDom(domString);
                    XPath xPath = XMLUtilities.getXPath();
                    NodeList productList = (NodeList) xPath.evaluate("./ul/li", document, XPathConstants.NODESET);
                    if (productList != null) {
                        for (int p = 0; p < productList.getLength(); p++) {
                            Node productNode = productList.item(p);
                            
                            Node productImageNode = (Node) xPath.evaluate(".//img", productNode, XPathConstants.NODE);
                            String productImage = productImageNode.getAttributes().getNamedItem("src").getTextContent().trim();
                            
                            Node productLinkNode = (Node) xPath.evaluate(".//div[@class='item-img products-thumb']/a", productNode, XPathConstants.NODE);
                            String productLink = productLinkNode.getAttributes().getNamedItem("href").getTextContent().trim();
                            
                            Node productNameNode = (Node) xPath.evaluate(".//div[@class='item-content products-content']/h4/a", productNode, XPathConstants.NODE);
                            String productName = productNameNode.getTextContent();
                            
                            String productPrice = (String) xPath.evaluate(".//span[@class='item-price']/span", productNode, XPathConstants.STRING);
                            
                            ProductDTO product = new ProductDTO(productType, productName, productImage, CommonUtilities.convertPriceXgear(productPrice), productLink);
                            productArray.add(product);
                        }
                    }
                }
            }
        } catch (XPathExpressionException | DOMException ex) {
            Logger.getLogger(XgearCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return productArray;
    }
    
    private String getInfoTableDomString(String url) {
        System.out.print("XGEAR - GET PRODUCT: " + url);
        try {
            BufferedReader reader = XMLUtilities.getBufferedReaderFromURL(url);
            String line;
            String document = "";
            boolean isStart = false;
            while ((line = reader.readLine()) != null) {
                if (line.contains("<table class=\"shop_attributes\">")) {
                    isStart = true;
                }
                // replace entity &-; with a-z
                if (isStart && line.contains("&") && line.contains(";")) {
                    line = line.replaceAll("&", "a").replaceAll(";", "z");
                }
                // close tag table
                if (isStart && line.contains("</table>")) {
                    line = line.substring(0, line.indexOf("</table>")) + "</table>";
                    document += line.trim();
                    break;
                }
                if (isStart && !line.trim().isEmpty()) {
                    document += line.trim();
                }
            }
            System.out.println(" - DONE");
            return document;
        } catch (IOException ex) {
            System.out.println(" - ERROR");
            Logger.getLogger(XgearCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    private Map<String, String> getInfoTableMap(String tableDomString) {
        Map<String, String> table = new HashMap<>();
        try {
            if (tableDomString.isEmpty()) {
                return table;
            }
            XMLStreamReader reader = XMLUtilities.parseStringToXMLStreamReader(tableDomString);
            String tmpKey = null;
            while (reader.hasNext()) {
                int cursor = reader.next();
                if (cursor == XMLStreamReader.START_ELEMENT) {
                    String tagName = reader.getLocalName();
                    if (tagName.equals("td") || tagName.equals("th")) {
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
            Logger.getLogger(XgearCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return table;
    }
    
    private LaptopDTO parseLaptop(String tableDomString, ProductDTO product) {
        try {
            Map<String, String> table = getInfoTableMap(tableDomString);
            String cpu = table.get("CPU");
            String gpu = table.get("GPU");
            String ram = table.get("RAM");
            String hardDrive = table.get("Ổ cứng SSD") + ", " + table.get("Ổ cứng HDD");
            String monitor = table.get("Màn hình");
            String ports = table.get("Các cổng kết nối");
            String lan = table.get("Lan");
            String wireless = table.get("Wireless Lan");
            LaptopDTO laptop = new LaptopDTO(cpu, gpu, ram, hardDrive, monitor, ports, lan, wireless, product);
            return laptop;
        } catch (Exception ex) {
            Logger.getLogger(XgearCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private MouseDTO parseMouse(String tableDomString, ProductDTO product) {
        try {
            Map<String, String> table = getInfoTableMap(tableDomString);
            String weight = table.get("Mouse Weight");
            String maxDPI = table.get("Mouse Max. speed");
            String led = table.get("Mouse Led");
            String numberOfButton = table.get("Mouse Buttons");
            MouseDTO mouse = new MouseDTO(weight, maxDPI, led, numberOfButton, product);
            return mouse;
        } catch (NumberFormatException ex) {
            Logger.getLogger(XgearCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private KeyboardDTO parseKeyboard(String tableDomString, ProductDTO product) {
        try {
            Map<String, String> table = getInfoTableMap(tableDomString);
            String numberOfKey = table.get("Keyboard Key Rollover");
            String pressForce = table.get("Keyboard Actuation force");
            String distance = table.get("Keyboard Travel distance");
            String led = table.get("Keyboard Backlighting");
            String weight = table.get("Keyboard Weight");
            String size = table.get("Keyboard Size");
            String switches = table.get("Keyboard Switches");
            KeyboardDTO keyboard = new KeyboardDTO(numberOfKey, pressForce, distance, led, weight, size, switches, product);
            return keyboard;
        } catch (NumberFormatException ex) {
            Logger.getLogger(XgearCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
