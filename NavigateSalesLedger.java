import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.Color;
import java.awt.Font;

public class NavigateSalesLedger implements ActionListener, FocusListener{
    JTextField fromField, toField, totalEarningField;
    JButton totalEarningButton;
    JButton fullDataButton, paidOnlyButton, unpaidOnlyButton, saleIDButton;
    JTextField saleIField;
    JButton searchButton;
    JTextField costumerIdField, itemField;
    JButton goButton;
    DefaultTableModel tableModel;
    Connection con;
    String query;

    NavigateSalesLedger(Connection con){
        this.con = con;

        // Create the JFrame
        JFrame frame = new JFrame("View Ledger Entries");
        frame.setSize(1000, 615);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(null);

        // Set background color
        frame.getContentPane().setBackground(new Color(240, 248, 255)); // Light blue shade

        // Font settings
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font buttonFont = new Font("Arial", Font.BOLD, 15);
        Font textFieldFont = new Font("Arial", Font.PLAIN, 14);

        // First Partition: Set Date
        JLabel setDateLabel = new JLabel("SET DATE:");
        setDateLabel.setBounds(20, 20, 100, 25);
        setDateLabel.setFont(labelFont);
        frame.add(setDateLabel);

        JLabel fromLabel = new JLabel("FROM~:");
        fromLabel.setBounds(40, 50, 60, 25);
        fromLabel.setFont(labelFont);
        frame.add(fromLabel);

        fromField = new JTextField();
        fromField.setBounds( 105, 50, 140, 25);
        fromField.setFont(textFieldFont);
        frame.add(fromField);

        JLabel toLabel = new JLabel("TO~:");
        toLabel.setBounds(270, 50, 40, 25);
        toLabel.setFont(labelFont);
        frame.add(toLabel);

        toField = new JTextField();
        toField.setBounds( 310, 50, 145, 25);
        toField.setFont(textFieldFont);
        frame.add(toField);

        JLabel totalRevenueLabel = new JLabel("TOTAL EARNING:");
        totalRevenueLabel.setBounds(500, 20, 130, 25);
        totalRevenueLabel.setFont(labelFont);
        frame.add(totalRevenueLabel);

        totalEarningField = new JTextField();
        totalEarningField.setBounds(500, 50, 145, 25);
        totalEarningField.setEditable(false);
        totalEarningField.setFont(textFieldFont);
        frame.add(totalEarningField);

        totalEarningButton = new JButton("CALCULATE");
        totalEarningButton.setBounds(655, 50, 115, 25);
        totalEarningButton.setFont(new Font("Arial",Font.BOLD,13));
        totalEarningButton.setForeground(Color.WHITE);
        totalEarningButton.setBackground(new Color(0, 153, 76));
        totalEarningButton.addActionListener(this);
        totalEarningButton.setFocusable(false);
        frame.add(totalEarningButton);

        // Second Partition: Fetch Complete Data
        JLabel fetchDataLabel = new JLabel("FETCH COMPLETE DATA:");
        fetchDataLabel.setBounds(20, 100, 200, 25);
        fetchDataLabel.setFont(labelFont);
        frame.add(fetchDataLabel);

        fullDataButton = new JButton("FULL DATA");
        fullDataButton.setBounds(40, 130, 120, 30);
        fullDataButton.setBackground(new Color(0, 153, 76));
        fullDataButton.setForeground(Color.WHITE);
        fullDataButton.setFont(buttonFont);
        fullDataButton.setFocusable(false);
        fullDataButton.addActionListener(this);
        frame.add(fullDataButton);

        paidOnlyButton = new JButton("PAID ONLY");
        paidOnlyButton.setBounds(170, 130, 120, 30);
        paidOnlyButton.setFont(buttonFont);
        paidOnlyButton.setBackground(new Color(0, 153, 76));
        paidOnlyButton.setForeground(Color.WHITE);
        paidOnlyButton.setFocusable(false);
        paidOnlyButton.addActionListener(this);
        frame.add(paidOnlyButton);

        unpaidOnlyButton = new JButton("UNPAID ONLY");
        unpaidOnlyButton.setBounds(300, 130, 155, 30);
        unpaidOnlyButton.setFont(buttonFont);
        unpaidOnlyButton.setBackground(new Color(0, 153, 76));
        unpaidOnlyButton.setForeground(Color.WHITE);
        unpaidOnlyButton.setFocusable(false);
        unpaidOnlyButton.addActionListener(this);
        frame.add(unpaidOnlyButton);

        JLabel saleIDLabel = new JLabel("SALE ID DATA:");
        saleIDLabel.setFont(labelFont);
        saleIDLabel.setBounds(500, 100, 200, 25);
        frame.add(saleIDLabel);

        saleIField = new JTextField();
        saleIField.setFont(textFieldFont);
        saleIField.setBounds(500, 130, 120, 30);
        frame.add(saleIField);

        saleIDButton = new JButton("SUBMIT");
        saleIDButton.setBounds(630, 130, 100, 30);
        saleIDButton.setFont(buttonFont);
        saleIDButton.setFocusable(false);
        saleIDButton.addActionListener(this);
        saleIDButton.setBackground(new Color(0, 153, 76));
        saleIDButton.setForeground(Color.WHITE);
        frame.add(saleIDButton);

        // Third Partition: Summarize
        JLabel singlePersonLabel = new JLabel("SINGLE PERSON DATA:");
        singlePersonLabel.setBounds(20, 180, 200, 25);
        singlePersonLabel.setFont(labelFont);
        frame.add(singlePersonLabel);

        JLabel costumerIdLabel = new JLabel("C-ID:");
        costumerIdLabel.setBounds( 20, 212, 200, 25);
        costumerIdLabel.setFont(labelFont);
        frame.add(costumerIdLabel);

        costumerIdField = new JTextField();
        costumerIdField.setBounds(60, 210, 50, 30);
        costumerIdField.setFont(textFieldFont);
        costumerIdField.addFocusListener(this);
        frame.add(costumerIdField);

        searchButton = new JButton("SEARCH");
        searchButton.setBounds(120, 210, 90, 30);
        searchButton.setFont(new Font("Arial",Font.BOLD,13));
        searchButton.setBackground(new Color(0, 153, 76));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusable(false);
        searchButton.addActionListener(this);
        frame.add(searchButton);

        JLabel itemLabel = new JLabel("ITEM:");
        itemLabel.setBounds(220, 212, 120, 25);
        itemLabel.setFont(labelFont);
        frame.add(itemLabel);

        itemField = new JTextField();
        itemField.setBounds(265, 210, 120, 30);
        itemField.setFont(textFieldFont);
        frame.add(itemField);

        goButton = new JButton("GO");
        goButton.setBounds(395, 210, 60, 30);
        goButton.setFont(buttonFont);
        goButton.setBackground(new Color(0, 153, 76));
        goButton.setForeground(Color.WHITE);
        goButton.setFocusable(false);
        goButton.addActionListener(this);
        frame.add(goButton);

        // Fourth Partition: JTable
        String[] columnNames = {
            "ID", "Customer ID", "Product Name", "Product Details", 
            "Weight", "Quantity", "Price", "Sale Date", "Status"
        };

        tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBounds(20, 260, 940, 300);
        frame.add(tableScrollPane);

        // Customize table appearance and column widths
        table.setFont(textFieldFont);
        table.setRowHeight(25);
        table.getTableHeader().setFont(labelFont);
        table.getTableHeader().setBackground(new Color(0, 153, 76)); // Green header background
        table.getTableHeader().setForeground(Color.WHITE);

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(55); // Sale ID
        columnModel.getColumn(1).setPreferredWidth(95); // Customer ID
        columnModel.getColumn(2).setPreferredWidth(150); // Product Name
        columnModel.getColumn(3).setPreferredWidth(200); // Product Details
        columnModel.getColumn(4).setPreferredWidth(80); // Weight
        columnModel.getColumn(5).setPreferredWidth(80); // Quantity
        columnModel.getColumn(6).setPreferredWidth(100); // Price
        columnModel.getColumn(7).setPreferredWidth(120); // Sale Date
        columnModel.getColumn(8).setPreferredWidth(50); // Status
   
        // Make the frame visible
        frame.setVisible(true);
    }

