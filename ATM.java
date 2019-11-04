/**
 * This class is the entrence of an ATM
 */
public class ATM
{

    private Bank bank;

    ATM(Bank bank)
    {
        this.bank = bank;
    }

    // run the ATM
    public void Run()
    {
        HomePage homepage = new HomePage(this.bank);
        homepage.setVisible(true);
    }
}
