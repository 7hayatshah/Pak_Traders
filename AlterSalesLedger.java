import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AlterSalesLedger implements ActionListener{
    JFrame frame;
    JTextField customerIdField, IDField, resultField;
    JTextField productNameField;
    JTextField productDetailsField;
    JTextField weightField;
    JTextField quantityField;
    JTextField priceField;
    JTextField dateField;
    JButton customerIdButton, productNameButton, submitButton, searchButton, IDSubmitButton;

    Connection con;
    AlterSalesLedger(Connection con){
        this.con = con;
        Font labelFont = new Font("Arial",Font.BOLD,13);
        Font textfieldFont = new Font("Arial",Font.PLAIN,14);

        frame = new JFrame("Change Ledger Entry");        //Create a JFrame
        frame.setSize(505, 520);
        frame.setLayout(null); // Set layout to null
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(240, 248, 255)); // Light blue shade

        // Heading label
        JLabel headingLabel = new JLabel("ALTER: LEDGER ENTRY");
        headingLabel.setBounds(40, 10, 405, 30);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headingLabel.setHorizontalAlignment(JLabel.CENTER);
        frame.add(headingLabel);

        // Labels and text fields
        JLabel customerIdLabel = new JLabel("CUSTOMER_ID:");
        customerIdLabel.setBounds(40, 50, 140, 30);
        customerIdLabel.setFont(labelFont);
        customerIdLabel.setHorizontalAlignment(JLabel.LEFT);
        frame.add(customerIdLabel);

        customerIdField = new JTextField();
        customerIdField.setBounds(190, 50, 150, 30);
        customerIdField.setFont(textfieldFont);
        frame.add(customerIdField);

        customerIdButton = new JButton("SEARCH");
        customerIdButton.setBounds(345, 50, 100, 30);
        customerIdButton.setFont(new Font("Arial",Font.BOLD,14));
        customerIdButton.setFocusable(false);
        customerIdButton.setBackground(new Color(0, 153, 76));
        customerIdButton.setForeground(Color.WHITE);
        customerIdButton.addActionListener(this);
        frame.add(customerIdButton);

        JLabel productNameLabel = new JLabel("PRODUCT_NAME^:");
        productNameLabel.setBounds(40, 90, 130, 30);
        productNameLabel.setFont(labelFont);
        frame.add(productNameLabel);

        productNameField = new JTextField();
        productNameField.setBounds(190, 90, 150, 30);
        productNameField.setFont(textfieldFont);
        frame.add(productNameField);

        productNameButton = new JButton("SEARCH");
        productNameButton.setBounds(345, 90, 100, 30);
        productNameButton.setFocusable(false);
        productNameButton.setFont(new Font("Arial",Font.BOLD,14));
        productNameButton.setBackground(new Color(0, 153, 76));
        productNameButton.setForeground(Color.WHITE);
        productNameButton.addActionListener(this);
        frame.add(productNameButton);

        JLabel productDetailsLabel = new JLabel("PRODUCT_DETAILS:");
        productDetailsLabel.setBounds(40, 130, 130, 30);
        productDetailsLabel.setFont(labelFont);
        frame.add(productDetailsLabel);

        productDetailsField = new JTextField();
        productDetailsField.setBounds(190, 130, 255, 30);
        productDetailsField.setFont(textfieldFont);
        frame.add(productDetailsField);

        JLabel weightLabel = new JLabel("WEIGHT:");
        weightLabel.setBounds(40, 170, 100, 30);
        weightLabel.setFont(labelFont);
        frame.add(weightLabel);

        weightField = new JTextField();
        weightField.setBounds(190, 170, 255, 30);
        weightField.setFont(textfieldFont);
        frame.add(weightField);

        JLabel quantityLabel = new JLabel("QUANTITY^:");
        quantityLabel.setBounds(40, 210, 100, 30);
        quantityLabel.setFont(labelFont);
        frame.add(quantityLabel);

        quantityField = new JTextField();
        quantityField.setBounds(190, 210, 255, 30);
        quantityField.setFont(textfieldFont);
        frame.add(quantityField);

        JLabel priceLabel = new JLabel("PRICE (TOTAL)^:");
        priceLabel.setBounds(40, 250, 140, 30);
        priceLabel.setFont(labelFont);
        frame.add(priceLabel);

        priceField = new JTextField();
        priceField.setBounds(190, 250, 255, 30);
        priceField.setFont(textfieldFont);
        frame.add(priceField);

        JLabel dateLabel = new JLabel("DATE^:");
        dateLabel.setBounds(40, 290, 100, 30);
        dateLabel.setFont(labelFont);
        frame.add(dateLabel);

        dateField = new JTextField();
        dateField.setBounds(190, 290, 255, 30);
        dateField.setFont(textfieldFont);
        frame.add(dateField);

        JLabel resultLabel = new JLabel("RESULT:");
        resultLabel.setBounds(40, 330, 100, 30);
        resultLabel.setFont(labelFont);
        frame.add(resultLabel);

        resultField = new JTextField();
        resultField.setBounds(190, 330, 255, 30);
        resultField.setFont(textfieldFont);
        resultField.setEditable(false);
        frame.add(resultField);

        // Submit button
        submitButton = new JButton("SUBMIT");
        submitButton.setBounds(40, 370, 405, 30);
        submitButton.setFocusable(false);
        submitButton.setBackground(new Color(0, 153, 76));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Arial",Font.BOLD,15));
        submitButton.addActionListener(this);
        frame.add(submitButton);

        JPanel panel = new JPanel();
        panel.setBounds(40, 410, 405, 5);
        panel.setBackground(Color.BLACK);
        frame.add(panel);

        JLabel saleIDLabel = new JLabel("SALE ID:");
        saleIDLabel.setBounds(40, 425, 60, 30);
        saleIDLabel.setFont(labelFont);
        frame.add(saleIDLabel);

        IDField = new JTextField();
        IDField.setFont(textfieldFont);
        IDField.setBounds(120,425,100,30);
        frame.add(IDField);

        searchButton = new JButton("SEARCH");
        searchButton.setBounds(230, 425, 100, 30);
        searchButton.setFont(new Font("Arial",Font.BOLD,15));
        searchButton.setBackground(new Color(0, 153, 76));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusable(false);
        searchButton.addActionListener(this);
        frame.add(searchButton);

        IDSubmitButton = new JButton("SUBMIT");
        IDSubmitButton.setBounds(340, 425, 105, 30);
        IDSubmitButton.setFont(new Font("Arial",Font.BOLD,15));
        IDSubmitButton.setBackground(new Color(0, 153, 76));
        IDSubmitButton.setFont(new Font("Arial",Font.BOLD,15));
        IDSubmitButton.setForeground(Color.WHITE);
        IDSubmitButton.setFocusable(false);
        IDSubmitButton.addActionListener(this);
        frame.add(IDSubmitButton);

        frame.setVisible(true);
    }

    void fetchFunc(){
        String query = "select * from sales_ledger where sale_id = ?;";
        PreparedStatement pstmt = null;
        int saleID;

        try{
            saleID = Integer.parseInt(IDField.getText());
        }catch(IllegalArgumentException iae){
            JOptionPane.showMessageDialog(null,"Invalid 'Sale ID'!!","", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try{
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, saleID);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                // NOTE: I'm ignoring first coulmn in resultset

                customerIdField.setText(String.valueOf(rs.getInt(2)));
                productNameField.setText(rs.getString(3));
                productDetailsField.setText(rs.getString(4));

                if(rs.getInt(5)==0){}
                else{weightField.setText(String.valueOf(rs.getInt(5)));}

                quantityField.setText(String.valueOf(rs.getInt(6)));
                priceField.setText(String.valueOf(rs.getInt(7)));
                dateField.setText(String.valueOf(rs.getDate(8)));

            }

        }catch(SQLException sqle){
            JOptionPane.showMessageDialog(null,sqle,"Error! Contact Developer",JOptionPane.ERROR_MESSAGE);
        }

        finally{try{
            if(pstmt != null){pstmt.close();}
            }catch(SQLException sqle){}}
    }

    void saveFunc(){
        String query=   "UPDATE sales_ledger "+
                        "SET costumer_id = ?, product_name =?, product_details =?, weight =?, quantity =?, price =?, sale_date =?, status =? "+
                        "WHERE sale_id = ?";
        int store;
        PreparedStatement pstmt = null, pstmt2 = null;
        Boolean customerIDBoolean = false; //When status is unpaid it becomes true
        String resultFieldOutput;
        try{
            pstmt = con.prepareStatement(query);
            if(productNameField.getText().isEmpty()){           //PRODUCT_NAME INPUT
                JOptionPane.showMessageDialog(null,"'PRODUCT_NAME' can't be empty!!","Invalid Input",JOptionPane.ERROR_MESSAGE);
                return;
            }else{
                pstmt.setString(2, productNameField.getText());
            }

            if(productDetailsField.getText().isEmpty()){           //PRODUCT_DETAILS INPUT
                pstmt.setNull(3, java.sql.Types.VARCHAR);
            }else{
                pstmt.setString(3, productDetailsField.getText());
            }

            if(weightField.getText().isEmpty()){                    //WEIGHT INPUT
                pstmt.setNull(4, java.sql.Types.INTEGER);
            }else{
                try{                                                        
                    store = Integer.parseInt(weightField.getText());        //Validating Int Input
                }catch(NumberFormatException nfe){
                    JOptionPane.showMessageDialog(null,"Please enter valid 'WEIGHT'","Invalid Input",JOptionPane.ERROR_MESSAGE);
                    return;}
                    pstmt.setInt(4,store);
            }
        
            if(quantityField.getText().isEmpty()){           //QUANTITY INPUT
                JOptionPane.showMessageDialog(null,"'QUANTITY' can't be empty!!","Invalid Input",JOptionPane.ERROR_MESSAGE);
                return;
            }else{
                try{                                                        
                    store = Integer.parseInt(quantityField.getText());        //Validating Int Input
                }catch(NumberFormatException nfe){
                    JOptionPane.showMessageDialog(null,"Please enter valid 'QUANTITY'","Invalid Input",JOptionPane.ERROR_MESSAGE);
                    return;}
                pstmt.setInt(5,store);
            }

            if(priceField.getText().isEmpty()){           //PRICE INPUT
                JOptionPane.showMessageDialog(null,"'PRICE' can't be empty!!","Invalid Input",JOptionPane.ERROR_MESSAGE);
                return;
            }else{                
                try{                                                        
                    store = Integer.parseInt(priceField.getText());        //Validating Int Input
                }catch(NumberFormatException nfe){
                    JOptionPane.showMessageDialog(null,"Please enter valid 'PRICE'","Invalid Input",JOptionPane.ERROR_MESSAGE);
                    return;}
                
                String tquery = "select max(total_paid) from unpaid_sales_ledger "+
                                "where sale_id = ? group by (sale_id);";
                pstmt2 = con.prepareStatement(tquery);
                pstmt2.setInt(1, Integer.parseInt(IDField.getText()));
                ResultSet rs = pstmt2.executeQuery();
                if(rs.next()){                          //Changing status based on price change
                    if(rs.getInt(1)>=store){
                        pstmt.setString(8,"P");
                        resultFieldOutput = "Status: P, Total Paid: "+String.valueOf(rs.getInt(1));
                    }else{
                        pstmt.setString(8, "U");
                        customerIDBoolean = true;
                        resultFieldOutput = "Status: U, Total Paid: "+String.valueOf(rs.getInt(1));
                    }
                }else {
                    pstmt.setString(8,"P");
                    resultFieldOutput = "Status: P, Total Paid: NULL";
                }
                pstmt.setInt(6,store);
                }

            if(dateField.getText().isEmpty()){           //DATE INPUT
                JOptionPane.showMessageDialog(null,"'DATE' can't be empty!!","Invalid Input",JOptionPane.ERROR_MESSAGE);
                return;
            }else{
                try{
                    pstmt.setDate(7,Date.valueOf(dateField.getText()));
                }catch(IllegalArgumentException iae){
                    JOptionPane.showMessageDialog(null,"Please Enter 'DATE' in correct manner!!","Invalid Input",JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            if(customerIdField.getText().isEmpty()){                        //CUSTOMER_ID INPUT INPUT
                System.out.println("Empty");
                if(customerIDBoolean){
                    System.out.println("true");
                    JOptionPane.showMessageDialog(null,"'CUSTOMER_ID' can't be empty when 'STATUS' is set to UNPAID","",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                System.out.println("null");
                pstmt.setNull(1, java.sql.Types.INTEGER);
            }else{
                try{                                                        //Validating Int Input
                    store = Integer.parseInt(customerIdField.getText());
                }catch(NumberFormatException nfe){
                    JOptionPane.showMessageDialog(null,"Please enter valid 'CUSTOMER_ID'","Invalid Input",JOptionPane.ERROR_MESSAGE);
                    return;}
                    pstmt.setInt(1,store);
            }

            try{                                            //Setting up sale ID input
                store = Integer.parseInt(IDField.getText());
            }catch(NumberFormatException nfe){
                JOptionPane.showMessageDialog(null,"Please enter valid 'SALE ID'","Invalid Input",JOptionPane.ERROR_MESSAGE);
                return;}
            pstmt.setInt(9,store);

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null,"UPDATE Succesfull","Insert Update",JOptionPane.PLAIN_MESSAGE);
            resultField.setText(resultFieldOutput);

        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e,"Insert Update",JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==IDSubmitButton){
            new Thread(() -> {
                fetchFunc();
            }).start();
        }
        if(e.getSource()==submitButton){
            new Thread(() -> {
                saveFunc();
            }).start();
        }

        if(e.getSource()==searchButton){new NavigateSalesLedger(con);}
        
    }
}