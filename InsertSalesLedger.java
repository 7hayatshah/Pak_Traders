import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Color;

public class InsertSalesLedger implements ActionListener , FocusListener{
    JFrame frame;
    JTextField customerIdField, customerNameField;
    JTextField productNameField;
    JTextField productDetailsField;
    JTextField weightField;
    JTextField quantityField;
    JTextField priceField;
    JTextField dateField;
    JTextField statusField;
    JButton customerIdButton;
    JButton productNameButton;
    JButton submitButton;

    String c_ID="", price="", quantity="", weight="";
    String c_name="", product_details="", product_name="", status="", Date1;

    Connection con;
            
    InsertSalesLedger(Connection con){
        this.con = con;
        Font labelFont = new Font("Arial",Font.BOLD,13);
        Font textfieldFont = new Font("Arial",Font.PLAIN,14);

        frame = new JFrame("Add New ledger Entry");        //Create a JFrame
        frame.setSize(650, 470);
        frame.setLayout(null); // Set layout to null
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(240, 248, 255)); // Light blue shade

        // Heading label
        JLabel headingLabel = new JLabel("New Ledger Entry");
        headingLabel.setBounds(130, 10, 405, 30);
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
        customerIdField.setBounds(190, 50, 80, 30);
        customerIdField.setFont(textfieldFont);
        frame.add(customerIdField);

        customerNameField = new JTextField();
        customerNameField.setBounds(275, 50, 210, 30);
        customerNameField.setEditable(false);
        customerNameField.setFont(textfieldFont);
        frame.add(customerNameField);

        customerIdButton = new JButton("SEARCH");
        customerIdButton.setBounds(490, 50, 100, 30);
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
        productNameField.setBounds(190, 90, 295, 30);
        productNameField.setFont(textfieldFont);
        productNameField.addFocusListener(this);
        frame.add(productNameField);

        productNameButton = new JButton("SEARCH");
        productNameButton.setBounds(490, 90, 100, 30);
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
        productDetailsField.setBounds(190, 130, 400, 30);
        productDetailsField.setFont(textfieldFont);
        frame.add(productDetailsField);

        JLabel weightLabel = new JLabel("WEIGHT:");
        weightLabel.setBounds(40, 170, 100, 30);
        weightLabel.setFont(labelFont);
        frame.add(weightLabel);

        weightField = new JTextField();
        weightField.setBounds(190, 170, 400, 30);
        weightField.setFont(textfieldFont);
        frame.add(weightField);

        JLabel quantityLabel = new JLabel("QUANTITY^:");
        quantityLabel.setBounds(40, 210, 100, 30);
        quantityLabel.setFont(labelFont);
        frame.add(quantityLabel);

        quantityField = new JTextField();
        quantityField.setBounds(190, 210, 400, 30);
        quantityField.setFont(textfieldFont);
        frame.add(quantityField);

        JLabel priceLabel = new JLabel("PRICE (TOTAL)^:");
        priceLabel.setBounds(40, 250, 140, 30);
        priceLabel.setFont(labelFont);
        frame.add(priceLabel);

        priceField = new JTextField();
        priceField.setBounds(190, 250, 400, 30);
        priceField.setFont(textfieldFont);
        frame.add(priceField);

        JLabel dateLabel = new JLabel("DATE^:");
        dateLabel.setBounds(40, 290, 100, 30);
        dateLabel.setFont(labelFont);
        frame.add(dateLabel);

        dateField = new JTextField();
        dateField.setBounds(190, 290, 400, 30);
        dateField.setFont(textfieldFont);
        frame.add(dateField);

        JLabel statusLabel = new JLabel("STATUS^:");
        statusLabel.setBounds(40, 330, 100, 30);
        statusLabel.setFont(labelFont);
        frame.add(statusLabel);

        statusField = new JTextField();
        statusField.setBounds(190, 330, 400, 30);
        statusField.setFont(textfieldFont);
        frame.add(statusField);

        // Submit button
        submitButton = new JButton("SUBMIT");
        submitButton.setBounds(40, 370, 550, 30);
        submitButton.setFocusable(false);
        submitButton.setBackground(new Color(0, 153, 76));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Arial",Font.BOLD,15));
        submitButton.addActionListener(this);
        frame.add(submitButton);

