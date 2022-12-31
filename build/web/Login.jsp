<%-- 
    Document   : Login
    Created on : Dec 18, 2022, 1:44:35 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css">
        <link rel="stylesheet" href="css/styles.css">
    </head>
    <body>
        <%
            Cookie[] listCookie = request.getCookies();
            String user = "";
            String pass = "";
            int co = 0;
            if (listCookie != null) {
                while (co < listCookie.length) {
                    if (listCookie[co].getName().equals("username")) {
                        user = listCookie[co].getValue();
                    }
                    if (listCookie[co].getName().equals("password")) {
                        pass = listCookie[co].getValue();
                    }
                    co++;
                }

            }
        %>

        <div class="box">
            <div class="inthe-box">
                <div class="form sign-in">
                    <form action="UserServlet?action=login" method="post">
                        <input type="hidden" id="errlogin" value="${errLogin}"/>
                        <script>
                            var errLogin = document.getElementById("errlogin");
                            if (errLogin !== null && errLogin.value) {
                                alert(errLogin.value);
                            }

                        </script>
                        <h2>Sign in</h2>
                        <div class="inputBox">
                            <input type="text" name="username" value="<%out.print(user);%>" required>
                            <span>Username</span>
                            <i></i>
                        </div>

                        <div class="inputBox">
                            <input type="password" name="password" value="<%out.print(pass);%>" required >
                            <!--                            autocomplete="off" readonly 
                                                               onfocus="this.removeAttribute('readonly');"-->
                            <span>Password</span>
                            <i></i>
                        </div>
                        <div class="remember">
                            <input type="checkbox" name="remember" id="remember">
                            <label for="remember">Remember me</label>
                        </div>
                        <div class="links">
                            <a href="#forgotPassModal" class="forgotPassBtn-link">Forgot password?</a>
                            <a href="#" class="signUpBtn-link">Sign up</a>
                        </div>

                        <input type="submit" value="Login">

                        <div class="social-platform">
                            <p>Or sign in with</p>
                            <div class="social-icons">
                                <a href="#"><i class="fa-brands fa-facebook-f"></i></a>
                                <a href="#"><i class="fa-brands fa-google"></i></a>
                                <a href="#"><i class="fa-brands fa-twitter"></i></a>
                            </div>
                        </div>
                    </form>
                </div>

                <div class="form sign-up">
                    <form action="">

                        <h2>Sign Up</h2>
                        <div class="inputBox2">
                            <input type="text" autocomplete="off" readonly 
                                   onfocus="this.removeAttribute('readonly');" required="required">
                            <span>Full Name</span>
                            <i></i>
                        </div>

                        <div class="inputBox2">
                            <input type="text" autocomplete="off" readonly 
                                   onfocus="this.removeAttribute('readonly');" required="required">
                            <span>Username</span>
                            <i></i>
                        </div>

                        <div class="inputBox2">
                            <input type="text" autocomplete="off" readonly 
                                   onfocus="this.removeAttribute('readonly');" required="required">
                            <span>Phone Number or Email</span>
                            <i></i>
                        </div>


                        <div class="inputBox2">
                            <input type="password" required="required" autocomplete="off" readonly 
                                   onfocus="this.removeAttribute('readonly');">
                            <span>Password</span>
                            <i></i>
                        </div>

                        <!-- <div class="inputBox2">
                            <input type="password" required="required" autocomplete="off" readonly 
                            onfocus="this.removeAttribute('readonly');">
                            <span>Confirm password</span>
                            <i></i>
                        </div> -->
                        <div class="remember">
                            <input type="checkbox" name="remember" id="remember">
                            <label for="remember">I agree to the terms & conditions</label>
                        </div>
                        <div class="links">
                            <p>Already have an account?</p>
                            <a href="#" class="signInBtn-link">Sign In</a>
                        </div>

                        <input type="submit" value="Sign Up">

                    </form>
                </div>

            </div>
        </div>
        <div class="overlay" id="forgotPassModal">
            <div class="modal">
                <svg xmlns="http://www.w3.org/2000/svg" width="100%" height="100%" viewBox="0 0 560 280"
                     preserveAspectRatio="true">
                <line id="svg_3" fill="none" stroke="#000000" stroke-width="2" x1="2.0" y1="2.0" x2="558" y2="2.0" />

                <line id="svg_4" fill="none" stroke="#000000" stroke-width="2" x1="558" y1="278" x2="558" y2="2.0" />

                <line id="svg_2" fill="none" stroke="#000000" stroke-width="2" x1="2.0" y1="278" x2="558" y2="278" />

                <line id="svg_5" fill="none" stroke="#000000" stroke-width="2" x1="2.0" y1="2.0" x2="2.0" y2="278" />
                </svg>
                <div class="modal-inner">
                    <a href="#" class="modal-close" title="Close Modal">X</a>
                    <h3>Forgot Password</h3>
                    <form action="ForgotPasswordServlet?action=checkUsername" style="display:flex;" method="post">

                        <div class="inputBox forgot-input">
                            <input type="text" name="forgotPassUsername" autocomplete="off" readonly 
                            onfocus="this.removeAttribute('readonly');">
                            <span>Enter your username</span>
                            <i></i>
                        </div>
                        <!-- button confirm -->
                        <div class="svg-wrapper">
                            <svg id="confirm-svg" height="40" width="150" xmlns="http://www.w3.org/2000/svg">
                                <rect id="shape" height="40" width="150" />
                                <div id="text">
                                  <a href="">
                                      <input id="forgot-button" type="submit" value="Confirm">

                                  </a>
                                </div>
                            </svg>
                          </div>
                    </form>
                    <p>Enter your account's username to define.</p>
                    <input type="hidden" name="forgoterror" value="${forgoterror}">
                    <script>
                        var forgoterror = document.getElementsByName("forgoterror")[0].value;
                        if (forgoterror.length > 0) {
                            alert("Username is not exist!");
                            // go to previous page after click ok delay 0.5s
                            setTimeout(function () {
                                window.history.back();
                            }, 500);
                            // window.history.back();
                        }
                    </script>
                </div>
            </div>

            <input type="hidden" id="forgotPassSuccessful" value="${forgotPassSuccessful}">
            <script>
                var forgotPassSuccessful = document.getElementById("forgotPassSuccessful").value;
                if (forgotPassSuccessful.length > 0) {
                    alert(forgotPassSuccessful);
                }
            </script>
        </div>

        <script src="js/script.js"></script>
        <script src="js/kinematics.js"></script>
    </body>
</html>
