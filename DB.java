// DB.java
// @author Douglas Kiang

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;

public class DB
{
    private ArrayList<Voter> voters;
    int numVoters;
    private int elections;
    private int[] electionIDs;
    private ArrayList<HashMap> votes;
    Scanner sn;
    
    public DB()
    {
        elections = countBallots();
        votes = new ArrayList<HashMap>();
        loadBallots();
    }
    
    // Fills the voters ArrayList with voters read in from voters.txt
    private void loadVoters()
    {
        try
        {
            sn = new Scanner(new File("voters.txt"));
            while (sn.hasNextLine())
            {
                ArrayList<String> values = getRecordFromLine(sn.nextLine());
                addVoter(new Voter(values.get(0), values.get(1), values.get(2), values.get(3), values.get(4)));
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found! Make sure that voters.txt is in the same folder as the class.");
        }
    }
    
    // Fills the 
    private void loadBallots()
    {
        try
        {
            // ultimately this will need to be a concatenated string with the year
            sn = new Scanner(new File("2020ballot.txt"));
            while (sn.hasNextLine())
            {
                ArrayList<String> values = getRecordFromLine(sn.nextLine());
                HashMap<String, String> b = new HashMap<String, String>();
                b.put("CandidateFirstName", values.get(0));
                b.put("CandidateLastName", values.get(1));
                b.put("CandidateID", values.get(2));
                b.put("CandidateOffice", values.get(3));
                votes.add(b);
            }
            print(votes);
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found! Make sure that ballot.txt is in the same folder as the class.");
        }
    }
    
    public void addVoter(Voter v)
    {
        voters.add(v);
    }
    
    // Returns the number of files in the current directory that
    // end in "ballot.txt". Should be equivalent to the number of elections.
    private int countBallots()
    {
        File currentDir = new File("./");
        int output = 0;
        FileFilter filter = new FileFilter()
        {
            public boolean accept(File f)
            {
                return f.getName().endsWith("ballot.txt");
            }
        };
        try {
            // Finds number of ballot.txt files in current directory
            output = currentDir.listFiles(filter).length;
        }
        catch (NullPointerException e) {
            System.out.println("No ####ballot.txt files found.");
        }
        return output;
    }
    
    
    private void loadVotes()
    {

    }
    
    // Takes a line of comma-separated values and
    // returns an ArrayList of individual strings.
    // from https://www.baeldung.com/java-csv-file-array
    private ArrayList<String> getRecordFromLine(String line)
    {
        ArrayList<String> values = new ArrayList<String>();
        try (Scanner rowScanner = new Scanner(line))
        {
            rowScanner.useDelimiter(",");
            while (rowScanner.hasNext())
            {
                values.add(rowScanner.next());
            }
        }
        return values;
    }
    
    private void print(ArrayList<HashMap> ballot)
    {
        for (HashMap<String, String> h : ballot)
        {
            // Print keys and values
            for (String i : h.keySet())
            {
                System.out.println("key: " + i + " value: " + h.get(i));
            }
            System.out.println();
        }
    }
}