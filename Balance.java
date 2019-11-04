import java.util.*;

/**
 * This class represent the different currency type balance,
 * each instance of balance has one kind of currency
 */
public class Balance
{
    private double amount;
    private Currency currency;
    private List<Transaction> transactions; //transaction record of this type of currency

    Balance()
    {
        amount = 0;
        currency = Currency.getInstance(Locale.US);
        transactions = new ArrayList<>();
    }

    Balance(Currency currency)
    {
        amount = 0;
        this.currency = currency;
        transactions = new ArrayList<>();
    }

    public Currency getCurrency()
    {
        return currency;
    }

    public String getCurrencyType()
    {
        return currency.getCurrencyCode();
    }

    public String getCurrencySymbol()
    {
        return currency.getSymbol();
    }

    public double getAmount()
    {
        return amount;
    }

    // add a new transaction record and update the balance amount
    public void addNewTransaction(Transaction newTransaction)
    {
        transactions.add(newTransaction);
        amount += newTransaction.getAmount();
    }

    public void initForTest()
    {
        amount = 100;

        Transaction newTransaction = new Transaction(new Date(), 5, "Deposit");
        transactions.add(newTransaction);
        newTransaction = new Transaction(new Date(), -5, "Open account");
        transactions.add(newTransaction);
    }

    public List<Transaction> getTransactions()
    {
        return transactions;
    }
}
