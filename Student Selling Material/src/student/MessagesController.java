package student;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MessagesController {

    @FXML private AnchorPane messagesPane;
    @FXML private TableView<?> messageTable;
    @FXML private TableColumn<?, ?> fromCol;
    @FXML private TableColumn<?, ?> subjectCol;
    @FXML private TableColumn<?, ?> dateCol;
    @FXML private Pane messageDetailsPane;
    @FXML private JFXTextArea messageContentTextArea;
    @FXML private JFXButton replyButton;
    @FXML private TextField fromField;
    @FXML private TextField subjectField;
    @FXML private DatePicker datField;

    
    //navegation instances
    private String viewToGo;
      
    
    @FXML
    void onBack(ActionEvent event) {
    	viewToGo="/student/MainPage.fxml";
    	changeView();

    }

    @FXML
    void onCancel(ActionEvent event) {

    }

    @FXML
    void onReply(ActionEvent event) {

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
    	Stage closingStage = (Stage)messagesPane.getScene().getWindow();
    	closingStage.close();
    	
    }

}
