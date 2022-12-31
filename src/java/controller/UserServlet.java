/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
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
public class UserServlet extends HttpServlet {

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
            out.println("<title>Servlet UserServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserServlet at " + request.getContextPath() + "</h1>");
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
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        if (action == null) {
            User user = checkCookie(request);
            if (user != null) {
                UserDAO dao = new UserDAO();
                if (dao.checkLogin(user.getUsername(), user.getPassword())) {
                    session.setAttribute("username", user.getUsername());
                    request.setAttribute("user", dao.getUser(user.getUsername()));
                    request.getRequestDispatcher("home-page.jsp").forward(request, response);
                } else {
                    request.setAttribute("errLogin", "Wrong username or password!");
                    request.getRequestDispatcher("Login.jsp").forward(request, response);
                }
            }
        } else if (action.equalsIgnoreCase("logout")) {
            session.removeAttribute("username");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("Login.jsp").forward(request, response);
        }
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
        UserDAO dao = new UserDAO();
        HttpSession session = request.getSession();
        if (action.equalsIgnoreCase("login")) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String remember = request.getParameter("remember");
            Cookie ckUsername = new Cookie("username", username);
            Cookie ckPassword = new Cookie("password", password);
            ckUsername.setMaxAge(60 * 60 * 24 * 30);
            if (dao.checkLogin(username, password)) {
                session.setAttribute("username", username);
                if (remember != null) {
                    ckUsername.setMaxAge(60 * 60 * 24 * 30);
                    ckPassword.setMaxAge(60 * 60 * 24 * 30);
                } else {
                    ckUsername.setMaxAge(0);
                    ckPassword.setMaxAge(0);
                }
                response.addCookie(ckUsername);
                response.addCookie(ckPassword);
                request.setAttribute("user", dao.getUser(username));
                request.getRequestDispatcher("home-page.jsp").forward(request, response);
            } else {
                request.setAttribute("errLogin", "Wrong username or password!");
                request.setAttribute("errusername", username);
                request.setAttribute("errpassword", password);
                request.getRequestDispatcher("Login.jsp").forward(request, response);
            }
            // request.getRequestDispatcher("home-page.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("faill.jsp").forward(request, response);
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

    private User checkCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        User user = null;
        if (cookies == null) {
            return null;
        } else {
            String username = "", password = "";
            for (Cookie ck : cookies) {
                if (ck.getName().equalsIgnoreCase("username")) {
                    username = ck.getValue();
                }
                if (ck.getName().equalsIgnoreCase("password")) {
                    password = ck.getValue();
                }
            }
            if (!username.isEmpty() && !password.isEmpty()) {
                user = new User(username, password);
            }
        }
        return user;
    }
}
