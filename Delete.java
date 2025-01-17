import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
public class Delete implements ActionListener{
    JTextField saleIDField, customerIDField, UsaleIDField, totalPaidField, dateField;
    JButton saleIDButton, customerIDButton, loanButton;
    Connection con;

    Delete(Connection con) {
        this.con = con;

        // Create the frame
        JFrame frame = new JFrame("Delete Records");
        frame.setSize(480, 330);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().setBackground(new Color(240, 248, 255)); // Light blue shade

        // Fonts
        Font headingFont = new Font("Arial", Font.BOLD, 18);
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font buttonFont = new Font("Arial", Font.BOLD, 15);

        // Partition 1: Sales Ledger
        JLabel saleIDLabel = new JLabel("DEL from sales ledger:");
        saleIDLabel.setFont(headingFont);
        saleIDLabel.setBounds(20, 10, 300, 30);
        frame.add(saleIDLabel);

        JLabel saleIDFieldLabel = new JLabel("SALE ID:");
        saleIDFieldLabel.setFont(labelFont);
        saleIDFieldLabel.setBounds(20, 50, 100, 30);
        frame.add(saleIDFieldLabel);

        saleIDField = new JTextField();
        saleIDField.setBounds(130, 50, 200, 30);
        frame.add(saleIDField);

        saleIDButton = new JButton("DELETE");
        saleIDButton.setBounds(340, 50, 100, 30);
        saleIDButton.setFocusable(false);
        saleIDButton.addActionListener(this);
        saleIDButton.setBackground(new Color(0, 153, 76)); // Green shade
        saleIDButton.setForeground(Color.WHITE);
        saleIDButton.setFont(buttonFont);
        frame.add(saleIDButton);

        // Partition 2: Customers
        JLabel customerIDLabel = new JLabel("DEL from customers:");
        customerIDLabel.setFont(headingFont);
        customerIDLabel.setBounds(20, 100, 300, 30);
        frame.add(customerIDLabel);

        JLabel customerIDFieldLabel = new JLabel("CUSTOMER ID:");
        customerIDFieldLabel.setFont(labelFont);
        customerIDFieldLabel.setBounds(20, 140, 120, 30);
        frame.add(customerIDFieldLabel);

        customerIDField = new JTextField();
        customerIDField.setBounds(130, 140, 200, 30);
        frame.add(customerIDField);

        customerIDButton = new JButton("DELETE");
        customerIDButton.setBounds(340, 140, 100, 30);
        customerIDButton.setFocusable(false);
        customerIDButton.addActionListener(this);
        customerIDButton.setBackground(new Color(0, 153, 76)); // Green shade
        customerIDButton.setForeground(Color.WHITE);
        customerIDButton.setFont(buttonFont);
        frame.add(customerIDButton);

        // Partition 3: Loan Ledger
        JLabel loanLabel = new JLabel("DEL from loan ledger:");
        loanLabel.setFont(headingFont);
        loanLabel.setBounds(20, 190, 300, 30);
        frame.add(loanLabel);

        JLabel loanSaleIDLabel = new JLabel("SALE ID:");
        loanSaleIDLabel.setFont(labelFont);
        loanSaleIDLabel.setBounds(20, 230, 100, 30);
        frame.add(loanSaleIDLabel);

        UsaleIDField = new JTextField();
        UsaleIDField.setBounds(130, 230, 200, 30);
        frame.add(UsaleIDField);

        loanButton = new JButton("DELETE");
        loanButton.setBounds(340, 230, 100, 30);
        loanButton.setFocusable(false);
        loanButton.addActionListener(this);
        loanButton.setBackground(new Color(0, 153, 76)); // Green shade
        loanButton.setForeground(Color.WHITE);
        loanButton.setFont(buttonFont);
        frame.add(loanButton);

        // Make the frame visible
        frame.setVisible(true);
    }

    void salesLedgerFunc(){
        String query = "delete from sales_ledger where sale_id = ?";
        int saleID;

        try{
            saleID = Integer.parseInt(saleIDField.getText());   //Validating Int
        }catch(NumberFormatException nfe){
            saleIDField.setBackground(new Color(0xFFCCCB));
            return;
        }

        try(PreparedStatement pstmt = con.prepareStatement(query)){
            saleIDField.setBackground(Color.WHITE);
            pstmt.setInt(1,saleID);
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(null,"Delete Successful","",JOptionPane.PLAIN_MESSAGE);
        }catch(SQLException sqle){
            JOptionPane.showMessageDialog(null,sqle,"Error Contact Developer",JOptionPane.ERROR_MESSAGE);
        }
    }

    void customerFunc(){
        String query = "delete from costumers where costumer_id = ?";
        int ID;

        try{
            ID = Integer.parseInt(customerIDField.getText());   //Validating Int
        }catch(NumberFormatException nfe){
            customerIDField.setBackground(new Color(0xFFCCCB));
            return;
        }

        try(PreparedStatement pstmt = con.prepareStatement(query)){
            customerIDField.setBackground(Color.WHITE);
            pstmt.setInt(1,ID);
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(null,"Delete Successful","",JOptionPane.PLAIN_MESSAGE);
        }catch(SQLException sqle){
            JOptionPane.showMessageDialog(null,"Failed to delete\nthis ID might be being used in sales ledger","Error Contact Developer",JOptionPane.ERROR_MESSAGE);
        }
    }

    void loanFunc(){
        String query =  "select max(total_paid) from unpaid_sales_ledger where sale_id = ? group by(sale_id)";
        int saleID, totalpaid;
        PreparedStatement pstmt = null;

        try{
            saleID = Integer.parseInt(UsaleIDField.getText());
        }catch(NumberFormatException nfe){
            UsaleIDField.setBackground(new Color(0xFFCCCB));
            return;
        }

        try{
            UsaleIDField.setBackground(Color.WHITE);

            pstmt = con.prepareStatement(query);    //Getting max Total paid
            pstmt.setInt(1, saleID);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){totalpaid = rs.getInt(1);}
            else{
                JOptionPane.showMessageDialog(null,"Failure!\n No entry found with sale_id "+saleID,"",JOptionPane.ERROR_MESSAGE);
                return;
            }

            query = "delete from unpaid_sales_ledger where sale_id = ? AND total_paid = ?";
            pstmt = con.prepareStatement(query);    //Getting max Total paid
            pstmt.setInt(1, saleID);
            pstmt.setInt(2, totalpaid);
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(null,"Delete Successful","",JOptionPane.PLAIN_MESSAGE);
        
        }catch(SQLException sqle){
            System.out.println(sqle);
            JOptionPane.showMessageDialog(null,sqle,"Error Contact Developer",JOptionPane.ERROR_MESSAGE);
            return;
        }

        query = "select * from unpaid_sales_ledger where sale_id = ?";

        try{
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, saleID);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()==false){
                query = "update sales_ledger set status = 'P' where sale_id = ?";
                pstmt = con.prepareStatement(query);
                pstmt.setInt(1, saleID);
                pstmt.executeUpdate();
            }
        }catch(SQLException sqle){
            JOptionPane.showMessageDialog(null,sqle,"Error Contact Developer",JOptionPane.ERROR_MESSAGE);
        }

        finally{try{
            if(pstmt != null){pstmt.close();}
            }catch(SQLException sqle){}}
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==saleIDButton){
            new Thread(()-> {
                salesLedgerFunc();
            }).start();
        }
        if(e.getSource()==customerIDButton){
            new Thread(()-> {
                customerFunc();
            }).start();
        }
        if(e.getSource()==loanButton){
            new Thread(()-> {
                loanFunc();
            }).start();
        }
    }
}