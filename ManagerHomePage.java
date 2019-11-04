import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;

public class ManagerHomePage extends JFrame
{
    private Bank bank;
    private LoginInterface father;
    private Customer selectedCustomer;
    private ManagerHomePage self = this;

    private JComboBox customerList = new JComboBox<String>();   // choose a customer to check his transaction
    private DefaultTableModel transactionTableModel = new DefaultTableModel();
    private JTable transactionTable = new JTable(); // show transaction of a specific customer

    ManagerHomePage(Bank bank, LoginInterface father)
    {
        this.bank = bank;
        this.father = father;

        setTitle("Manger");
        setSize(500,550);
        setResizable(false);
        setLayout(new FlowLayout());
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                closeFrame();
            }
        });

        customerList.setEditable(false);
        customerList.setEnabled(true);
        customerListListener customerListL = new customerListListener();
        customerList.addItemListener(customerListL);
        for(Customer item : bank.getCustomers())    // add customer into the customerList
        {
            String name = item.getName().toString();
            String id = item.getUserID();
            if(!item.getLoans().isEmpty())  // if the customer owe bank money, he or she will be marked with a "*"
            {
                customerList.addItem(name + "(" + id + ")" + "*");
            }
            else
            {
                customerList.addItem(name + "(" + id + ")");
            }
        }

        JTableHeader transactionHeader = transactionTable.getTableHeader();
        transactionHeader.setResizingAllowed(true);
        JScrollPane transactionScroll = new JScrollPane(transactionTable);
        String[] transactionHeaderName = {"Account", "Currency", "Amount","Source\\Target", "Date"};
        transactionTableModel.setColumnIdentifiers(transactionHeaderName);
        transactionTable.setModel(transactionTableModel);
        transactionTable.setEnabled(false);
        refreshTransactionTable();

        JButton jbtReport = new JButton("Today Report");
        reportListener reportL = new reportListener();
        jbtReport.addActionListener(reportL);

        add(customerList);
        add(transactionScroll);
        add(jbtReport);
    }

    class customerListListener implements ItemListener
    {
        @Override
        public void itemStateChanged(ItemEvent e)
        {
            if(ItemEvent.SELECTED == e.getStateChange())
            {
                String selected = e.getItem().toString();
                int start = selected.indexOf("(");
                int end = selected.indexOf(")");

                String id = selected.substring(start+1, end);

                for(Customer item : bank.getCustomers())
                {
                    if(item.getUserID().equals(id))
                    {
                        selectedCustomer = item;
                        break;
                    }
                }

                refreshTransactionTable();
            }
        }
    }

    class reportListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            ReportInterface reportInterface = new ReportInterface(bank, self);
            setVisible(false);
            reportInterface.setVisible(true);
        }
    }

    public void refreshTransactionTable()
    {
        if(selectedCustomer == null)
        {
            return;
        }
        transactionTableModel.setRowCount(0);
        for(Account account : selectedCustomer.getAccounts())
        {
            for(Balance balance : account.getBalance())
            {
                for(Transaction transaction : balance.getTransactions())
                {
                    String acc = account.getAccountID();    // account ID
                    String currencyType = balance.getCurrencyType();
                    String amount = String.format(" %.2f",transaction.getAmount());
                    String sNtID = transaction.getSourceNtargetID();    // source or target ID
                    String date = transaction.getDate().toString();
                    transactionTableModel.addRow(new String[]{acc, currencyType, amount,sNtID, date});
                }
            }
        }
    }

    private void closeFrame()
    {
        father.setVisible(true);
        dispose();
    }
}
