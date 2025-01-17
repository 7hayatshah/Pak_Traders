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
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductWindow implements ActionListener{
    JTextField itemNameField;
    JButton insertButton;
    JButton deleteButton;
    JButton searchButton;
    DefaultTableModel tableModel;
    Connection con;

    ProductWindow(Connection con){
        this.con = con;
        // Create the JFrame
        JFrame frame = new JFrame("Item Management");
        frame.setSize(300, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);
        frame.setResizable(false);

        // Set background color
        frame.getContentPane().setBackground(new Color(240, 248, 255)); // Light blue shade

        // Font settings
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font textFieldFont = new Font("Arial", Font.PLAIN, 14);

        // Label and TextField for ITEM_NAME
        JLabel itemNameLabel = new JLabel("ITEM_NAME:");
        itemNameLabel.setBounds(20, 20, 100, 25);
        itemNameLabel.setFont(labelFont);
        frame.add(itemNameLabel);

        itemNameField = new JTextField();
        itemNameField.setBounds(20, 50, 130, 25);
        itemNameField.setFont(textFieldFont);
        frame.add(itemNameField);

        // Insert and Delete Buttons
        insertButton = new JButton("INSERT");
        insertButton.setBounds(170, 20, 100, 30);
        insertButton.setBackground(new Color(0, 153, 76));
        insertButton.setForeground(Color.WHITE);
        insertButton.setFont(labelFont);
        insertButton.setFocusable(false);
        insertButton.addActionListener(this);
        frame.add(insertButton);

        deleteButton = new JButton("DELETE");
        deleteButton.setBounds(170, 60, 100, 30);
        deleteButton.setBackground(new Color(0, 153, 76));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFont(labelFont);
        deleteButton.setFocusable(false);
        deleteButton.addActionListener(this);
        frame.add(deleteButton);

        searchButton = new JButton("SEARCH");
        searchButton.setBounds(170, 100, 100, 30);
        searchButton.setBackground(new Color(0, 153, 76));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFont(labelFont);
        searchButton.setFocusable(false);
        searchButton.addActionListener(this);
        frame.add(searchButton);

        // JTable with one column
        String[] columnNames = {"ITEMS"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);

        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBounds(20, 140, 250, 200);
        frame.add(tableScrollPane);

        // Customize table appearance
        table.setFont(textFieldFont);
        table.setRowHeight(25);
        table.getTableHeader().setFont(labelFont);
        table.getTableHeader().setBackground(new Color(0, 153, 76)); // Green header background
        table.getTableHeader().setForeground(Color.WHITE);

        // Make the frame visible
        frame.setVisible(true);

        autoShowAllItems();
    }

    void autoShowAllItems(){
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from products order by(product_name);");

            while(rs.next()){
                tableModel.addRow(new Object[]{rs.getString("product_name")});
            }

            stmt.close();
            rs.close();
        }catch(SQLException sqle){}
    }

    void searchFunc(){
        tableModel.setRowCount(0);
        String query = "select * from products where product_name like ?;";
        try(PreparedStatement pstmt= con.prepareStatement(query);){          
            pstmt.setString(1,"%"+itemNameField.getText()+"%");
            ResultSet rs = pstmt.executeQuery();
            
            while(rs.next()){
                tableModel.addRow(new Object[]{rs.getString("product_name")});
            }
        }catch(SQLException sqle){}
    }

    void insertFunc(){
        String query = "insert into products values(?);";

        try(PreparedStatement pstmt = con.prepareStatement(query);){
            pstmt.setString(1, itemNameField.getText());
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(null,"Insert Succesful!!","Update",JOptionPane.PLAIN_MESSAGE);
        }catch(SQLException sqle){
            JOptionPane.showMessageDialog(null,"Something went wrong\n"+sqle,"Error! Call Developer",JOptionPane.ERROR_MESSAGE);
        }
    }

    void deleteFunc(){
        String query = "delete from products where product_name = ?;";
        try(PreparedStatement pstmt = con.prepareStatement(query);){
            pstmt.setString(1, itemNameField.getText());
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(null,"Deletion Succesful","Update",JOptionPane.PLAIN_MESSAGE);
        }catch(SQLException sqle){
            JOptionPane.showMessageDialog(null,"Something went wrong\n"+sqle,"Error! Call Developer",JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==searchButton){searchFunc();}
        if(e.getSource()==insertButton){insertFunc();}
        if(e.getSource()==deleteButton){deleteFunc();}
    }
}
