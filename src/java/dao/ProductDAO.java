package dao;

import dto.ProductDTO;
import dto.ProductType;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.naming.NamingException;
import utility.DBUtilities;

/**
 *
 * @author dangxuananh1997
 */
public class ProductDAO implements Serializable {

    public List<ProductDTO> getAllProduct() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<ProductDTO> productList = new LinkedList<>();
        
        try {
            con = DBUtilities.makeConnection();
            if (con != null) {
                String sql = "SELECT * FROM Product";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                
                while (rs.next()) {
                    int id = rs.getInt("Id");
                    int productType = rs.getInt("ProductTypeId");
                    String hashCode = rs.getString("HashCode");
                    String name = rs.getString("Name");
                    String image = rs.getString("Image");
                    int price = rs.getInt("Price");
                    String productLink = rs.getString("ProductLink");
                    ProductDTO product = new ProductDTO(id, ProductType.valueOf(productType), hashCode, name, image, price, productLink);
                    productList.add(product);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return productList;
    }
    
    public ProductDTO addProduct(ProductDTO product) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBUtilities.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO Product (ProductTypeId, HashCode, Name, Image, Price, ProductLink) VALUES (?,?,?,?,?,?)";
                stm = con.prepareStatement(sql, stm.RETURN_GENERATED_KEYS);
                stm.setInt(1, product.getProductType().getValue());
                stm.setString(2, product.getHashCode());
                stm.setString(3, product.getName());
                stm.setString(4, product.getImage());
                stm.setInt(5, product.getPrice());
                stm.setString(6, product.getProductLink());
                boolean success = stm.executeUpdate() > 0;
                
                rs = stm.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    product.setId(id);
                    return product;
                }
            }
            return null;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    
    public List<ProductDTO> getAllProduct(ProductType productType) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<ProductDTO> productList = new LinkedList<>();
        
        try {
            con = DBUtilities.makeConnection();
            if (con != null) {
                String sql = "SELECT * FROM Product WHERE ProductTypeId = ?";
                stm = con.prepareStatement(sql);
                stm.setInt(1, productType.getValue());
                rs = stm.executeQuery();
                
                while (rs.next()) {
                    int id = rs.getInt("Id");
                    String hashCode = rs.getString("HashCode");
                    String name = rs.getString("Name");
                    String image = rs.getString("Image");
                    int price = rs.getInt("Price");
                    String productLink = rs.getString("ProductLink");
                    ProductDTO product = new ProductDTO(id, productType, hashCode, name, image, price, productLink);
                    productList.add(product);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return productList;
    }
    
}
