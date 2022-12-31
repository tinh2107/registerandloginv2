/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dbcontext.DBConnect;
import entity.User;

/**
 *
 * @author Admin
 */
public class UserDAO {
    DBConnect db = new DBConnect();
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public ArrayList<User> getAll() {
        ArrayList<User> list = new ArrayList<>();
        String sql = "SELECT * FROM Acc";
        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setFullname(rs.getString("name"));
                u.setEmail(rs.getString("email"));
                u.setPhone(rs.getString("phone"));
                list.add(u);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public boolean checkLogin(String username, String password) {
        String sql = "SELECT * FROM Acc WHERE username = ? AND password = ?";
        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean checkValidUsername(String username) {
        String sql = "SELECT * FROM Acc WHERE username = ?";
        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public User getUser(String username) {
        String sql = "SELECT * FROM Acc WHERE username = ?";
        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setFullname(rs.getString("name"));
                u.setEmail(rs.getString("email"));
                u.setPhone(rs.getString("phone"));
                return u;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    // check if user use email or phone to login
    public String checkEmailOrPhone(String username){
        String sql = "SELECT * FROM Acc WHERE username = ?";
        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                if(rs.getString("email") != null&&rs.getString("email").length() > 0){
                    return rs.getString("email");
                }else{
                    return rs.getString("phone");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public void resetPass(String username, String password) {
        String sql = "UPDATE Acc SET password = ? WHERE username = ?";
        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, password);
            ps.setString(2, username);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        UserDAO dao = new UserDAO();

        // for (User u : dao.getAll()) {
        //     System.out.println(u);
        // }

        // System.out.println(dao.checkLogin("vuong1", "abcd1234"));
        // System.out.println(dao.checkValidUsername("vuong"));

        System.out.println(dao.checkEmailOrPhone("vuong2"));
        System.out.println("==================================");
        System.out.println(dao.checkEmailOrPhone("vuong1"));
    }
}
