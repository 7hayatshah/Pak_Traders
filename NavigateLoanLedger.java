import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NavigateLoanLedger implements ActionListener{
    JTextField fromTextField, toTextField, resultTextField, saleIdTextField;
    JButton receivedButton, pendingButton, fullDataButton, searchButton, saleIDsubmitButton;
    DefaultTableModel tableModel;
    Connection con;
    
    NavigateLoanLedger(Connection con){
        this.con = con;
        JFrame frame = new JFrame("View Loan Entries");
        frame.setSize(610, 510);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.getContentPane().setBackground(new Color(240, 248, 255)); // Light blue shade

        // Custom Font
        Font customFont = new Font("Arial", Font.BOLD, 14);

        // "SET DATE:" Label
        JLabel setDateLabel = new JLabel("SET DATE (Only works for Recieved & Full Data):");
        setDateLabel.setFont(customFont);
        setDateLabel.setBounds(20, 20, 400, 25);
        frame.add(setDateLabel);

        // "FROM~:" and "TO~:" Labels and TextFields
        JLabel fromLabel = new JLabel("FROM~:");
        fromLabel.setFont(customFont);
        fromLabel.setBounds(20, 50, 60, 25);
        frame.add(fromLabel);

        fromTextField = new JTextField();
        fromTextField.setBounds(80, 50, 150, 25);
        frame.add(fromTextField);

        JLabel toLabel = new JLabel("TO~:");
        toLabel.setFont(customFont);
        toLabel.setBounds(250, 50, 60, 25);
        frame.add(toLabel);

        toTextField = new JTextField();
        toTextField.setBounds(310, 50, 150, 25);
        frame.add(toTextField);

        // "RECEIVED" and "PENDING" Buttons
        receivedButton = new JButton("RECEIVED");
        receivedButton.setBounds(20, 90, 120, 25);
        receivedButton.setFocusable(false);
        receivedButton.setBackground(new Color(0, 153, 76));
        receivedButton.setForeground(Color.WHITE);
        receivedButton.setFont(customFont);
        receivedButton.addActionListener(this);
        frame.add(receivedButton);

        pendingButton = new JButton("PENDING");
        pendingButton.setBounds(150, 90, 120, 25);
        pendingButton.setFocusable(false);
        pendingButton.setBackground(new Color(0, 153, 76));
        pendingButton.setForeground(Color.WHITE);
        pendingButton.setFont(customFont);
        pendingButton.addActionListener(this);
        frame.add(pendingButton);

        JLabel resultLabel = new JLabel("RESULT:");
        resultLabel.setBounds(290, 90, 100, 25);
        resultLabel.setFont(customFont);
        frame.add(resultLabel);

        resultTextField = new JTextField();
        resultTextField.setBounds(360, 90, 150, 25);
        resultTextField.setEditable(false);
        frame.add(resultTextField);

        // "FULL DATA" Button
        fullDataButton = new JButton("FULL DATA");
        fullDataButton.setBounds(20, 130, 250, 25);
        fullDataButton.setFocusable(false);
        fullDataButton.setBackground(new Color(0, 153, 76));
        fullDataButton.setForeground(Color.WHITE);
        fullDataButton.setFont(customFont);
        fullDataButton.addActionListener(this);
        frame.add(fullDataButton);

        // "SALE_ID" Label, TextField, and "SEARCH" Button
        JLabel saleIdLabel = new JLabel("SALE_ID:");
        saleIdLabel.setFont(customFont);
        saleIdLabel.setBounds(20, 170, 70, 25);
        frame.add(saleIdLabel);

        saleIdTextField = new JTextField();
        saleIdTextField.setBounds(90, 170, 150, 25);
        frame.add(saleIdTextField);

        searchButton = new JButton("SEARCH");
        searchButton.setBounds(250, 170, 100, 25);
        searchButton.setForeground(Color.WHITE);
        searchButton.setBackground(new Color(0, 153, 76)); // Green header background
        searchButton.addActionListener(this);
        searchButton.setFocusable(false);
        frame.add(searchButton);

        saleIDsubmitButton = new JButton("SUBMIT");
        saleIDsubmitButton.setBounds(360, 170, 100, 25);
        saleIDsubmitButton.setForeground(Color.WHITE);
        saleIDsubmitButton.setBackground(new Color(0, 153, 76)); // Green header background
        saleIDsubmitButton.addActionListener(this);
        saleIDsubmitButton.setFocusable(false);
        frame.add(saleIDsubmitButton);


        // JTable
        String[] columnNames = {"Sale ID", "Price", "Total Paid", "Amount", "Date", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0); // Start with no rows
        JTable table = new JTable(tableModel);
        table.getColumnModel().getColumn(0).setPreferredWidth(40); // Sale ID
        table.getColumnModel().getColumn(1).setPreferredWidth(70); // Price
        table.getColumnModel().getColumn(2).setPreferredWidth(70); // Received
        table.getColumnModel().getColumn(3).setPreferredWidth(60); // Pending
        table.getColumnModel().getColumn(4).setPreferredWidth(110); // Date
        table.getColumnModel().getColumn(5).setPreferredWidth(40); // Status
        table.getTableHeader().setBackground(new Color(0, 153, 76)); // Green header background
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Arial",Font.BOLD,12));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 210, 560, 250);
        frame.add(scrollPane);

        frame.setVisible(true);
    }

    
    void fullDataFunc(){
        tableModel.setRowCount(0);
        String query = "SELECT u.sale_id, p.price, u.total_paid, u.current_payment_amount, u.payment_receive_date, p.status "+
        "FROM unpaid_sales_ledger u " +
        "INNER JOIN sales_ledger p ON p.sale_id = u.sale_id " +
        "WHERE u.payment_receive_date BETWEEN ? AND ? "+
        "ORDER BY (u.payment_receive_date)";

        PreparedStatement pstmt = null;
        try{
            pstmt = con.prepareStatement(query);
            
            try{                                                        //Validating Date Input
            pstmt.setDate(1, Date.valueOf(fromTextField.getText()));
            pstmt.setDate(2, Date.valueOf(toTextField.getText()));
            }catch(IllegalArgumentException iae){
                fromTextField.setBackground(new Color(0xFFCCCB));
                toTextField.setBackground(new Color(0xFFCCCB));
                return;
            }
            fromTextField.setBackground(Color.WHITE);
            toTextField.setBackground(Color.WHITE);
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                tableModel.addRow(new Object[]{rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getDate(5),rs.getString(6)});
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e,"Error! Contact Developer",JOptionPane.ERROR_MESSAGE);
        }
        finally{try{
                    if(pstmt != null){pstmt.close();}
                    }catch(SQLException sqle){}}
    }

    void receivedFunc(){
        String query = "select sum(current_payment_amount) from unpaid_sales_ledger where payment_receive_date between ? and ?;";
        PreparedStatement pstmt = null;

        try{
            pstmt = con.prepareStatement(query);

            //Validating Date Input
            try{
                pstmt.setDate(1, Date.valueOf(fromTextField.getText()));
                pstmt.setDate(2, Date.valueOf(toTextField.getText()));
            }catch(IllegalArgumentException iae){
                fromTextField.setBackground(new Color(0xFFCCCB));
                toTextField.setBackground(new Color(0xFFCCCB));
                return;
            }
            fromTextField.setBackground(Color.WHITE);
            toTextField.setBackground(Color.WHITE);
            
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){resultTextField.setText(String.valueOf(rs.getInt(1)));}
        }catch(SQLException sqle){
            JOptionPane.showMessageDialog(null,sqle,"Error! Contact Developer",JOptionPane.ERROR_MESSAGE);
        }
        finally{try{
            if(pstmt != null){pstmt.close();}
            }catch(SQLException sqle){}}
    }

    void pendingFunc(){
        String query = "SELECT sum(price) - "+
        "(SELECT sum(current_payment_amount) from unpaid_sales_ledger where sale_id in( "+
        "(SELECT sale_id from sales_ledger where status = 'U'))) AS remain from sales_ledger where status = 'U';";
        PreparedStatement pstmt = null;

        try{
            pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){resultTextField.setText(String.valueOf(rs.getInt(1))+" Date Ignored");}
        }catch(SQLException sqle){
            JOptionPane.showMessageDialog(null,sqle,"Error! Contact Developer",JOptionPane.ERROR_MESSAGE);
        }

        finally{try{
            if(pstmt != null){pstmt.close();}
            }catch(SQLException sqle){}}
    }

    void saleIDFunc(){
        tableModel.setRowCount(0);
        String query = "SELECT u.sale_id, p.price, u.total_paid, u.current_payment_amount, u.payment_receive_date, p.status " +
        "FROM unpaid_sales_ledger u " +
        "INNER JOIN sales_ledger p ON p.sale_id = u.sale_id " +
        "WHERE u.sale_id = ? "+
        "ORDER BY (u.total_paid)";
        PreparedStatement pstmt = null;
        int saleID;
        try{
            saleID = Integer.parseInt(saleIdTextField.getText());
        }catch(NumberFormatException nfe){
            saleIdTextField.setBackground(new Color(0xFFCCCB));
            return;
        }
        saleIdTextField.setBackground(Color.WHITE);
        try{
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, saleID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                tableModel.addRow(new Object[]{rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getDate(5),rs.getString(6)});
            }
        }catch(SQLException sqle){
            JOptionPane.showMessageDialog(null,sqle,"Error! Contact Developer",JOptionPane.ERROR_MESSAGE);
        }

        finally{try{
            if(pstmt != null){pstmt.close();}
            }catch(SQLException sqle){}}
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==receivedButton){
            new Thread(() ->{
                receivedFunc();
            }).start();
        }
        if(e.getSource()==fullDataButton){
            new Thread(() ->{
                fullDataFunc();
            }).start();
        }
        if(e.getSource()==pendingButton){
            new Thread(() ->{
                pendingFunc();
            }).start();
        }
        if(e.getSource()==saleIDsubmitButton){
            new Thread(() ->{
                saleIDFunc();
            }).start();
        }
        if(e.getSource()==searchButton){new NavigateSalesLedger(con);}
        

    }
}    

