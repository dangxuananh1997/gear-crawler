package dao;

import dto.LaptopDTO;
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
public class LaptopDAO implements Serializable {
    
    public boolean addLaptop(LaptopDTO laptop) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBUtilities.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO Laptop (ProductId, CPU, GPU, RAM, HardDrive, Monitor, Ports, Lan, Wireless) VALUES (?,?,?,?,?,?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setInt(1, laptop.getProduct().getId());
                stm.setString(2, laptop.getCpu());
                stm.setString(3, laptop.getGpu());
                stm.setString(4, laptop.getRam());
                stm.setString(5, laptop.getHardDrive());
                stm.setString(6, laptop.getMonitor());
                stm.setString(7, laptop.getPorts());
                stm.setString(8, laptop.getLan());
                stm.setString(9, laptop.getWireless());
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
