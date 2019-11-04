import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * registration interface
 */
public class RegistrationInterface extends JFrame
{
    Bank bank;
    UserType userType;

    JFrame father;

    JTextField lastNameField=new JTextField(10);    // input last name
    JTextField firstNameField=new JTextField(10);   // input first name
    JTextField streetField=new JTextField(10);     // input street
    JTextField cityField=new JTextField(10);    // input city
    JTextField stateField=new JTextField(10);   // input state
    JTextField zipCodeField=new JTextField(10); // input zip code
    JTextField phoneField=new JTextField(10);   // input phone number
    JTextField userIDField=new JTextField(10);  //set a user Id
    JTextField passcodeField=new JTextField(10);    //set the passcode
    public RegistrationInterface(Bank bank, UserType userType, JFrame father)
    {
        this.bank = bank;
        this.userType = userType;
        this.father = father;

        if(userType == UserType.CUSTOMER)
        {
            setTitle("Customer Registration");
        }
        if(userType == UserType.MANAGER)
        {
            setTitle("Manager Registration");
        }

        //setSize(400,250);
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                closeFrame();
            }
        });
        //setLayout(new GridLayout(5,2,5,5));
        GridBagLayout gridBag = new GridBagLayout();
        GridBagConstraints c = null;

        JPanel panel = new JPanel(gridBag);

        JLabel lastNameLabel = new JLabel("Last name", JLabel.LEFT);
        JLabel firstNameLabel = new JLabel("First name", JLabel.LEFT);
        JLabel streetLabel = new JLabel("Street", JLabel.LEFT);
        JLabel cityLabel = new JLabel("City", JLabel.LEFT);
        JLabel stateLabel = new JLabel("State", JLabel.LEFT);
        JLabel zipCodeLabel = new JLabel("Zip code", JLabel.LEFT);
        JLabel phoneLabel = new JLabel("Phone", JLabel.LEFT);
        JLabel userIDLabel = new JLabel("UserID", JLabel.LEFT);
        JLabel passcodeLabel = new JLabel("Passcode", JLabel.LEFT);

        Dimension buttonSize = new Dimension(75,25);
        JButton jbtOK = new JButton("OK");
        jbtOK.setPreferredSize(buttonSize);
        okListener okL = new okListener();
        jbtOK.addActionListener(okL);
        JButton jbtCancel = new JButton("Cancel");
        jbtCancel.setPreferredSize(buttonSize);
        cancelListener cancelL = new cancelListener();
        jbtCancel.addActionListener(cancelL);

        c = new GridBagConstraints();
        c.insets = new Insets(10,5,10,5);
        gridBag.addLayoutComponent(lastNameLabel, c);
        gridBag.addLayoutComponent(lastNameField, c);
        gridBag.addLayoutComponent(firstNameLabel, c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridBag.addLayoutComponent(firstNameField, c);

        c = new GridBagConstraints();
        c.insets = new Insets(10,5,10,5);
        gridBag.addLayoutComponent(streetLabel, c);
        gridBag.addLayoutComponent(streetField, c);
        gridBag.addLayoutComponent(cityLabel, c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridBag.addLayoutComponent(cityField, c);

        c = new GridBagConstraints();
        c.insets = new Insets(10,5,10,5);
        gridBag.addLayoutComponent(stateLabel, c);
        gridBag.addLayoutComponent(stateField, c);
        gridBag.addLayoutComponent(zipCodeLabel, c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridBag.addLayoutComponent(zipCodeField, c);

        c = new GridBagConstraints();
        c.insets = new Insets(10,5,10,5);
        gridBag.addLayoutComponent(phoneLabel, c);
        gridBag.addLayoutComponent(phoneField, c);
        gridBag.addLayoutComponent(userIDLabel, c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridBag.addLayoutComponent(userIDField, c);

        c = new GridBagConstraints();
        c.insets = new Insets(10,5,10,5);
        gridBag.addLayoutComponent(passcodeLabel, c);
        gridBag.addLayoutComponent(passcodeField, c);

        JPanel p = new JPanel();
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridBag.addLayoutComponent(p, c);

        c.gridwidth = 2;
        gridBag.addLayoutComponent(jbtOK, c);
        gridBag.addLayoutComponent(jbtCancel, c);

        panel.add(lastNameLabel);
        panel.add(lastNameField);
        panel.add(firstNameLabel);
        panel.add(firstNameField);
        panel.add(streetLabel);
        panel.add(streetField);
        panel.add(cityLabel);
        panel.add(cityField);
        panel.add(stateLabel);
        panel.add(stateField);
        panel.add(zipCodeLabel);
        panel.add(zipCodeField);
        panel.add(passcodeLabel);
        panel.add(passcodeField);
        panel.add(p);
        panel.add(jbtOK);
        panel.add(jbtCancel);

        setContentPane(panel);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void closeFrame()
    {
        father.setVisible(true);
        dispose();
    }

    //register a customer
    public boolean regCustomer()
    {
        //Find whether the userID has been used
        for(Customer item : bank.getCustomers())
        {
            if(item.getUserID().equals(userIDField.getText()))
            {
                return false;
            }
        }

        Name newName = new Name(lastNameField.getText(), firstNameField.getText());
        Address newAddress = new Address(streetField.getText(), cityField.getText(), stateField.getText(), zipCodeField.getText());
        Customer newCustomer = new Customer(newName, newAddress, phoneField.getText(), userIDField.getText(), passcodeField.getText());
        bank.getCustomers().add(newCustomer);

        //System.out.println(bank.getCustomers().size());
        return true;
    }

    //register a manager
    public boolean regManager()
    {
        //Find whether the userID has been used
        for(Manager item : bank.getManagers())
        {
            if(item.getManagerID().equals(userIDField.getText()))
            {
                return false;
            }
        }

        Name newName = new Name(lastNameField.getText(), firstNameField.getText());
        Address newAddress = new Address(streetField.getText(), cityField.getText(), stateField.getText(), zipCodeField.getText());
        Manager newManager = new Manager(newName, newAddress, phoneField.getText(), userIDField.getText(), passcodeField.getText());
        bank.getManagers().add(newManager);

        //System.out.println(bank.getCustomers().size());
        return true;
    }

    class okListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(!isValidName(lastNameField.getText()) || !isValidName(firstNameField.getText()))
            {
                MessageDialog amountError = new MessageDialog("Error", "Invalid Name!");
                amountError.setVisible(true);
                return;
            }
            if (!isValidID(userIDField.getText()))
            {
                MessageDialog amountError = new MessageDialog("Error", "Invalid ID!");
                amountError.setVisible(true);
                return;
            }
            if(userType == UserType.CUSTOMER)
            {
                if(regCustomer())
                {
                    closeFrame();
                    return;
                }
            }
            if(userType == UserType.MANAGER)
            {
                if(regManager())
                {
                    closeFrame();
                    return;
                }
            }
            MessageDialog message = new MessageDialog("Error", "This ID has existed!");
            message.setVisible(true);
        }
    }

    class cancelListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            closeFrame();
        }
    }

    private boolean isValidID(String id)
    {
        String reg = "^[a-z0-9A-Z]+$";
        return id.matches(reg);
    }

    // check whether the name is valid
    private boolean isValidName(String name)
    {
        String reg = "^[a-zA-Z]+$";
        return name.matches(reg);
    }
}
