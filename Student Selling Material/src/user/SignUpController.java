package user;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import alert.ErrorAlert;
import alert.SuccessAlert;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import navegation.ChangeView;
import student.ads.Actions;

public class SignUpController implements Initializable{

    @FXML private Pane signUpPane;
    @FXML private Pane resetPassword;
    
    @FXML private JFXTextField studentNumber;
    @FXML private JFXTextField username;
    @FXML private JFXPasswordField password;
    @FXML private JFXTextField email;
    @FXML private JFXPasswordField retypePassword;
    

    @FXML private JFXTextField studentNumberRen;
    @FXML private JFXTextArea resetPassInfo;
    
    @FXML private JFXButton create;
    @FXML private JFXButton cancel;
    
    //Navegation
    ChangeView newView;
    public static boolean isSignUp = true;
    public SuccessAlert success = new SuccessAlert();
    
//  USER
    User user;
    
//    ACTIONS
    Actions actions = new Actions();
    
//    ERRORS
    ErrorAlert alert = new ErrorAlert();
    ArrayList<String> errors =  new ArrayList<String>();
    private String allErrors="";

    
	@FXML
    void onCancel(ActionEvent event) {
    	newView = new ChangeView(
    			"/user/login.fxml",
    			false,
    			false,
    			signUpPane);
    	newView.changeView();
    }
    

    @FXML
    void onCancelReset(ActionEvent event) {
    	newView = new ChangeView(
    			"/user/login.fxml",
    			false,
    			false,
    			signUpPane);
    	newView.changeView();
    }

    @FXML
    void onCreate(ActionEvent event) {
    	user = validateUser();
    	if(user != null){
    		//CREATE A TEMPORY DATABSE TABLE AND STORE STUDENT NUMBER AND PASSWORD
     		//GENERATE A 5 DIGIT PIN 
    		generatePin();

    		newView = new ChangeView(
        			"/user/login.fxml",
        			false,
        			false,
        			signUpPane);
        	newView.changeView();
		
    		}else{
		
    	}
    }
    

    private void generatePin() {
    	try{
    		int pin;
        	pin = actions.generatePin();
        		 //CHECK IF THE USER IS NOT IN THE DATABSSE ALREADY SO THAT THE SAME USER DOES NOT SEND
            	   //MULTIPLE PIN REQUESTS, ONLY SEND ONE AT THE TIME
        		   if(actions.getTempUserAndCheckPin(user.getStudentNumber(), pin) == false){
        			   //PIN DOES NOT EXIST   AND USER IS NOT IN THE TEMP USER DB
        			    //ASSIGN TO THE NEW USER TRYING TO REGISTER ON THE TEMP DB
        			   if(actions.addTempUser(user, pin)){
        				   System.out.println(" Generated pin => " + pin );
        				   System.out.println(pin);
        				   actions.sendPin(user.getEmail(),
        						   "Pin request",
        						   "Dear user \n\n"
        								   + "Your request to open an account has being received.\n"
        								   + "There is only one more step. Here is your pin number,"
        								   + "\n enter it on your first login attemp : "+pin
        								   + "\n\nPlease note that this pin will expire in 5 minutes"
        								   + "\n\n"
        								   + "Student Selling Material Support team");
        				   
        				   success.displayResult(
        						   "Success",
        						   "Received registration request",
        						   "Your request has been received\n"
        								   + "Please login again with the pin\n"
        								   + "That was sent to your email");
        			   }
        			   else {
        				   //USER IS ALREADY IN THE TEMP USER DB FOR A MAXI 10 MIN
        				   //ALERT THE USER TO EITHER WAIT OR CHECH IS EMAIL ADDRESS
        				   alert.displayError("Login Error", "User not allowed to request pin twice",
        						   "It seems that you have requested a Pin just now, and you "
        								   + "are trying to request a new pin without the 5 minutes exired"
        								   + "\nPlease check your email again and use the same Pin that was sent to you"
        								   + "\n Or wait 10 minutes and sign up again !");
        			   }
       
        		   }else{
    				   alert.displayError(
    						   "Registration error",
    						   "Unable to register your profile",
    						   "Please make sure that your are still under the 5 minutes "
    						   + "time allowed to registrar.\n"
    						   + "Otherwhise just sign up again");
        		   }
        
    		
    	}catch(Exception ex){
    		
    	}
    	
		
	}


