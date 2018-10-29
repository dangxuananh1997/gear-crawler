package dao;

import dto.KeyboardDTO;
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
    
}
