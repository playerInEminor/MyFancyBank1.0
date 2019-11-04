/**
 * manager of this bank
 */
public class Manager extends Person
{
    private String passcode;
    private String managerID;

    Manager(Name name, Address address, String phoneNumber, String managerID, String passcode)
    {
        super(name, address, phoneNumber);
        this.managerID = managerID;
        this.passcode = passcode;
    }

    public String getPasscode()
    {
        return passcode;
    }

    public String getManagerID()
    {
        return managerID;
    }
}
