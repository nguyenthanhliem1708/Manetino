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
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import thliem.dtos.HistoryDTO;
import thliem.dtos.QuestionDTO;
import thliem.utils.DBUtil;

/**
 *
 * @author LiemNguyen
 */
public class QuizDAO {

    final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(QuizDAO.class);
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
    
    public List<QuestionDTO> getRandomQuestion(int quantityLimit,int subjectId){
         List<QuestionDTO> list = new ArrayList<>();
        try{
            conn = DBUtil.getConnection();
            String sql = "SELECT TOP(?) id,question_content,answer1,answer2,answer3,answer4,answer_correct "
                    + "FROM tblQuestion WHERE status = 1 AND subjectID = ? ORDER BY NEWID() ";
            stm = conn.prepareStatement(sql);
            
            stm.setInt(1,quantityLimit);
            stm.setInt(2, subjectId);
            rs = stm.executeQuery();
            
            while(rs.next()){
                int questionId = rs.getInt("id");
                String content = rs.getString("question_content");
                String answer1 = rs.getString("answer1");
                String answer2 = rs.getString("answer2");
                String answer3 = rs.getString("answer3");
                String answer4 = rs.getString("answer4");
                String answer_correct = rs.getString("answer_correct");
                list.add(new QuestionDTO(questionId, subjectId, content, answer1, answer2, answer3, answer4, answer_correct,Date.valueOf(LocalDate.now()), true));
            }
            
            
        }catch(Exception e){
            LOG.error(e);
        }
        finally{
            closeConnection();
        }
        return list;
    }
    
    public int createQuiz(HistoryDTO dto){
        int quizId = 0;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO tblQuiz (user_email,subject_id,correct_answer,number_of_question,mark,createDate) "
                    + "VALUES (?,?,?,?,?,?)";
            stm = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, dto.getEmail());
            stm.setInt(2, dto.getSubjectId());
            stm.setInt(3, dto.getTotalCorrect());
            stm.setInt(4,dto.getTotalQuestion());
            stm.setFloat(5, dto.getMark());
            stm.setDate(6, dto.getCreateDate());
            
            rs = stm.executeQuery();
            if(rs.next()){
                quizId = rs.getInt(1);
            }
        }
        catch (Exception e) {
            LOG.error(e);
        }
        finally {
            closeConnection();
        }
        return quizId;
    }
    
    public void createDetail(int questionId, String studentAnswer,int quizId){
             
        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO tblDetail (quiz_id, question_id, student_answer) VALUES(?,?,?)";
            stm = conn.prepareStatement(sql);
            stm.setInt(1, quizId);
            stm.setInt(2, questionId);
            stm.setString(3,studentAnswer);
                    
            rs = stm.executeQuery();
            
        }
        catch (Exception e) {
            LOG.error(e);
        }
        finally {
            closeConnection();
        }
        
    }
    
}
