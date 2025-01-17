import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class NavigateCustomer implements ActionListener{
    DefaultTableModel tableModel;
    JTextField nameField;
    JButton button;
    Connection con;

    NavigateCustomer(Connection con){
        this.con = con;

        // Create the JFrame
        JFrame frame = new JFrame("View Customers");
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);
        frame.setResizable(false);

        // Set background color
        frame.getContentPane().setBackground(new Color(240, 248, 255)); // Light blue shade

        // Font settings
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font textFieldFont = new Font("Arial", Font.PLAIN, 14);

        // Name Label and TextField
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(20, 20, 100, 25);
        nameLabel.setFont(labelFont);
        nameLabel.setForeground(new Color(0, 51, 102));
        frame.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(130, 20, 520, 25);
        nameField.setFont(textFieldFont);
        frame.add(nameField);

        // Button
        button = new JButton();
        button.setBounds(660, 20, 90, 25);
        button.setBackground(new Color(0, 153, 76));
        button.setText("SUBMIT");
        button.setFocusable(false);
        button.setForeground(Color.WHITE);
        button.addActionListener(this);
        frame.add(button);

        // JTable Section
        String[] columnNames = {"ID", "Name", "Address", "Contact"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);

        // Adding sample rows for demonstration
        //tableModel.addRow(new Object[]{"1", "Row1-Col2", "Row1-Col3", "Row1-Col4"});

        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBounds(20, 70, 740, 250);
        frame.add(tableScrollPane);

        // Customize table appearance
        table.setFont(textFieldFont);
        table.setRowHeight(25);
        table.getTableHeader().setFont(labelFont);
        table.getTableHeader().setBackground(new Color(0, 153, 76)); // Green header background
        table.getTableHeader().setForeground(Color.WHITE);

        // Adjust column widths
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50); // Small width for the first column
        columnModel.getColumn(1).setPreferredWidth(230);
        columnModel.getColumn(2).setPreferredWidth(230);
        columnModel.getColumn(3).setPreferredWidth(230);

        // Make the frame visible
        frame.setVisible(true);
    }

    void costumerDataFunc(){
        tableModel.setRowCount(0);
        String query= "select * from costumers where costumer_name like ?";
        PreparedStatement pstmt = null;
        if(nameField.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"'NAME' can't be empty","Input a value",JOptionPane.ERROR_MESSAGE);
        }else{
        try{
            pstmt = con.prepareStatement(query);
            pstmt.setString(1,"%"+nameField.getText()+"%");

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
            tableModel.addRow(new Object[]{String.valueOf(rs.getInt("costumer_id")), rs.getString("costumer_name"), rs.getString("address"), rs.getString("contact")});
            }
            
            }catch(SQLException sqle){}
            
            finally{
                if(pstmt != null){
                    try{pstmt.close();}catch(SQLException sqle){
                        JOptionPane.showMessageDialog(null,"couldn't close PreparedStatements\n"+sqle,"Report to Developer",JOptionPane.ERROR_MESSAGE);
                    }
                }
            } //FINALLY END
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==button){
            costumerDataFunc();
        }
    }
}
