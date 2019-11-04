import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Choose use this system as manager or customer
 */
public class HomePage extends JFrame
{
    private Bank bank;
    private HomePage self = this;

    public HomePage(Bank bank)
    {
        this.bank = bank;

        setTitle("Welcome to my bank");
        setSize(200,150);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton jbtCustomer = new JButton("Customer");
        CustomerListener customerL = new CustomerListener();
        jbtCustomer.addActionListener(customerL);
        JButton jbtManager = new JButton("Manager");
        ManagerListener managerL = new ManagerListener();
        jbtManager.addActionListener(managerL);
        Font f = new Font("", Font.BOLD, 16);
        jbtCustomer.setFont(f);
        jbtManager.setFont(f);

        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5,5,5,5);
        add(jbtCustomer, c);
        c.gridx = 0;
        c.gridy = 1;
        add(jbtManager, c);
    }

    class CustomerListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            LoginInterface customerLogin = new LoginInterface(bank, UserType.CUSTOMER, self);
            setVisible(false);
            customerLogin.setVisible(true);
        }
    }

    class ManagerListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            LoginInterface ManagerLogin = new LoginInterface(bank, UserType.MANAGER, self);
            setVisible(false);
            ManagerLogin.setVisible(true);
        }
    }
}
