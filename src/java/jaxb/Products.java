package jaxb;

import dto.ProductDTO;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author dangxuananh1997
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "products", propOrder = { "product" })
public class Products {
    
    @XmlElement(required = true, name = "product")
    private List<ProductDTO> product;

    public Products(List<ProductDTO> product) {
        this.product = product;
    }

    public List<ProductDTO> getProduct() {
        if (product == null) {
            product = new LinkedList<>();
        }
        return product;
    }

    public void setProduct(List<ProductDTO> productList) {
        this.product = productList;
    }
    
}
