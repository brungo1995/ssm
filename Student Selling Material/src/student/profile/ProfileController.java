package student.profile;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import alert.ErrorAlert;
import alert.SuccessAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import navegation.ChangeView;
import student.ads.Actions;
import user.User;

public class ProfileController implements Initializable{

    @FXML  private Pane resetPassword;
    @FXML  private Pane signUpPane;
    
    @FXML  private JFXTextField studentNumber;
    @FXML  private JFXTextField username;
    @FXML  private JFXPasswordField password;
    @FXML  private JFXPasswordField retypePassword;
    @FXML  private JFXTextField email;
   
    @FXML  private JFXButton cancel;
    @FXML  private JFXButton save;
    
//  NAVEGATE
    ChangeView newView;
    
    
//  USER
    User user=null;
    
//    ACTIONS
    Actions actions = new Actions();
    
//    ERRORS
    ArrayList<String> errors =  new ArrayList<String>();
    ErrorAlert alert = new ErrorAlert();
    SuccessAlert success = new SuccessAlert();
    public String allErrors=  "";
    

    @FXML
    void onCancel(ActionEvent event) {
    	Stage closeStage = (Stage)signUpPane.getScene().getWindow();
    	closeStage.close();
    }

 
    @FXML
    void onSave(ActionEvent event) {
    	user = validateUser();
    	if(user != null){
    		if(actions.updateUser(user)){
    			success.displayResult(
    					"Success",
    					"Profile updated",
    					"Your profile has been successfully updated");
    		}else{
    			alert.displayError(
    					"Error updating",
    					"Could not update your profile", 
    					"There was an error while trying to update your profile"
    					+ "\n Please try again");
    		}
    		Stage closeStage = (Stage)signUpPane.getScene().getWindow();
    		closeStage.close();
    	}else{
    		
    	}
    }


	private User validateUser() {
		//VALIDATE ALL THE FIELDS BEFORE YOU CREATE  AN UPDATE USER TO SEND IN THE DATBASE
		User updatedUser;
		if(!username.getText().isEmpty()){
			if(username.getText().toString().matches("-?\\d+"))
				errors.add("Your username can not be only numbers!");
		}else{
			errors.add("Username must not be left empty");
		}
		
		
		if(!password.getText().isEmpty() || !retypePassword.getText().isEmpty()){
			if(!password.getText().equals(retypePassword.getText())){
				errors.add("Passwords do not match");				
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
	  		alert.displayError("Input Error", "Error Reading Profile fieldsd", allErrors);
	  		allErrors="";
			errors.clear();;
			return null;
		}
	}

	private void setParams() {
		user = actions.getUser(Integer.toString(Actions.currentStudent));		
		if(user== null){
			alert.displayError("User not found",
					"Failed to fecth the user",
					"There was an error trying to fecth the user from the database");
		}else{
			setFieldValues();			
		}
	}
	
	

	private void setFieldValues() {

		studentNumber.setStyle("-fx-text-inner-color: #F3F31F;");
		username.setStyle("-fx-text-inner-color: #F3F31F;");
		password.setStyle("-fx-text-inner-color: #F3F31F;");
		email.setStyle("-fx-text-inner-color: #F3F31F;");
		retypePassword.setStyle("-fx-text-inner-color: #F3F31F;");
		
		
		//SET ALL THE VALUES FROM THE USER OBJECT TO THE FIELDS
		studentNumber.setText(Integer.toString(user.getStudentNumber()));
	    username.setText(user.getUsername());
	    password.setText(user.getPassword());
	    email.setText(user.getStudentNumber()+"@mycput.ac.za");
	    email.setEditable(false);
	    studentNumber.setEditable(false);
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setParams();
		
		
	}





}
