import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class test {
	
	/*
	 * Main method will create DB and Report objects to bring in all of the 
	 * information from txt files and store it in the program. It then calls 
	 * a method that creates a loop of menus that continue to prompt the user 
	 * for inputs until an exit scenario is met to save all progress in the 
	 * election and exit the program.
	 */
	public static void main(String[] args) {
		DB db = new DB();
		Scanner sn = new Scanner(System.in);
		homeScreen(db, sn);
		sn.close();
		// This is where methods would be called to save election progress to txt files.
	}	
	
	/*
	 * Creates a loop that continues to repeat until the user enters 'exit'
	 * This loop continues to ask the user to either enter the vote screen, admin screen, 
	 * or exit program.
	 */
	public static void homeScreen(DB db, Scanner sn) {
		int exit = 0;				// used to exit the program
		String givenString = "";	// used to store user input, which is then used to "change screens"
		while(exit == 0) {
			System.out.println("Enter 'vote' to place a vote, 'admin' for reports, or 'exit' to exit:");
			givenString = sn.next();
			// brings user to admin menus
			if(givenString.equals("admin")) {
				adminScreen(db,sn);
			}
			// brings user to voter menu
			else if(givenString.equals("vote")) {
				voteScreen(db,sn);
			}
			// exits the program
			else if(givenString.equals("exit"))
				exit++;
			// print statement if an incorrect input is given
			else 
				System.out.println("Unknown command.");
		}
	}
	
	/*
	 * Creates a loop that continues to repeat until the user enters 'exit'
	 * This loop prompts the user to enter the year election they want to participate in and 
	 * then gives them the ability to place their votes on the given year's ballot.
	 */
	public static void voteScreen(DB db, Scanner sn) {
		int givenNumber = 0;						// used to store the year election to participate in
		int[] ballots = db.getElectionIDs();		// used to print out the ballots to choose from
		
		/*
		 * Finds the election that the voter wants to participate in and then creates an array to hold the votes
		 */
		System.out.println("Type the year of the listed election that you want to participate in: ");
		for(int i = 0; i < ballots.length; i++) {
			System.out.println("" + ballots[i]);
		}
		givenNumber = sn.nextInt();
		Set<Integer> numOffices = new HashSet<Integer>();
		for(Candidate c: db.getBallot(givenNumber)) {
			numOffices.add(c.getOfficeID());
		}
		int[] votes = new int[numOffices.size()];	// used to store user input, which is then used to choose
		
		/*
		 * Asks the voter to place votes 1 at a time based on the offices on the ballot.
		 */
		int count = 0;
		for(int office: numOffices) {
			System.out.print(String.format("%-30s", "Running for " + db.getBallot(givenNumber).get(0).generateOffice(office) + ":"));
			System.out.print(String.format("%25s", "Candidate ID:") + "\n");
			for(int j = 0; j < db.getBallot(givenNumber).size(); j++) {
				Candidate c = db.getBallot(givenNumber).get(j);
				if(c.getOfficeID() == office) {
					System.out.print(String.format("%-15s", c.getFirstName()));
					System.out.print(String.format("%-15s", c.getLastName()));
					System.out.print(String.format("%25s", c.getCandidateID()) + "\n");
				}
			}
			System.out.print("Enter Candidate ID that you want to vote for: ");
			votes[count] = sn.nextInt();
			System.out.print("\n");
			count++;
		}
		
		/*
		 * Puts the vote in the correct Candidate object and also creates a Vote object
		 * and places it in the database. It contains who the user voted for.
		 */
		for(int i = 0; i < votes.length; i++) {
			for(Candidate c: db.getBallot(givenNumber)) {
				if(votes[i] == c.getCandidateID()) {
					c.addVote();
					break;
				}
			}
		}
		db.addVote(new Vote(givenNumber,votes));
	}
	
	/*
	 * Creates a loop that continues to repeat until the user enters 'exit'
	 * This loop continues to ask the user for inputs to print out specific reports, 
	 * look up information, or go back to previous screen.
	 */
	public static void adminScreen(DB db, Scanner sn) {
		int exit = 0;				// used to exit the screen
		String givenString = "";
		int givenNumber = 0;
		// Loop that continues to ask for the year election that you want to view until you give a valid year
		while(exit == 0) {
			System.out.print("What year election information do you want to see? ");
			givenNumber = sn.nextInt();
			for(int year: db.getElectionIDs()) {
				// If the given year is valid, exit loop and go to next menu
				if(givenNumber == year) {
					exit++;
				}
			}
			if(exit == 0)
				System.out.println("No election information for that year.");
		}
		exit = 0;
		Report r = new Report(givenNumber, db.getBallot(givenNumber), db.getVotes(givenNumber), db.getVoters());
		// loop that continues to ask what kind of report you want until you enter 'exit'
		while(exit == 0) {
			System.out.println("What kind of report do you want: ");
			System.out.println("\t Type 'voter' if you want to see voter information.");
			System.out.println("\t Type 'ballot' if you want to see ballot information.");
			System.out.println("\t Type 'general' if you want to see election election results.");
			System.out.println("\t Type 'exit' if you want to exit program.");
			givenString = sn.next();
			// depending on what the user enters, the program will either ask more information or print out requested information
			switch (givenString) {
				// Tests the voter reports
				case "voter": 
					System.out.println("Enter last name of voter you want to look up: ");
					System.out.println("Or enter 'all' to see all voter information");
					givenString = sn.next();
					if (givenString.equals("all"))
						System.out.println(r.reportVoters());
					else {
						String[] name = givenString.split(" ");
						int counter = 0;
						for(Voter v: db.getVoters()) {
							if(name[0].equals(v.getLastName())) {
								System.out.println(v.toString());
								counter++;
							}
						}
						if(counter == 0)
							System.out.println("No voters with that name.");
					}
					break;
				// Tests the ballot reports
				case "ballot": 
					System.out.println(r.ballot());
					break;
				// Tests the general reports
				case "general": 
					System.out.println(r.electionReport());
					break;
				// Exits the loop, to either close the program or return to main screen options
				case "exit": 
					exit++;
					break;
				// If input is wrong, print out message and then ask again.
				default: 
					System.out.println("Error with input");
					break;
			}
		}
	}
}