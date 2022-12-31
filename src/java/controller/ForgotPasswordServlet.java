/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import entity.User;

/**
 *
 * @author Admin
 */
public class ForgotPasswordServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ForgotPasswordServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ForgotPasswordServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the
    // + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        UserDAO userDAO = new UserDAO();

        if (action.equalsIgnoreCase("checkUsername")) {
            String username = request.getParameter("forgotPassUsername");
            if (userDAO.checkValidUsername(username)) {
                User u = userDAO.getUser(username);
                request.setAttribute("usernametoset", u.getUsername());
                if (u.getPhone().length() > 0) {
                    request.setAttribute("phone", u.getPhone());
                    request.getRequestDispatcher("phoneemailtogetpass.jsp").forward(request, response);
                } else {
                    request.setAttribute("email", u.getEmail());
                    request.getRequestDispatcher("phoneemailtogetpass.jsp").forward(request, response);

                }
            } else {
                request.setAttribute("forgoterror", "Username is not valid");
                request.getRequestDispatcher("#forgotPassModal").forward(request, response);
            }

        } else if (action.equalsIgnoreCase("checkEmailPhone")) {
            String usernametoset = request.getParameter("usernametoset");
            // String usertocheck= request.getParameter("username");
            User us = userDAO.getUser(usernametoset);
            String email = request.getParameter("emailtocheck");
            String phone = request.getParameter("phonetocheck");
            int otpvalue = 0;
            HttpSession mySession = request.getSession();
            RequestDispatcher dispatcher = null;
            if (us.getEmail().length() > 0) {
                if (email.equalsIgnoreCase(us.getEmail())) {
                    // vao viec

                    Random ran = new Random();
                    otpvalue = ran.nextInt(1255650);
                    String to = email;

                    Properties props = new Properties();
                    props.put("mail.smtp.host", "smtp.gmail.com");
                    props.put("mail.smtp.socketFactory.port", "465");
                    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                    props.put("mail.smtp.auth", "true");
                    props.put("mail.smtp.port", "465");

                    Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("buzzthl107@gmail.com", "hrzkrblgrbuigvfs");// Put your email
                                                                                                 // id and
                                                                                                 // password here
                        }
                    });
                    // compose message
                    try {
                        MimeMessage message = new MimeMessage(session);
                        message.setFrom(new InternetAddress(email));// change accordingly
                        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                        message.setSubject("Hello");
                        message.setText("your OTP is: " + otpvalue);
                        // send message
                        Transport.send(message);
                        System.out.println("message sent successfully");
                    } catch (MessagingException e) {
                        throw new RuntimeException(e);
                    }
                    request.setAttribute("usernameToSetPass", us.getUsername());
                    dispatcher = request.getRequestDispatcher("otp.jsp");
                    mySession.setAttribute("otpvalue", otpvalue);
                    mySession.setAttribute("email", email);
                    dispatcher.forward(request, response);
                } else {
                    request.setAttribute("forgotmailerror", "Email is not valid");
                    request.getRequestDispatcher("phoneemailtogetpass.jsp").forward(request, response);
                }
            } else if (us.getPhone().length() > 0) {
                if (phone.equalsIgnoreCase(us.getPhone())) {
                    request.getRequestDispatcher("faill.jsp").forward(request, response);
                } else {
                    request.setAttribute("forgotphoneerror", "Phone is not valid");
                    request.getRequestDispatcher("phoneemailtogetpass.jsp").forward(request, response);
                }

            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
