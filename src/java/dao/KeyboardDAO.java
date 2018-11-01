package dao;

import dto.KeyboardDTO;
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
public class KeyboardDAO implements Serializable {
        
    public boolean addKeyboard(KeyboardDTO keyboard) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBUtilities.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO Keyboard (ProductId, NumberOfKeys, PressForce, Distance, LED, Weight, Size, Switches) VALUES (?,?,?,?,?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setInt(1, keyboard.getProduct().getId());
                stm.setString(2, keyboard.getNumberOfKey());
                stm.setString(3, keyboard.getPressForce());
                stm.setString(4, keyboard.getDistance());
                stm.setString(5, keyboard.getLed());
                stm.setString(6, keyboard.getWeight());
                stm.setString(7, keyboard.getSize());
                stm.setString(8, keyboard.getSwitches());
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
    
    public KeyboardDTO getKeyboardByProduct(ProductDTO product) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        KeyboardDTO keyboard = null;
        
        try {
            con = DBUtilities.makeConnection();
            if (con != null) {
                String sql = "SELECT * FROM Keyboard WHERE ProductId = ?";
                stm = con.prepareStatement(sql);
                stm.setInt(1, product.getId());
                rs = stm.executeQuery();
                
                while (rs.next()) {
                    int Id =  rs.getInt("Id");
                    String numberOfKeys = rs.getString("NumberOfKeys");
                    String pressForce = rs.getString("PressForce");
                    String distance = rs.getString("Distance");
                    String led = rs.getString("LED");
                    String weight = rs.getString("Weight");
                    String size = rs.getString("Size");
                    String switches = rs.getString("Switches");
                    keyboard = new KeyboardDTO(Id, numberOfKeys, pressForce, distance, led, weight, size, switches, product);
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
        return keyboard;
    }
    
}
