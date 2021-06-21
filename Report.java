/*
 * Report.java is going to be called from the main method when an admin of the program asks the program to generate a report of an election.
 * When used correctly, the object can generate a String that the program can use to display:
 * 		- the current number of registered voters
 * 		- the number of votes placed in the given election
 * 		- the ballot
 * 		- the current vote breakdown
 * It can also seperatly output information regarding number of registered voters, number of votes placed in a given election, ballot information, and current vote breakdown.
 */

import java.util.ArrayList;

public class Report {
	
	ArrayList<Candidate> ballot = new ArrayList<Candidate>();	// Holds all the Candidate information relevant to the given electionID
	private int[] votes;										// Holds all of the votes for the candidates on the ballot
	private int electionID;										// Used to find the correct ####votes.txt file and in reporting the name of the election
	private int numVoters;										// Used to report how many registered voters there are
	private int voteCount;										// Used to report the numbers of votes placed in the given election
	private int candidates;										// Used in calculating percentages of votes
	
	public Report() {
		// default constructor not used
	}
	
	public Report(int electionID, int numVoters, ArrayList<Candidate> ballot) {
		this.ballot = ballot;
		this.electionID = electionID;
		this.numVoters = numVoters;
		// count the number of candidates on ballot and update candidates
		votes = new int[candidates];
		for (int i = 0; i < candidates; i++) {
			votes[i] = 0;
		}
	}
	
	public String displayReport() {
		// finds and reads the ####votes.txt file related to the electionID
		// while reading ####votes.txt update ArrayList votes and voteCount
		// call calcPercentage() to calculate percentage of votes to each candidate
		// print out the number of registered voters
		// print out the number of votes in this election
		// sort the candidates in order of who receive the most votes to least votes
		// print out the results
		return "test";
	}
	
	@SuppressWarnings("unused")
	private int calcPercentage() {
		// does the required math to figure out the percentages of votes to each candidate
		return 0;
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
}