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
    private int elections; // Number of ballots in the ballots directory
    private File[] ballotNames; // List of all the ballot names in the ballots directory
    private ArrayList<Voter> voters; // Voter database
    int numVoters; // Total number of registered voters
    private int[] electionIDs; // Array of unique elections
    private ArrayList<Candidate> ballots;
    Scanner sn;

    public DB()
    {
        elections = countBallots(); // Gets number of ballot text files
        // print(ballotNames); // Testing line
        ballots = new ArrayList<Candidate>();
        voters = new ArrayList<Voter>();
        loadBallots();
        loadVoters();
        // printVoters();
    }

    // Fills the voters ArrayList with voters read in from voters.txt
    // First five fields are voter info, any fields beyond that are added to an ArrayList of electionIDs.
    private void loadVoters()
    {
        try
        {
            sn = new Scanner(new File("voters/voters.txt"));
            while (sn.hasNextLine())
            {
                ArrayList<String> values = getRecordFromLine(sn.nextLine());
                String first = values.get(0);
                String last = values.get(1);
                String address = values.get(2);
                String city = values.get(3);
                String state = values.get(4);
                // If voter hasn't voted in past elections, create a default Voter
                if (values.size() == 5)
                {
                    addVoter(new Voter(first, last, address, city, state));
                }
                // Otherwise, create new ArrayList treating all remaining Strings as new electionIDs
                else
                {
                    ArrayList<Integer> past = new ArrayList<Integer>();
                    for (int i = 5; i < values.size(); i++)
                    {
                        try {
                            int e = Integer.parseInt(values.get(i));
                            past.add(e);
                        }
                        catch (NumberFormatException e) {
                            System.out.println("Bad electionID in voters.txt.");
                        }
                    }
                    addVoter(new Voter(first, last, address, city, state, past));
                }
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
        // For each ballot file in directory
        for (int i = 0; i < elections; i++)
        {
            try
            {
                // Scan through the ballot
                sn = new Scanner(ballotNames[i]);
                String filename = ballotNames[i].getName();
                String ballotYear = filename.substring(0,filename.indexOf("ballot"));
                // Construct a new object for each candidate in the ballot
                while (sn.hasNextLine())
                {
                    ArrayList<String> values = getRecordFromLine(sn.nextLine());
                    String first = values.get(0);
                    String last = values.get(1);
                    int id = Integer.parseInt(values.get(2));
                    int office = Integer.parseInt(values.get(3));
                    int year = Integer.parseInt(ballotYear);
                    ballots.add(new Candidate(first,last,id,office,year));
                }
                //print(ballots);
            }
            catch (FileNotFoundException e)
            {
                System.out.println("File not found! Make sure that ballot.txt is in the same folder as the class.");
            }
        }
    }

    // Allows external classes to add a voter to the voter database.
    public void addVoter(Voter v)
    {
        voters.add(v);
    }

    // Returns the number of files in the current directory that
    // end in "ballot.txt". Should be equivalent to the number of elections.
    private int countBallots()
    {
        File currentDir = new File("./ballots/");
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
            ballotNames = currentDir.listFiles(filter);
            output = ballotNames.length;
        }
        catch (NullPointerException e) {
            System.out.println("No ####ballot.txt files found.");
        }
        return output;
    }

    // Returns an ArrayList of Candidates who are running in a given election year.
    public ArrayList<Candidate> getBallot(int year)
    {
        //System.out.println("GetBallot called.");
        ArrayList<Candidate> output = new ArrayList<Candidate>();
        // printBallot(ballots);
        for (Candidate c : ballots)
        {
            if(c.getYear() == year)
            {
                output.add(c);
            }
        }
        printBallot(output);
        return output;
    }

    // Loads votes from votes.txt
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

    // For testing, prints out the Ballot
    private void printBallot(ArrayList<Candidate> ballot)
    {
        for (Candidate c : ballot)
        {
            System.out.println(c);
        }
    }

    private void printFiles(File[] files)
    {
        for (File f : files)
        {
            // Print File
            System.out.println(f);
        }
    }
    
    private void printVoters()
    {
        for (Voter v : voters)
        {
            // Print File
            System.out.println(v);
        }
    }
}