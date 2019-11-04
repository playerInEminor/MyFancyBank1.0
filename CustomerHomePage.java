import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * GUI of customer
 */
public class CustomerHomePage extends JFrame
{
    private Customer customer;
    private Bank bank;
    private CustomerHomePage self = this;
    private LoginInterface father;

    CustomerHomePage(Bank bank, Customer customer, LoginInterface father)
    {
        this.bank = bank;
        this.customer = customer;
        this.father = father;

        setTitle("Customer Homepage");
        setSize(200,150);
        setResizable(false);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                closeFrame();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2,1,5,10));
        JButton jbtAccounts = new JButton("Accounts");
        accountsListener accountL = new accountsListener();
        jbtAccounts.addActionListener(accountL);
        JButton jbtLoan = new JButton("Loan");
        loanListener loanL = new loanListener();
        jbtLoan.addActionListener(loanL);
        buttonPanel.add(jbtAccounts);
        buttonPanel.add(jbtLoan);

        Font f = new Font("", Font.BOLD, 16);
        jbtAccounts.setFont(f);
        jbtLoan.setFont(f);

        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, c);
    }

    class accountsListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            AccountInterface accountInterface = new AccountInterface(bank, customer.getAccounts(), self);
            setVisible(false);
            accountInterface.setVisible(true);
        }
    }

    class loanListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            LoanInterface loanInterface = new LoanInterface(self, customer.getAccounts(), customer);
            setVisible(false);
            loanInterface.setVisible(true);
        }
    }

    private void closeFrame()
    {
        father.setVisible(true);
        dispose();
    }
}
