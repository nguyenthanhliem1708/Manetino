<%-- 
    Document   : register
    Created on : Jan 25, 2021, 7:47:48 PM
    Author     : LiemNguyen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<!------ Include the above in your HEAD tag ---------->

<!DOCTYPE html>
<html lang="en">
    <head> 
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.css">

        <!-- Website CSS style -->
        <link rel="stylesheet" type="text/css" href="assets/css/main.css">

        <!-- Website Font style -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.1/css/font-awesome.min.css">

        <!-- Google Fonts -->
        <link href='https://fonts.googleapis.com/css?family=Passion+One' rel='stylesheet' type='text/css'>
        <link href='https://fonts.googleapis.com/css?family=Oxygen' rel='stylesheet' type='text/css'>

        <title>Register Page</title>
    </head>
    
    <body>
        
        <c:set var="errDTO" value="${requestScope.REGISTER_ERROR}"/>
        
        
        
        <div class="container">
            <div class="row main">
                <div class="panel-heading">
                    <div class="panel-title text-center">
                        <h1 class="title">Quizkipedia</h1>
                        <hr />
                    </div>
                </div> 
                <div class="main-login main-center">
                    <form class="form-horizontal" method="POST" action="CreateAccountController">

                        <div class="form-group">
                            <label for="name" class="cols-sm-2 control-label">Your Name</label>
                            <div class="cols-sm-10">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-user fa" aria-hidden="true"></i></span>
                                    <input type="text" class="form-control" required name="name" pattern="[a-zA-Z ]{0,50}" minlength="0" maxlength="50" title="Please enter your full name" id="name"  placeholder="Enter your Name"/>
                                </div>
                            </div>
                        </div>
                        <div id="errMess">
                            <span id="errorMessage">${errDTO.nameErr}</span>
                        </div>
                        
                        
                        <div class="form-group">
                            <label for="email" class="cols-sm-2 control-label">Your Email</label>
                            <div class="cols-sm-10">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-envelope fa" aria-hidden="true"></i></span>
                                    <input type="text" class="form-control" required name="email" id="email"  pattern="^[A-Za-z]([A-Za-z0-9._]{5,15})+@fpt.edu.vn$" title="Please enter xxx@fpt.edu.vn email"  placeholder="Enter your Email"/>
                                </div>
                            </div>
                        </div>
                        <div id="errMess">
                            <span id="errorMessage">${errDTO.emailErr}</span>
                        </div>


                        <div class="form-group">
                            <label for="password" class="cols-sm-2 control-label">Password</label>
                            <div class="cols-sm-10">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span>
                                    <input type="password" class="form-control" required onkeyup="validate();" minlength="6" maxlength="16" name="password" id="password"  placeholder="Enter your Password"/>
                                </div>
                            </div>
                        </div>
                        <div id="errMess">
                            <span id="errorMessage">${errDTO.passwordErr}</span>
                        </div>
                        
                        
                        <div class="form-group">
                            <label for="confirm" class="cols-sm-2 control-label">Confirm Password</label>
                            <div class="cols-sm-10">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span>
                                    <input type="password" class="form-control" name="confirm" id="confirm" onkeyup="validate();"  placeholder="Confirm your Password"/>
                                </div>
                            </div>
                        </div>

                        
                        
                        <div id="confirmMess">
                            <span id="message"></span>
                        </div>

                        <div class="form-group ">
                            <button type="submit" id="submit" class="btn btn-primary btn-lg btn-block login-button" 
                                    onclick="return confirm('Do you wish to continue register this account ?');">Register</button>
                        </div>
                        <div class="login-register">
                            <a href="login.jsp">Login</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <script type="text/javascript" src="assets/js/bootstrap.js"></script>
    </body>
</html>

<style> 


    /*
     * General styles
     */

    body, html{
        height: 100%;
        background-repeat: no-repeat;
        background-color: #d3d3d3;
        font-family: 'Oxygen', sans-serif;
    }

    .main{
        margin-top: 70px;
    }

    h1.title { 
        font-size: 50px;
        font-family: 'Passion One', cursive; 
        font-weight: 400; 
    }

    hr{
        width: 10%;
        color: #fff;
    }

    .form-group{
        margin-bottom: 15px;
    }

    label{
        margin-bottom: 15px;
    }

    input,
    input::-webkit-input-placeholder {
        font-size: 11px;
        padding-top: 3px;
    }

    .main-login{
        background-color: #fff;
        /* shadows and rounded borders */
        -moz-border-radius: 2px;
        -webkit-border-radius: 2px;
        border-radius: 2px;
        -moz-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
        -webkit-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
        box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);

    }

    .main-center{
        margin-top: 30px;
        margin: 0 auto;
        max-width: 330px;
        padding: 40px 40px;

    }

    .login-button{
        margin-top: 5px;
    }

    .login-register{
        font-size: 11px;
        text-align: center;
    }

</style>

<script>
    function validate() {
        var pass = document.getElementById('password').value;
        var conf = document.getElementById('confirm').value;
        if (pass === conf) {
            if (pass != '') {
                document.getElementById('message').style.color = 'green';
                document.getElementById('message').style.fontFamily = "sans-serif";
                document.getElementById("message").innerHTML = 'Match';
                document.getElementById('submit').disabled = false;
            } else {
                document.getElementById("message").innerHTML = '';
            }
        } else {
            document.getElementById('message').style.fontFamily = "sans-serif";
            document.getElementById('message').style.color = 'red';
            document.getElementById("message").innerHTML = '*Not Match';
            document.getElementById('submit').disabled = true;
        }
    }
</script>