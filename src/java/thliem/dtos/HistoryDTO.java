/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thliem.dtos;

import java.sql.Date;

/**
 *
 * @author LiemNguyen
 */
public class HistoryDTO {

    private int quizId;
    private String email;
    private int subjectId;
    private int totalQuestion;
    private int totalCorrect;
    private float mark;
    private Date createDate;

    public HistoryDTO() {
    }

    public HistoryDTO(int quizId, String email, int subjectId, int totalQuestion, int totalCorrect, float mark, Date createDate) {
        this.quizId = quizId;
        this.email = email;
        this.subjectId = subjectId;
        this.totalQuestion = totalQuestion;
        this.totalCorrect = totalCorrect;
        this.mark = mark;
        this.createDate = createDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getTotalQuestion() {
        return totalQuestion;
    }

    public void setTotalQuestion(int totalQuestion) {
        this.totalQuestion = totalQuestion;
    }

    public int getTotalCorrect() {
        return totalCorrect;
    }

    public void setTotalCorrect(int totalCorrect) {
        this.totalCorrect = totalCorrect;
    }

    public float getMark() {
        return mark;
    }

    public void setMark(float mark) {
        this.mark = mark;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}
