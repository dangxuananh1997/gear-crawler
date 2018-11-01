package dao;

import dto.LaptopDTO;
import dto.MouseDTO;
import dto.ProductDTO;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import utility.DBUtilities;

/**
 *
 * @author dangxuananh1997
 */
public class MouseDAO implements Serializable {
        
    public boolean addMouse(MouseDTO mouse) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBUtilities.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO Mouse (ProductId, Weight, MaxDPI, LED, NumberOfButton) VALUES (?,?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setInt(1, mouse.getProduct().getId());
                stm.setString(2, mouse.getWeight());
                stm.setString(3, mouse.getMaxDPI());
                stm.setString(4, mouse.getLed());
                stm.setString(5, mouse.getNumberOfButton());
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
    
    public MouseDTO getMouseByProduct(ProductDTO product) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        MouseDTO mouse = null;
        
        try {
            con = DBUtilities.makeConnection();
            if (con != null) {
                String sql = "SELECT * FROM Mouse WHERE ProductId = ?";
                stm = con.prepareStatement(sql);
                stm.setInt(1, product.getId());
                rs = stm.executeQuery();
                
                while (rs.next()) {
                    int Id =  rs.getInt("Id");
                    String weight = rs.getString("Weight");
                    String maxDPI = rs.getString("MaxDPI");
                    String led = rs.getString("LED");
                    String numberOfButton = rs.getString("NumberOfButton");
                    mouse = new MouseDTO(Id, weight, maxDPI, led, numberOfButton, product);
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
        return mouse;
    }
    
}
