// DB.java
// @author Douglas Kiang
import java.util.Arrays;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class DB
{
    private final int ELECTIONDATA = 5;
    private int NUMOFFICES = 4;
    private int elections; // Number of ballots in the ballots directory
    private File[] ballotNames; // List of all the ballot names in the ballots directory
    private File[] voteNames; // List of all the vote names in the votes directory
    private ArrayList<Voter> voters; // Voter database
    private ArrayList<Vote> votes; // All votes for all elections
    private int numVoters; // Total number of registered voters
    private ArrayList<Candidate> ballots;

    Scanner sn;

    public DB()
    {
        elections = countBallots(); // Gets number of ballot text files
        ballots = new ArrayList<Candidate>();
        voters = new ArrayList<Voter>();
        loadAllBallots();
        //loadVoters();
        votes = new ArrayList<Vote>();
        loadAllVotes();
    }

    /***************************************************
	 *               Getter Methods                    *
	 ***************************************************/

    // Returns the total number of candidates on the ballot in a given year.
    public int getNumberOfCandidates(int year)
    {
        return getBallot(year).size();
    }
    
    // Returns the number of candidates on the ballot for a given office in a given year.
    public int getNumberOfCandidates(int year, String office)
    {
        int count = 0;
        ArrayList<Candidate> all = getBallot(year);
        for (Candidate c : all)
        {
            if (c.getOffice().equals(office))
            {
                count++;
            }
        }
        return count;
    }

    // Returns an ArrayList of Candidates who are running in a given election year.
    public ArrayList<Candidate> getBallot(int year)
    {
        ArrayList<Candidate> output = new ArrayList<Candidate>();
        for (Candidate c : ballots)
        {
            if(c.getYear() == year)
            {
                output.add(c);
            }
        }
        //printBallot(output);
        return output;
    }
    
    // Returns all voters
    public ArrayList<Voter> getVoters()
    {
        return voters;
    }
    
    // Returns last occurrence of a Voter with matching id
    // If no match, returns null
    public Voter search(int id)
    {
        Voter output = null;
        for (Voter v : voters)
        {
            if (v.getID() == id)
            {
                output = v;
            }
        }
        return output;
    }
    
    // Returns an ArrayList of all votes recorded for all elections
    public ArrayList<Vote> getVotes()
    {
        return votes;
    }
    
    // Returns an ArrayList of all votes recorded for a given electionID
    public ArrayList<Vote> getVotes(int id)
    {
        ArrayList<Vote> output = new ArrayList<Vote>();
        for (Vote v : votes)
        {
            if (v.getElectionID() == id)
            {
                output.add(v);
            }
        }
        return output;
    }
    
    /***************************************************
	 *               Setter Methods                    *
	 ***************************************************/
    
    // Allows external classes to add a voter to the voter database.
    public void addVoter(Voter v)
    {
        voters.add(v);
    }


    /***************************************************
	 *               Helper Methods                    *
	 ***************************************************/


    // Returns the number of files in the current directory that
    // end in "ballot.txt". Should be equivalent to the number of elections.
    private int countBallots()
    {
        File currentDir = new File("ballots/");
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

    // Fills the voters ArrayList with voters read in from voters.txt
    // First six fields are voter info, any fields beyond that are added to an ArrayList of electionIDs.
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
                if (values.size() == ELECTIONDATA)
                {
                    addVoter(new Voter(first, last, address, city, state));
                }
                // Otherwise, create new ArrayList treating all remaining Strings as new electionIDs
                else
                {
                    ArrayList<Integer> past = new ArrayList<Integer>();
                    for (int i = ELECTIONDATA; i < values.size(); i++)
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
        //printVoters();
    }

    // Fills the ballot with all Candidates from every year read in from all ballot.txt files
    private void loadAllBallots()
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
                //printBallot(ballots);
            }
            catch (FileNotFoundException e)
            {
                System.out.println("File not found! Make sure that ballot.txt is in the same folder as the class.");
            }
        }
    }

    // Loads votes from all ####votes.txt files into results array
    private void loadAllVotes()
    {
        // Load all ####votes.txt filenames into voteNames
        File currentDir = new File("votes/");
        FileFilter filter = new FileFilter()
        {
            public boolean accept(File f)
            {
                return f.getName().endsWith("votes.txt");
            }
        };
        try {
            // Finds number of votes.txt files in current directory
            voteNames = currentDir.listFiles(filter);
        }
        catch (NullPointerException e) {
            System.out.println("No ####votes.txt files found.");
        }
        // Iterate over voteNames and process votes into results
        for (int i = 0, n = voteNames.length; i < n; i++)
        {
            try {
                sn = new Scanner(voteNames[i]);
            }
            catch (FileNotFoundException e) {
                System.out.println("No file found.");
            }
            String filename = voteNames[i].getName();
            // Set ballotYear to the date prefix from filename
            int ballotYear = Integer.parseInt(filename.substring(0,filename.indexOf("votes")));
            while (sn.hasNextLine())
            {
                // Gets each line as an array of Strings
                ArrayList<String> voteStrings = getRecordFromLine(sn.nextLine());
                int[] temp = new int[NUMOFFICES];
                for (int j = 0, m = voteStrings.size(); j < m; j++)
                {
                    if (j < temp.length) // Safety check
                    {
                        temp[j] = Integer.parseInt(voteStrings.get(j));
                    }
                }
                // System.out.println(Arrays.toString(temp)); // Temporary debug
                votes.add(new Vote(ballotYear,temp));
            }
        }
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