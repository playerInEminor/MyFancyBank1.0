import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class represent the Account of customers
 * This class is a father class, it has two subclasses, CheckingAccount and SavingAccount
 */
public class Account
{
    private static String maxID = "0000000000000000";   // store the max ID this bank has created

    protected List<Balance> balance;    // The balances of tis account, these balances are composed by different currency types
    protected String accountID;

    Account()
    {
        balance = new ArrayList<>();
    }

    public List<Balance> getBalance()
    {
        return balance;
    }

    // maxID + 1, so the maxID can be used for another new account
    public static void maxIDIncrease()
    {
        Long idNUM = Long.valueOf(maxID);
        idNUM++;
        //maxID = idNUM.toString();
        maxID = String.format("%016d", idNUM);
    }

    public void setAccountID(String accountID)
    {
        this.accountID = accountID;
    }

    public String getAccountID()
    {
        return accountID;
    }

    public static String getMaxID()
    {
        return maxID;
    }

    public static void setMaxID(String maxID)
    {
        Account.maxID = maxID;
    }

    // create a new currency type balance
    public void addNewBalance(Balance newBalance)
    {
        balance.add(newBalance);
    }

    // calculate the interest from last transaction time to now
    public void calculateCurrentInterest(Balance balance, double interestRate)
    {
        List<Transaction> transactions = balance.getTransactions();
        Date lastTransactionDate = null;
        Date now = new Date();
        if(transactions.size() > 0)
        {
            Transaction lastTransaction = transactions.get(transactions.size() - 1);
             lastTransactionDate = lastTransaction.getDate();
        }
        else // if has no transaction yet, there will no interest
        {
            lastTransactionDate = now;
        }

        long startTime = lastTransactionDate.getTime();
        long endTime = now.getTime();
        int seconds = (int)((endTime - startTime) / 1000);
        int depositTime = seconds/5;
        double interest = interestRate * depositTime; // For test convinence, I set the interest rate to be 5 seconds interest rate, but not day interest rate, this can be easily change to day rate
        if(interest == 0)
        {
            return;
        }
        Transaction interestTransaction = new Transaction(new Date(), interest, "Interest");
        balance.addNewTransaction(interestTransaction); // add interest into the specific balance
    }

    //withdraw money
    public boolean withdraw(Balance balance, double interestRate, double amount)
    {
        calculateCurrentInterest(balance, interestRate);    // calculate the interest first, for user can withdraw the interest at this time
        double balanceAmount = balance.getAmount();
        if((amount) <= balanceAmount)
        {
            Transaction newWithdraw = new Transaction(new Date(), -amount, "Withdraw");
            balance.addNewTransaction(newWithdraw); // add this transaction record
            return true;
        }
        else    // do not have enough money
        {
            return false;
        }
    }

    public void deposit(Balance balance, double interestRate, double amount)
    {
        calculateCurrentInterest(balance, interestRate);    // calculate the interest first, for the total amount will change after this deposit
        Transaction newDeposit = new Transaction(new Date(), amount, "Deposit");
        balance.addNewTransaction(newDeposit);
    }

    public void transferIn(Balance balance, double interestRate, double amount, Account targetAccount)
    {
        calculateCurrentInterest(balance, interestRate);
        double transactionFee = amount * Bank.getTransactionFeeRate() * 0.01;   // calculate the transaction fee
        Transaction newTransaction = new Transaction(new Date(), amount, targetAccount.getAccountID());
        balance.addNewTransaction(newTransaction);
        Transaction newTransactionFee = new Transaction(new Date(), -transactionFee, "Transaction fee");
        balance.addNewTransaction(newTransactionFee);

        Transaction fee = new Transaction(new Date(), transactionFee, accountID);   // bank will charge transaction fee from each transaction
        fee.setReason("Transaction fee");
        Bank.addIncome(fee);
    }
}
