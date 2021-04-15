/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thliem.dtos;

import java.util.Date;
import java.util.List;

/**
 *
 * @author LiemNguyen
 */
public class QuizDTO {
    private Date startTime;
    private Date endTime;
    private int subjectId;
    private List<QuestionDTO> list;

    public QuizDTO() {
    }

    public QuizDTO(Date startTime, Date endTime, int subjectId, List<QuestionDTO> list) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.subjectId = subjectId;
        this.list = list;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public List<QuestionDTO> getList() {
        return list;
    }

    public void setList(List<QuestionDTO> list) {
        this.list = list;
    }

    
    
}
