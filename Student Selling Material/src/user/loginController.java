package user;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import admin.Config;
import alert.ConfirmationAlert;
import alert.ErrorAlert;
import alert.Pin;
import database.DatabaseHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import navegation.ChangeView;
import student.ads.Actions;

public class loginController implements Initializable{

	@FXML private AnchorPane loginPane;
	@FXML private ImageView logo;
	@FXML private Label wrongPassLabel;
	@FXML private Label appNameLabel;
	
    @FXML private JFXTextField username;
    @FXML private JFXPasswordField password;
    
    @FXML private JFXButton signUp;
    @FXML private JFXButton cancel;
    @FXML private JFXButton login;
    
//    DATABSE INSTANCE
    DatabaseHandler instance = null;
    
    //Navegation Instances
    ChangeView newView;
    ConfirmationAlert confirmationAlert =  new ConfirmationAlert();
    Pin pinAlert =  new Pin();
    public Config config = null;
    
//    USER
    User user;
    boolean isReg = false;
    
//    ACTIONS
    Actions actions = new Actions();
    
    //Error 
    ErrorAlert alert = new ErrorAlert();
    ArrayList<String> errors =  new ArrayList<String>();
    private String allErrors = "";
    

    

	@FXML
    void onCancel(ActionEvent event) {
//		ALERT THE USER HE IS ABOUT TO CLOSE THE APPLICATION
		if(confirmationAlert
		.displayConfirmation("Confirmation","Closing application","Do you want to close the Application ?"))
			Platform.exit();
    }

    @FXML
    void onForgotPassword(ActionEvent event) {
    	if(isConfig()){
    		SignUpController.setSignUp(false);
    		newView = new ChangeView(
    				"/user/SignUp.fxml",
    				false,
    				false,
    				loginPane);
    		newView.changeView();
    		
    	}else{
    		alert.displayError(
    				"Config Error", 
    				"Failed to load the image folder and the email address", 
    				"There was an error while trying"
    				+ "\nto read the image folders and the "
    				+ "\nsupport email and password"
    				+ "\nIf you are the Admin please set the  "
    				+ "\n Imgae folders path and the Email and "
    				+ "\npassword");
    		
    	}
    }

   
    @FXML
    void onLogin(ActionEvent event) {
    	System.out.println("dfghbjnk");
    	if(isConfig()){
    		user = null;
    		validateUser();
    		
    		if(user !=null){
    			if(isAdmin()){
    				//if the credentials are admin credentials then the admin
    				//pane will be displayed with all admin functionalities
    				newView = new ChangeView(
    						"/admin/Admin.fxml",
    						true,
    						false,
    						loginPane);
    				newView.changeView();
    				//
    			}else{
    				if(isUser()){
    					validateRegUser();
    				}
    				else if(isTemp()){
    					validateTempUser();
    				}else{
    					alert.displayError("Not found error",
    							"Profile not found",
    							"Your profile has not been found\n"
    									+ "Please make sure that you insert correct credentials");
    				}
    				
    			}
    		}else{
    			alert.displayError("Bad Login",
    					"Revise your student number",
    					"Please make sure that yor student number is valid");
    		}
    		
    	}else{
    		
    	}
    	}
    	
	private boolean isAdmin() {
		int sn = Integer.parseInt(username.getText());
		String pass = password.getText();
				
		if(sn == 213456789
				&& pass.equalsIgnoreCase("qazwsx")){
			
			return true;
			
		}else{
			return false;
		}

	}

	private void validateTempUser() {
		int userEnteredPin =0;
		userEnteredPin = pinAlert.getPinFromUser("PIN", "User Input", "Please Enter"
				+ "the Pin that was sent to your student email."
				+ "\n PLease be advised that this Pin expired 10 minutes"
				+ "after it was sent");
		if(userEnteredPin > 0 && Integer.toString(userEnteredPin).length() == 5){
			if(checkPin(userEnteredPin)){
				if(checkTempUserCredentials()){
					if(addUser()){
						if(deleteTempUser(user.getStudentNumber())){
//							actions.setCurrentStudent(user.getStudentNumber());
							Actions.currentStudent = user.getStudentNumber();
							newView = new ChangeView(
									"/student/account/MainPage.fxml",
									false,
									true,
									loginPane);
							newView.changeView();    						
						}
						else{
							//ERROR TRYING TO DELETE THE TEMP USER FROM THE TABLE 
							alert.displayError("Delete Error", "Error deliting user from temp",
									"There was an error deliting the user");
						}
						
					}else{
						alert.displayError(
								"Save Error",
								"Failded to save the user to the db",
								"Please sign up again");
						
					}
			}else{
				alert.displayError("Wrong Credentials",
						"Pin not valid or username or password incorrect",
						"Please enter your credentials again\n"
						+ "Make sure that your pin is still under the 5 minutes since it was sent"
						+ "\nOtherwhise just sign up again");
					}
				}
		else{
			//THERE WAS AN ERROR WITH THE VALUE THE USER ENTERED AS A PIN
			alert.displayError("Validation Error", "Error validating the pin",
					"There seems to have an error on the value you entered. "
							+ "\nPlease make sure that you entered the correct 5 digits "
							+ "sent to your email");
				}
			}
		
		}

	private boolean addUser() {
		User userdb = actions.getTempUserById(username.getText());
		if(actions.addUser(userdb)){
			return true;
		}else
		return false;
	}

