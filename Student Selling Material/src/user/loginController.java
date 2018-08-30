package user;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class loginController {

    @FXML private ImageView logo;
    @FXML private Label appNameLabel;
    @FXML private JFXTextField username;
    @FXML private JFXPasswordField password;
    @FXML private JFXButton cancel;
    @FXML private JFXButton login;
    @FXML private Label wrongPassLabel;
    @FXML private JFXButton signUp;
    
    //Class instances
    private String viewToGo;

    @FXML
    void onCancel(ActionEvent event) {
    	

    }

    @FXML
    void onForgotPassword(ActionEvent event) {

    }

    /**
     * 
     * @param event on successful login the main page is displayed to the user
     */
    @FXML
    void onLogin(ActionEvent event) {
    	viewToGo = "/student/MainPage.fxml";
    	changeView();

    }

    @FXML
    void onSignUp(ActionEvent event) {
    	String calledBy="/user/login.fxml";
    	SignUpController.setCalledBy(calledBy);
    	viewToGo="/user/SignUp.fxml";
    	changeView();
//    	viewToGo = "/user/SignUp.fxml";
//    	changeView();

    }
    
    public void changeView(){
    	try {
    		Parent root= FXMLLoader.load(getClass().getResource(viewToGo));
    		Scene scene =  new Scene(root);
    		Stage stage = new Stage();
    		stage.setScene(scene);
    		stage.show();
    	} catch (IOException e) {
    		
    		e.printStackTrace();
    	}
    	
    	//no error close the previous window and clear the viewToGo
    	Stage closingStage = (Stage)login.getScene().getWindow();
    	closingStage.close();
    	viewToGo="";
    	
    }

}
