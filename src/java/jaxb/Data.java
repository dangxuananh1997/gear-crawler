package jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author dangxuananh1997
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "pageNumber", "lastPage", "products" })
@XmlRootElement(name = "data")
public class Data {
    
    @XmlElement(required = true, name = "pageNumber")
    private int pageNumber;
    
    @XmlElement(required = true, name = "lastPage")
    private int lastPage;
    
    @XmlElement(required = true, name = "products")
    private Products products;

    public Data(int lastPage, int pageNumber, Products products) {
        this.lastPage = lastPage;
        this.pageNumber = pageNumber;
        this.products = products;
    }

    public Data() {
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }
    
}
