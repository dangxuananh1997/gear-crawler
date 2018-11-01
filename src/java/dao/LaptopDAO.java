package dao;

import dto.LaptopDTO;
import dto.ProductDTO;
import dto.ProductType;
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
    
    public LaptopDTO getLaptopByProduct(ProductDTO product) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        LaptopDTO laptop = null;
        
        try {
            con = DBUtilities.makeConnection();
            if (con != null) {
                String sql = "SELECT * FROM Laptop WHERE ProductId = ?";
                stm = con.prepareStatement(sql);
                stm.setInt(1, product.getId());
                rs = stm.executeQuery();
                
                while (rs.next()) {
                    int Id =  rs.getInt("Id");
                    String cpu = rs.getString("CPU");
                    String gpu = rs.getString("GPU");
                    String ram = rs.getString("RAM");
                    String hardDrive = rs.getString("HardDrive");
                    String monitor = rs.getString("Monitor");
                    String ports = rs.getString("Ports");
                    String lan = rs.getString("Lan");
                    String wireless = rs.getString("Wireless");
                    laptop = new LaptopDTO(cpu, gpu, ram, hardDrive, monitor, ports, lan, wireless, product);
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
        return laptop;
    }
    
}
