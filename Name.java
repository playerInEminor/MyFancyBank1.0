/**
 * a class of the information of a name
 */
public class Name
{
    private String lastName;
    private String firstName;

    Name(String lastName, String firstName)
    {
        this.lastName = lastName;
        this.firstName = firstName;
    }

    @Override
    public String toString()
    {
        String totalName = firstName + lastName;
        return totalName;
    }
}
