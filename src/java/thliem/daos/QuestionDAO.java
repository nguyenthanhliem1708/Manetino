/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thliem.daos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import thliem.dtos.QuestionDTO;
import thliem.utils.DBUtil;

/**
 *
 * @author LiemNguyen
 */
public class QuestionDAO {

    final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(QuestionDAO.class);

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

    public boolean addNewQuestion(String subjectID, String questionContent, String ans1, String ans2, String ans3, String ans4, String correctAns) {
        boolean isAdded = false;
        String url;
        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO tblQuestion (subjectID,question_content,answer1,answer2,answer3,answer4,answer_correct,createDate) VALUES (?,?,?,?,?,?,?,?)";
            stm = conn.prepareStatement(sql);
            stm.setString(1, subjectID);
            stm.setString(2, questionContent);
            stm.setString(3, ans1);
            stm.setString(4, ans2);
            stm.setString(5, ans3);
            stm.setString(6, ans4);
            stm.setString(7, correctAns);
            stm.setDate(8, Date.valueOf(LocalDate.now(ZoneId.systemDefault())));

            int affRow = stm.executeUpdate();
            if (affRow > 0) {
                isAdded = true;
            }
        }
        catch (ClassNotFoundException | SQLException | NamingException e) {
            LOG.error(e);
        }
        finally {
            closeConnection();
        }
        return isAdded;
    }

    public boolean checkQuestionExist(String questionContent, int type, int questionID) {
        try {
            conn = DBUtil.getConnection();
            if (type == 1) {
                String sql = "SELECT id FROM tblQuestion WHERE question_content = ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, questionContent);
            }
            else {
                String sql = "SELECT id FROM tblQuestion WHERE question_content = ? AND id != ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, questionContent);
                stm.setInt(2, questionID);
            }

            rs = stm.executeQuery();

            if (rs.next()) {

                return false;
            }
        }
        catch (ClassNotFoundException | SQLException | NamingException e) {
            LOG.error(e);
        }
        finally {
            closeConnection();
        }
        return true;
    }

    public int getPageNumber() {
        int row = 0;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT COUNT(id) FROM tblQuestion WHERE status = 1";
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

    public int getSearchResultPageNumber(int type, String content, int subjectId, boolean searchStatus) {
        int row = 0;
        try {
            conn = DBUtil.getConnection();
            String sql = "";
            switch (type) {
                case 0:
                    sql = "SELECT COUNT(id) FROM tblQuestion WHERE question_content LIKE ? ";
                    stm = conn.prepareStatement(sql);
                    stm.setString(1, "%" + content + "%");
                    break;
                case 1:
                    sql = "SELECT COUNT(id) FROM tblQuestion WHERE subjectID = ? ";
                    stm = conn.prepareStatement(sql);
                    stm.setInt(1, subjectId);
                    break;
                default:
                    sql = "SELECT COUNT(id) FROM tblQuestion WHERE status = ? ";
                    stm = conn.prepareStatement(sql);
                    stm.setBoolean(1, searchStatus);
                    break;
            }
            //stm = conn.prepareStatement(sql);
            rs = stm.executeQuery();

            if (rs.next()) {
                row = rs.getInt(1);
            }
        }
        catch (ClassNotFoundException | SQLException | NamingException e) {
            System.out.println(e);
            LOG.error(e);
        }
        finally {
            closeConnection();
        }
        return row;
    }

    public List<QuestionDTO> getAllQuestionList(int index) {
        List<QuestionDTO> list = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT id,question_content,subjectID,answer1,answer2,answer3,answer4,answer_correct,createDate,status "
                    + "FROM tblQuestion ORDER BY subjectID "
                    + "OFFSET (? - 1)* 6 ROWS FETCH NEXT 6 ROWS ONLY";
            stm = conn.prepareStatement(sql);
            stm.setInt(1, index);
            rs = stm.executeQuery();

            list = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                int subjectID = rs.getInt("subjectID");

                String questionContent = rs.getString("question_content");
                String ans1 = rs.getString("answer1");
                String ans2 = rs.getString("answer2");
                String ans3 = rs.getString("answer3");
                String ans4 = rs.getString("answer4");

                String correctAns = rs.getString("answer_correct");

                Date createDate = rs.getDate("createDate");
                boolean status = rs.getBoolean("status");

                list.add(new QuestionDTO(id, subjectID, questionContent, ans1, ans2, ans3, ans4, correctAns, createDate, status));
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

    public boolean deleteQuestion(int questionId) {
        boolean isDeleted = false;
        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE tblQuestion SET status = 0 WHERE id = ?";
            stm = conn.prepareStatement(sql);
            stm.setInt(1, questionId);
            int affRow = stm.executeUpdate();

            if (affRow > 0) {
                isDeleted = true;
            }
        }
        catch (ClassNotFoundException | SQLException | NamingException e) {
            LOG.error(e);
        }
        finally {
            closeConnection();
        }
        return isDeleted;
    }

    public boolean restoreQuestion(int questionId) {
        boolean isRestored = false;
        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE tblQuestion SET status = 1 WHERE id = ?";
            stm = conn.prepareStatement(sql);
            stm.setInt(1, questionId);
            int affRow = stm.executeUpdate();

            if (affRow > 0) {
                isRestored = true;
            }
        }
        catch (ClassNotFoundException | SQLException | NamingException e) {
            LOG.error(e);
        }
        finally {
            closeConnection();
        }
        return isRestored;
    }

    public boolean updateQuestion(int questionId, int subjectID, String questionContent, String ans1, String ans2, String ans3, String ans4, String correctAns) {
        boolean success = false;

        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE tblQuestion SET question_content = ?,subjectID = ?,answer1 = ?,answer2 = ?,answer3 = ?,answer4 = ?,answer_correct = ? WHERE id = ?";
            stm = conn.prepareStatement(sql);

            stm.setString(1, questionContent);
            stm.setInt(2, subjectID);
            stm.setString(3, ans1);
            stm.setString(4, ans2);
            stm.setString(5, ans3);
            stm.setString(6, ans4);
            stm.setString(7, correctAns);
            stm.setInt(8, questionId);

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

    public List<QuestionDTO> searchByContent(String content, int index) {
        List<QuestionDTO> list = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT id,question_content,subjectID,answer1,answer2,answer3,answer4,answer_correct,createDate,status "
                    + "FROM tblQuestion WHERE question_content LIKE ? ORDER BY subjectID "
                    + "OFFSET (? - 1)* 6 ROWS FETCH NEXT 6 ROWS ONLY";
            stm = conn.prepareStatement(sql);
            stm.setString(1, "%" + content + "%");
            stm.setInt(2, index);
            rs = stm.executeQuery();
            list = new ArrayList<QuestionDTO>();
            while (rs.next()) {
                int id = rs.getInt("id");
                int subjectID = rs.getInt("subjectID");

                String questionContent = rs.getString("question_content");
                String ans1 = rs.getString("answer1");
                String ans2 = rs.getString("answer2");
                String ans3 = rs.getString("answer3");
                String ans4 = rs.getString("answer4");

                String correctAns = rs.getString("answer_correct");

                Date createDate = rs.getDate("createDate");
                boolean status = rs.getBoolean("status");

                list.add(new QuestionDTO(id, subjectID, questionContent, ans1, ans2, ans3, ans4, correctAns, createDate, status));
            }
        }
        catch (Exception e) {
            LOG.error(e);
        }
        finally {
            closeConnection();
        }
        return list;
    }

    public List<QuestionDTO> searchBySubject(int subjectId, int index) {
        List<QuestionDTO> list = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT id,question_content,subjectID,answer1,answer2,answer3,answer4,answer_correct,createDate,status "
                    + "FROM tblQuestion WHERE subjectID = ? ORDER BY subjectID "
                    + "OFFSET (? - 1)* 6 ROWS FETCH NEXT 6 ROWS ONLY";
            stm = conn.prepareStatement(sql);
            stm.setInt(1, subjectId);
            stm.setInt(2, index);
            rs = stm.executeQuery();
            list = new ArrayList<QuestionDTO>();
            while (rs.next()) {
                int id = rs.getInt("id");
                int subjectID = rs.getInt("subjectID");

                String questionContent = rs.getString("question_content");
                String ans1 = rs.getString("answer1");
                String ans2 = rs.getString("answer2");
                String ans3 = rs.getString("answer3");
                String ans4 = rs.getString("answer4");

                String correctAns = rs.getString("answer_correct");

                Date createDate = rs.getDate("createDate");
                boolean status = rs.getBoolean("status");

                list.add(new QuestionDTO(id, subjectID, questionContent, ans1, ans2, ans3, ans4, correctAns, createDate, status));
            }
        }
        catch (Exception e) {
            LOG.error(e);
        }
        finally {
            closeConnection();
        }
        return list;
    }

    public List<QuestionDTO> searchByStatus(boolean searchStatus, int index) {
        List<QuestionDTO> list = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT id,question_content,subjectID,answer1,answer2,answer3,answer4,answer_correct,createDate,status "
                    + "FROM tblQuestion WHERE status = ? ORDER BY status "
                    + "OFFSET (? - 1)* 6 ROWS FETCH NEXT 6 ROWS ONLY";
            stm = conn.prepareStatement(sql);
            stm.setBoolean(1, searchStatus);
            stm.setInt(2, index);
            rs = stm.executeQuery();
            list = new ArrayList<QuestionDTO>();
            while (rs.next()) {
                int id = rs.getInt("id");
                int subjectID = rs.getInt("subjectID");

                String questionContent = rs.getString("question_content");
                String ans1 = rs.getString("answer1");
                String ans2 = rs.getString("answer2");
                String ans3 = rs.getString("answer3");
                String ans4 = rs.getString("answer4");

                String correctAns = rs.getString("answer_correct");

                Date createDate = rs.getDate("createDate");
                boolean status = rs.getBoolean("status");

                list.add(new QuestionDTO(id, subjectID, questionContent, ans1, ans2, ans3, ans4, correctAns, createDate, status));
            }
        }
        catch (Exception e) {
            LOG.error(e);
        }
        finally {
            closeConnection();
        }
        return list;
    }

}
