import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * GUI for deposit
 */
public class DepositInterface extends JFrame
{
    private Account account;
    private Balance balance;
    private AccountInterface father;

    private JTextField amountField=new JTextField(4);   // get the amount of this deposit

    DepositInterface(AccountInterface father, Account account, Balance balance)
    {
        this.father = father;
        this.account = account;
        this.balance = balance;

        setTitle("Deposit");
        setSize(500,200);
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                closeFrame();
            }
        });

        JLabel amountLabel = new JLabel("Amount");
        JPanel amountPanel = new JPanel();
        amountPanel.setLayout(new FlowLayout());
        amountPanel.add(amountLabel);
        amountPanel.add(amountField);

        JLabel savingAccountInterestRate = new JLabel("SavingAccountInterestRate: " + String.valueOf(Bank.getSavingAccountDepositInterestRate()), JLabel.CENTER);
        JLabel checkingAccountInterestRate = new JLabel("CheckingAccountInterestRate: " + String.valueOf(Bank.getCheckingAccountDepositInterestRate()), JLabel.CENTER);
        Font f = new Font("", Font.BOLD, 16);
        savingAccountInterestRate.setFont(f);
        checkingAccountInterestRate.setFont(f);

        JPanel okPanel = new JPanel();
        JButton jbtOK = new JButton("OK");
        Dimension jbtOKSize = new Dimension(50,45);
        okPanel.setPreferredSize(jbtOKSize);
        okListener okL = new okListener();
        jbtOK.addActionListener(okL);
        okPanel.add(jbtOK);

        add(amountPanel, BorderLayout.NORTH);
        JPanel interestRatePanel = new JPanel();
        interestRatePanel.setLayout(new BorderLayout());
        interestRatePanel.add(savingAccountInterestRate, BorderLayout.NORTH);
        interestRatePanel.add(checkingAccountInterestRate, BorderLayout.CENTER);
        add(interestRatePanel, BorderLayout.CENTER);
        add(okPanel, BorderLayout.SOUTH);
    }

    class okListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(isValid(amountField.getText()))
            {
                double depositAmount = Double.valueOf(amountField.getText());
                if(account instanceof SavingAccount)    // check the type of account, for different account has different interest rate
                {
                    account.deposit(balance, Bank.getSavingAccountDepositInterestRate(), depositAmount);
                }
                if(account instanceof CheckingAccount)
                {
                    account.deposit(balance, Bank.getCheckingAccountDepositInterestRate(), depositAmount);
                }

                closeFrame();
            }
            else
            {
                MessageDialog amountError = new MessageDialog("Error", "Amount invalid!");
                amountError.setVisible(true);
            }
        }
    }

    // check whether the amount is valid
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
        father.setEnabled(true);
        father.refreshTransactionTable();
        dispose();
    }
}
