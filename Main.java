import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args){
        SQL_Connector sqlc = new SQL_Connector();
        MainWindowSales mws = new MainWindowSales(sqlc.con);
        
            mws.getSalesFrame().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
            try{
            sqlc.con.close();
            }catch(SQLException sqle){
                JOptionPane.showMessageDialog(null,"Failed to close Connection\nData Might be lost\nContact Developer","",JOptionPane.ERROR_MESSAGE);
            }
            }
        });
    }
}