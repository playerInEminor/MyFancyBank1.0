import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

enum UserType {CUSTOMER, MANAGER}   // two kinds of user of this system

public class LoginInterface extends JFrame
{
    private HomePage father;
    private Bank bank;
    private UserType userType;
    private JTextField idField=new JTextField(10);
    private JPasswordField passcodeField=new JPasswordField(10);
    private LoginInterface self = this;

    LoginInterface(Bank bank, UserType userType, HomePage father)
    {
        this.father = father;
        this.bank = bank;
        this.userType = userType;
        if(userType == UserType.CUSTOMER)
        {
            setTitle("Customer Login");
        }
        if(userType == UserType.MANAGER)
        {
            setTitle("Manager Login");
        }

        setSize(400,150);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        JLabel idLabel = new JLabel("ID");
        idLabel.setSize(70,20);
        idLabel.setLocation(75, 15);
        idField.setSize(200, 20);
        idField.setLocation(150, 15);
        idField.setText("ID");

        JLabel passcodeLabel = new JLabel("PASSCODE");
        passcodeLabel.setSize(70,20);
        passcodeLabel.setLocation(75,40);
        passcodeField.setSize(200,20);
        passcodeField.setLocation(150,40);
        passcodeField.setText("PASSCODE");

        JButton jbtLogin = new JButton("Login");
        jbtLogin.setSize(110,20);
        jbtLogin.setLocation(50,75);
        loginListener loginL = new loginListener();
        jbtLogin.addActionListener(loginL);
        JButton jbtReg = new JButton("Registration");
        jbtReg.setSize(110,20);
        jbtReg.setLocation(250,75);
        regListener regL = new regListener();
        jbtReg.addActionListener(regL);

        add(idLabel);
        add(idField);
        add(passcodeLabel);
        add(passcodeField);
        add(jbtLogin);
        add(jbtReg);

        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                closeFrame();
            }
        });
    }

    public void setUserType(UserType userType)
    {
        this.userType = userType;
    }

    class loginListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            boolean isAccountExist = false;
            if(userType == UserType.CUSTOMER)
            {
                for(Customer item :bank.getCustomers())
                {
                    if(item.getUserID().equals(idField.getText()))
                    {
                        Customer customer = item;
                        if(customer.getPasscode().equals(passcodeField.getText()))
                        {
                            CustomerHomePage customerHomePage = new CustomerHomePage(bank, item, self);
                            setVisible(false);
                            customerHomePage.setVisible(true);
                            isAccountExist = true;
                            break;
                        }
                        else
                        {
                            MessageDialog noCustomer = new MessageDialog("Error!", "Wrong passcode!");
                            noCustomer.setVisible(true);
                            return;
                        }
                    }
                }
                if(!isAccountExist)
                {
                    MessageDialog noCustomer = new MessageDialog("Error!", "No UserId: " + idField.getText());
                    noCustomer.setVisible(true);
                    return;
                }
            }

            if(userType == UserType.MANAGER)
            {
                for(Manager item :bank.getManagers())
                {
                    if(item.getManagerID().equals(idField.getText()))
                    {
                        Manager manager = item;
                        if(manager.getPasscode().equals(passcodeField.getText()))
                        {
                            ManagerHomePage managerHomePage = new ManagerHomePage(bank, self);
                            setVisible(false);
                            managerHomePage.setVisible(true);
                            isAccountExist = true;
                            break;
                        }
                        else
                        {
                            MessageDialog noCustomer = new MessageDialog("Error!", "Wrong passcode!");
                            noCustomer.setVisible(true);
                            return;
                        }
                    }
                }
                if(!isAccountExist)
                {
                    MessageDialog noCustomer = new MessageDialog("Error!", "Wrong manager ID: " + idField.getText());
                    noCustomer.setVisible(true);
                }
            }
        }
    }

    class regListener implements  ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            RegistrationInterface registrationInterface = new RegistrationInterface(bank, userType, self);
            setVisible(false);
            registrationInterface.setVisible(true);
        }
    }

    private void closeFrame()
    {
        father.setVisible(true);
        dispose();
    }

}
