/*
 * Report.java is going to be called from the main method when an admin of the program asks the program to generate a report of an election.
 * When used correctly, the object can generate formated Strings that the program can use to display:
 * 		- the current number of registered voters
 * 		- the number of votes placed in the given election
 * 		- the ballot
 * 		- the current vote breakdown
 * It can also seperatly output information regarding number of registered voters, number of votes placed in a given election, ballot information, and current vote breakdown.
 */

import java.util.ArrayList;
import java.util.Collections;

public class Report {
	
	private ArrayList<Candidate> ballot = new ArrayList<Candidate>();	// Holds all the Candidate information relevant to the given electionID
	private ArrayList<Integer> votes = new ArrayList<Integer>();		// Holds all of the votes for the candidates on the ballot
	private Double[] percentages;										// Holds the percentages of votes to each candidate
	private int electionID;												// Used in reporting the name of the election
	private int numVoters;												// Used to report how many registered voters there are
	private int voteCount;												// Used to report the numbers of votes placed in the given election
	private int candidates;												// Used in calculating percentages of votes
	private int officeCount;											// Used for looping through the ArrayLists
	
	// default constructor not used
	public Report() {}
	
	/*
	 * Argument constructor used in setting/initializing the values of all the variables for the object.
	 */
	public Report(int electionID, int numVoters, ArrayList<Candidate> ballot, ArrayList<Integer> votes) {
		this.ballot = ballot;
		this.votes = votes;
		this.electionID = electionID;
		this.numVoters = numVoters;
		candidates = ballot.size();
		officeCount = votes.size();
		for(Integer i : votes) {
			voteCount += i;
		}
		percentages = new Double[candidates];
		calcPercentage();
	}
	
	/*
	 * A "report everything" method
	 * Will generate a large formated string of everything related to the given election.
	 * Other methods are used to generate specific parts of the report.
	 */
	public String electionReport() {
		String result = "";
		result += ballotReport();
		result += participationReport();		
		return result;
	}
	
	/*
	 * Returns a string in this format:
	 * 
	 * 	First:		Last:		Office:		Votes:		Percentage:
	 * 
	 * 	John		Doe			President	10					52.0
	 *  Bob			Dob			President	9					48.0
	 *  
	 *  Steve		Peve		Mayor		8					53.0
	 *  Ray			Day			Mayor		7					47.0
	 *  
	 *  Copies the values in the votes ArrayList into another ArrayList and then sorts those votes. 
	 *  Then loops through the the ballot n number of times (n being the number of offices being run
	 *  for on the ballot), each time adding the n office's ballot results in order from most votes 
	 *  to least votes to the string that will be returned by the method.
	 */
	public String ballotReport() {
		ArrayList<Integer> sortedVotes = new ArrayList<Integer>();
		String result = "Displaying ballot for the year of " + electionID + ":\n\n";
		result += String.format("%-12s", "First:") + String.format("%-12s", "Last:") + String.format("%-14s", "Office:") + String.format("%-10s", "Votes:") + String.format("%-12s", "Percentage:") + "\n\n";
		for(Integer i: votes) {
			sortedVotes.add(i);
		}
		Collections.sort(sortedVotes);
		for(int i = 0; i < officeCount; i++) {
			for(int j = (votes.size() - 1); j >= 0; j--) {
				int k = votes.indexOf(sortedVotes.get(j));
				if(ballot.get(k).getOfficeID() == i) {
				result += 
						String.format("%-12s", ballot.get(k).getFirstName()) + 
						String.format("%-12s", ballot.get(k).getLastName()) + 
						String.format("%-14s", ballot.get(k).getOffice()) + 
						String.format("%-10s", votes.get(k)) + 
						String.format("%12.1f", percentages[k]) + "\n";	
				}
			}
			result += "\n";
		}
		return result;
	}
	
	/*
	 * Returns a formated string that displays ballot information including: 
	 * 	year of ballot, names of candidates, and the offices they are running for.
	 * Similar format as ballotReport method.
	 */
	public String ballot() {
		String result = "Displaying ballot for the year of " + electionID + ":\n\n";
		result += String.format("%-12s", "First:");
		result += String.format("%-12s", "Last:");
		result += String.format("%-20s", "Office running for:");
		result += "\n\n";
		for(int i = 0; i < officeCount; i++) {
			for(int j = 0; j < candidates; j++) {
				if(ballot.get(j).getOfficeID() == i) {
					result += String.format("%-12s", ballot.get(j).getFirstName());
					result += String.format("%-12s", ballot.get(j).getLastName());
					result += String.format("%-20s", ballot.get(j).getOffice());
					result += "\n";
				}
			}
			result += "\n";
		}
		return result;
	}
	
	/*
	 * Reports on the participation of the voters in the given election. Formats a string 
	 * to display information about the voter turnout and then returns it.
	 */
	public String participationReport() {
		String result = "";
		double participation = ((double)voteCount/numVoters) * 100;		
		result += String.format("%-35s", "Number of registered voters: ") + String.format("%10s", numVoters) + "\n";
		result += String.format("%-35s", "Number of votes placed: ") + String.format("%10s", voteCount) + "\n";
		result += String.format("%-35s", "Percent participation: ") + String.format("%10.1f", participation) + "\n";
		return result;
	}
	
	/*
	 *  Does the required math to figure out the percentages of votes to each candidate.
	 *  Percentages are based off total votes for each given office, not total votes.
	 */
	private void calcPercentage() {
		double perc = 0;
		int[] officeVoteCount = new int[officeCount];
		for(int i = 0; i < officeCount; i++) {
			officeVoteCount[i] = 0;
		}
		for(int i = 0; i < officeCount; i++) {
			for(int j = 0; j < candidates; j++) {
				if(ballot.get(j).getOfficeID() == i) {
					officeVoteCount[i] += votes.get(j);
				}
			}
		}
		for(int i = 0; i < officeCount; i++) {
			for(int j = 0; j < candidates; j++) {
				if(ballot.get(j).getOfficeID() == i) {
					perc = ((double)votes.get(j) / officeVoteCount[i]) * 100;
					percentages[j] = perc;
				}
			}
		}
	}

	/***************************************************
	 *               Getters and Setters               *
	 ***************************************************/
	
	public int getElectionID() {
		return electionID;
	}

	public void setElectionID(int electionID) {
		this.electionID = electionID;
	}

	public int getNumVoters() {
		return numVoters;
	}

	public void setNumVoters(int numVoters) {
		this.numVoters = numVoters;
	}

	public int getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(int voteCount) {
		this.voteCount = voteCount;
	}

	public int getCandidates() {
		return candidates;
	}

	public void setCandidates(int candidates) {
		this.candidates = candidates;
	}

	public int getOfficeCount() {
		return officeCount;
	}

	public void setOfficeCount(int officeCount) {
		this.officeCount = officeCount;
	}
}