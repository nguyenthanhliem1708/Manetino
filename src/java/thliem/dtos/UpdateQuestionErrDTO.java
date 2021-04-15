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
public class UpdateQuestionErrDTO {
    private String questionContentErr,dupAnswerErr,correctAnsErr,dupQuestionErr;

    public UpdateQuestionErrDTO() {
    }

    public UpdateQuestionErrDTO(String questionContentErr, String dupAnswerErr, String correctAnsErr, String dupQuestionErr) {
        this.questionContentErr = questionContentErr;
        this.dupAnswerErr = dupAnswerErr;
        this.correctAnsErr = correctAnsErr;
        this.dupQuestionErr = dupQuestionErr;
    }

    public String getQuestionContentErr() {
        return questionContentErr;
    }

    public void setQuestionContentErr(String questionContentErr) {
        this.questionContentErr = questionContentErr;
    }

    public String getDupAnswerErr() {
        return dupAnswerErr;
    }

    public void setDupAnswerErr(String dupAnswerErr) {
        this.dupAnswerErr = dupAnswerErr;
    }

    public String getCorrectAnsErr() {
        return correctAnsErr;
    }

    public void setCorrectAnsErr(String correctAnsErr) {
        this.correctAnsErr = correctAnsErr;
    }

    public String getDupQuestionErr() {
        return dupQuestionErr;
    }

    public void setDupQuestionErr(String dupQuestionErr) {
        this.dupQuestionErr = dupQuestionErr;
    }
    
    
}
