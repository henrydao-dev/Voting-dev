/*
 * Candidate.java will represent a candidate for a particular office
 * This object will allow a user to return a string of information the user wants about the candidate including:
 * 	-first name
 * 	-last name
 * 	-office they are running for
 * 	-candidateID
 */

public class Candidate {
	
	private String firstName;		// First name of candidate
	private String lastName;		// Last name of candidate
	private String office;			// Name of the office the candidate is running for
	private int candidateID;		// number used in ####votes.txt to show the votes belongs to this candidate
	
	// default constructor not used
	public Candidate() {}
	
	/*
	 * argument constructor used in setting the values of all the variables for the object
	 */
	public Candidate(String firstName, String lastName, int office, int id) {
		this.firstName = firstName;
		this.lastName = lastName;
		switch (office) {
			case 0: this.office = "President";
			case 1: this.office = "House Rep";
			case 2: this.office = "Senate";
			case 3: this.office = "Mayor";
				break;
			default:
				break;
		}
		this.candidateID = id;
	}
	
	/*
	 * Outputs a text-friendly string
	 */
	public String toString() {
		return "test";
	}

	/***************************************************
	 *               Getters and Setters               *
	 ***************************************************/
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	public int getCandidateID() {
		return candidateID;
	}

	public void setCandidateID(int candidateID) {
		this.candidateID = candidateID;
	}
}