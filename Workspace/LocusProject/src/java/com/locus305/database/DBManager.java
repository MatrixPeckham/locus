/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.locus305.database;
import com.mysql.jdbc.ResultSetImpl;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author William Peckham
 */
public class DBManager {
    private static DBManager inst = null;
    private static final String connectionURL = 
            "jdbc:mysql://mysql1.cs.sunysb.edu?"+
            "user=wpeckham&password=106690352";
    public static final String GENERAL_SELECT = "SELECT %s FROM wpeckham.%s WHERE %s";
    private Connection con = null;
    public static DBManager get(){
        if(inst==null){
            inst=new DBManager();
        }
        return inst;
    }
    
    public DBManager(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            return;
        }
        try {
            con = DriverManager.getConnection(connectionURL);
            
        } catch (SQLException ex) {
            con=null;
        }
    }
    
    public ResultSet select(String what, String from, String where){
        String query = format(GENERAL_SELECT,what, from, where);
        try {
            Statement stmt = con.createStatement();
            return stmt.executeQuery(query);
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public String addUser(String email, String fname, String lname, String password){
        try {
            con.setAutoCommit(false);
            Statement stmt = con.createStatement();
            stmt.execute("INSERT INTO wpeckham.persons (last_name,first_name,email,password) VALUES("+lname+","+fname+","+email+","+password+")",Statement.RETURN_GENERATED_KEYS);
            ResultSet keys = stmt.getGeneratedKeys();
            int userid = 0;
            while(keys.next()){
                userid=keys.getInt(1);
            }
            
            stmt.execute("INSERT INTO wpeckham.accounts (Account_Creatoin_Date,state) VALUES("+"CURDATE()"+","+0+")",Statement.RETURN_GENERATED_KEYS);
            keys = stmt.getGeneratedKeys();
            int accountid = 0;
            while(keys.next()){
                accountid=keys.getInt(1);
            }
            
            stmt.executeUpdate("INSERT INTO wpeckham.users (user_id,account_creation_date,rating) VALUES (" + userid + ",CURDATE(),0)");
            
            stmt.executeUpdate("INSERT INTO wpeckham.user_has_account (user_id, account_number) VALUES (" + userid + "," + accountid + ")");
            
            con.commit();
            
            con.setAutoCommit(true);
        } catch (SQLException ex) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return ex.getMessage();
            } catch (SQLException ex1) {
                return ex.getMessage();
            }
        } finally {
            
        } 
        return "An Unknown Error Occured";
    }
    
    public boolean contains(String table, String colName, String value){
        String query = format(GENERAL_SELECT, "*", table, colName + "='" + value + "'");
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            return rs.next();
        } catch (SQLException ex) {
            String str = ex.toString();
            int i = 1;
        } catch (Exception e) { 
            String str = e.toString();
            int i = 1;
        }
        return false;
    }
    
    
    
    public static String format(String str, Object... args){
        StringWriter writer = new StringWriter();
        PrintWriter out = new PrintWriter(writer);
        out.format(str, args);
        return writer.toString();
    }
    
}