    //VALIDATE ALL THE FIELDS
	private User validateUser() {  	
    	User updatedUser;
    	//STUDENT NUMBER
    	if(!studentNumber.getText().isEmpty()){
    		if(studentNumber.getText().length() == 9 && studentNumber.getText().startsWith("2")){
    			if(!studentNumber.getText().toString().matches("-?\\d+"))
    				errors.add("Your Student number can not contain letters!");
    			
    		}else{
    			errors.add("Please Enter a valid student number ");
    		}
		}else{
			errors.add("Student number must not be left empty");
		}
    	
    	//USERNAME
		if(!username.getText().isEmpty()){
			if(username.getText().length() < 20 && username.getText().length() > 3 ){
				if(username.getText().toString().matches("-?\\d+")){
					errors.add("Your username can not be only numbers!");
					
					}else{
						
					}
				
			}else{
				errors.add("Your username can not be longer than 19 characters"
						+ "\nAnd can not be less than 3 characters");
			}
		}else{
			errors.add("Username must not be left empty");
		}
		
		//EMAIL
		
		
		//PASSWORD
		if(password != null){
			
			
			if(password.getText().length() < 30 && password.getText().length() >= 6){
				
				
				if(!password.getText().isEmpty() || !retypePassword.getText().isEmpty()){
					if(!password.getText().equals(retypePassword.getText())){
						errors.add("Passwords do not match");				
					}else{
						
					}
				}else{
					errors.add("Both password fields must be filled !");
				}
			}
			
			
			else{
				errors.add("The Length of your password cannot be bigger than 30 characters"
						+ "\nAnd less than 6 charcaters");
			}
			
			
		}else{
			errors.add("Both password fields must be filled !");
			
		}
		
		
		 
		if(errors.isEmpty()){
			
			updatedUser = new User(
					Integer.parseInt(studentNumber.getText()),
					username.getText(),
					password.getText(),
					email.getText()
					);
			return updatedUser;
		}
		else {
			errors.stream().forEach(error -> allErrors += error +"\n");
	  		alert.displayError("Input Error", "Error Reading Profile fields", allErrors);
	  		allErrors="";
			errors.clear();;
			return null;
		}
    	
	}


	@FXML
    void onReset(ActionEvent event) {
		//STUDENT NUMBER
    	if(studentNumberRen.getText() != null){
    		if(studentNumberRen.getText().length() == 9 && studentNumberRen.getText().startsWith("2")){
    			
    			if(!studentNumberRen.getText().toString().matches("-?\\d+")){
    				alert.displayError("Bad Format","Incorrect student number format",
    						"Your Student number can not contain letters!");    				
    			}else{
    				//GET FIRST THE STUDENT FROM THE USERS DB
    				//IF USER IS NOT FOUND THEN HE IS NOT REGISTERED TO THE SYSTEM
    				//AFTER STUDENT IS CHECKED TO BE IN THE SYSTEM, GET PASSWORD AND SEND EMAIL
    				User user;
    				user = actions.getUser(studentNumberRen.getText());
    				if(user !=null){
    					String pass = actions.getPassword(Integer.parseInt(studentNumberRen.getText()));
    					if(pass != null){
    						System.out.println("dc kjsd");
    						actions.sendEmail(studentNumberRen.getText()+"@mycput.ac.za",
    								"Reset password",
    								"Dear Student"
    										+ "\nA request to change your email has bein received"
    										+ "\nYour password is : "+pass+"\n"
    										+  "After login again you can update your"+
    								"\npassword under Profile");
    						success.displayResult(
    								"Success", 
    								"Arequest to change your email has bein sent", 
    								"Please open your email and login again");
    						newView = new ChangeView(
    								"/user/login.fxml",
    								false,
    								false,
    								signUpPane);
    						newView.changeView();
    						
    					}else{
    						alert.displayError("Student record error",
    								"User not found",
    								"You are not registered in the system"
    								);
    						studentNumberRen.setText(null);
    					}
    					
    				}else{
    					alert.displayError("User not foud", 
    							"This user seems not to be registered",
    	    					"Please enter your student number again"
    	    					+ "\nEnsure that it is your credentials");
    				}
    				
    			}
//    			
    		}else{
    			alert.displayError("Incorrect Student Number Format", "Please enter your student number again",
    					"Follow the Universiy policy for student numbers"
    					+ "\nCheck the length (9 digits)!");
    		}
		}else{
			alert.displayError("Incorrect Student Number", "Student number must not be left empty",
					"Please Enter A valid Student Number");
		}
    	

    }
    
    public static void  setSignUp(boolean isSignUp) {
    	SignUpController.isSignUp = isSignUp;
	}
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private void setParams() {
    	if(isSignUp){
    		signUpPane.toFront();
    		
    	}
    	
    	else{
    		resetPassword.toFront();
    		
    	}
    	try{
    		studentNumber.setStyle("-fx-text-inner-color: #F3F31F;");
    		username.setStyle("-fx-text-inner-color: #F3F31F;");
    		password.setStyle("-fx-text-inner-color: #F3F31F;");
    		email.setStyle("-fx-text-inner-color: #F3F31F;");
    		retypePassword.setStyle("-fx-text-inner-color: #F3F31F;");
    		
    		studentNumberRen.setStyle("-fx-text-inner-color: #F3F31F;");
    		
    	}catch(Exception ex){
    		
    	}
        
        //BIND EMAIL AND STUDENT NUMBER
        try{
        studentNumber.textProperty().addListener(new ChangeListener(){
	        @Override 
	        public void changed(ObservableValue o,Object oldVal, 
	                 Object newVal){
	        	if(!studentNumber.getText().isEmpty()){
	        		email.setText(newVal.toString()+"@mycput.ac.za");	        		
	        	}
	             
	        }
	      });
        }catch(Exception ex){
        	
        }
        
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setParams();			
	}



}
