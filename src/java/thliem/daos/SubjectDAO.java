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
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import thliem.dtos.SubjectDTO;
import thliem.utils.DBUtil;

/**
 *
 * @author LiemNguyen
 */
public class SubjectDAO {

    final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(SubjectDAO.class);
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
        catch (SQLException e) {
            LOG.error(e);
        }
    }

    public int getSubjectNumber() {
        int row = 0;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT COUNT(id) FROM tblSubject";
            stm = conn.prepareStatement(sql);
            rs = stm.executeQuery();
            if (rs.next()) {
                row = rs.getInt(1);
            }
        }
        catch (ClassNotFoundException | SQLException | NamingException e) {
            LOG.error(e);
        }
        finally {
            closeConnection();
        }
        return row;
    }

    public int getSubjectSize(int id) {
        int size = 0;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT COUNT(id) FROM tblQuestion WHERE subjectID = ?";
            stm = conn.prepareStatement(sql);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            if (rs.next()) {
                size = rs.getInt(1);
            }
        }
        catch (ClassNotFoundException | SQLException | NamingException e) {
            LOG.error(e);
        }
        finally {
            closeConnection();
        }
        return size;
    }

    public List<SubjectDTO> loadSubjectList(int type, int index) {
        List<SubjectDTO> list = null;
        try {
            conn = DBUtil.getConnection();
            String sql;
            if (type == 0) {
                sql = "SELECT id,subject_name,status,quantity_limit,time_limit FROM tblSubject WHERE status = 1 ORDER BY subject_name";
                stm = conn.prepareStatement(sql);
            }
            else {
                sql = "SELECT id,subject_name,status,quantity_limit,time_limit FROM tblSubject ORDER BY id OFFSET (? - 1)*3 ROWS FETCH NEXT 3 ROWS ONLY";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, index);
            }

            rs = stm.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("subject_name");
                boolean status = rs.getBoolean("status");
                int quatityLimit = rs.getInt("quantity_limit");
                int timeLimit = rs.getInt("time_limit");
                list.add(new SubjectDTO(id, name, status, quatityLimit, timeLimit));
            }
        }
        catch (ClassNotFoundException | SQLException | NamingException e) {
            LOG.error(e);
        }
        finally {
            closeConnection();
        }
        return list;
    }

    public boolean checkDupSubject(String subjectName,int id) {
        boolean valid = true;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT id FROM tblSubject WHERE subject_name = ? AND id != ?";
            stm = conn.prepareStatement(sql);
            stm.setString(1, subjectName);
            stm.setInt(2, id);
            rs = stm.executeQuery();
            if (rs.next()) {
                valid = false;
            }
        }
        catch (ClassNotFoundException | SQLException | NamingException e) {
            LOG.error(e);
        }
        finally {
            closeConnection();
        }
        return valid;
    }

    public boolean createSubject(String subjectName, int quantityLimit, int timeLimit) {
        boolean success = false;
        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO tblSubject (subject_name,status,quantity_limit,time_limit) VALUES  (?,?,?,?)";
            stm = conn.prepareStatement(sql);
            stm.setString(1, subjectName);
            stm.setBoolean(2, true);
            stm.setInt(3, quantityLimit);
            stm.setInt(4, timeLimit);
            int affRow = stm.executeUpdate();
            if (affRow > 0) {
                success = true;
            }
        }
        catch (ClassNotFoundException | SQLException | NamingException e) {
            LOG.error(e);
        }
        finally {
            closeConnection();
        }
        return success;
    }

    public boolean updateSubject(String subjectName, int subjectId, int quantityLimit, int timeLimit) {
        boolean success = false;
        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE tblSubject SET subject_name = ?,quantity_limit = ?,time_limit = ?  WHERE id = ?";
            stm = conn.prepareStatement(sql);
            stm.setString(1, subjectName);
            stm.setInt(2, quantityLimit);
            stm.setInt(3, timeLimit);
            stm.setInt(4, subjectId);
            int affRow = stm.executeUpdate();
            if (affRow > 0) {
                success = true;
            }
        }
        catch (ClassNotFoundException | SQLException | NamingException e) {
            LOG.error(e);
        }
        finally {
            closeConnection();
        }
        return success;
    }

    public boolean deleteSubject(int subjectId) {
        boolean success = false;
        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE tblSubject SET status = 0 WHERE id = ?";
            stm = conn.prepareStatement(sql);
            stm.setInt(1, subjectId);
            int affRow = stm.executeUpdate();
            if (affRow > 0) {
                success = true;
            }
        }
        catch (Exception e) {
            LOG.error(e);
        }
        finally {
            closeConnection();
        }
        return success;
    }
}
