
import java.util.ArrayList;
import java.util.List;
/**
 * this class represents the whole bank system
 */
public class Bank
{
    private static double AccountFee = 5;   // the open account fee
    private static double loanInterestRate = 4;
    private static double savingAccountDepositInterestRate = 1.75;  // interest rate of saving account. When use it, it should multiply with 0.01
    private static double checkingAccountDepositInterestRate = 0;   // interest rate of checking account. When use it, it should multiply with 0.01
    private static double transactionFeeRate = 0.10;    // the charge rate of transaction, also a number need multiply with 0.01
    private static double withdrawFeeRate = 0.50;
    private static List<Transaction> income = new ArrayList<>();    // income of this bank

    private List<Manager> managers; // managers of this bank
    private List<Customer> customers;   // customers of this bank
    private List<ATM> atm;

    Bank()
    {
        managers = new ArrayList<>();
        customers = new ArrayList<>();
        atm = new ArrayList<>();
        ATM newATM = new ATM(this);
        atm.add(newATM);

        initForTest();
    }

    public static double getAccountFee()
    {
        return AccountFee;
    }

    public static double getSavingAccountDepositInterestRate()
    {
        return savingAccountDepositInterestRate;
    }

    public static double getCheckingAccountDepositInterestRate()
    {
        return checkingAccountDepositInterestRate;
    }

    public static double getLoanInterest()
    {
        return loanInterestRate;
    }

    public static double getWithdrawFeeRate()
    {
        return withdrawFeeRate;
    }

    public static double getTransactionFeeRate()
    {
        return transactionFeeRate;
    }

    public static List<Transaction> getIncome()
    {
        return income;
    }

    public static void addIncome(Transaction newIncome)
    {
        income.add(newIncome);
    }

    private void initForTest()
    {
        Name customerName = new Name("Yifei", "Fang");
        Address customerAddress = new Address("457 Park Drive", "Boston", "MA", "02215");
        String customerPhone = "6179389691";
        String customerID = "yff";
        String customerPasscode = "passcode";
        Customer defaultCustomer = new Customer(customerName, customerAddress, customerPhone, customerID, customerPasscode);
        defaultCustomer.initForTest();
        customers.add(defaultCustomer);

        Name managerName = new Name("Mana", "Ger");
        Address managerAddress = new Address("725 Commonwealth Avenue", "Boston", "MA", "02215");
        String managerPhone = "6173532401";
        String managerID = "CAS";
        String managerPasscode = "123456789";
        Manager defaultManager = new Manager(managerName, managerAddress, managerPhone, managerID, managerPasscode);
        managers.add(defaultManager);
    }

    public List<Customer> getCustomers()
    {
        return customers;
    }

    public List<Manager> getManagers()
    {
        return managers;
    }

    public void RunATM(int index)
    {
        ATM Atm = atm.get(index);
        Atm.Run();
    }

    public static void main(String[] args)
    {
        Bank bank = new Bank();
        bank.RunATM(0);
    }
}
