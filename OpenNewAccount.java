import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.List;

/**
 * interface of open a new account
 */
public class OpenNewAccount extends JDialog
{
    private List<Account> accounts;
    private AccountType newAccountType;
    private AccountInterface father;

    JComboBox accountType = new JComboBox<String>();
    private JCheckBox USDBox = new JCheckBox(CurrencyType.USD.toString(),true); // account can save USD
    private JCheckBox EURBox = new JCheckBox(CurrencyType.EUR.toString(),true); // account can save EUR
    private JCheckBox CNYBox = new JCheckBox(CurrencyType.CNY.toString(),true); // account can save CNY
    private JCheckBox JPYBox = new JCheckBox(CurrencyType.JPY.toString());  // select whether this new account save JPY
    OpenNewAccount(List<Account> accounts, AccountInterface father)
    {
        this.father = father;
        this.accounts = accounts;
        setTitle("Open Account");
        setSize(300,200);
        setResizable(false);
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                closeFrame();
            }
        });

        accountType.setEditable(false);
        accountType.setEnabled(true);
        for(AccountType item : AccountType.values())
        {
            accountType.addItem(item.toString());
            //newAccountType = item;
        }
        newAccountType = AccountType.valueOf((String)accountType.getItemAt(0));
        accountTypeListener accountTypeL = new accountTypeListener();
        accountType.addItemListener(accountTypeL);

        JPanel currencyType = new JPanel();
        currencyType.setLayout(new FlowLayout());
        USDBox.setEnabled(false);
        currencyType.add(USDBox);
        EURBox.setEnabled(false);
        currencyType.add(EURBox);
        CNYBox.setEnabled(false);   // three default currency type of an account
        currencyType.add(CNYBox);
        currencyType.add(JPYBox);

        JButton jbtOK = new JButton("OK");
        okListener okL = new okListener();
        jbtOK.addActionListener(okL);

        JButton jbtCancel = new JButton("Cancel");
        cancelListener cancelL = new cancelListener();
        jbtCancel.addActionListener(cancelL);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(jbtOK);
        buttonPanel.add(jbtCancel);
        Dimension jbtOKSize = new Dimension(50,75);
        buttonPanel.setPreferredSize(jbtOKSize);
        buttonPanel.add(jbtOK);

        add(accountType, BorderLayout.NORTH);
        add(currencyType, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    class accountTypeListener implements ItemListener
    {
        @Override
        public void itemStateChanged(ItemEvent e)
        {
            if(ItemEvent.SELECTED == e.getStateChange())
            {
                for(AccountType item : AccountType.values())
                {
                    if(item.toString().equals(e.getItem().toString()))
                    {
                        newAccountType = item;
                        break;
                    }
                }
            }
        }
    }

    class okListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            Account newAccount = AccountType.getAccount(newAccountType);
            if(USDBox.isSelected())
            {
                Balance newBalance = new Balance(CurrencyType.getCurrencyType(CurrencyType.USD));
                Transaction firstDeposit = new Transaction(new Date(), Bank.getAccountFee(), "Deposit");
                Transaction openAccountFee = new Transaction(new Date(), -Bank.getAccountFee(), "Open Account Fee");
                newBalance.addNewTransaction(firstDeposit);
                newBalance.addNewTransaction(openAccountFee);
                newAccount.addNewBalance(newBalance);
            }
            if(EURBox.isSelected())
            {
                Balance newBalance = new Balance(CurrencyType.getCurrencyType(CurrencyType.EUR));
                newAccount.addNewBalance(newBalance);
            }
            if(CNYBox.isSelected())
            {
                Balance newBalance = new Balance(CurrencyType.getCurrencyType(CurrencyType.CNY));
                newAccount.addNewBalance(newBalance);
            }
            if(JPYBox.isSelected())
            {
                Balance newBalance = new Balance(CurrencyType.getCurrencyType(CurrencyType.JPY));
                newAccount.addNewBalance(newBalance);
            }
            // If less than three currency types selected
            if(newAccount.getBalance().size()<3)
            {
                MessageDialog noCurrencyTypeError = new MessageDialog("Error", "Please select at least three currency types! ");
                noCurrencyTypeError.setVisible(true);
                return;
            }

            // Update the maxID of this bank
            Account.maxIDIncrease();
            newAccount.setAccountID(Account.getMaxID());
            accounts.add(newAccount);

            //add the open account fee as income of the bank
            Transaction accountFee = new Transaction(new Date(), Bank.getAccountFee(), newAccount.getAccountID());
            accountFee.setReason("Open account");
            Bank.addIncome(accountFee);

            father.initAccountList();
            closeFrame();
        }
    }

    class cancelListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            dispose();
        }
    }

    private void closeFrame()
    {
        father.setEnabled(true);
        dispose();
    }
}
