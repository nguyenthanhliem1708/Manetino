/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thliem.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import thliem.dtos.UserDTO;
import thliem.utils.DBUtil;

/**
 *
 * @author LiemNguyen
 */
public class UserDAO {

    final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(UserDAO.class);

    private Connection conn;
    private PreparedStatement stm;
    private ResultSet rs;

    private void closeConnection() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (rs != null) {
                conn.close();
            }
        }
        catch (Exception e) {
            LOG.error(e);
        }
    }

    public UserDTO checkLogin(String email, String password) {
        UserDTO dto = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT name,roleID,status FROM tblUser WHERE email = ? AND password = ?";
            stm = conn.prepareStatement(sql);
            stm.setString(1, email);
            stm.setString(2, password);
            
            rs = stm.executeQuery();
            
            if (rs.next()) {
                String name = rs.getString("name");
                String roleID = rs.getString("roleID");
                String status = rs.getString("status");

                dto = new UserDTO(email, roleID, name, "***", status);
            }

        }
        catch (Exception e) {
            LOG.error(e);
        }
        finally {
            closeConnection();
        }
        return dto;
    }

    public boolean createNewAccount(String name, String email, String password) {
        boolean success = false;

        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO tblUser (email,name,password,roleID) VALUES (?,?,?,?)";
            stm = conn.prepareStatement(sql);
            stm.setString(1, email);
            stm.setString(2, name);
            stm.setString(3, password);
            stm.setString(4, "ST");
            

            int affRow = stm.executeUpdate();

            if (affRow > 0) {
                success = true;
            }
        }
        catch (ClassNotFoundException | SQLException | NamingException e) {
            System.out.println(e);
            LOG.error(e);
        }
        finally {
            closeConnection();
        }
        return success;
    }

}
