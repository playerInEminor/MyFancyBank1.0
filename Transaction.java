import java.util.Date;

/**
 * one transaction record
 */
public class Transaction
{
    private Date date;
    private double amount;
    private String sourceNtargetID; // the target or resource account of this transaction, including interest, withdraw and deposit
    private String reason;  // reason of this transaction, only will be used when this transaction record is an income of bank

    Transaction(Date date, double amount, String sourceNtargetID)
    {
        this.date = date;
        this.amount = amount;
        this.sourceNtargetID = sourceNtargetID;
    }

    public String getReason()
    {
        return reason;
    }

    public void setReason(String reason)
    {
        this.reason = reason;
    }

    public double getAmount()
    {
        return amount;
    }

    public Date getDate()
    {
        return date;
    }

    public String getSourceNtargetID()
    {
        return sourceNtargetID;
    }
}
