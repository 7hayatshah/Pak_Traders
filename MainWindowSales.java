import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class MainWindowSales implements ActionListener{
    private JButton addButton1, addButton2, addButton3;
    private JButton viewButton1, viewButton2, viewButton3;
    private JButton alterButton1, alterButton2;
    private JButton deleteButton, productsButton;
    private JFrame frame;
    
    Connection con;
    MainWindowSales(Connection con){
        this.con = con;
        // Create the frame
        frame = new JFrame("Sales Management");
        frame.setSize(500, 400);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().setBackground(new Color(240, 248, 255)); // Light blue shade

        // Fonts
        Font headingFont = new Font("Arial", Font.BOLD, 16);
        Font buttonFont = new Font("Arial", Font.BOLD, 16);

        JLabel pLabel = new JLabel("P");
        pLabel.setBounds(380, 140, 100, 70);
        pLabel.setFont(new Font("Arial", Font.BOLD, 90));
        frame.add(pLabel);

        JLabel tLabel = new JLabel("T");
        tLabel.setBounds(380, 160, 100, 200);
        tLabel.setFont(new Font("Arial", Font.BOLD, 90));
        frame.add(tLabel);

        // Button color settings
        Color buttonBackground = new Color(0, 153, 76);
        Color buttonForeground = Color.WHITE;

        JLabel MainLabel = new JLabel("SALES MANAGEMENT");
        MainLabel.setFont(new Font("Consolas",Font.BOLD,25));
        MainLabel.setBounds(130,310,250,30);
        frame.add(MainLabel);
        
        // Partition 1: Add Section
        JLabel addLabel = new JLabel("ADD:");
        addLabel.setFont(headingFont);
        addLabel.setBounds(20, 20, 100, 30);
        frame.add(addLabel);

        addButton1 = new JButton("SALE");
        addButton1.setBounds(120, 20, 100, 30);
        addButton1.setBackground(buttonBackground);
        addButton1.setForeground(buttonForeground);
        addButton1.setFocusable(false);
        addButton1.addActionListener(this);
        addButton1.setFont(buttonFont);
        frame.add(addButton1);

        addButton2 = new JButton("CUSTOMER");
        addButton2.setBounds(230, 20, 120, 30);
        addButton2.setBackground(buttonBackground);
        addButton2.setForeground(buttonForeground);
        addButton2.setFocusable(false);
        addButton2.addActionListener(this);
        addButton2.setFont(new Font("Arial", Font.BOLD, 14));
        frame.add(addButton2);

        addButton3 = new JButton("LOAN");
        addButton3.setBounds(360, 20, 100, 30);
        addButton3.setBackground(buttonBackground);
        addButton3.setForeground(buttonForeground);
        addButton3.setFocusable(false);
        addButton3.addActionListener(this);
        addButton3.setFont(buttonFont);
        frame.add(addButton3);

        // Partition 2: View Section
        JLabel viewLabel = new JLabel("VIEW:");
        viewLabel.setFont(headingFont);
        viewLabel.setBounds(20, 80, 100, 30);
        frame.add(viewLabel);

        viewButton1 = new JButton("SALE");
        viewButton1.setBounds(120, 80, 100, 30);
        viewButton1.setBackground(buttonBackground);
        viewButton1.setForeground(buttonForeground);
        viewButton1.setFocusable(false);
        viewButton1.addActionListener(this);
        viewButton1.setFont(buttonFont);
        frame.add(viewButton1);

        viewButton2 = new JButton("CUSTOMER");
        viewButton2.setBounds(230, 80, 120, 30);
        viewButton2.setBackground(buttonBackground);
        viewButton2.setForeground(buttonForeground);
        viewButton2.setFocusable(false);
        viewButton2.addActionListener(this);
        viewButton2.setFont(new Font("Arial", Font.BOLD, 14));
        frame.add(viewButton2);

        viewButton3 = new JButton("LOAN");
        viewButton3.setBounds(360, 80, 100, 30);
        viewButton3.setBackground(buttonBackground);
        viewButton3.setForeground(buttonForeground);
        viewButton3.setFocusable(false);
        viewButton3.addActionListener(this);
        viewButton3.setFont(buttonFont);
        frame.add(viewButton3);

        // Partition 3: Alter Section
        JLabel alterLabel = new JLabel("ALTER:");
        alterLabel.setFont(headingFont);
        alterLabel.setBounds(20, 140, 100, 30);
        frame.add(alterLabel);

        alterButton1 = new JButton("SALE");
        alterButton1.setBounds(120, 140, 100, 30);
        alterButton1.setBackground(buttonBackground);
        alterButton1.setForeground(buttonForeground);
        alterButton1.setFocusable(false);
        alterButton1.addActionListener(this);
        alterButton1.setFont(buttonFont);
        frame.add(alterButton1);

        alterButton2 = new JButton("CUSTOMER");
        alterButton2.setBounds(230, 140, 120, 30);
        alterButton2.setBackground(buttonBackground);
        alterButton2.setForeground(buttonForeground);
        alterButton2.setFocusable(false);
        alterButton2.addActionListener(this);
        alterButton2.setFont(new Font("Arial", Font.BOLD, 14));
        frame.add(alterButton2);

        // Partition 4: Delete Section
        JLabel deleteLabel = new JLabel("DELETE:");
        deleteLabel.setFont(headingFont);
        deleteLabel.setBounds(20, 200, 100, 30);
        frame.add(deleteLabel);

        deleteButton = new JButton("RECORD");
        deleteButton.setBounds(120, 200, 230, 30);
        deleteButton.setBackground(buttonBackground);
        deleteButton.setForeground(buttonForeground);
        deleteButton.setFocusable(false);
        deleteButton.addActionListener(this);
        deleteButton.setFont(buttonFont);
        frame.add(deleteButton);

        // Partition 5: Products Section
        JLabel productsLabel = new JLabel("ITEMS:");
        productsLabel.setFont(headingFont);
        productsLabel.setBounds(20, 260, 150, 30);
        frame.add(productsLabel);

        productsButton = new JButton("MANAGE");
        productsButton.setBounds(120, 260, 230, 30);
        productsButton.setBackground(buttonBackground);
        productsButton.setForeground(buttonForeground);
        productsButton.setFocusable(false);
        productsButton.addActionListener(this);
        productsButton.setFont(buttonFont);
        frame.add(productsButton);

        // Make the frame visible
        frame.setVisible(true);
    }

    JFrame getSalesFrame(){
        return frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==addButton1){new InsertSalesLedger(con);}
        if(e.getSource()==addButton2){new InsertNewCostumer(con);}
        if(e.getSource()==addButton3){new InsertSalesLoanLedger(con,true);}

        if(e.getSource()==viewButton1){new NavigateSalesLedger(con);}
        if(e.getSource()==viewButton2){new NavigateCustomer(con);}
        if(e.getSource()==viewButton3){new NavigateLoanLedger(con);}

        if(e.getSource()==alterButton1){new AlterSalesLedger(con);}
        if(e.getSource()==alterButton2){new AlterCustomer(con);}

        if(e.getSource()==deleteButton){new Delete(con);}

        if(e.getSource()==productsButton){new ProductWindow(con);}      
    }
}
