import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * customer of bank
 */
public class Customer extends Person
{
    private String UserID;
    private String passcode;
    private List<Account> accounts;
    private List<Loan> loans;   // loans of this customer

    Customer(Name name, Address address, String phoneNumber, String UserID, String passcode)
    {
        super(name, address, phoneNumber);
        this.UserID = UserID;
        this.passcode = passcode;
        accounts = new ArrayList<>();
        this.loans = new ArrayList<>();
    }

    public void initForTest()
    {
        SavingAccount newSavingAccount = new SavingAccount();
        newSavingAccount.setAccountID(Account.getMaxID());
        Account.maxIDIncrease();
        newSavingAccount.initForTest();
        accounts.add(newSavingAccount);
        Transaction newTransaction = new Transaction(new Date(), 5, newSavingAccount.getAccountID());
        newTransaction.setReason("Open account fee");
        Bank.addIncome(newTransaction);

        CheckingAccount newCheckingAccount = new CheckingAccount();
        newCheckingAccount.setAccountID(Account.getMaxID());
        Account.maxIDIncrease();
        newCheckingAccount.initForTest();
        accounts.add(newCheckingAccount);
        newTransaction = new Transaction(new Date(), 5, newCheckingAccount.getAccountID());
        newTransaction.setReason("Open account fee");
        Bank.addIncome(newTransaction);
    }

    public Name getName()
    {
        return name;
    }

    public List<Loan> getLoans()
    {
        return loans;
    }

    public String getUserID()
    {
        return UserID;
    }

    public String getPasscode()
    {
        return passcode;
    }

    public List<Account> getAccounts()
    {
        return accounts;
    }

    public void addLoan(Loan newLoan)
    {
        loans.add(newLoan);
    }

    //repay the loan
    public boolean repayLoan(String loanID, Balance balance, Account account)
    {
        Loan repayLoan = null;
        for(Loan item : loans)  // find the loan
        {
            if(item.getID().equals(loanID))
            {
                repayLoan = item;
                break;
            }
        }

        long startTime = repayLoan.getDate().getTime();
        long endTime = new Date().getTime();
        int seconds = (int)((endTime - startTime) / 1000);
        int loanTime = seconds/5 + 1;   // Once the customer loan money, there will be interest, slightly different with deposit interest, for the bank wants to make more money

        // calculate the interest until now of accounts, for they maybe ca repay the loan with the interest
        if(account instanceof SavingAccount)
        {
            account.calculateCurrentInterest(balance, Bank.getSavingAccountDepositInterestRate());
        }
        if(account instanceof CheckingAccount)
        {
            account.calculateCurrentInterest(balance, Bank.getCheckingAccountDepositInterestRate());
        }

        double interest = repayLoan.getAmount() * loanTime * (Bank.getLoanInterest() * 0.01);   // calculate the loan interest
        if((repayLoan.getAmount() + interest) > balance.getAmount())
        {   // the account doesn't have enough money
            return false;
        }
        else
        {
            Transaction repayTransaction = new Transaction(new Date(), -repayLoan.getAmount(), "Loan repay");
            balance.addNewTransaction(repayTransaction);
            Transaction repayFeeTransaction = new Transaction(new Date(), -interest, "Loan interest");
            balance.addNewTransaction(repayFeeTransaction);

            // profit of this loan
            Transaction loanFee = new Transaction(new Date(), interest, account.getAccountID());
            loanFee.setReason("Loan interest");
            Bank.addIncome(loanFee);

            loans.remove(repayLoan);
            return true;
        }
    }
}
