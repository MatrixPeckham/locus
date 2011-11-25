/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.locus305.database;
import com.locus305.beans.AccountBean;
import com.locus305.beans.UserBean;
import com.mysql.jdbc.ResultSetImpl;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.*;
import java.util.ArrayList;
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
    
    public String addUser(String email, String name, String password){
        try {
            con.setAutoCommit(false);
            Statement stmt = con.createStatement();
            stmt.execute("INSERT INTO wpeckham.persons (display_name,email_address,password) VALUES(\""+name+"\",\""+email+"\",\""+password+"\")",Statement.RETURN_GENERATED_KEYS);
            ResultSet keys = stmt.getGeneratedKeys();
            int userid = 0;
            while(keys.next()){
                userid=keys.getInt(1);
            }
            
            stmt.execute("INSERT INTO wpeckham.accounts (Account_Creation_Date,state) VALUES("+"CURDATE()"+","+0+")",Statement.RETURN_GENERATED_KEYS);
            keys = stmt.getGeneratedKeys();
            int accountid = 0;
            while(keys.next()){
                accountid=keys.getInt(1);
            }
            
            stmt.executeUpdate("INSERT INTO wpeckham.users (user_id,account_creation_date,rating) VALUES (" + userid + ",CURDATE(),0)");
            
            stmt.executeUpdate("INSERT INTO wpeckham.user_has_account (user_id, account_number) VALUES (" + userid + "," + accountid + ")");
            
            con.commit();
            
            con.setAutoCommit(true);
            return "OK";
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
    }
    
    public void fillUserBean(UserBean ub, String username, String password) throws Exception{
        try {
            ResultSet rs = select("*", "persons", "display_name='"+username+"'");
            if(rs.next()){
                String pass = rs.getString("password");
                if(!pass.equals(password)){
                    throw new Exception("Incorrect Password");
                }
                ub.setAddr(rs.getString("address"));
                ub.setCity(rs.getString("city"));
                ub.setFname(rs.getString("first_name"));
                ub.setLname(rs.getString("last_name"));
                ub.setPhone(rs.getString("telephone"));
                ub.setUsername(username);
                ub.setZip(rs.getInt("zip_code"));
                ub.setUserid(rs.getInt("SSN"));
            } else {
                throw new Exception("User Name does not exist");
            }
        } catch (SQLException ex) {
            throw new Exception("An Unknown Error Occurred");
        }
        
    }

    public boolean updateUserData(UserBean user){
        String addr = user.getAddr();
        String city = user.getCity();
        String fname= user.getFname();
        String lname= user.getLname();
        String phone= user.getPhone();
        int zip  = user.getZip();
        int uid = user.getUserid();
        try {
            Statement stmt = con.createStatement();
            String sql = "update wpeckham.persons set " +
                    "address=\"" + addr+ "\" "+
                    "city=\"" + city+ "\" "+
                    "first_name=\"" + fname+ "\" "+
                    "last_name=\"" + lname+ "\" "+
                    "telephone=\"" + phone+ "\" "+
                    "zip_code=" + zip+ " "+
                    "where ssn="+uid;
            stmt.executeUpdate(sql);
            return true;
        } catch (SQLException ex) {
            return false;
        }
        
        
    }
    
    public ArrayList<AccountBean> getUserAccounts(String usr){
        
        ArrayList<Integer> acctIds = new ArrayList<Integer>();
        ArrayList<AccountBean> beans = new ArrayList<AccountBean>();
        try{
            Statement stmt = con.createStatement();
            String query = format(GENERAL_SELECT,"ssn","persons","display_name='"+usr+"'");
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            int ssn = rs.getInt("ssn");
            query = format(GENERAL_SELECT, "account_number","user_has_account","user_id='"+ssn+"'");
            rs=stmt.executeQuery(query);
            while(rs.next()){
                acctIds.add(rs.getInt("account_number"));
            }
            for(int id : acctIds){
                AccountBean b = new AccountBean();
                b.setAccnum(id);
                query=format(GENERAL_SELECT,"Credit_Card_Number","accounts","account_number="+id);
                rs=stmt.executeQuery(query);
                rs.next();
                String card = rs.getString("credit_card_number");
                if(card==null) {
                    card="";
                    b.setCcn(card);
                } else {
                    b.setCcn(card.substring(card.length()-4));
                }
                beans.add(b);
            }
            return beans;
        }catch(SQLException e){
            int i = 29;
        }
        
        return null;
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