    void secondPartitionFunc(){
        tableModel.setRowCount(0);
        try(PreparedStatement pstmt = con.prepareStatement(query);){
            ResultSet rs;

            //HANDLING DATE INPUT EXCEPTION
            try{
                pstmt.setDate(1,Date.valueOf(fromField.getText()));
                pstmt.setDate(2,Date.valueOf(toField.getText()));
                rs = pstmt.executeQuery();
                }catch(IllegalArgumentException iae){
                toField.setBackground(new Color(0xFFCCCB));
                fromField.setBackground(new Color(0xFFCCCB));
                return;
                }
                toField.setBackground(Color.WHITE);
                fromField.setBackground(Color.WHITE);

            while(rs.next()){
            tableModel.addRow(new Object[]{rs.getInt("sale_id"),rs.getInt("costumer_id"),rs.getString("product_name"),rs.getString("product_details"),rs.getInt("weight"),rs.getInt("quantity"),rs.getInt("price"),rs.getDate("sale_date"),rs.getString("status")});
            }

            pstmt.close();
            rs.close();    
        }catch(SQLException sqle){System.out.println(sqle);}
    }

    //THIRD PARTITION IMPLEMENTED
    void nameOnlyFunc(){
        costumerIdField.setBackground(Color.WHITE);
        tableModel.setRowCount(0);
        query="select * from sales_Ledger where costumer_id = ? order by(sale_date);";
        try(PreparedStatement pstmt = con.prepareStatement(query)){
            try{        //Validating costumer_id input
            pstmt.setInt(1, Integer.parseInt(costumerIdField.getText()));
            }catch(IllegalArgumentException iae){
                JOptionPane.showMessageDialog(null,"couldn't execute query\nCheck if 'C-ID' input is correct","Execution Problem",JOptionPane.ERROR_MESSAGE);
                return;
            }
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                tableModel.addRow(new Object[]{rs.getInt("sale_id"),rs.getInt("costumer_id"),rs.getString("product_name"),rs.getString("product_details"),rs.getInt("weight"),rs.getInt("quantity"),rs.getInt("price"),rs.getDate("sale_date"),rs.getString("status")});
                }

        }catch(SQLException sqle){
            JOptionPane.showMessageDialog(null, sqle, "Error! Contact Developer", JOptionPane.ERROR_MESSAGE);
        }
    }

    void nameAndItemFunc(){
        costumerIdField.setBackground(Color.WHITE);
        tableModel.setRowCount(0);
        query="select * from sales_Ledger where costumer_id=? AND product_name=? order by(sale_date);";
        try(PreparedStatement pstmt = con.prepareStatement(query)){
            try{        //Validating costumer_id & Item name input
            pstmt.setInt(1, Integer.parseInt(costumerIdField.getText()));
            pstmt.setString(2,itemField.getText());
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,"couln't execute query\nCheck if 'C-ID' & 'ITEM' input is correct","Execution Problem",JOptionPane.ERROR_MESSAGE);
                return;
            }
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                tableModel.addRow(new Object[]{rs.getInt("sale_id"),rs.getInt("costumer_id"),rs.getString("product_name"),rs.getString("product_details"),rs.getInt("weight"),rs.getInt("quantity"),rs.getInt("price"),rs.getDate("sale_date"),rs.getString("status")});
                }
        }catch(SQLException sqle){
            JOptionPane.showMessageDialog(null, sqle, "Error! Contact Developer", JOptionPane.ERROR_MESSAGE);
        }
    }

    void earningCalcFunc(){
        int earning;
        PreparedStatement pstmt = null;
        query = "select sum(price) from sales_ledger "+
                "WHERE status = 'P' and sale_date between ? and ? and "+
                "sale_id NOT IN(select sale_id from unpaid_sales_ledger where payment_receive_date between ? AND ?)";
        try{
            pstmt = con.prepareStatement(query);
            try{
            pstmt.setDate(1, Date.valueOf(fromField.getText()));
            pstmt.setDate(2, Date.valueOf(toField.getText()));
            pstmt.setDate(3, Date.valueOf(fromField.getText()));
            pstmt.setDate(4, Date.valueOf(toField.getText()));
            }catch(IllegalArgumentException iae){
                toField.setBackground(new Color(0xFFCCCB));
                fromField.setBackground(new Color(0xFFCCCB));
                return;
            }
            toField.setBackground(Color.WHITE);
            fromField.setBackground(Color.WHITE);

            ResultSet rs = pstmt.executeQuery();
            rs.next();
            earning = rs.getInt(1);

            query = "select sum(current_payment_amount) from unpaid_sales_ledger where payment_receive_date between ? AND ?;";
            pstmt = con.prepareStatement(query);
            try{
            pstmt.setDate(1, Date.valueOf(fromField.getText()));
            pstmt.setDate(2, Date.valueOf(toField.getText()));
            rs = pstmt.executeQuery();
            }catch(IllegalArgumentException iae){
                return;
            }
            if(rs.next()){earning += rs.getInt(1);
            }
            totalEarningField.setText(String.valueOf(earning));
        }catch(SQLException sqle){
            JOptionPane.showMessageDialog(null, sqle,"",JOptionPane.ERROR_MESSAGE);
        }
        finally{try{
            if(pstmt != null){pstmt.close();}
            }catch(SQLException sqle){}} 
    }

    void IDdataFunc(){
        tableModel.setRowCount(0);
        saleIField.setBackground(Color.WHITE);
        query = "select * from sales_ledger where sale_id = ? order by(sale_date);";
        int saleID;
        try{
            saleID = Integer.parseInt(saleIField.getText());
        }catch(IllegalArgumentException iae){
            saleIField.setBackground(new Color(0xFFCCCB));
            return;
        }
        try(PreparedStatement pstmt = con.prepareStatement(query)){
            saleIField.setBackground(Color.WHITE);
            pstmt.setInt(1,saleID);
           ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                tableModel.addRow(new Object[]{rs.getInt("sale_id"),rs.getInt("costumer_id"),rs.getString("product_name"),rs.getString("product_details"),rs.getInt("weight"),rs.getInt("quantity"),rs.getInt("price"),rs.getDate("sale_date"),rs.getString("status")});
            }
        }catch(SQLException sqle){}
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==fullDataButton){          //FullDataFunc
            query = "select * from sales_Ledger where sale_date between ? and ? order by(sale_date);";
            new Thread(() ->{
            secondPartitionFunc();}).start();
        }
        if(e.getSource()==paidOnlyButton){          //paidOnlyFunc
            query = "select * from sales_Ledger where status='P' AND sale_date between ? and ? order by(sale_date);";
            new Thread(() ->{
            secondPartitionFunc();}).start();
        }
        if(e.getSource()==unpaidOnlyButton){          //unPaidOnlyFunc
            query = "select * from sales_Ledger where status='U' AND sale_date between ? and ? order by(sale_date);";
            new Thread(() ->{
            secondPartitionFunc();}).start();
        }
        if(e.getSource()==searchButton){
            new NavigateCustomer(con);
        }
        if(e.getSource()==goButton){
            if(costumerIdField.getText().isEmpty()){
                costumerIdField.setBackground(new Color(0xFFCCCB));
            }else{
                new Thread(()->{
                if(itemField.getText().isEmpty()){
                    nameOnlyFunc();
                }else{nameAndItemFunc();}
                }).start();
            }
        }
        if(e.getSource()==totalEarningButton){
            new Thread(()->{
                earningCalcFunc();
            }).start();
        }
        if(e.getSource()==saleIDButton){
            new Thread(()->{
                IDdataFunc();
            }).start();
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        if(e.getSource()==costumerIdField)
            itemField.setBackground(new Color(0x90EE90));
    }

    @Override
    public void focusLost(FocusEvent e) {
        if(e.getSource()==costumerIdField)
        itemField.setBackground(Color.WHITE);
    }
}