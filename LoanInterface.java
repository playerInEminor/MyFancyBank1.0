import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.List;


enum CollateralType{ESTATE, VEHICLE, DEVICE}    // collateral type supported by this bank

public class LoanInterface extends JFrame
{
    private CustomerHomePage father;
    private List<Account> accounts;
    private Customer customer;
    private CollateralType collateral;
    private Account targetAccount;
    private Account repayAccount;

    private JTextField amountField = new JTextField(25);    // input the amount
    private JComboBox collateralList = new JComboBox<String>(); // select collateral
    private JComboBox accountList = new JComboBox<String>();    // save this loan to selected account
    private tableModel loanTableModel = new tableModel();
    private JComboBox repayAccountList = new JComboBox<String>();   // select an account to repay a loan
    private JTable loanTable = new JTable();    // show the unpaid loan
    private JButton jbtRepay = new JButton("Repay");
    LoanInterface(CustomerHomePage father, List<Account> accounts, Customer customer)
    {
        this.father = father;
        this.accounts = accounts;
        this.customer = customer;

        setTitle("Loan");
        setSize(500,660);
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

        accountList.setEditable(false);
        accountList.setEnabled(true);
        accountListListener accountListL = new accountListListener();
        accountList.addItemListener(accountListL);
        for(Account item : accounts)    // add accounts into the accountList
        {

            if(item instanceof SavingAccount)
            {
                SavingAccount savingAccount = (SavingAccount)item;
                accountList.addItem(savingAccount.toString());
            }
            else if (item instanceof CheckingAccount)
            {
                CheckingAccount checkingAccount = (CheckingAccount)item;
                accountList.addItem(checkingAccount.toString());
            }
        }
        JLabel accountLabel = new JLabel("Target", JLabel.CENTER);
        JPanel accountPanel = new JPanel();
        accountPanel.add(accountLabel);
        accountPanel.add(accountList);

        collateralList.setEditable(false);
        collateralList.setEnabled(true);
        collateralListListener collateralListL = new collateralListListener();
        collateralList.addItemListener(collateralListL);
        for(CollateralType item : CollateralType.values())
        {
            collateralList.addItem(item.toString());
        }
        JLabel collateralLabel = new JLabel("Collateral", JLabel.CENTER);
        JPanel collateralPanel = new JPanel();
        collateralPanel.add(collateralLabel);
        collateralPanel.add(collateralList);

        JButton jbtLoan = new JButton("Loan");
        loanListener okL = new loanListener();
        jbtLoan.addActionListener(okL);

        JTableHeader loanHeader = loanTable.getTableHeader();
        loanHeader.setResizingAllowed(true);
        JScrollPane loanScroll = new JScrollPane(loanTable);
        String[] loanHeaderName = {"Amount", "Date", "Collateral", "ID"};
        loanTableModel.setColumnIdentifiers(loanHeaderName);
        loanTable.setModel(loanTableModel);
        loanTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jbtRepay.setEnabled(false);
        refreshLoanTable();

        repayAccountList.setEditable(false);
        repayAccountList.setEnabled(true);
        repayAccountListListener repayAccountListL = new repayAccountListListener();
        repayAccountList.addItemListener(repayAccountListL);
        for(Account item : accounts)    // add accounts into the accountList
        {

            if(item instanceof SavingAccount)
            {
                SavingAccount savingAccount = (SavingAccount)item;
                repayAccountList.addItem(savingAccount.toString());
            }
            else if (item instanceof CheckingAccount)
            {
                CheckingAccount checkingAccount = (CheckingAccount)item;
                repayAccountList.addItem(checkingAccount.toString());
            }
        }
        JLabel repayLabel = new JLabel("Repay account", JLabel.CENTER);
        JPanel repayPanel = new JPanel();
        repayPanel.add(repayLabel);
        repayPanel.add(repayAccountList);

        repayListener repayL = new repayListener();
        jbtRepay.addActionListener(repayL);

        JLabel amountLabel = new JLabel("Loan amount", JLabel.CENTER);
        JPanel amountPanel = new JPanel();
        amountPanel.add(amountLabel);
        amountPanel.add(amountField);

        JPanel listPanel = new JPanel();
        listPanel.add(accountPanel);
        listPanel.add(collateralPanel);

        JPanel loanPanel = new JPanel();
        loanPanel.setLayout(new BorderLayout());
        loanPanel.add(amountPanel, BorderLayout.NORTH);
        loanPanel.add(listPanel, BorderLayout.CENTER);
        loanPanel.add(jbtLoan, BorderLayout.SOUTH);

        add(loanPanel);
        add(loanScroll);
        add(repayPanel);
        add(jbtRepay);
    }

