import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Voting_UI extends Application{
	
	private StackPane mainMenuPane, voteMenuPane;
	private Pane loginPane;
	private Scene mainMenuScene, loginScene, voteMenuScene, adminMenuScene;
	private Button voteButton, adminButton,CandidateButton1,
	CandidateButton2,CandidateButton3,lookupButton,registerVoterButton,
	createElectionButton,updateBallotButton,countButton,reportbutton;
	private Stage window;
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
		
	}

	private void createSystemMenuScene() {
		
		//this creates the window where the buttons and the title appears
		mainMenuPane = new StackPane();
		mainMenuPane.setPadding(new Insets(0,0,0,0));
		mainMenuScene = new Scene(mainMenuPane,400,500);
		
		//This for the creation of the title
		Label title = new Label();
		title.setText("Welcome");
		title.setPrefSize(250, 50);
		title.setFont(Font.font("Times New Roman",35));
		title.setTranslateY(-200);
		title.setTranslateX(45);
		
		//creates Buttons
		voteButton = new Button("Vote");
		voteButton.setPrefSize(150,50);
		voteButton.setTranslateY(-100);
		
		//Upon clicking will run the code inside.
		voteButton.setOnAction(e ->
		{
			//saving this for later 
			
		});
		
		adminButton = new Button("Vote");
		adminButton.setPrefSize(150,50);
		adminButton.setTranslateY(-100);
		
		//Upon clicking will run the code inside.
		adminButton.setOnAction(e ->
		{
					//saving this for later 
					
		});
		
		
	
	}
	
	private void createVoterLoginScene() {
		
		//Create Login Pane
		loginPane = new Pane();
		loginPane.setPadding(new Insets(0,0,0,0));
		
		//creates Scene
		loginScene = new Scene(loginPane,400,200);
		
		//creates title
		Label loginTitle = new Label();
		loginTitle.setText("Login");
		loginTitle.setPrefSize(250, 50);
		loginTitle.setFont(Font.font("Times New Roman",20));
		loginTitle.setLayoutX(135);;
		loginTitle.setLayoutY(5);
		loginPane.getChildren().add(loginTitle);
		
		//label for voter ID field
		Label voterID = new Label();
		voterID.setText("Username:");
		voterID.setPrefSize(250, 50);
		voterID.setFont(Font.font("Times New Roman",15));
		voterID.setLayoutX(50);;
		voterID.setLayoutY(40);
		loginPane.getChildren().add(voterID);
		
		//colors the message red
		Label message = new Label();
		message.setText("");
		message.setStyle("-fx-text-inner-color: red;");
		message.setPrefSize(250, 50);
		message.setFont(Font.font("Times New Roman",15));
		message.setLayoutX(140);
		message.setLayoutY(108);
		message.setWrapText(true);
		loginPane.getChildren().add(message);

		//Voter Id Text field
		TextField voterIDField = new TextField();
		voterIDField.setMaxWidth(375);
		voterIDField.setLayoutX(130);
		voterIDField.setLayoutY(53);
		loginPane.getChildren().add(voterIDField);
		
		//password field *This might be optional*
		Label password = new Label();
		password.setText("Password:");
		password.setPrefSize(250, 50);
		password.setFont(Font.font("Times New Roman",15));
		password.setLayoutX(50);;
		password.setLayoutY(80);
		loginPane.getChildren().add(password);
		
		//password Textfield *This might be optional*
		TextField passwordField = new TextField();
		passwordField.setMaxWidth(375);
		passwordField.setLayoutX(130);
		passwordField.setLayoutY(93);
		loginPane.getChildren().add(passwordField);
		
		// Button to confirm Login
		Button loginButton = new Button("Login");
		loginButton.setPrefSize(150, 40);
		loginButton.setLayoutX(195);
		loginButton.setLayoutY(145);
		loginPane.getChildren().add(loginButton);

		//upon clicking "Login", if verified: Transfers to vote menu
			loginButton.setOnAction(e -> 
			{
				try {
				/*needs a completed database to continue working 
				 *but the general idea is to compare the information
				 *That the voter enter can then it transfer to vote menu otherwise
				 *It throws an invalid credentials
				 *I'm not sure what we're using for the database just yet.
				 */

					window.setScene(voteMenuScene);
					
					}catch (Exception e1) {

						System.out.println(e1.getMessage());
						message.setText("Invalid Credentials");
						message.setTextFill(Color.RED);
					}
				});

		
	}
	
