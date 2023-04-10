package sample;
import java.sql.*;
import java.util.ArrayList;


public class DataModel {

    Connection connection;

    public DataModel() {
        connection = SqlConector.connect();
        if (connection == null) {
            System.out.println("null");
        }
    }

    public boolean isDbCon() throws SQLException {
        return !connection.isClosed();
    }

    public void insertUserCh(ListChannel listCh) {
        try{
            Connection conn = this.connection;
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO UserChannel(Name,Authorization,Cookie,Referer," +
                            "Authuser,Pageid,DelegatContext,idChanel) VALUES(?,?,?,?,?,?,?,?)");
            ps.setString(1, listCh.getName());
            ps.setString(2, listCh.getAuthorization());
            ps.setString(3, listCh.getCookie());
            ps.setString(4, listCh.getReferer());
            ps.setString(5, listCh.getAuthuser());
            ps.setString(6, listCh.getPageid());
            ps.setString(7, listCh.getDelegatcontext());
            ps.setString(8, listCh.getIdChanel());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public void insertDB(ListData listD, String Tablet) {
        try{
            Connection conn = this.connection;
            PreparedStatement ps = conn.prepareStatement("INSERT INTO "+ Tablet +" (DATA,DATE) VALUES(?,?)");
            ps.setString(1, listD.getData());
            ps.setString(2, listD.getDate());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public void insert(String date, String tablet, String row) {
        deleteRow("Work_dun");
        try{
            Connection conn = this.connection;
            PreparedStatement ps = conn.prepareStatement("INSERT INTO "+ tablet +"("+ row +") VALUES(?)");
            ps.setString(1, date);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public void insertBox(String date, String tablet, String row) {
        try{
            Connection conn = this.connection;
            PreparedStatement ps = conn.prepareStatement("INSERT INTO "+ tablet +"("+ row +") VALUES(?)");
            ps.setString(1, date);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public void deleteRow(String TABLET) {
        if (!checkTablet(TABLET)) {
            try {
                Connection conn = this.connection;
                Statement stmt = conn.createStatement();
                String sql = "DELETE FROM " + TABLET;
                stmt.executeUpdate(sql);
            } catch (SQLException e) {
                e.getMessage();
            }
        } else {
            System.out.println("false");
        }
    }

    public void deleteOneRow(String TABLET, String ROW, String name) {
            try {
                Connection conn = this.connection;
                Statement stmt = conn.createStatement();
                String sql = "DELETE FROM " + TABLET + " WHERE " +
                        ROW + "= '"+ name +"'";
                stmt.executeUpdate(sql);
            } catch (SQLException e) {
                e.getMessage();
            }
    }

    public boolean checkTablet(String TABLET) {
        try {
            Connection conn = this.connection;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + TABLET);
            if(rs.next()){
                return true;
            }
        } catch (SQLException e) {
            e.getMessage();
        }
        return false;
    }

    public boolean checkRow(String text) {
        try {
            Connection conn = this.connection;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DATA FROM Subscriber WHERE DATA= '"+ text +"'");
            if(rs.next()){
                return true;
            }
        } catch (SQLException e) {
            e.getMessage();
        }
        return false;
    }

    public String getDate(String tablet, String ROW) {
        String date = null;
        try {
            Connection conn = this.connection;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT "+ ROW +" FROM "+ tablet);
            if(rs.next()){
                do {
                    date = rs.getString(ROW);
                } while (rs.next());
            }

        } catch (SQLException e) {
            e.getMessage();
        }
        return date;
    }

    public String getSubscriber() {
        String subscriber = null;
        try {
            Connection conn = this.connection;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DATA FROM Subscriber");
            if(rs.next()){
                do {
                    subscriber = rs.getString("DATA");
                } while (rs.next());
            }
        } catch (SQLException e) {
            e.getMessage();
        }
        return subscriber;
    }

    public ArrayList<String> getName() {
        ArrayList<String> returnList = new ArrayList<>();
        try {
            Connection conn = this.connection;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Name FROM UserChannel");
            if(rs.next()){
                do {
                    returnList.add(rs.getString("Name"));
                } while (rs.next());
            }
        } catch (SQLException e) {
            e.getMessage();
        }
        return returnList;
    }

    public ArrayList<ListChannel> getAllUser(String text) {
        ArrayList<ListChannel> returnList = new ArrayList<>();
        try {
            Connection conn = this.connection;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM UserChannel WHERE Name='"+ text +"'");
            if(rs.next()){
                do {
                    int nID = rs.getInt("ID");
                    String name = rs.getString("Name");
                    String author = rs.getString("Authorization");
                    String cookie = rs.getString("Cookie");
                    String referer = rs.getString("Referer");
                    String authuser = rs.getString("Authuser");
                    String pageid = rs.getString("Pageid");
                    String delegat = rs.getString("DelegatContext");
                    String idCh = rs.getString("idChanel");
                    ListChannel user = new ListChannel(
                            nID, name, author,cookie,referer,authuser,pageid,delegat,idCh);
                    returnList.add(user);
                } while (rs.next());
            }
        } catch (SQLException e) {
           e.getMessage();
        }
        return returnList;
    }
}
