import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.SQLException;

class SQL_Connector {
    String url = "jdbc:mysql://localhost:3306/pak_traders";
    String username = "root";
    String password = "2k2/sep/30";
    Connection con;

    SQL_Connector(){
        try{
        con = DriverManager.getConnection(url, username, password);
        }catch(SQLException sqle){}
    }
}