private void createAdminLoginScene() {
		
		//Create Login Pane
		loginPane = new Pane();
		loginPane.setPadding(new Insets(0,0,0,0));
		
		//creates Scene
		loginScene = new Scene(loginPane,400,200);
		
		//creates title
		Label loginTitleA = new Label();
		loginTitleA.setText("Login");
		loginTitleA.setPrefSize(250, 50);
		loginTitleA.setFont(Font.font("Times New Roman",20));
		loginTitleA.setLayoutX(135);;
		loginTitleA.setLayoutY(5);
		loginPane.getChildren().add(loginTitleA);
		
		//label for voter ID field
		Label adminID = new Label();
		adminID.setText("Username:");
		adminID.setPrefSize(250, 50);
		adminID.setFont(Font.font("Times New Roman",15));
		adminID.setLayoutX(50);;
		adminID.setLayoutY(40);
		loginPane.getChildren().add(adminID);
		
		//colors the message red
		Label message2 = new Label();
		message2.setText("");
		message2.setStyle("-fx-text-inner-color: red;");
		message2.setPrefSize(250, 50);
		message2.setFont(Font.font("Times New Roman",15));
		message2.setLayoutX(140);
		message2.setLayoutY(108);
		message2.setWrapText(true);
		loginPane.getChildren().add(message2);

		//Voter Id Text field
		TextField adminIDField = new TextField();
		adminIDField.setMaxWidth(375);
		adminIDField.setLayoutX(130);
		adminIDField.setLayoutY(53);
		loginPane.getChildren().add(adminIDField);
		
		//password field *This might be optional*
		Label password2 = new Label();
		password2.setText("Password:");
		password2.setPrefSize(250, 50);
		password2.setFont(Font.font("Times New Roman",15));
		password2.setLayoutX(50);;
		password2.setLayoutY(80);
		loginPane.getChildren().add(password2);
		
		//password Textfield *This might be optional*
		TextField passwordField2 = new TextField();
		passwordField2.setMaxWidth(375);
		passwordField2.setLayoutX(130);
		passwordField2.setLayoutY(93);
		loginPane.getChildren().add(passwordField2);
		
		// Button to confirm Login
		Button loginButton2 = new Button("Login");
		loginButton2.setPrefSize(150, 40);
		loginButton2.setLayoutX(195);
		loginButton2.setLayoutY(145);
		loginPane.getChildren().add(loginButton2);

		//upon clicking "Login", if verified: Transfers to vote menu
			loginButton2.setOnAction(e -> 
			{
				try {
				/*needs a completed database to continue working 
				 *but the general idea is to compare the information
				 *That the voter enter can then it transfer to vote menu otherwise
				 *It throws an invalid credentials
				 *I'm not sure what we're using for the database just yet.
				 */

					window.setScene(adminMenuScene);
					
					}catch (Exception e1) {

						System.out.println(e1.getMessage());
						message2.setText("Invalid Credentials");
						message2.setTextFill(Color.RED);
					}
				});

		
	}

private void createVoteMenuScene() {
	
	//this creates the window where the buttons and the title appears
	voteMenuPane = new StackPane();
	voteMenuPane.setPadding(new Insets(0,0,0,0));
	voteMenuScene = new Scene(voteMenuPane,400,500);
	
	//This for the creation of the title
	Label title2 = new Label();
	title2.setText("Who are you voting for?");
	title2.setPrefSize(250, 50);
	title2.setFont(Font.font("Times New Roman",35));
	title2.setTranslateY(-200);
	title2.setTranslateX(45);
	
	//creates Buttons
	CandidateButton1 = new Button("Candidate1");
	CandidateButton1.setPrefSize(150,50);
	CandidateButton1.setTranslateY(-100);
	
	//Upon clicking will run the code inside.
	CandidateButton1.setOnAction(e ->
	{
		//saving this for later 
		
	});
	
	CandidateButton2 = new Button("Candidate2");
	CandidateButton2.setPrefSize(150,50);
	CandidateButton2.setTranslateY(-100);
	
	//Upon clicking will run the code inside.
	CandidateButton2.setOnAction(e ->
	{
		//saving this for later 
		
	});
	
	CandidateButton3 = new Button("Candidate3");
	CandidateButton3.setPrefSize(150,50);
	CandidateButton3.setTranslateY(-100);
	
	//Upon clicking will run the code inside.
	CandidateButton3.setOnAction(e ->
	{
		//saving this for later 
		
	});
	
	
}

private void createAdminMenuScene() {
	
	//this creates the window where the buttons and the title appears
	voteMenuPane = new StackPane();
	voteMenuPane.setPadding(new Insets(0,0,0,0));
	voteMenuScene = new Scene(voteMenuPane,400,500);
	
	//This for the creation of the title
	Label title3 = new Label();
	title3.setText("Who are you voting for?");
	title3.setPrefSize(250, 50);
	title3.setFont(Font.font("Times New Roman",35));
	title3.setTranslateY(-200);
	title3.setTranslateX(45);
	
	//creates Buttons
	lookupButton = new Button("lookup voter");
	lookupButton.setPrefSize(150,50);
	lookupButton.setTranslateY(-100);
	
	//Upon clicking will run the code inside.
	lookupButton.setOnAction(e ->
	{
		//saving this for later 
		
	});
	
	registerVoterButton = new Button("Register Voter");
	registerVoterButton.setPrefSize(150,50);
	registerVoterButton.setTranslateY(-100);
	
	//Upon clicking will run the code inside.
	CandidateButton2.setOnAction(e ->
	{
		//saving this for later 
		
	});
	
	createElectionButton = new Button("Start a new election");
	createElectionButton.setPrefSize(150,50);
	createElectionButton.setTranslateY(-100);
	
	//Upon clicking will run the code inside.
	createElectionButton.setOnAction(e ->
	{
		//saving this for later 
		
	});
	
	updateBallotButton = new Button("Update Ballot");
	updateBallotButton.setPrefSize(150,50);
	updateBallotButton.setTranslateY(-100);
	
	//Upon clicking will run the code inside.
	updateBallotButton.setOnAction(e ->
	{
		//saving this for later 
		
	});
	
	countButton = new Button("Count votes");
	countButton.setPrefSize(150,50);
	countButton.setTranslateY(-100);
	
	//Upon clicking will run the code inside.
	countButton.setOnAction(e ->
	{
		//saving this for later 
		
	});
	
	reportbutton = new Button("Report election results");
	reportbutton.setPrefSize(150,50);
	reportbutton.setTranslateY(-100);
	
	//Upon clicking will run the code inside.
	reportbutton.setOnAction(e ->
	{
		//saving this for later 
		
	});
	
}
	




	
	

}
