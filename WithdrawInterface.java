import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * withdraw interface
 */
public class WithdrawInterface extends JDialog
{
    private Account account;
    private Balance balance;
    private AccountInterface father;

    private JTextField amountField=new JTextField(4);   // withdraw amount

    WithdrawInterface(AccountInterface father, Account account, Balance balance)
    {
        this.father = father;
        this.account = account;
        this.balance = balance;

        setTitle("Withdraw");
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
        okListener okL = new okListener();
        jbtOK.addActionListener(okL);
        Dimension jbtOKSize = new Dimension(50,45);
        okPanel.setPreferredSize(jbtOKSize);
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
                double withdrawAmount = Double.valueOf(amountField.getText());
                if (account instanceof SavingAccount)   // different account type has different action of withdraw
                {
                    if (!((SavingAccount)account).withdraw(balance, Bank.getSavingAccountDepositInterestRate(), withdrawAmount))
                    {
                        MessageDialog amountError = new MessageDialog("Error", "No enough money!");
                        amountError.setVisible(true);
                        return;
                    }
                }
                if (account instanceof CheckingAccount)
                {
                    double withdrawFeeAmount = withdrawAmount * (Bank.getWithdrawFeeRate() * 0.01);
                    if (!((CheckingAccount)account).withdraw(balance, Bank.getCheckingAccountDepositInterestRate(), withdrawAmount, withdrawFeeAmount))
                    {
                        MessageDialog amountError = new MessageDialog("Error", "No enough money!");
                        amountError.setVisible(true);
                        return;
                    }
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
