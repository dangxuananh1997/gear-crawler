package dao;

import dto.ProductDTO;
import dto.ProductType;
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
public class ProductDAO {

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
            return productList;
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
    
    public ProductDTO findProduct(ProductType productType, String hashCode) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        
        try {
            con = DBUtilities.makeConnection();
            if (con != null) {
                String sql = "SELECT * FROM Product WHERE ProductTypeId = ? AND HashCode = ?";
                stm = con.prepareStatement(sql);
                stm.setInt(1, productType.getValue());
                rs = stm.executeQuery();
                
                if (rs.next()) {
                    int id = rs.getInt("Id");
                    String name = rs.getString("Name");
                    String image = rs.getString("Image");
                    int price = rs.getInt("Price");
                    String productLink = rs.getString("ProductLink");
                    ProductDTO product = new ProductDTO(id, productType, hashCode, name, image, price, productLink);
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
    
    public boolean addProduct(ProductDTO product) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBUtilities.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO Product (ProductTypeId, HashCode, Name, Image, Price, ProductLink) VALUES (?,?,?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setInt(1, product.getProductType().getValue());
                stm.setInt(2, product.hashCode());
                stm.setString(3, product.getName());
                stm.setString(4, product.getImage());
                stm.setInt(5, product.getPrice());
                stm.setString(6, product.getProductLink());
                return (stm.executeUpdate() > 0);
            }
            return false;
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
    
    public boolean addProducts(List<ProductDTO> productList, ProductType productType) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        
        List<ProductDTO> existingProductList = getAllProduct(productType);
        List<String> hashCodeList = new LinkedList<>();
        for (ProductDTO product : existingProductList) {
            hashCodeList.add(product.getHashCode());
        }
        
        try {
            con = DBUtilities.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO Product (ProductTypeId, HashCode, Name, Image, Price, ProductLink) VALUES (?,?,?,?,?,?)";
                stm = con.prepareStatement(sql);
                for (ProductDTO product : productList) {
                    if (!hashCodeList.contains(product.getHashCode())) {
                        stm.setInt(1, product.getProductType().getValue());
                        stm.setString(2, product.getHashCode());
                        stm.setString(3, product.getName());
                        stm.setString(4, product.getImage());
                        stm.setInt(5, product.getPrice());
                        stm.setString(6, product.getProductLink());
                        stm.addBatch();
                    }
                }
                int[] result = stm.executeBatch();
                for (int i : result) {
                    if (i <= 0) {
                        return false;
                    }
                }
                return true;
            }
            return false;
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
    
}
