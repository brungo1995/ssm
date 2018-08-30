package user;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SignUpController implements Initializable{

    @FXML private Pane signUpPane;
    @FXML private JFXTextField studentNumber;
    @FXML private JFXTextField username;
    @FXML private JFXPasswordField password;
    @FXML private JFXPasswordField retypePassword;
    @FXML private JFXButton cancel;
    @FXML private JFXButton create;
    @FXML private JFXTextField email;
    @FXML private Pane resetPassword;
    @FXML private JFXTextField studentNumberRen;
    @FXML private JFXPasswordField passReno;
    @FXML private JFXTextField emailRen;
    @FXML private JFXPasswordField reEnterPassRen;
    
    //navegation instances
    private String viewToGo;
    private static String calledBy;
    
    
    


	private Pane frontPane;
    
    //constructors
    public SignUpController(){
    	
    }
    
//    public SignUpController(String frontPane, String calledBy){
//    	this.calledBy = calledBy;
//    }

    
    @FXML
    void onCancel(ActionEvent event) {
    	viewToGo=calledBy;
    	changeView();
    }

    @FXML
    void onCancelReset(ActionEvent event) {

    }

    @FXML
    void onCreate(ActionEvent event) {

    }

    @FXML
    void onReset(ActionEvent event) {

    }
    
    public static String getCalledBy() {
    	return calledBy;
    }
    
    public static void setCalledBy(String calledBy) {
    	SignUpController.calledBy = calledBy;
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
    	Stage closingStage = (Stage)create.getScene().getWindow();
    	closingStage.close();
    	viewToGo="";
    	
    }


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		
		
	}

}
