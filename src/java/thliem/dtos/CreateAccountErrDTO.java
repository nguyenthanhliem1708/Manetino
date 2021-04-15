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
public class CreateAccountErrDTO {
    private String nameErr,emailErr,passwordErr;

    public CreateAccountErrDTO() {
    }

    public CreateAccountErrDTO(String nameErr, String emailErr, String passwordErr) {
        this.nameErr = nameErr;
        this.emailErr = emailErr;
        this.passwordErr = passwordErr;
    }

    public String getNameErr() {
        return nameErr;
    }

    public void setNameErr(String nameErr) {
        this.nameErr = nameErr;
    }

    public String getEmailErr() {
        return emailErr;
    }

    public void setEmailErr(String emailErr) {
        this.emailErr = emailErr;
    }

    public String getPasswordErr() {
        return passwordErr;
    }

    public void setPasswordErr(String passwordErr) {
        this.passwordErr = passwordErr;
    }
    
    
}
