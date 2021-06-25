import java.util.Scanner;

public class test {
	
	public static void main(String[] args) {
		
		Scanner sn = new Scanner(System.in);
		DB db = new DB();
		String givenString;
		String givenVoterName;
		int givenYear = 0;
		int exiter = 0;
		int exit = 0;
		// Loop that will make the program return to main options after exiting a sub menu
		// Asks the user if they want to vote, look at reports, or exit program
		while(exiter == 0) {
			System.out.println("Enter 'vote' to place a vote, 'admin' for reports, or 'exit' to exit:");
			givenString = sn.next();
			
			/****************************
			 * 	ADMIN OPTIONS       *
			 ****************************/
			if(givenString.equals("admin")) {
				// Loop that continues to ask for the year election that you want to view until you give a valid year
				while(exit == 0) {
					System.out.print("What year election information do you want to see? ");
					givenYear = sn.nextInt();
					for(int year: db.getElectionIDs()) {
						// If the given year is valid, exit loop and go to next menu
						if(givenYear == year) {
							exit++;
						}
					}
					if(exit == 0)
						System.out.println("No election information for that year.");
				}
				exit = 0;
				Report r = new Report(givenYear, db.getBallot(givenYear), db.getVotes(givenYear), db.getVoters());
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
							givenVoterName = sn.next();
							if (givenVoterName.equals("all"))
								System.out.println(r.reportVoters());
							else {
								String[] name = givenVoterName.split(" ");
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
			/****************************
			 * 	VOTER OPTIONS       *
			 ****************************/
			else if(givenString.equals("vote")) {
				// vote code
			}
			// exits the program
			else if(givenString.equals("exit"))
				exiter++;
			// print statement if an incorrect input is given
			else 
				System.out.println("Unknown command.");
		}
		sn.close();
	}
}
