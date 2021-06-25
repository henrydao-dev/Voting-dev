// DB.java
// @author Douglas Kiang
import java.util.Arrays;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.util.Collections;
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
    private int[] electionIDs; // Array of all electionIDs
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
        electionIDs = new int[countBallots()];
        loadAllBallots();
        loadVoters();
        votes = new ArrayList<Vote>();
        loadAllVotes();
    }

    /***************************************************
	 *               Getter Methods                    *
	 ***************************************************/

    // Returns an ArrayList of Candidates who are running in a given election year.
    public ArrayList<Candidate> getBallot(int electionID)
    {
        ArrayList<Candidate> output = new ArrayList<Candidate>();
        for (Candidate c : ballots)
        {
            if(c.getYear() == electionID)
            {
                output.add(c);
            }
        }
        //printBallot(output);
        return output;
    }

    // Returns the total number of candidates on the ballot in a given year.
    public int getNumberOfCandidates(int electionID)
    {
        return getBallot(electionID).size();
    }

    // Returns the number of candidates on the ballot for a given office in a given year.
    public int getNumberOfCandidates(int electionID, String office)
    {
        int count = 0;
        ArrayList<Candidate> all = getBallot(electionID);
        for (Candidate c : all)
        {
            if (c.getOffice().equals(office))
            {
                count++;
            }
        }
        return count;
    }

    // Returns all voters
    public ArrayList<Voter> getVoters()
    {
        return voters;
    }

    // Returns first occurrence of a Voter with matching id
    // If no match, returns null
    public Voter search(int voterID)
    {
        int index = findVoter(voterID);
        if (index < 0)
        {
            return null;
        }
        return voters.get(index);
    }

    // Returns an ArrayList of all votes recorded for all elections
    public ArrayList<Vote> getVotes()
    {
        return votes;
    }

    // Returns an ArrayList of all votes recorded for a given electionID
    public ArrayList<Vote> getVotes(int electionID)
    {
        ArrayList<Vote> output = new ArrayList<Vote>();
        for (Vote v : votes)
        {
            if (v.getElectionID() == electionID)
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

    // Updates Voter's record to show they voted in a given election
    public void recordElection(int electionID, int voterID)
    {
        int index = findVoter(voterID);
        // If valid voterID and electionID add electionID to voter's record.
        // Otherwise, fail silently.
        if (index >= 0 && isValid(electionID))
        {
            voters.get(index).addElection(electionID);
        }
    }

    // Adds a Vote to the votes database.
    // Verifies that electionID is valid and number of votes
    // matches the number of candidates on the ballot.
    public void addVote(Vote vote)
    {
        int year = vote.getElectionID();
        if(isValid(year) && vote.getVotes().length == getNumberOfCandidates(year))
        {
            votes.add(vote);
        }
    }
    
    public int[] tabulate(int electionID)
    {
        int[] results = new int[getNumberOfCandidates(electionID)];
        ArrayList<Vote> table = getVotes(electionID);
        for (Vote v : table)
        {
            int[] votes = v.getVotes();
            for (int n : votes)
            {
                results[n]++;
            }
        }
        return results;
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
    // First five fields are voter info, sixth is generated ID, any fields beyond that are added to an ArrayList of electionIDs.
    private void loadVoters()
    {
        try
        {
            sn = new Scanner(new File("voters/voters.txt"));
            while (sn.hasNextLine())
            {
                ArrayList<String> values = getRecordFromLine(sn.nextLine());
                // If voter hasn't voted in past elections, create a default Voter
                if (values.size() == ELECTIONDATA)
                {
                     addVoter(new Voter(values.get(0), values.get(1), values.get(2), values.get(3), values.get(4)));
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
                    addVoter(new Voter(values.get(0), values.get(1), values.get(2), values.get(3), values.get(4), past));
                }
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found! Make sure that voters.txt is in the same folder as the class.");
        }
        // Sort voters by ID
        quickSort(0,voters.size() - 1);
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
                // ballotNames contains the names of every ballot file in the ballots directory
                sn = new Scanner(ballotNames[i]);
                String filename = ballotNames[i].getName();
                int ballotYear = Integer.valueOf(filename.substring(0,filename.indexOf("ballot")));
                // Add ballotYear to electionIDs
                electionIDs[i] = ballotYear;
                // Construct a new object for each candidate in the ballot
                while (sn.hasNextLine())
                {
                    ArrayList<String> values = getRecordFromLine(sn.nextLine());
                    String first = values.get(0);
                    String last = values.get(1);
                    int id = Integer.parseInt(values.get(2));
                    int office = Integer.parseInt(values.get(3));
                    ballots.add(new Candidate(first,last,id,office,ballotYear));
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

    // Binary search returns the index of the voter with given id.
    // Returns -1 if voter is not found.
    private int findVoter(int id)
    {
        int start = 0; 
        int end = voters.size() - 1;
        while (start <= end)
        {
            // get midpoint
            int mid = (start + end) / 2;
            // if midpoint < val
            if (voters.get(mid).getID() < id)
            {
                // move start just to the right of midpoint
                start = mid + 1;
            }
            else if (voters.get(mid).getID() > id)
            {
                // move end just to the left of midpoint
                end = mid - 1;
            }
            else return mid;
        }
        // Voter not found
        return -1;
    }

    // Returns true iff electionID is one of the ballot prefixes.
    private boolean isValid(int electionID)
    {
        for (int e : electionIDs)
        {
            if (electionID == e)
            {
                return true;
            }
        }
        return false;
    }

    // For testing, prints out the Ballot
    private void printBallot(ArrayList<Candidate> ballot)
    {
        for (Candidate c : ballot)
        {
            System.out.println(c);
        }
    }

    // For testing, prints out all Files in files
    private void printFiles(File[] files)
    {
        for (File f : files)
        {
            // Print File
            System.out.println(f);
        }
    }

    // For testing, prints out voters
    private void printVoters()
    {
        for (Voter v : voters)
        {
            // Print File
            System.out.println(v);
        }
    }
    
    // Manually sort Voters by id. Collections wasn't doing it for me.
    private void quickSort(int low, int high)
    {
        if (low < high)
        {
            int pi = partition(low, high);
            quickSort(low, pi - 1);
            quickSort(pi + 1, high);
        }
    }
        
    // Helper method for QuickSort
    private int partition(int low, int high)
    {
        int pivot = voters.get(high).getID();
        int i = low - 1;
        for (int j = low; j <= high - 1; j++)
        {
            if (voters.get(j).getID() < pivot)
            {
                i++;
                swap(i,j);
            }
        }
        swap(i + 1, high);
        return (i + 1);
    }
    
    // Helper method for QuickSort
    private void swap(int x, int y)
    {
        Voter temp = voters.get(x);
        voters.set(x, voters.get(y));
        voters.set(y,temp);
    }
}