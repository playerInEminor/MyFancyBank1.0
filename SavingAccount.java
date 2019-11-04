import java.util.*;

/**
 * saving account
 */
public class SavingAccount extends Account
{
    public static final AccountType type = AccountType.SAVING;

    SavingAccount()
    {
        super();
    }

    public String toString()
    {
        return "Saving Account " + accountID;
    }

    public void initForTest()
    {
        Balance newBalance = new Balance(Currency.getInstance(Locale.US));
        newBalance.initForTest();
        balance.add(newBalance);
        newBalance = new Balance(Currency.getInstance(Locale.FRANCE));
        balance.add(newBalance);
        newBalance = new Balance(Currency.getInstance(Locale.CHINA));
        balance.add(newBalance);
    }
}
