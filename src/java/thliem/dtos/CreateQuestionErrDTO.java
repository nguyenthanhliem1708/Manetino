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
public class CreateQuestionErrDTO {

    private String questionContentErr, ans1Err, ans2Err, ans3Err, ans4Err, correctAnsErr;
    private String subjectIDErr,dupAnswerErr;

    public String getDupAnswerErr() {
        return dupAnswerErr;
    }

    public void setDupAnswerErr(String dupAnswerErr) {
        this.dupAnswerErr = dupAnswerErr;
    }

    public CreateQuestionErrDTO() {
    }

    public CreateQuestionErrDTO(String questionContentErr, String ans1Err, String ans2Err, String ans3Err, String ans4Err, String correctAnsErr, String subjectIDErr) {
        this.questionContentErr = questionContentErr;
        this.ans1Err = ans1Err;
        this.ans2Err = ans2Err;
        this.ans3Err = ans3Err;
        this.ans4Err = ans4Err;
        this.correctAnsErr = correctAnsErr;
        this.subjectIDErr = subjectIDErr;
    }

    public String getQuestionContentErr() {
        return questionContentErr;
    }

    public void setQuestionContentErr(String questionContentErr) {
        this.questionContentErr = questionContentErr;
    }

    public String getAns1Err() {
        return ans1Err;
    }

    public void setAns1Err(String ans1Err) {
        this.ans1Err = ans1Err;
    }

    public String getAns2Err() {
        return ans2Err;
    }

    public void setAns2Err(String ans2Err) {
        this.ans2Err = ans2Err;
    }

    public String getAns3Err() {
        return ans3Err;
    }

    public void setAns3Err(String ans3Err) {
        this.ans3Err = ans3Err;
    }

    public String getAns4Err() {
        return ans4Err;
    }

    public void setAns4Err(String ans4Err) {
        this.ans4Err = ans4Err;
    }

    public String getCorrectAnsErr() {
        return correctAnsErr;
    }

    public void setCorrectAnsErr(String correctAnsErr) {
        this.correctAnsErr = correctAnsErr;
    }

    public String getSubjectIDErr() {
        return subjectIDErr;
    }

    public void setSubjectIDErr(String subjectIDErr) {
        this.subjectIDErr = subjectIDErr;
    }

}