    class collateralListListener implements ItemListener
    {
        @Override
        public void itemStateChanged(ItemEvent e)
        {
            if(ItemEvent.SELECTED == e.getStateChange())
            {
                String collteralString = e.getItem().toString();
                for(CollateralType item : CollateralType.values())
                {
                    if(item.toString().equals(collteralString))
                    {
                        collateral = item;
                        break;
                    }
                }
            }
        }
    }

    class accountListListener implements ItemListener
    {
        @Override
        public void itemStateChanged(ItemEvent e)
        {
            if(ItemEvent.SELECTED == e.getStateChange())
            {
                String selectAccount = e.getItem().toString();
                String accountID = selectAccount.substring(selectAccount.length()-16);
                for(Account item : accounts)
                {
                    if(item.getAccountID().equals(accountID))
                    {
                        targetAccount = item;
                        break;
                    }
                }
            }
        }
    }

    class repayAccountListListener implements ItemListener
    {
        @Override
        public void itemStateChanged(ItemEvent e)
        {
            if(ItemEvent.SELECTED == e.getStateChange())
            {
                String selectAccount = e.getItem().toString();
                String accountID = selectAccount.substring(selectAccount.length()-16);
                for(Account item : accounts)
                {
                    if(item.getAccountID().equals(accountID))
                    {
                        repayAccount = item;
                        break;
                    }
                }
            }
        }
    }

    class loanListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(!isValid(amountField.getText()))
            {
                MessageDialog amountError = new MessageDialog("Error", "Invalid amount!");
                amountError.setVisible(true);
                return;
            }
            double amount = Double.valueOf(amountField.getText());
            Loan newLoan = new Loan(new Date(), amount, collateral, Loan.getMAXID());
            Loan.maxIDIncrease();
            customer.addLoan(newLoan);

            Transaction loanTransaction = new Transaction(new Date(), amount, "Loan");
            Balance targetBalance = null;
            for(Balance item : targetAccount.getBalance())
            {
                if(item.getCurrencyType().equals("USD"))
                {
                    targetBalance = item;
                    break;
                }
            }
            targetBalance.addNewTransaction(loanTransaction);   // target account receive money
            refreshLoanTable();
        }
    }

    class repayListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            int index = loanTable.getSelectedRow();
            if(index == -1)
            {
                MessageDialog amountError = new MessageDialog("Error", "Please select a loan!");
                amountError.setVisible(true);
                return;
            }
            String loanID = (String)loanTable.getValueAt(index, 3); // get the id of this loan

            Balance repayBalance = null;
            for(Balance item : repayAccount.getBalance())
            {
                if(item.getCurrencyType().equals("USD"))
                {
                    repayBalance = item;
                    break;
                }
            }

            if(!customer.repayLoan(loanID, repayBalance, repayAccount))
            {
                MessageDialog amountError = new MessageDialog("Error", "This Account does not have enough money!");
                amountError.setVisible(true);
                return;
            }
            refreshLoanTable();
        }
    }

    class tableModel extends DefaultTableModel
    {
        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex)
        {
            return false;
        }
    }

    public void refreshLoanTable()
    {
        loanTableModel.setRowCount(0);
        for(Loan item : customer.getLoans())
        {
            String amount = String.format(" %.2f",item.getAmount());
            String date = item.getDate().toString();
            String collateral = item.getCollateral().toString();
            String id = item.getID();
            loanTableModel.addRow(new String[]{amount, date, collateral, id});
        }

        if(loanTableModel.getRowCount() != 0)
        {
            jbtRepay.setEnabled(true);
        }
        //balanceAmount.setText(currentBalance.getCurrencyType() + String.format(" %.2f",currentBalance.getAmount()));
    }

    // check the validation of input
    private boolean isValid(String str)
    {
        String reg = "^[0-9]+(.[0-9]+)?$";
        if(str.matches(reg))
        {
            return Double.valueOf(str) > 0;
        }
        return false;
    }

    private void closeFrame()
    {
        father.setVisible(true);
        dispose();
    }
}
