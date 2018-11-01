package action.client;

import com.opensymphony.xwork2.Action;
import dao.KeyboardDAO;
import dao.LaptopDAO;
import dao.MouseDAO;
import dao.ProductDAO;
import dto.KeyboardDTO;
import dto.LaptopDTO;
import dto.MouseDTO;
import dto.ProductDTO;
import utility.XMLUtilities;

/**
 *
 * @author dangxuananh1997
 */
public class ViewDetailsAction {
    
    // Inputs
    private int productId;
    
    // Outputs
    private String xmlOutput;
    
    public ViewDetailsAction() {
    }
    
    public String execute() throws Exception {
        try {
            ProductDAO productDAO = new ProductDAO();
            ProductDTO product = productDAO.getProductById(productId);
            switch (product.getProductType()) {
                case LAPTOP:
                    LaptopDAO laptopDAO = new LaptopDAO();
                    LaptopDTO laptop = laptopDAO.getLaptopByProduct(product);
                    xmlOutput = XMLUtilities.marshalling(laptop);
                    break;
                case KEYBOARD:
                    KeyboardDAO keyboardDAO = new KeyboardDAO();
                    KeyboardDTO keyboard = keyboardDAO.getKeyboardByProduct(product);
                    xmlOutput = XMLUtilities.marshalling(keyboard);
                    break;
                case MOUSE:
                    MouseDAO mouseDAO = new MouseDAO();
                    MouseDTO mouse = mouseDAO.getMouseByProduct(product);
                    xmlOutput = XMLUtilities.marshalling(mouse);
                    break;
            }
            return Action.SUCCESS;
        } catch (Exception e) {
            System.out.println(e);
            return Action.ERROR;
        }
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getXmlOutput() {
        return xmlOutput;
    }

    public void setXmlOutput(String xmlOutput) {
        this.xmlOutput = xmlOutput;
    }
    
}
