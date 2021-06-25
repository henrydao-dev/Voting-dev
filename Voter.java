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
    private int id;
    private ArrayList<Integer> elections;
    
    public Voter(String first, String last, String address, String city, String state)
    {
        firstName = first;
        lastName = last;
        this.address = address;
        this.city = city;
        this.state = state;
        id = generateID();
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
    	String result = "";
    	result += String.format("%-12s", firstName);
    	result += String.format("%-12s", lastName);
    	result += String.format("%-4s", state) + "\n";
    	result += String.format("%-30s", "Elections participated in: " + elections) + "\n\n";
        return result;
    }
    
    // Generates a unique Voter ID (hash) based on the voter's name and address
    // Hash will always be the same given the same name and address
    private int generateID()
    {
        String temp = firstName + lastName + address + city + state;
        int hash = 7;
        for (int i = 0, n = temp.length(); i < n; i++)
        {
            hash = hash * 31 + temp.charAt(i);
        }
        return hash;
    }
}
