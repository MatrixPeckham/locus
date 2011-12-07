/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.locus305.database;

import com.locus305.beans.AccountBean;
import com.locus305.beans.AdBean;
import com.locus305.beans.BestSellerBean;
import com.locus305.beans.CircleBean;
import com.locus305.beans.CommentBean;
import com.locus305.beans.EmployeeBean;
import com.locus305.beans.MessageBean;
import com.locus305.beans.PostBean;
import com.locus305.beans.TransactionBean;
import com.locus305.beans.UserBean;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.*;
import java.util.Date;
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
        if (inst == null || inst.con==null) {
            inst = new DBManager();
        } 
        try {
            if (!inst.con.isValid(0)) {
                inst = new DBManager();
            }
        } catch (SQLException ex) {
            inst = new DBManager();
        }

        return inst;
    }

    private DBManager() {
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
            int i = 0;
            return null;
        }
    }

    public ResultSet select(String what, String from, String where, String orderby) {
        String query = format(GENERAL_SELECT, what, from, where);
        query = query + " order by " + orderby + " asc";
        try {
            Statement stmt = con.createStatement();
            return stmt.executeQuery(query);
        } catch (SQLException ex) {
            int i = 0;
            return null;
        }
    }

    public int addUser(String email, String name, String password) {
        try {
            con.setAutoCommit(false);
            Statement stmt = con.createStatement();
            stmt.execute("INSERT INTO wpeckham.persons (display_name,email_address,password) VALUES(\"" + name + "\",\"" + email + "\",\"" + password + "\")", Statement.RETURN_GENERATED_KEYS);
            ResultSet keys = stmt.getGeneratedKeys();
            int userid = 0;
            while (keys.next()) {
                userid = keys.getInt(1);
            }

            stmt.execute("INSERT INTO wpeckham.accounts (Account_Creation_Date,state) VALUES(" + "now()" + "," + 0 + ")", Statement.RETURN_GENERATED_KEYS);
            keys = stmt.getGeneratedKeys();
            int accountid = 0;
            while (keys.next()) {
                accountid = keys.getInt(1);
            }

            stmt.executeUpdate("INSERT INTO wpeckham.users (user_id,account_creation_date,rating) VALUES (" + userid + ",now(),0)");

            stmt.executeUpdate("INSERT INTO wpeckham.user_has_account (user_id, account_number) VALUES (" + userid + "," + accountid + ")");

            con.commit();

            con.setAutoCommit(true);
            return userid;
        } catch (SQLException ex) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return -1;
            } catch (SQLException ex1) {
                return -1;
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
                ub.setState(rs.getString("state"));
                ub.setUsername(username);
                ub.setZip(rs.getInt("zip_code"));
                ub.setUserid(rs.getInt("SSN"));
                rs = select("preference", "user_preferences", "id=" + ub.getUserid());
                ub.setPreferences("");
                boolean first = true;
                while (rs.next()) {
                    ub.setPreferences(ub.getPreferences() + (first ? "" : ",") + rs.getString("preference"));
                    first = false;
                }
                ub.setType(0);
                if (contains("employees", "ssn", ub.getUserid() + "")) {
                    ub.setType(1);
                }
                if (contains("managers", "ssn", ub.getUserid() + "")) {
                    ub.setType(2);
                }
            } else {
                throw new Exception("User Name does not exist");
            }
        } catch (SQLException ex) {
            throw new Exception("An Unknown Error Occurred");
        }

    }

    public void fillUserBean(UserBean ub, String username) {
        try {
            ResultSet rs = select("*", "persons", "display_name='" + username + "'");
            if (rs.next()) {
                ub.setCity(rs.getString("city"));
                ub.setFname(rs.getString("first_name"));
                ub.setLname(rs.getString("last_name"));
                ub.setState(rs.getString("state"));
                ub.setAddr(rs.getString("address"));
                ub.setZip(rs.getInt("zip_code"));
                ub.setPhone(rs.getString("telephone"));
                ub.setUsername(username); 
                ub.setUserid(rs.getInt("SSN"));
                rs = select("preference", "user_preferences", "id=" + ub.getUserid());
                ub.setPreferences("");
                boolean first = true;
                while (rs.next()) {
                    ub.setPreferences(ub.getPreferences() + (first ? "" : ",") + rs.getString("preference"));
                    first = false;
                }
                ub.setType(0);
                if (contains("employees", "ssn", ub.getUserid() + "")) {
                    ub.setType(1);
                }
                if (contains("managers", "ssn", ub.getUserid() + "")) {
                    ub.setType(2);
                }
            }
        } catch (SQLException ex) {
        }

    }

    public boolean updateUserData(UserBean user) {
        String addr = user.getAddr();
        String city = user.getCity();
        String state = user.getState();
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
                    + "state='" + state + "', "
                    + "first_name='" + fname + "', "
                    + "last_name='" + lname + "', "
                    + "telephone='" + phone + "', "
                    + "zip_code=" + zip + " "
                    + "where ssn=" + uid;
            stmt.executeUpdate(sql);
            if (prefe != null) {
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
                    String sql = "insert into wpeckham.accounts (account_creation_date, credit_card_number, state) values ( now()," + b.getCcn() + ",0)";
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

    public ArrayList<CircleBean> getCircles() {
        try {
            ResultSet rs = select("*", "circles", "1=1");
            ArrayList<CircleBean> list = new ArrayList<CircleBean>();
            while (rs.next()) {
                CircleBean b = new CircleBean();
                b.setCatagory(rs.getString("catagory"));
                b.setName(rs.getString("circle_name"));
                b.setId(rs.getInt("circle_id"));
                b.setOwnerID(rs.getInt("owner_of_circle"));
                int id = b.getOwnerID();
                ResultSet own = select("display_name", "persons", "ssn=" + id);
                own.next();
                b.setOwnerName(own.getString(1));
                b.setPub(rs.getBoolean("public"));
                list.add(b);
            }
            return list;
        } catch (SQLException ex) {
            int i = 0;
            return null;
        }
    }

    public CircleBean getCircle(int circle) {
        try {
            ResultSet rs = select("*", "circles", "circle_id=" + circle);
            rs.next();
            CircleBean b = new CircleBean();
            b.setCatagory(rs.getString("catagory"));
            b.setName(rs.getString("circle_name"));
            b.setId(rs.getInt("circle_id"));
            b.setOwnerID(rs.getInt("owner_of_circle"));
            int id = b.getOwnerID();
            ResultSet own = select("display_name", "persons", "ssn=" + id);
            own.next();
            b.setOwnerName(own.getString(1));
            b.setPub(rs.getBoolean("public"));
            return b;
        } catch (SQLException ex) {
            int i = 0;
            return null;
        }
    }

    public ArrayList<PostBean> getPosts(int circle) {
        try {
            ArrayList<PostBean> list = new ArrayList<PostBean>();
            ResultSet posts = select("*", "posts", "circle=" + circle, "_date");
            while (posts.next()) {
                PostBean b = new PostBean();
                b.setAuthor(posts.getInt("author"));
                ResultSet auth = select("display_name", "persons", "ssn=" + b.getAuthor());
                auth.next();
                b.setName(auth.getString(1));
                b.setCircle(posts.getInt("circle"));
                b.setContent(posts.getString("content"));
                b.setDate(posts.getDate("_date"));
                b.setId(posts.getInt("post_id"));
                ResultSet likes = select("count(*)", "user_likes_post", "post_id=" + b.getId());
                likes.next();
                b.setLikes(likes.getInt(1));
                list.add(b);
            }

            return list;
        } catch (SQLException e) {
            int i = 0;
            return null;
        }
    }

    public ArrayList<CommentBean> getComments(int post) {
        try {
            ArrayList<CommentBean> list = new ArrayList<CommentBean>();
            ResultSet comms = select("*", "comments", "post=" + post, "_date");
            while (comms.next()) {
                CommentBean b = new CommentBean();
                b.setAuthor(comms.getInt("author"));
                ResultSet auth = select("display_name", "persons", "ssn=" + b.getAuthor());
                auth.next();
                b.setAuthorName(auth.getString(1));
                b.setContent(comms.getString("content"));
                b.setDate(comms.getDate("_date"));
                b.setId(comms.getInt("comment_id"));
                b.setPost(comms.getInt("post"));
                ResultSet likes = select("count(*)", "user_likes_comment", "comment_id=" + b.getId());
                likes.next();
                b.setLikes(likes.getInt(1));
                list.add(b);
            }
            return list;
        } catch (SQLException e) {
            int i = 0;
            return null;
        }
    }

    public static String format(String str, Object... args) {
        StringWriter writer = new StringWriter();
        PrintWriter out = new PrintWriter(writer);
        out.format(str, args);
        return writer.toString();
    }

    public boolean userLikesPost(int userid, int post) {
        ResultSet rs = select("*", "user_likes_post", "post_id=" + post + " and user_id=" + userid);
        try {
            return rs.next();
        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean userLikesComment(int userid, int comment) {
        ResultSet rs = select("*", "user_likes_comment", "comment_id=" + comment + " and user_id=" + userid);
        try {
            return rs.next();
        } catch (SQLException ex) {
            return false;
        }
    }

    public void likePost(int userid, int post) {
        try {
            String sql = "insert into wpeckham.user_likes_post (user_id,post_id) values (" + userid + "," + post + ")";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            int i = 0;
        }

    }

    public void unlikePost(int userid, int post) {
        try {
            String sql = "delete from wpeckham.user_likes_post where user_id=" + userid + " and post_id=" + post;
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            int i = 0;
        }
    }

    public void likeComment(int userid, int comment) {
        try {
            String sql = "insert into wpeckham.user_likes_comment (user_id,comment_id) values (" + userid + "," + comment + ")";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            int i = 0;
        }

    }

    public void unlikeComment(int userid, int comment) {
        try {
            String sql = "delete from wpeckham.user_likes_comment where user_id=" + userid + " and comment_id=" + comment;
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            int i = 0;
        }
    }

    public ArrayList<MessageBean> getMessages(int userid) {
        try {
            ArrayList<MessageBean> list = new ArrayList<MessageBean>();
            ResultSet rs = select("*", "messages", "receiver=" + userid, "_date");
            while (rs.next()) {
                MessageBean b = new MessageBean();
                b.setContent(rs.getString("content"));
                b.setDate(rs.getDate("_date"));
                b.setId(rs.getInt("message_id"));
                b.setReceiver(rs.getInt("receiver"));
                b.setSender(rs.getInt("sender"));
                b.setSubject(rs.getString("subject"));
                ResultSet send = select("display_name", "persons", "ssn=" + b.getSender());
                send.next();
                b.setSendername(send.getString(1));
                ResultSet rece = select("display_name", "persons", "ssn=" + b.getReceiver());
                rece.next();
                b.setReceivername(rece.getString(1));
                list.add(b);
            }
            return list;
        } catch (SQLException ex) {
            return null;
        }

    }

    public boolean removeMessage(int id) {
        try {
            String sql = "delete from wpeckham.messages where Message_Id =" + id;
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean sendMessage(MessageBean b) {
        try {
            String sql = "insert into wpeckham.messages (sender,receiver,subject,content,_date) values (" + b.getSender() + "," + b.getReceiver() + ",\"" + b.getSubject() + "\",\"" + b.getContent() + "\",now())";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public void addPost(PostBean b) {
        try {
            String sql = "insert into wpeckham.posts (circle,content,author,_date) values (" + b.getCircle() + ",\"" + b.getContent() + "\"," + b.getAuthor() + ",now())";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            int i = 0;
        }
    }

    public void addComment(CommentBean b) {
        try {
            String sql = "insert into wpeckham.comments (post,content,author,_date) values (" + b.getPost() + ",\"" + b.getContent() + "\"," + b.getAuthor() + ",now())";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            int i = 0;
        }
    }

    public void removePost(int post) {
        try {
            String sql = "delete from wpeckham.posts where post_id=" + post;
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            int i = 0;
        }

    }

    public void removeComment(int comment) {
        try {
            String sql = "delete from wpeckham.comments where comment_id=" + comment;
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            int i = 0;
        }

    }

    public int getUserIDFromName(String name) {
        ResultSet rs = select("ssn", "persons", "display_name=\"" + name + "\"");
        try {
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return -1;
            }
        } catch (SQLException ex) {
            int i = 0;
            return -1;
        }
    }

    public ArrayList<CircleBean> getCircles(String user) {
        try {
            ArrayList<CircleBean> list = getCircles();
            int id = getUserIDFromName(user);
            ResultSet rs = select("circleid", "circle_memberships", "customerid=\"" + id + "\"");
            ArrayList<CircleBean> ret = new ArrayList<CircleBean>();
            while (rs.next()) {
                for (CircleBean b : list) {
                    if (b.getId() == rs.getInt(1)) {
                        ret.add(b);
                        break;
                    }
                }
            }
            return ret;
        } catch (SQLException ex) {
            int i = 0;
            return null;
        }
    }

    public ArrayList<CircleBean> getOwnedCircles(int user) {
        ArrayList<CircleBean> list = new ArrayList<CircleBean>();
        ResultSet rs = select("*", "circles", "owner_of_circle=" + user);
        try {
            while (rs.next()) {
                CircleBean b = new CircleBean();
                b.setCatagory(rs.getString("catagory"));
                b.setName(rs.getString("circle_name"));
                b.setId(rs.getInt("circle_id"));
                b.setOwnerID(rs.getInt("owner_of_circle"));
                b.setPub(rs.getBoolean("public"));
                int id = b.getOwnerID();
                ResultSet own = select("display_name", "persons", "ssn=" + id);
                own.next();
                b.setOwnerName(own.getString(1));
                list.add(b);
            }
        } catch (SQLException ex) {
            int i = 0;
        }

        return list;
    }

    public void updatePost(PostBean b) {
        try {
            String sql = "update wpeckham.posts set _date=now(), content=\"" + b.getContent() + "\" where post_id=" + b.getId();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            int i = 0;
        }
    }

    public void updateComment(CommentBean b) {
        try {
            String sql = "update wpeckham.comments set _date=now(), content=\"" + b.getContent() + "\" where comment_id=" + b.getId();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            int i = 0;
        }
    }

    public static boolean isMember(int uid, int circle) {
        ResultSet rs = get().select("*", "circle_memberships", "customerid=" + uid + " and circleid=" + circle);
        try {
            return rs.next();
        } catch (SQLException ex) {
            int i = 0;
            return false;
        }
    }

    public String joinCircle(int uid, int circle) {
        try {
            CircleBean b = getCircle(circle);
            String uname = getUserNameFromID(uid);
            if (b.isPub()) {
                String sql = "insert into wpeckham.circle_memberships (customerid,circleid) values (" + uid + "," + circle + ")";
                Statement stmt = con.createStatement();
                stmt.executeUpdate(sql);
                return "You Joined " + b.getName() + " successfully.";
            } else {
                MessageBean m = new MessageBean();
                String userlink = "<a href=\\\"javascript:void(0);\\\" onclick=\\\"changePage('JSPChunks/Profile.jsp?user=" + uname + "')\\\">" + uname + "</a>";
                String circlelink = "<a href=\\\"javascript:void(0);\\\" onclick=\\\"changePage('JSPChunks/ViewCircle.jsp?circle=" + b.getId() + "')\\\">" + b.getName() + "</a>";
                String acceptlink = "<a href=\\\"javascript:void(0);\\\" onclick=\\\"acceptMember(" + uid + "," + circle + ")\\\">" + "Accept" + "</a>";
                String declinelink = "<a href=\\\"javascript:void(0);\\\" onclick=\\\"declineMember(" + uid + "," + circle + ")\\\">" + "Decline" + "</a>";
                String message = userlink + " has requested to be in your circle: " + circlelink + " <br /> You can " + acceptlink + " or " + declinelink;
                m.setContent(message);
                m.setSubject("You have a Circle Membership Request");
                m.setReceiver(b.getOwnerID());
                m.setSender(0);
                sendMessage(m);
                return "You have requested to join " + b.getName() + ".";
            }


        } catch (SQLException e) {
            int i = 0;
        }
        return "ERROR";
    }

    public void unjoinCircle(int uid, int circle) {
        try {
            Statement stmt = con.createStatement();
            String sql = "delete from wpeckham.circle_memberships where circleid=" + circle + " and customerid=" + uid;
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            int i = 0;
        }
    }

    public void acceptUser(int uid, int circle) {
        try {
            CircleBean b = getCircle(circle);
            String sql = "insert into wpeckham.circle_memberships (customerid,circleid) values (" + uid + "," + circle + ")";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
            String circlelink = "<a href=\\\"javascript:void(0);\\\" onclick=\\\"changePage('JSPChunks/ViewCircle.jsp?circle=" + b.getId() + "')\\\">" + b.getName() + "</a>";
            MessageBean m = new MessageBean();
            m.setSubject("You were accepted into a Circle");
            m.setContent("You were accepted into " + circlelink);
            m.setSender(0);
            m.setReceiver(uid);
            sendMessage(m);
        } catch (SQLException ex) {
            int i = 0;
        }
    }

    public void declineUser(int uid, int circle) {
        CircleBean b = getCircle(circle);
        String circlelink = "<a href=\\\"javascript:void(0);\\\" onclick=\\\"changePage('JSPChunks/ViewCircle.jsp?circle=" + b.getId() + "')\\\">" + b.getName() + "</a>";
        MessageBean m = new MessageBean();
        m.setSubject("You were denied access into a Circle");
        m.setContent("You were denied access into " + circlelink);
        m.setSender(0);
        m.setReceiver(uid);
        sendMessage(m);
    }

    public void removeMember(int uid, int circle) {
        try {
            CircleBean b = getCircle(circle);
            Statement stmt = con.createStatement();
            String sql = "delete from wpeckham.circle_memberships where circleid=" + circle + " and customerid=" + uid;
            stmt.executeUpdate(sql);
            String circlelink = "<a href=\\\"javascript:void(0);\\\" onclick=\\\"changePage('JSPChunks/ViewCircle.jsp?circle=" + b.getId() + "')\\\">" + b.getName() + "</a>";
            MessageBean m = new MessageBean();
            m.setSubject("You were removed from a Circle");
            m.setContent("You were removed from " + circlelink);
            m.setSender(0);
            m.setReceiver(uid);
            sendMessage(m);
        } catch (SQLException ex) {
            int i = 0;
        }
    }

    public String getUserNameFromID(int uid) {
        ResultSet rs = select("display_name", "persons", "ssn=" + uid);
        try {
            if (rs.next()) {
                return rs.getString(1);
            } else {
                return "";
            }
        } catch (SQLException ex) {
            int i = 0;
            return "";
        }
    }

    public ArrayList<UserBean> listMembers(int circle) {
        ArrayList<UserBean> list = new ArrayList<UserBean>();
        try {
            ResultSet rs = select("customerid", "circle_memberships", "circleid=" + circle);
            while (rs.next()) {
                int id = rs.getInt(1);
                UserBean b = new UserBean();
                fillUserBean(b, getUserNameFromID(id));
                list.add(b);
            }
        } catch (SQLException ex) {
            int i = 0;
        }
        return list;
    }

    public void editCircle(CircleBean b) {
        try {
            Statement stmt = con.createStatement();
            String sql = "update wpeckham.circles set circle_name=\"" + b.getName() + "\",catagory=\"" + b.getCatagory() + "\",public=" + (b.isPub() ? 1 : 0) + " where circle_id=" + b.getId();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            int i = 0;
        }
    }

    public void newCircle(CircleBean b) {
        try {
            Statement stmt = con.createStatement();
            String sql = "insert into wpeckham.circles (circle_name,owner_of_circle,catagory,public) values (\"" + b.getName() + "\"," + b.getOwnerID() + ",\"" + b.getCatagory() + "\"," + (b.isPub() ? 1 : 0) + ")";
            stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            sql = "insert into wpeckham.circle_memberships (customerid,circleid) values (" + b.getOwnerID() + "," + rs.getInt(1) + ")";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            int i = 0;
        }
    }

    public void removeCircle(int circle) {
        String sql = "delete from wpeckham.circles where circle_id=" + circle;
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            int i = 0;
        }
    }

    public void hire(EmployeeBean b) {
        int id = b.getUsr().getUserid();
        String sql = "insert into wpeckham.employees (ssn, start_date,hourly_rate,manager) values (" + id + ",now()," + b.getHourly() + "," + b.getManager() + ")";
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            int i = 0;
        }
    }

    public void fire(int id) {
        String sql = "delete from wpeckham.employees where ssn=" + id;
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            int i = 0;
        }
    }

    public ArrayList<EmployeeBean> getEmployees(int manager) {
        ArrayList<EmployeeBean> list = new ArrayList<EmployeeBean>();

        ResultSet rs = select("*", "employees", "manager=" + manager);
        try {
            while (rs.next()) {
                UserBean b = new UserBean();
                fillUserBean(b, getUserNameFromID(rs.getInt("ssn")));
                EmployeeBean eb = new EmployeeBean();
                eb.setUsr(b);
                eb.setHourly(rs.getInt("hourly_rate"));
                eb.setDate(rs.getDate("start_date"));
                eb.setManager(manager);
                list.add(eb);
            }
        } catch (SQLException ex) {
            int i = 0;
        } catch (Exception e){
            int i = 0;
        }


        return list;
    }

    public void fillEmployeeBean(EmployeeBean b, int empl) {
        ResultSet rs = select("*", "employees", "ssn=" + empl);
        try {
            if (rs.next()) {
                UserBean ub = new UserBean();
                fillUserBean(ub, getUserNameFromID(rs.getInt("ssn")));
                b.setUsr(ub);
                b.setHourly(rs.getInt("hourly_rate"));
                b.setDate(rs.getDate("start_date"));
                b.setManager(rs.getInt("manager"));
                b.setManagerName(getUserNameFromID(b.getManager()));
            }
        } catch (SQLException ex) {
            int i = 0;
        }
    }

    public void editEmployee(EmployeeBean eb) {
        try {
            String sql = "update wpeckham.employees set hourly_rate=" + eb.getHourly() + " where ssn=" + eb.getUsr().getUserid();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            int i = 0;
        }
    }
    public ArrayList<AdBean> getAdsForUser(int uid){
        ArrayList<AdBean> userAds = new ArrayList<AdBean>();
        try{
            Statement stmt = con.createStatement();
            String sql;
            if(uid > 0){
                sql ="CALL wpeckham.getAdsForUser(" + uid +")";    
            }
            else{
                sql = "SELECT * FROM wpeckham.advertisement";
            }
            ResultSet rs =  stmt.executeQuery(sql);
            while(rs.next())
            {
                AdBean b = new AdBean();
                b.setId(rs.getInt(1));
                b.setEmpId(rs.getInt(2));
                b.setCat(rs.getString(3));
                b.setDate(rs.getDate(4));
                b.setCompany(rs.getString(5));
                b.setItem(rs.getString(6));
                b.setAdContent(rs.getString(7));
                b.setUnitPrice(rs.getInt(8));
                b.setAvailable(rs.getInt(9));
                userAds.add(b);
            }
            if((userAds.size() <= 0)&& (uid > 0 )){
                userAds = getAdsForUser(-1);
            }
        }
        catch (SQLException ex){
            int i = 0;
        }
        return userAds;
    }
    public void fillAdBean(AdBean b, int adId){
        ResultSet rs = select("*", "advertisement", "Advertisement_Id=" +adId);
        try{
            if(rs.next()){
                b.setId(rs.getInt(1));
                b.setEmpId(rs.getInt(2));
                b.setCat(rs.getString(3));
                b.setDate(rs.getDate(4));
                b.setCompany(rs.getString(5));
                b.setItem(rs.getString(6));
                b.setAdContent(rs.getString(7));
                b.setUnitPrice(rs.getInt(8));
                b.setAvailable(rs.getInt(9));
             }
                 
        } catch (SQLException ex) {
            int i = 0;
        }
    }
    
    public void addAd(AdBean b){
        try{
            String sql = "INSERT INTO wpeckham.advertisement( Employee, Catagory, _Date, Company, Item_Name, Content, Unit_Price, Available_Units) VALUES (" + b.getEmpId()+",\""+b.getCat()+"\",NOW(),\""+b.getCompany()+"\",\""+b.getItem()+"\",\""+b.getAdContent()+"\","+b.getUnitPrice()+","+b.getAvailable()+")";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
        }catch (SQLException ex) {
            int i = 0;
        }
    }
    
    public void deleteAd(int adid){
        try{
            String sql = "delete from wpeckham.advertisement where advertisement_id="+adid;
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
        } catch(SQLException e){
            int i = 0;
        }
    }
    
    public ArrayList<AdBean> getEmployeesAds(int empl){
        ArrayList<AdBean> list = new ArrayList<AdBean>();
        
        try{
            ResultSet rs = select("advertisement_id", "advertisement", "employee="+empl);
            while(rs.next()){
                AdBean b = new AdBean();
                fillAdBean(b, rs.getInt(1));
                list.add(b);
            }
        } catch(SQLException e){
            int i=0;
        }
        
        return list;
    }
    
    public Date getEaliestTransactionDate(){
        String sql = "select unique _date from wpeckham.transactions where 1=1 order by _date desc";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                Date d = rs.getDate(1);
                return d;
            }
        } catch (SQLException ex) {
            int i = 0;
        }
        return new Date();
        
    }

    public int makePurchase(int account, int adid, int number) {
        try{
            con.setAutoCommit(false);
            
            AdBean b = new AdBean();
            fillAdBean(b, adid);
            ResultSet rs = select("credit_card_number", "accounts","account_number="+account );
            String ccn = "";
            if(rs.next()){
                ccn = rs.getString(1);
            } else {
                return -1;
            }
            if(ccn==null||ccn.equals("")){
                return -1;
            }
            if(b.getAvailable()<=number){
                number=b.getAvailable();
            }
            
            String sql = "insert into wpeckham.purchases (_date,advertisement,number_of_units,account) values (now(),"+b.getId()+","+number+","+account+")";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
            
            sql="update wpeckham.advertisement set available_units=" + (b.getAvailable()-number) + " where advertisement_id="+b.getId();
            stmt.executeUpdate(sql);
            
            con.commit();
            
            con.setAutoCommit(true);
            return number; 
        } catch(SQLException e){
            try {
                con.rollback();
                con.setAutoCommit(true);
            } catch (SQLException ex) {
                int i = 0;
            }
            int i = 0;
        } 
        
        return -1;
    }

    public ArrayList<TransactionBean> getAccountTransactions(int acct) {
        ArrayList<TransactionBean> list = new ArrayList<TransactionBean>();
        
        String sql = "select * from wpeckham.purchases, wpeckham.advertisement where advertisement=advertisement_id and account=" +acct;
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                AdBean b = new AdBean();
                fillAdBean(b, rs.getInt("advertisement_id"));
                TransactionBean tb = new TransactionBean();
                tb.setB(b);
                tb.setDate(rs.getDate("purchases._date"));
                tb.setNumUnits(rs.getInt("number_of_units"));
                list.add(tb);
            }
        } catch (SQLException ex) {
            int i = 0;
        }
        
        
        return list;
    }

    public ArrayList<BestSellerBean> getBestSellers() {
        String sql = "call wpeckham.Best_Sellers();";
        ArrayList<BestSellerBean> list = new ArrayList<BestSellerBean>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                AdBean b = new AdBean();
                fillAdBean(b, rs.getInt("Advertisement"));
                BestSellerBean bsb = new BestSellerBean();
                bsb.setB(b);
                bsb.setNumsold(rs.getInt("NumSold"));
                list.add(bsb);
            }
        } catch (SQLException ex) {
            int i =0;
        }
        return list;
    }

    public ArrayList<TransactionBean> getSalesForMonth(int month, int year) {
        String sql = "call wpeckham.salesReport("+month+","+year+")";
        ArrayList<TransactionBean> list = new ArrayList<TransactionBean>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                AdBean b = new AdBean();
                fillAdBean(b, rs.getInt("advertisement"));
                TransactionBean tb = new TransactionBean();
                tb.setB(b);
                tb.setDate(rs.getDate("_date"));
                tb.setNumUnits(rs.getInt("number_of_units"));
                tb.setTotal(tb.getNumUnits()*b.getUnitPrice());
                list.add(tb);
            }
        } catch (SQLException ex) {
            int i = 0;
        }
        
        return list;
    }

    public ArrayList<TransactionBean> getItemTransactions(int item) {
        ArrayList<TransactionBean> list = new ArrayList<TransactionBean>();
        ResultSet rs = select("*", "purchases", "advertisement="+item);
        try {
            while(rs.next()){
                AdBean b = new AdBean();
                fillAdBean(b, rs.getInt("advertisement"));
                TransactionBean tb = new TransactionBean();
                tb.setB(b);
                tb.setDate(rs.getDate("_date"));
                tb.setNumUnits(rs.getInt("number_of_units"));
                tb.setTotal(tb.getNumUnits()*b.getUnitPrice());
                list.add(tb);
            }
        } catch (SQLException ex) {
            int i = 0;
        }
        return list;
    }
    
    
    
}
