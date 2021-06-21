// Voter.java
// Represents a voter
// @Author Douglas Kiang

import java.util.ArrayList;

public class Voter
{
    private String firstName;
    private String lastName;
    private String address;
    private String state;
    private ArrayList<Integer> elections;
    
    public Voter(String first, String last, String address, String state)
    {
        firstName = first;
        lastName = last;
        this.address = address;
        this.state = state;
        elections = new ArrayList<Integer>();
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
        return firstName + " " + lastName + ", " + state;
    }
}