	private boolean checkTempUserCredentials() {
		User tempUser=null;
		tempUser = actions.getTempUserById(username.getText());
		if(tempUser != null){
			int sn = Integer.parseInt(username.getText());
			String pass = password.getText();
			if(tempUser.getStudentNumber() == sn
					&& tempUser.getPassword().equals(pass)){
				tempUser =null;
				return true;
			
		}else{
			return false;
		}
	}else return false;
	}

	private boolean isTemp() {
		if(actions.getTempUser(Integer.parseInt(username.getText()))){
			return true;
		}else return false;
	}

	private boolean isUser() {
		if(actions.isUserOnDb(Integer.parseInt(username.getText()))){
			return true;
		}else return false;
	}

	private void validateRegUser() {
		User regUser = null;
		regUser = actions.getUser(Integer.toString(user.getStudentNumber()));
     	if(regUser != null){
     		int sn =  Integer.parseInt(username.getText());
    		//student is registrated to the system already
    		if(sn == regUser.getStudentNumber()
    				&& user.getPassword().equals(regUser.getPassword())){
    			System.out.println("Password matches !");
    			Actions.currentStudent = user.getStudentNumber();
    			newView = new ChangeView(
						"/student/account/MainPage.fxml",
						false,
						true,
						loginPane);
				newView.changeView();
    		}else{
    			username.clear();
    			password.clear();
    			user = null;
    			
    			wrongPassLabel.setVisible(true);
    		}
    	}else{
    		alert.displayError("User not found",
    				"User not found on our databse",
    				"The user has not been found."
    				+ "\nPlease enter your credentials again");
    	}
		
	}
	private boolean deleteTempUser(int sn) {
		//DELETE THE USER FROM TEMP DB TABLE
		if(actions.deleteTempUser(sn))
			return true;
		else return false;

	}

	private User validateUser() {
		
		if(username.getText() != null){
    		if(username.getText().length() == 9 && username.getText().startsWith("2")){
    			if(!username.getText().toString().matches("-?\\d+"))
    				errors.add("Your Student number can not contain letters!");
    			
    		}else{
    			errors.add("Please Enter a valid student number ");
    		}
		}else{
			errors.add("Student number must not be left empty");
		}
		
		if(errors.isEmpty()){
			
			user = new User(
					Integer.parseInt(username.getText()),
					password.getText()
					);
			return user;
			
		}
		else {
			errors.stream().forEach(error -> allErrors += error +"\n");
	  		alert.displayError("Input Error", "Bad username format", allErrors);
	  		allErrors="";
			errors.clear();
			user = null;
			return null;
		}
			
	}
	

	 
	 private boolean checkPin(int  userEnteredPin) {
			//VERIFY EMAIL, PASSWORD AND TEMP PIN IN THE TEMP USER TABLE
			if(actions.checkTempUserPin(user,userEnteredPin)){
				return true;
				
			}else return false;
		}


	@SuppressWarnings("unused")
	@FXML
    void onSignUp(ActionEvent event) {
		try{
			if(isConfig()){
				SendEmail send = new SendEmail();
				SignUpController.setSignUp(true);
				newView = new ChangeView(
						"/user/SignUp.fxml",
						false,
						false,
						loginPane);
				newView.changeView();
				
			}else{
				
				
			}
			
		}catch(Exception ex){
			
		}
    
    }
	
	private boolean isConfig() {
		config = actions.getConfig();
		if(config != null){
			return true;
		}else 
			{
			
			alert.displayError(
    				"Config Error", 
    				"Failed to load the image folder and the email address", 
    				"There was an error while trying"
    				+ "\nIf your are Admin please"
    				+ "\nplease config the application");
			
			newView = new ChangeView(
					"/admin/Admin.fxml",
					true,
					false,
					loginPane);
			newView.changeView();
			return false;
			}
	}
    

	
	/**
	 * Set al te parameters necessary for the app to start
	 * specifically the 
	 */
    private void setParams() {
    	//CREATE THE FOLDER TO STORE IMAGES
    	//THIS MUST BE DONE ON THE SERVER SIDE ONCE IT STARTS
    	
    	instance = DatabaseHandler.getInstance();
    	if(instance == null){
    		alert.displayError("Databse error",
    				"Failed to start the databse", 
    				"There is another instance of the "
    				+ "\ndatabase already running"
    				+ "\nPlease close all the SSM windows"
    				+ "and start again");
    	}


    		//pass the instance to the MainPageController	
    		Actions.setInstance(instance);
    		
    		System.out.println("got the instance");
    		//check if the email is configured and the password
    		//check if the image path is configured
    		config = actions.getConfig();
    		if(config != null){
    			
    		}else{
    			//alert the user that the email server has not been set yet and that 
    			//the program will have to finish becaus eof that
    			alert.displayError(
    					"Config Error", 
    					"Failed to load the image folder and the email address", 
    					"If you are the Admin please confugure the application"
    							+ "\nfirst by trying to login (or just click the login"
    							+ " \nbutton)"
    					);    	
    			config =null;
    		}
    	
    
    	
    	
    	try{

    		password.setStyle("-fx-text-inner-color: #F3F31F;");
    		username.setStyle("-fx-text-inner-color: #F3F31F;");  	
    		wrongPassLabel.setVisible(false);
    		
    	}catch(Exception ex){
    		
    	}    	  
    	
    	try{
    		Image logoImage = new Image("ssm.png");
    		logo.setImage(logoImage);
    		
    	}catch(Exception ex){
    		alert.displayError("Logo Image Error",
    				"Failed to load logo image",
    				"Please make sure that the image logo is "
    				+ "\nInside the src folder");
    	}
    	
    }
    
    
    
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setParams();
		
	}


}
