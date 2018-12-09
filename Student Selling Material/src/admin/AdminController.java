package admin;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import alert.ErrorAlert;
import alert.SuccessAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import student.ads.Actions;

public class AdminController implements Initializable{
		@FXML private JFXTextField email;
	    @FXML private JFXTextField imagePath;
	    @FXML private JFXTextField retypPass;
	    @FXML private JFXTextField password;
	    @FXML private JFXButton cancel;
	    @FXML private JFXButton update;
	    
	    private Config config;
	    public Actions actions = new Actions();
	    public ArrayList<String> errors =  new ArrayList<String>();
	    public ErrorAlert alert = new ErrorAlert();
	    private String allErrors ="";
		private String folder =null;
		public SuccessAlert success = new SuccessAlert();
	    
	    
	    @FXML
	    void onCancel(ActionEvent event) {
	    	Stage stage = (Stage)cancel.getScene().getWindow();
	    	stage.close();

	    }

	    @FXML
	    void onUpdate(ActionEvent event) {
	    	validateConfig();
	    	if(config != null){
	    		if(actions.createConfig(config)){
	    			success.displayResult(
	    					"Success", 
	    					"Updated Application Settings", 
	    					"Settings were updated Successfully"
	    					+ "\nUsers Can log in now !");
	    			Stage stage = (Stage)email.getScene().getWindow();
	    			stage.close();
	    		}else{
	    			
	    		alert.displayError(
						   "Error",
						   "Failed to update the Settings",
						   "Please try again");
	    		}
	    	}
	    	else{
	    		System.out.println("config is null");
	    	}
	    }
	    
	    @FXML
	    void onCreateFolder(){
	    	creadFolder(System.getProperty("user.dir").toString());
	    	
	    }
	    
	    /**
	     * Validate the config fields
	     */
	    private void validateConfig() {
	    	
	    	if(email.getText() != null){
	    		if(email.getText().length() >= 70 || email.getText().contains("@gmail.com")){
	    			if(!email.getText().toString().matches("-?\\d+")){
	    				
	    			}else{
	    				
	    				errors.add("Email can not be only numbers");
	    			}
	    			
	    		}else{
	    			errors.add("Please Enter a valid email ");
	    		}
			}else{
				errors.add("Email must not be left empty");
			}
	    	
	    	
	    	
	    	if(password != null){
				if(password.getText().length() <= 29){
					if(!password.getText().isEmpty() || !retypPass.getText().isEmpty()){
						if(!password.getText().equals(retypPass.getText())){
							errors.add("Passwords do not match");				
						}
					}else{
						errors.add("Both password fields must be filled !");
					}
				}else{
					errors.add("The password length can not exceed 29 characters");
				}
			}else{
				errors.add("Both password fields must be filled !");
				
			}
	    	
	    	
	    	
	    	if(imagePath.getText() != null){	    		
	    		
	    	}else{
	    		errors.add("Please create an image folder");
	    	}
	    	
	    	if(folder  != null){
	    		
	    	}else{
	    		errors.add("Please create an image folder");
	    	}
	    	
	    	if(errors.isEmpty()){
				
				config = new Config(
						"x",
						email.getText(),
						password.getText(),
						folder
						);
				
			}
			else {
				errors.stream().forEach(error -> allErrors += error +"\n");
		  		alert.displayError("Input Error", "Error Reading config fields", allErrors);
		  		allErrors="";
				errors.clear();;
				config = null;
			}
			
			
			
		}

		public void creadFolder(String path){	    
	 // CREATE A FOLDER N THE ROOT TO STORE THE IMAGES
	 		String directory = path+"/images";
	 		File file =  new File(directory);
	 		
	 		 if (! file.exists()){
	 		        file.mkdir();
	 		        System.out.println("ceated image folders");
	 		    }
	 		 folder  =path+"/images";
	 		 imagePath.setText(folder);	
	 	}
	    
	    
		private void setFieldValues() {
			email.setText(config.getEmail());
			password.setText(config.getPassword());
			retypPass.setText(config.getPassword());
			imagePath.setText(folder);			
		}
		
	    private void setparams() {
	    	email.setText("studentsellingmaterial@gmail.com");
	    	password.setText("NewPassword2018");
			retypPass.setText("NewPassword2018");
			
	    	config = actions.getConfig();
	    	if(config != null){
	    		setFieldValues();
	    	}
	    	
	    }


		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			setparams();
			
		}



}
