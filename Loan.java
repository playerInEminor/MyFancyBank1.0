import java.util.Date;

/**
 * store some features of a loan
 */
public class Loan
{
    // mark the max loan ID
    private static String MAXID = "0000";

    private Date date;
    private double amount;
    private CollateralType collateral;
    private String id;  //each loan has an unique id

    Loan(Date date, double amount, CollateralType collateral, String id)
    {
        this.date = date;
        this.amount = amount;
        this.collateral = collateral;
        this.id = id;
    }

    public static String getMAXID()
    {
        return MAXID;
    }

    public static void maxIDIncrease()
    {
        int idNUM = Integer.valueOf(MAXID);
        idNUM++;
        MAXID = String.format("%04d", idNUM);
    }

    public String getID()
    {
        return id;
    }

    public double getAmount()
    {
        return amount;
    }

    public Date getDate()
    {
        return date;
    }

    public CollateralType getCollateral()
    {
        return collateral;
    }
}
