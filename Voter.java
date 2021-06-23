// Voter.java
// Represents a voter
// @Author Douglas Kiang

import java.util.ArrayList;

public class Voter
{
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String state;
    private ArrayList<Integer> elections;
    
    public Voter(String first, String last, String address, String city, String state)
    {
        firstName = first;
        lastName = last;
        this.address = address;
        this.city = city;
        this.state = state;
        elections = new ArrayList<Integer>();
    }
    
    public Voter(String first, String last, String address, String city, String state, ArrayList<Integer> elections)
    {
        this(first,last,address,city,state);
        for (Integer i : elections)
        {
            this.elections.add(i);
        }
    }
    
    public String getFirstName()
    {
        return firstName;
    }
    
    public String getLastName()
    {
        return lastName;
    }
    
    public String getAddress()
    {
        return address;
    }
    
    public String getCity()
    {
        return city;
    }
    
    public String getState()
    {
        return state;
    }
    
    public void addElection(int id)
    {
        elections.add(id);
    }
    
    public String toString()
    {
        return firstName + " " + lastName + ", " + state + " " + elections;
    }
}
