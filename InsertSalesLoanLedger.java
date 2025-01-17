import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
class InsertSalesLoanLedger implements ActionListener {
    Connection con;
    JButton submitButton, searchButton;
    JTextField saleIdField, currentPaymentField, paymentDateField, resultField;
    boolean check;
    JFrame frame;

    String sale_id = "", amount = "", date1 = "";


    InsertSalesLoanLedger(Connection con, boolean check){
        this.check = check;
        this.con = con;    
        // Create the JFrame
        frame = new JFrame("Add new Loan Entry");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setLayout(null);
        frame.setResizable(false);
        
        if(check){frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);}
        
        // Set background color
        frame.getContentPane().setBackground(new Color(240, 248, 255)); // Light blue shade

        // Font settings
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font textFieldFont = new Font("Arial", Font.PLAIN, 14);

        // Sale ID Label and TextField
        JLabel saleIdLabel = new JLabel("SALE ID^:");
        saleIdLabel.setBounds(20, 20, 100, 25);
        saleIdLabel.setFont(labelFont);
        saleIdLabel.setForeground(new Color(0, 51, 102));
        frame.add(saleIdLabel);

        saleIdField = new JTextField();
        saleIdField.setBounds(130, 20, 70, 25);
        saleIdField.setFont(textFieldFont);
        saleIdField.setEditable(check);
        frame.add(saleIdField);

        // Loan Payment Amount Label and TextField
        JLabel currentPaymentLabel = new JLabel("LOAN PAYMENT AMOUNT^:");
        currentPaymentLabel.setBounds(20, 60, 200, 25);
        currentPaymentLabel.setFont(labelFont);
        currentPaymentLabel.setForeground(new Color(0, 51, 102));
        frame.add(currentPaymentLabel);

        currentPaymentField = new JTextField();
        currentPaymentField.setBounds(210, 60, 120, 25);
        currentPaymentField.setFont(textFieldFont);
        frame.add(currentPaymentField);

        // Date Label and TextField
        JLabel paymentDateLabel = new JLabel("DATE^:");
        paymentDateLabel.setBounds(20, 100, 100, 25);
        paymentDateLabel.setFont(labelFont);
        paymentDateLabel.setForeground(new Color(0, 51, 102));
        frame.add(paymentDateLabel);

        paymentDateField = new JTextField();
        paymentDateField.setBounds(130, 100, 200, 25);
        paymentDateField.setFont(textFieldFont);
        frame.add(paymentDateField);

        // Result Label and TextField
        JLabel resultLabel = new JLabel("RESULT:");
        resultLabel.setBounds(20, 140, 100, 25);
        resultLabel.setFont(labelFont);
        resultLabel.setForeground(new Color(0, 51, 102));
        frame.add(resultLabel);

        resultField = new JTextField();
        resultField.setBounds(130, 140, 200, 25);
        resultField.setFont(textFieldFont);
        resultField.setEditable(false);
        frame.add(resultField);

        // Submit Button
        submitButton = new JButton("SUBMIT");
        submitButton.setBounds(150, 200, 100, 30);
        submitButton.setBackground(new Color(0, 153, 76)); // Green shade
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.setFocusable(false);
        submitButton.addActionListener(this);
        frame.add(submitButton);

        // Search Button
        searchButton = new JButton("SEARCH");
        searchButton.setBounds(210, 20, 120, 25);
        searchButton.setBackground(new Color(0, 153, 76)); // Green shade
        searchButton.setForeground(Color.WHITE);
        searchButton.setFont(new Font("Arial", Font.BOLD, 14));
        searchButton.setFocusable(false);
        searchButton.addActionListener(this);
        frame.add(searchButton);

        // Make the frame visible
        frame.setVisible(true);
    }

    void newLoanEntryFunc(){
        PreparedStatement pstmt = null;
        ResultSet rs;
        String query;
        int saleID, newPayment, totalamountpaid;
        try{
            if(check){
                saleID = Integer.parseInt(saleIdField.getText());
            }else{
                query = "select max(sale_id) as new_sale_id from sales_ledger;";
                pstmt = con.prepareStatement(query);
                rs = pstmt.executeQuery();
                rs.next();
                saleID = rs.getInt("new_sale_id");
            }

            query = "select max(total_paid) as max_paid from unpaid_sales_ledger where sale_id = ? group by (sale_id);";
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, saleID);
            rs = pstmt.executeQuery();
            newPayment = Integer.parseInt(currentPaymentField.getText());

            if(rs.next()){totalamountpaid = rs.getInt("max_paid") + newPayment;}
            else{totalamountpaid=newPayment;}
            
            query = "insert into unpaid_sales_ledger values (?,?,?,?);";
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, saleID);
            pstmt.setInt(2, totalamountpaid);
            pstmt.setInt(3, newPayment);
            pstmt.setDate(4, Date.valueOf(paymentDateField.getText()));
            pstmt.executeUpdate();

            query = "select price from sales_ledger where sale_id = ?;";
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, saleID);
            rs = pstmt.executeQuery();
            rs.next();

            if(rs.getInt("price")<=totalamountpaid){
                query = "update sales_ledger set status = 'P' where sale_id = ?;";
                pstmt = con.prepareStatement(query);
                pstmt.setInt(1,saleID);
                pstmt.executeUpdate();
            }else{
                query = "update sales_ledger set status = 'U' where sale_id = ?;";
                pstmt = con.prepareStatement(query);
                pstmt.setInt(1,saleID);
                pstmt.executeUpdate();
            }
            JOptionPane.showMessageDialog(null,"Entry Successfull!!","Success", JOptionPane.PLAIN_MESSAGE);

            //Displaying ResultField Output
            {
                String display = "";
                query = "select max(total_paid) from unpaid_sales_ledger where sale_id = ?";
                pstmt = con.prepareStatement(query);
                pstmt.setInt(1, saleID);
                rs = pstmt.executeQuery();
                if(rs.next()){display = "Total Paid: "+ rs.getInt(1);}

                query = "select status from sales_ledger where sale_id = ?";
                pstmt = con.prepareStatement(query);
                pstmt.setInt(1, saleID);
                rs = pstmt.executeQuery();
                if(rs.next()){display += " Status: "+ rs.getString(1);}

                resultField.setText(display);
            }

        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Entry Failed!!\nCheck if all date is correct\n"+e,"Input Failure",JOptionPane.ERROR_MESSAGE);
        }
        finally{
            try{
            if(pstmt!=null){pstmt.close();}
            }catch(SQLException sqle){}
        }
    }

    boolean sameInputFunc(){
        boolean output = false;

        if(!saleIdField.getText().equals(sale_id)){
            output = true;
            sale_id = saleIdField.getText();
        }
        if(!currentPaymentField.getText().equals(amount)){
            output = true;
            amount = currentPaymentField.getText();
        }
        if(!paymentDateField.getText().equals(date1)){
            output = true;
            date1 = paymentDateField.getText();
        }

        if(!output){JOptionPane.showMessageDialog(null,"Entry Failed!!\nDuplicate Input","",JOptionPane.ERROR_MESSAGE);}
        return output;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==submitButton){
            new Thread(() -> {
                if(sameInputFunc()){newLoanEntryFunc();}
            }).start();
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }

        if(e.getSource()==searchButton){new NavigateSalesLedger(con);}
    }

}
