package PropertyManager.PM.Database;


import java.sql.*;
import java.util.concurrent.Callable;

public class SqlConnection {


    private static Connection con;


    public static void init(){

        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/demodb","postgres", "password");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }


    }

    public static ResultSet executeQuery(String sqlQuery) {
        ResultSet rs = executeQuery(sqlQuery, false);
        return rs;
    }

    public static ResultSet executeQuery(String sqlQuery, boolean exitIfFail) {
        ResultSet rs = executeQuery(sqlQuery, exitIfFail, () -> { return null; });
        return rs;
    }

    public static ResultSet executeQuery(String sqlQuery, boolean exitIfFail, boolean update){
        ResultSet rs = executeQuery(sqlQuery, exitIfFail, () -> { return null; }, update);
        return rs;
    }

    public static ResultSet executeQuery(String sqlQuery, boolean exitIfFail, Callable<Void> executeOnFailure) {
        ResultSet rs = executeQuery(sqlQuery, exitIfFail, executeOnFailure, false);
        return rs;
    }

    public static ResultSet executeQuery(String sqlQuery, boolean exitIfFail, Callable<Void> executeOnFailure, boolean update){
        ResultSet rs = null;

        try{
            if (update){
                // This is for operations who don't return a value, so that no error is thrown
                getConnection().createStatement().executeUpdate(sqlQuery);
            }
            else {
                // This is for getting data from the table
                rs = getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE).executeQuery(sqlQuery);
            }
        } catch(SQLException e){
            System.out.println("A SQL Query has failed.");
            System.err.println("Failed Query: " + sqlQuery);
            e.printStackTrace();
            try {
                // Do something if something fails
                executeOnFailure.call();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if(exitIfFail){
                // to close the program if ever we want a failure to be fatal.
                System.exit(1);
            }
        }
        return rs;
    }


    public static Connection getConnection(){

        if (con==null){
            init();
        }
        try {
            if (con.isClosed())
                init();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;

    }


    public static void main(String[] args) throws SQLException {

        String sql = "SELECT * FROM person";

        ResultSet nrs = executeQuery(sql);

        while (nrs.next()) {
            System.out.println(nrs.getString("id"));
            System.out.println(nrs.getString("name"));
        }

        System.out.println(nrs.previous());


    }

}

