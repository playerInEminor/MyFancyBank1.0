import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * report of transaction and income
 */
public class ReportInterface extends JFrame
{
    Bank bank;
    ManagerHomePage father;

    private DefaultTableModel transactionTableModel = new DefaultTableModel();
    private JTable transactionTable = new JTable(); //transaction history
    private DefaultTableModel incomeTableModel = new DefaultTableModel();
    private JTable incomeTable = new JTable();  // income report
    ReportInterface(Bank bank, ManagerHomePage father)
    {
        this.bank = bank;
        this.father = father;

        setTitle("Manger");
        setSize(950,700);
        setResizable(false);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                closeFrame();
            }
        });

        JTableHeader transactionHeader = transactionTable.getTableHeader();
        transactionHeader.setResizingAllowed(true);
        JScrollPane transactionScroll = new JScrollPane(transactionTable);
        String[] transactionHeaderName = {"Account", "Currency", "Amount","Source\\Target", "Date"};
        transactionTableModel.setColumnIdentifiers(transactionHeaderName);
        transactionTable.setModel(transactionTableModel);
        transactionTable.setEnabled(false);
        refreshTransactionTable();

        JTableHeader incomeHeader = incomeTable.getTableHeader();
        incomeHeader.setResizingAllowed(true);
        JScrollPane incomeScroll = new JScrollPane(incomeTable);
        String[] incomeHeaderName = {"Account", "Amount", "Date", "Reason"};
        incomeTableModel.setColumnIdentifiers(incomeHeaderName);
        incomeTable.setModel(incomeTableModel);
        incomeTable.setEnabled(false);
        refreshincomeTable();

        JLabel transactionLabel = new JLabel("Transaction History", JLabel.CENTER);
        JLabel incomeLabel = new JLabel("Income History", JLabel.CENTER);

        JPanel transactionPanel = new JPanel();
        transactionPanel.setLayout(new BorderLayout());
        transactionPanel.add(transactionLabel, BorderLayout.NORTH);
        transactionPanel.add(transactionScroll, BorderLayout.CENTER);

        JPanel incomePanel = new JPanel();
        incomePanel.setLayout(new BorderLayout());
        incomePanel.add(incomeLabel, BorderLayout.NORTH);
        incomePanel.add(incomeScroll, BorderLayout.CENTER);

        add(transactionPanel, BorderLayout.WEST);
        add(incomePanel, BorderLayout.EAST);
    }

    public void refreshTransactionTable()
    {
        transactionTableModel.setRowCount(0);
        for(Customer customer : bank.getCustomers())
        {
            for (Account account : customer.getAccounts()) {
                for (Balance balance : account.getBalance()) {
                    for (Transaction transaction : balance.getTransactions()) {
                        String acc = account.getAccountID();    // account ID
                        String currencyType = balance.getCurrencyType();
                        String amount = String.format(" %.2f", transaction.getAmount());
                        String sNtID = transaction.getSourceNtargetID();    // source or target ID
                        String date = transaction.getDate().toString();
                        transactionTableModel.addRow(new String[]{acc, currencyType, amount, sNtID, date});
                    }
                }
            }
        }
    }

    public void refreshincomeTable()
    {
        for(Transaction item : Bank.getIncome())
        {
            String acc = item.getSourceNtargetID();    // source ID
            String amount = String.format(" %.2f", item.getAmount());
            String date = item.getDate().toString();
            String reason = item.getReason();
            incomeTableModel.addRow(new String[]{acc, amount, date, reason});
        }
    }

    private void closeFrame()
    {
        father.setVisible(true);
        dispose();
    }
}
