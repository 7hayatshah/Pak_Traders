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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class InsertNewCostumer implements ActionListener{
JTextField nameField, addressField, contactField, resultField;
JButton submitButton;
Connection con;
String name="", address="", contact="";

InsertNewCostumer(Connection con){
        this.con = con;    
        // Create the JFrame
        JFrame frame = new JFrame("Add New Customer");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);
        frame.setResizable(false);
        
        // Set background color
        frame.getContentPane().setBackground(new Color(240, 248, 255)); // Light blue shade

        // Font settings
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font textFieldFont = new Font("Arial", Font.PLAIN, 14);

        // Name Label and TextField
        JLabel nameLabel = new JLabel("NAME^:");
        nameLabel.setBounds(20, 20, 100, 25);
        nameLabel.setFont(labelFont);
        nameLabel.setForeground(new Color(0, 51, 102));
        frame.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(130, 20, 200, 25);
        nameField.setFont(textFieldFont);
        frame.add(nameField);

        // Address Label and TextField
        JLabel addressLabel = new JLabel("ADDRESS:");
        addressLabel.setBounds(20, 60, 100, 25);
        addressLabel.setFont(labelFont);
        addressLabel.setForeground(new Color(0, 51, 102));
        frame.add(addressLabel);

        addressField = new JTextField();
        addressField.setBounds(130, 60, 200, 25);
        addressField.setFont(textFieldFont);
        frame.add(addressField);

        // Contact Label and TextField
        JLabel contactLabel = new JLabel("CONTACT:");
        contactLabel.setBounds(20, 100, 100, 25);
        contactLabel.setFont(labelFont);
        contactLabel.setForeground(new Color(0, 51, 102));
        frame.add(contactLabel);

        contactField = new JTextField();
        contactField.setBounds(130, 100, 200, 25);
        contactField.setFont(textFieldFont);
        frame.add(contactField);

        // Result Label and TextField
        JLabel resultLabel = new JLabel("Result:");
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

        // Make the frame visible
        frame.setVisible(true);
    }

    boolean sameInput(){
        if(nameField.getText().equals(name) && addressField.getText().equals(address) && contactField.getText().equals(contact)){
            JOptionPane.showMessageDialog(null,"Entry Failed!!\nDuplicate Input","",JOptionPane.ERROR_MESSAGE);
            return false;
        }else{
            name = nameField.getText();
            address = addressField.getText();
            contact = contactField.getText();
            return true;
        }
    }

    void newCostumerFunc(){
        String query = "insert into costumers(costumer_name, address, contact) values(?,?,?)";
        PreparedStatement pstmt = null;
        if(nameField.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"'NAME' can't be empty","Invalid Input",JOptionPane.ERROR_MESSAGE);
            return;
        }else{
            try{
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, nameField.getText());

            if(addressField.getText().isEmpty()){
                pstmt.setNull(2, java.sql.Types.VARCHAR);
            }else{pstmt.setString(2, addressField.getText());}
            
            if(addressField.getText().isEmpty()){
                pstmt.setNull(3, java.sql.Types.VARCHAR);
            }else{pstmt.setString(3, contactField.getText());}

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null,"Entry Succesful","",JOptionPane.PLAIN_MESSAGE);
            
            query = "select costumer_id,costumer_name from costumers where costumer_id=(select max(costumer_id) from costumers)";
            pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            resultField.setText("ID: "+String.valueOf(rs.getInt("costumer_id")+" Name: "+rs.getString("costumer_name")));

            }catch(SQLException sqle){
                resultField.setText("Couldn't Prepare Statement");
            }
            finally{
                if(pstmt != null){
                    try{pstmt.close();}catch(SQLException sqle){
                        JOptionPane.showMessageDialog(null,"couldn't close PreparedStatements\n"+sqle,"Report to Developer",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==submitButton){
            new Thread(()-> {
            if(sameInput()){newCostumerFunc();}
            }).start();
        }
    }
}