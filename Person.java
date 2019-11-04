/**
 * basic attribute and ability of a person
 */
public class Person
{
    protected Name name;
    protected Address address;
    protected String phoneNumber;

    Person(Name name, Address address, String phoneNumber)
    {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
