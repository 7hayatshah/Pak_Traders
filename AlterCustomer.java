import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AlterCustomer implements ActionListener{
    Connection con;
    JTextField customerIDField , nameField, addressField, contactField, resultField;
    JButton submitButton, searchButton, IDSubmitButton;
    AlterCustomer(Connection con){
        this.con = con;    
        // Create the JFrame
        JFrame frame = new JFrame("Change Customer data");
        frame.setSize(470, 360);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);
        frame.setResizable(false);
        
        // Set background color
        frame.getContentPane().setBackground(new Color(240, 248, 255)); // Light blue shade

        // Font settings
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font textFieldFont = new Font("Arial", Font.PLAIN, 14);

        // Name Label and TextField

        JLabel mainLabel = new JLabel("ALTER: CUSTOMER DETAILS");
        mainLabel.setBounds(100, 270, 270, 40);
        mainLabel.setFont(new Font("Arial", Font.BOLD, 18));
        frame.add(mainLabel);

        JLabel customerIDLabel = new JLabel("CUSTOMER ID^:");
        customerIDLabel.setBounds(20, 20, 110, 25);
        customerIDLabel.setFont(labelFont);
        frame.add(customerIDLabel);

        customerIDField = new JTextField();
        customerIDField.setBounds(140, 20, 90, 25);
        customerIDField.setFont(textFieldFont);
        frame.add(customerIDField);
        
        JLabel nameLabel = new JLabel("NAME^:");
        nameLabel.setBounds(20, 60, 100, 25);
        nameLabel.setFont(labelFont);
        frame.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(130, 60, 300, 25);
        nameField.setFont(textFieldFont);
        frame.add(nameField);

        // Address Label and TextField
        JLabel addressLabel = new JLabel("ADDRESS:");
        addressLabel.setBounds(20, 100, 100, 25);
        addressLabel.setFont(labelFont);
        frame.add(addressLabel);

        addressField = new JTextField();
        addressField.setBounds(130, 100, 300, 25);
        addressField.setFont(textFieldFont);
        frame.add(addressField);

        // Contact Label and TextField
        JLabel contactLabel = new JLabel("CONTACT:");
        contactLabel.setBounds(20, 140, 100, 25);
        contactLabel.setFont(labelFont);
        frame.add(contactLabel);

        contactField = new JTextField();
        contactField.setBounds(130, 140, 300, 25);
        contactField.setFont(textFieldFont);
        frame.add(contactField);

        // Result Label and TextField
        JLabel resultLabel = new JLabel("Result:");
        resultLabel.setBounds(20, 180, 100, 25);
        resultLabel.setFont(labelFont);
        frame.add(resultLabel);

        resultField = new JTextField();
        resultField.setBounds(130, 180, 300, 25);
        resultField.setFont(textFieldFont);
        resultField.setEditable(false);
        frame.add(resultField);

        // Submit Button
        submitButton = new JButton("SUBMIT");
        submitButton.setBounds(175, 230, 120, 30);
        submitButton.setBackground(new Color(0, 153, 76)); // Green shade
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitButton.setFocusable(false);
        submitButton.addActionListener(this);
        frame.add(submitButton);

        // Search Button
        searchButton = new JButton("SEARCH");
        searchButton.setBounds(240, 20, 90, 25);
        searchButton.setBackground(new Color(0, 153, 76)); // Green shade
        searchButton.setForeground(Color.WHITE);
        searchButton.setFont(new Font("Arial", Font.BOLD, 12));
        searchButton.setFocusable(false);
        searchButton.addActionListener(this);
        frame.add(searchButton);

        // Customer ID submit Button
        IDSubmitButton = new JButton("SUBMIT");
        IDSubmitButton.setBounds(340, 20, 90, 25);
        IDSubmitButton.setBackground(new Color(0, 153, 76));
        IDSubmitButton.setForeground(Color.WHITE);
        IDSubmitButton.setFont(new Font("Arial", Font.BOLD, 12));
        IDSubmitButton.setFocusable(false);
        IDSubmitButton.addActionListener(this);
        frame.add(IDSubmitButton);

        // Make the frame visible
        frame.setVisible(true);
    }

    void fetchFunc(){
        String query = "select costumer_name, address, contact from costumers where costumer_id = ?;";
        int ID;
        try{
            ID = Integer.parseInt(customerIDField.getText());
        }catch(IllegalArgumentException iae){
            JOptionPane.showMessageDialog(null, "Invalid 'Customer ID' input", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        PreparedStatement pstmt = null;
        try{
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, ID);
            ResultSet rs = pstmt.executeQuery();

            //Setting TextFields
            if(rs.next()){

                nameField.setText(rs.getString(1));
                addressField.setText(rs.getString(2));
                contactField.setText(rs.getString(3));
            }else{
                JOptionPane.showMessageDialog(null,"No data found","Error! Contact Developer",JOptionPane.ERROR_MESSAGE);
            }
        }catch(SQLException sqle){
            JOptionPane.showMessageDialog(null,sqle,"Error! Contact Developer",JOptionPane.ERROR_MESSAGE);
        }

        finally{try{
            if(pstmt != null){pstmt.close();}
            }catch(SQLException sqle){}}
    }

    void saveFunc(){
        String query =  "UPDATE costumers "+
                        "SET costumer_name = ?, address =?, contact = ? WHERE costumer_id =?;";
        int ID;
        PreparedStatement pstmt = null;
        
        try{
            ID = Integer.parseInt(customerIDField.getText());
        }catch(IllegalArgumentException iae){
            JOptionPane.showMessageDialog(null, "Invalid 'Customer ID' input", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(nameField.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"'NAME' can't be empty","Invalid Input",JOptionPane.ERROR_MESSAGE);
            return;
        }

        try{
            pstmt = con.prepareStatement(query);
            
            pstmt.setString(1, nameField.getText());

            if(addressField.getText().isEmpty()){pstmt.setNull(2, java.sql.Types.VARCHAR);
            }else{pstmt.setString(2,addressField.getText());}

            if(contactField.getText().isEmpty()){pstmt.setNull(3, java.sql.Types.VARCHAR);
            }else{pstmt.setString(3,contactField.getText());}

            pstmt.setInt(4,ID);
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Update Succesful", "", JOptionPane.PLAIN_MESSAGE);
        }catch(SQLException sqle){
            JOptionPane.showMessageDialog(null,sqle,"Error! Contact Developer",JOptionPane.ERROR_MESSAGE);
        }

        finally{try{
            if(pstmt != null){pstmt.close();}
            }catch(SQLException sqle){}}        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==searchButton){new NavigateCustomer(con);}

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
    }
}
