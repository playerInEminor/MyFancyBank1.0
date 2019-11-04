public enum AccountType
{
    CHECKING,
    SAVING;

    // return different kind of account
    public static Account getAccount(AccountType type)
    {
        switch(type)
        {
            case CHECKING:
                return new CheckingAccount();
            case SAVING:
                return new SavingAccount();
            default:
                return null;
        }
    }
}
