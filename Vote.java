/*
 * This object represents a voter's votes on a ballot
 * The candidates that were voted for along with the year the vote was placed is stored here.
 */

public class Vote {
	
	private int electionID;
	private int[] votes;
	
	// default constructor not used
	public Vote() {}
	
	/*
	 *  arg constructor that stores the votes in an int array along with the candidateID (year)
	 */
	public Vote(int electionID, int[] votes) {
		this.electionID = electionID;
		this.votes = votes;
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

	public int[] getVotes() {
		return votes;
	}

	public void setVotes(int[] votes) {
		this.votes = votes;
	}
}