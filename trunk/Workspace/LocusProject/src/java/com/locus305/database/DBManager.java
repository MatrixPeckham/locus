/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.locus305.database;

import com.locus305.beans.AccountBean;
import com.locus305.beans.CircleBean;
import com.locus305.beans.UserBean;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author William Peckham
 */
public class DBManager {

    private static DBManager inst = null;
    private static final String connectionURL =
            "jdbc:mysql://mysql1.cs.sunysb.edu?"
            + "user=wpeckham&password=106690352";
    public static final String GENERAL_SELECT = "SELECT %s FROM wpeckham.%s WHERE %s";
    private Connection con = null;

    public static DBManager get() {
        if (inst == null) {
            inst = new DBManager();
        }
        return inst;
    }

    public DBManager() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            return;
        }
        try {
            con = DriverManager.getConnection(connectionURL);

        } catch (SQLException ex) {
            con = null;
        }
    }

    public ResultSet select(String what, String from, String where) {
        String query = format(GENERAL_SELECT, what, from, where);
        try {
            Statement stmt = con.createStatement();
            return stmt.executeQuery(query);
        } catch (SQLException ex) {
            return null;
        }
    }

    public String addUser(String email, String name, String password) {
        try {
            con.setAutoCommit(false);
            Statement stmt = con.createStatement();
            stmt.execute("INSERT INTO wpeckham.persons (display_name,email_address,password) VALUES(\"" + name + "\",\"" + email + "\",\"" + password + "\")", Statement.RETURN_GENERATED_KEYS);
            ResultSet keys = stmt.getGeneratedKeys();
            int userid = 0;
            while (keys.next()) {
                userid = keys.getInt(1);
            }

            stmt.execute("INSERT INTO wpeckham.accounts (Account_Creation_Date,state) VALUES(" + "CURDATE()" + "," + 0 + ")", Statement.RETURN_GENERATED_KEYS);
            keys = stmt.getGeneratedKeys();
            int accountid = 0;
            while (keys.next()) {
                accountid = keys.getInt(1);
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

    public void fillUserBean(UserBean ub, String username, String password) throws Exception {
        try {
            ResultSet rs = select("*", "persons", "display_name='" + username + "'");
            if (rs.next()) {
                String pass = rs.getString("password");
                if (!pass.equals(password)) {
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
                rs = select("preference", "user_preferences", "id=" + ub.getUserid());
                boolean first = true;
                while (rs.next()) {
                    if (!first) {
                        ub.setPreferences(ub.getPreferences() + ",");
                    }
                    ub.setPreferences(ub.getPreferences() + rs.getString("preference"));
                    first = false;
                }
            } else {
                throw new Exception("User Name does not exist");
            }
        } catch (SQLException ex) {
            throw new Exception("An Unknown Error Occurred");
        }

    }

    public boolean updateUserData(UserBean user) {
        String addr = user.getAddr();
        String city = user.getCity();
        String fname = user.getFname();
        String lname = user.getLname();
        String phone = user.getPhone();
        String prefe = user.getPreferences();
        int zip = user.getZip();
        int uid = user.getUserid();
        try {
            Statement stmt = con.createStatement();
            String sql = "update wpeckham.persons set "
                    + "address='" + addr + "', "
                    + "city='" + city + "', "
                    + "first_name='" + fname + "', "
                    + "last_name='" + lname + "', "
                    + "telephone='" + phone + "', "
                    + "zip_code=" + zip + " "
                    + "where ssn=" + uid;
            stmt.executeUpdate(sql);
            StringTokenizer prefs = new StringTokenizer(prefe, ",", false);
            ArrayList<String> preflist = new ArrayList<String>();
            while (prefs.hasMoreTokens()) {
                preflist.add(prefs.nextToken());
            }
            sql = "delete from wpeckham.user_preferences where id=" + uid;
            stmt.executeUpdate(sql); 
            for (String s : preflist) {
                sql = "insert into wpeckham.user_preferences (id, preference) values (" + uid + ",'" + s + "')";
                stmt.executeUpdate(sql);
            }
            return true;
        } catch (SQLException ex) {
            int i = 0;
            return false;
        }


    }

    public boolean updateUserAccounts(int uid, ArrayList<AccountBean> accts) {
        Statement stmt;
        try {
            stmt = con.createStatement();
        } catch (SQLException ex) {
            int i = 0;
            return false;
        }
        for (AccountBean b : accts) {
            if (b.getAccnum() != -1) {
                String sql = "update wpeckham.accounts set credit_card_number='" + b.getCcn() + "' where account_number=" + b.getAccnum();
                try {
                    stmt.executeUpdate(sql);
                } catch (SQLException ex) {
                    int i = 0;
                    return false;
                }
            } else {
                try {
                    con.setAutoCommit(false);
                    String sql = "insert into wpeckham.accounts (account_creation_date, credit_card_number, state) values ( CURDATE()," + b.getCcn() + ",0)";
                    stmt.execute(sql, Statement.RETURN_GENERATED_KEYS);
                    ResultSet rs = stmt.getGeneratedKeys();
                    rs.next();
                    int acctNum = rs.getInt(1);
                    sql = "insert into wpeckham.user_has_account (user_id,account_number) values (" + uid + "," + acctNum + ")";
                    stmt.execute(sql);

                    con.commit();
                    con.setAutoCommit(true);
                } catch (SQLException ex) {
                    try {
                        con.rollback();
                        con.setAutoCommit(true);
                        return false;
                    } catch (SQLException ex1) {
                        int i = 0;
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public ArrayList<AccountBean> getUserAccounts(String usr) {

        ArrayList<Integer> acctIds = new ArrayList<Integer>();
        ArrayList<AccountBean> beans = new ArrayList<AccountBean>();
        try {
            Statement stmt = con.createStatement();
            String query = format(GENERAL_SELECT, "ssn", "persons", "display_name='" + usr + "'");
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            int ssn = rs.getInt("ssn");
            query = format(GENERAL_SELECT, "account_number", "user_has_account", "user_id='" + ssn + "'");
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                acctIds.add(rs.getInt("account_number"));
            }
            for (int id : acctIds) {
                AccountBean b = new AccountBean();
                b.setAccnum(id);
                query = format(GENERAL_SELECT, "Credit_Card_Number", "accounts", "account_number=" + id);
                rs = stmt.executeQuery(query);
                rs.next();
                String card = rs.getString("credit_card_number");
                if (card == null) {
                    card = "";
                    b.setCcn(card);
                } else {
                    b.setCcn(card.substring(card.length() - 4));
                }
                beans.add(b);
            }
            return beans;
        } catch (SQLException e) {
            int i = 29;
        }

        return null;
    }

    public boolean contains(String table, String colName, String value) {
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

    public ArrayList<CircleBean> getCircles(){
        try {
            ResultSet rs = select("*", "circles", "1=1");
            ArrayList<CircleBean> list = new ArrayList<CircleBean>();
            while(rs.next()){
                CircleBean b = new CircleBean();
                b.setCatagory(rs.getString("catagory"));
                b.setName(rs.getString("circle_name"));
                b.setId(rs.getInt("circle_id"));
                b.setOwnerID(rs.getInt("owner_of_circle"));
                int id = b.getOwnerID();
                ResultSet own = select("display_name", "persons", "ssn="+id);
                own.next();
                b.setCatagory(own.getString(1));
                list.add(b);
            }
            return list;
        } catch (SQLException ex) {
            int i =0;
            return null;
        }
    }
    
    public static String format(String str, Object... args) {
        StringWriter writer = new StringWriter();
        PrintWriter out = new PrintWriter(writer);
        out.format(str, args);
        return writer.toString();
    }
}