        frame.setVisible(true);
    }
    
    void ledgerInserterFunc(){
        String query= "insert into sales_Ledger(costumer_id, product_name, product_details, weight, quantity, price, sale_date, status) values (?,?,?,?,?,?,?,?);";
        int store;
        try(PreparedStatement pstmt = con.prepareStatement(query)){                                                                

            if(customerIdField.getText().isEmpty()){                        //CUSTOMER_ID INPUT INPUT
                if(statusField.getText().equalsIgnoreCase("u")){
                    JOptionPane.showMessageDialog(null,"'CUSTOMER_ID' can't be empty when 'STATUS' is set to UNPAID","Invalid Input",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                pstmt.setNull(1, java.sql.Types.INTEGER);
            }else{
                try{                                                        //Validating Int Input
                    store = Integer.parseInt(customerIdField.getText());
                }catch(NumberFormatException nfe){
                    JOptionPane.showMessageDialog(null,"Please enter valid 'CUSTOMER_ID'","Invalid Input",JOptionPane.ERROR_MESSAGE);
                    return;}

                    pstmt.setInt(1,store);
            }

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
                pstmt.setInt(6,store);
            }

            if(dateField.getText().isEmpty()){           //DATE INPUT
                JOptionPane.showMessageDialog(null,"'DATE' can't be empty!!","Invalid Input",JOptionPane.ERROR_MESSAGE);
                return;
            }else{
                try{
                    pstmt.setDate(7, Date.valueOf(dateField.getText()));
                }catch(IllegalArgumentException iae){
                    JOptionPane.showMessageDialog(null,"Please Enter 'DATE' in correct manner!!","Invalid Input",JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            if(statusField.getText().isEmpty()){
                JOptionPane.showMessageDialog(null,"'STATUS' can't be empty!!","Invalid Input",JOptionPane.ERROR_MESSAGE);
                    return;
            }else{
                if(statusField.getText().equalsIgnoreCase("P") || statusField.getText().equalsIgnoreCase("U")){
                    pstmt.setString(8, statusField.getText().toUpperCase());
            }else{
                JOptionPane.showMessageDialog(null,"Please Insert correct value in 'STATUS'!!","Invalid Input",JOptionPane.ERROR_MESSAGE);
                return;
            }

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null,"Entry Succesfull","Insert Update",JOptionPane.PLAIN_MESSAGE);

            if(statusField.getText().equalsIgnoreCase("U")){new InsertSalesLoanLedger(con,false);}
        }
        }catch(SQLException sqle){
            JOptionPane.showMessageDialog(null,sqle,"Error! Contact Developer",JOptionPane.ERROR_MESSAGE);
        }
    }

    void nameFetcherFunc(){
        String query = "select costumer_name from costumers where costumer_id = ?";
        int ID;
        try{
            ID = Integer.parseInt(customerIdField.getText());
        }catch(NumberFormatException nfe){return;}
        try(PreparedStatement pstmt = con.prepareStatement(query)){
            pstmt.setInt(1, ID);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                customerNameField.setText(rs.getString(1));
            }
        }catch(SQLException sqle){}
    }

    boolean sameInputFunc(){
        boolean output = false;

        if(!customerIdField.getText().equals(c_ID)){output = true;}
        if(!productNameField.getText().equals(product_name)){output = true;}
        if(!productDetailsField.getText().equals(product_details)){output = true;}
        if(!weightField.getText().equals(weight)){output = true;}
        if(!quantityField.getText().equals(quantity)){output = true;}
        if(!priceField.getText().equals(price)){output = true;}
        if(!dateField.getText().equals(Date1)){output = true;}
        if(!statusField.getText().equals(status)){output = true;}

        if(output){
            c_ID = customerIdField.getText();
            product_name =  productNameField.getText();
            product_details = productDetailsField.getText();
            weight = weightField.getText();
            quantity = quantityField.getText();
            price = priceField.getText();
            Date1 = dateField.getText();
            status = statusField.getText();
        }

        if(!output){JOptionPane.showMessageDialog(null,"Entry Failed!!\nDuplicate Input","",JOptionPane.ERROR_MESSAGE);}
        return output;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==submitButton){
            new Thread(() -> {
                if(sameInputFunc()){ledgerInserterFunc();}
            }).start();
        }
        if(e.getSource()==customerIdButton){new NavigateCustomer(con);}
        if(e.getSource()==productNameButton){new ProductWindow(con);}
    }

    @Override
    public void focusGained(FocusEvent e) {
        if(e.getSource()==productNameField){
            new Thread(() -> {
                nameFetcherFunc();
            }).start();
        }
    }

    public void focusLost(FocusEvent arg0) {}
}
