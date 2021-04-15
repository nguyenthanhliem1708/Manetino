/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thliem.dtos;

/**
 *
 * @author LiemNguyen
 */
public class SubjectDTO {

    private int subjectId;
    private String subjectName;
    private boolean status;
    private int quantityLimit;
    private int timeLimit;

    public SubjectDTO() {
    }

    public SubjectDTO(int subjectId, String subjectName, boolean status, int quantityLimit, int timeLimit) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.status = status;
        this.quantityLimit = quantityLimit;
        this.timeLimit = timeLimit;
    }

    public int getQuantityLimit() {
        return quantityLimit;
    }

    public void setQuantityLimit(int quantityLimit) {
        this.quantityLimit = quantityLimit;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
