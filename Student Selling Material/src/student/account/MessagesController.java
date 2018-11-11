package student.account;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import alert.ConfirmationAlert;
import alert.ErrorAlert;
import alert.SuccessAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import navegation.ChangeView;
import student.ads.Actions;
import user.User;

public class MessagesController implements Initializable{

    @FXML private AnchorPane messagesPane;
    @FXML private Pane messageDetailsPane;
        
    @FXML private TableView<MessageProperty> messageTable;
    @FXML private TableColumn<MessageProperty, String> fromCol;
    @FXML private TableColumn<MessageProperty, String> subjectCol;
    @FXML private TableColumn<MessageProperty, String> dateCol;
    
    @FXML private JFXTextArea messageContentTextArea;
    @FXML private TextField fromField;
    @FXML private TextField subjectField;
    @FXML private DatePicker datField;

    @FXML private JFXButton replyButton;
    @FXML private Label fromLabel;
    @FXML private JFXButton sendButton;
    
    //Navigation instances
    ChangeView newView;
    
    //MESSAGES
    public ArrayList<Message> myMessages = new ArrayList<Message>();
    private Message message;
    ObservableList<MessageProperty> messageToTheTable = FXCollections.observableArrayList();
    MessageProperty item;
    
    //ACTIONS
    Actions actions  = new Actions();
    
//  ERRORS AND ALERTS
    ArrayList<String> errors =  new ArrayList<String>();
    ErrorAlert alert = new ErrorAlert();
    String allErrors= "";
    SuccessAlert success = new SuccessAlert();
    ConfirmationAlert confALert = new ConfirmationAlert();
    
    
      
    
    @FXML
    void onBack(ActionEvent event) {
    	Stage currentStage =(Stage) messageDetailsPane.getScene().getWindow();
    	currentStage.close();

    }

    @FXML
    void onCancel(ActionEvent event) {
    	clearFields();
    	sendButton.setVisible(false);
    	replyButton.setVisible(false);
    	
    }
    

    @FXML
    void tableClicked(MouseEvent  event){
	    	if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
	    		item = messageTable.getSelectionModel().getSelectedItem();
	    		LocalDate date;
	    		if(item.getDatField() !=null ){
	    			 date = LocalDate.parse(item.getDatField());
	    			
	    		}else{
	    			 date = LocalDate.now();
	    		}
	    		
	    		if(item != null){
	    			replyButton.setVisible(true);
	    			message = new Message(
	    					item.getSendor(),
	    					item.getToField(),
	    					item.getEnqMessageTextArea(),
	    					item.getSubjectField(),
	    					date,
	    					item.getProductField(),
	    					item.getMessageId()
	    					);
	    			setMessageFieldValues();
	    	    	
	    		}	    			
	    }
    }
    
    
    private void setMessageFieldValues() {
		messageContentTextArea.setText(item.getEnqMessageTextArea());
    	fromField.setText(Integer.toString(item.getSendor()));
    	subjectField.setText(item.getSubjectField());
    	datField.setValue(message.getDatField());
    	
    	messageContentTextArea.setEditable(false);
    	fromField.setEditable(false);
    	subjectField.setEditable(false);
    	datField.setEditable(false);
		
	}

	private void clearFields() {
    	messageContentTextArea.setText(null);
    	fromField.setText(null);
    	subjectField.setText(null);
    	datField.setValue(null);
    	message = null;
    	item = null;
		
	}

	@FXML
    void onReply(ActionEvent event) {
		if(message != null){			
	    	sendButton.setVisible(true);
			messageContentTextArea.setEditable(true);
			messageContentTextArea.setText("");
			fromLabel.setText("To");
			subjectField.setEditable(false);
			datField.setEditable(true);			
		}else{
			alert.displayError("", "", "Please select a message first before replying to it"
					+ "\nDouble Click on a row to reply the message");
		}
	}
	
	
	@FXML
	void onSend(ActionEvent event){
		if(item != null){
			message = createMessage();
			if(message != null){
				
				if(actions.sendMessage(message)){
					replyButton.setVisible(false);
					sendButton.setVisible(false);
					messageContentTextArea.setEditable(false);
					message=null;
					success.displayResult("Success",
	    					"Yor message was sent Successfully",
	    					"The recepient has received your enquiry!");
					clearFields();					
				}else{
		    		alert.displayError("Message not sent...",
    				"Error reading message field values",
    				"Please check all the fielld values again");
				};
				
				
			}
			
		}else{
			alert.displayError("Message", "No message selected", "Please selcted a message before send"
					+ "\n After selecting Click reply to write your answer to the sender"
					+ "\nThen Click Send ");
		}
    
	}
    
    
    private Message createMessage() {
		//VALIDATE ALL FIELDS
    	if(!messageContentTextArea.getText().isEmpty()){
    		
    	}else{
    		errors.add("Message body can not be empty");
    	}
    	
    	if(!subjectField.getText().isEmpty()){
    		if(subjectField.getText().length() <=50){
    			if(subjectField.getText().toString().matches("-?\\d+"))
    				errors.add("Subject cannot contain only numbers!");
    			
    		}else{
    			errors.add("Subject cannot be more than 50 characters");
    		}
		}else{
			errors.add("Subject field must not be left empty");
		}
    	
    	if(errors.isEmpty()){
    		LocalDate now =  LocalDate.now();
    		int mId = actions.generateMessageId();
    		message = new Message(
    				Actions.currentStudent,
    				item.getSendor(),
    				messageContentTextArea.getText(),
    		    	item.getSubjectField(),
    		    	now,
    		    	item.getProductField(),
    		    	mId
    				);
			return message;
		}
		else {
			errors.stream().forEach(error -> allErrors += error +"\n");
	  		alert.displayError("Message Error", "Bad message format", allErrors);
	  		allErrors="";
			errors.clear();
			message = null;
			return null;
		}
    		
	}

	private void setParams() {
		fromCol.setCellValueFactory(new PropertyValueFactory<MessageProperty, String>("sendorName"));
		subjectCol.setCellValueFactory(new PropertyValueFactory<MessageProperty, String>("subjectField"));
		dateCol.setCellValueFactory(new PropertyValueFactory<MessageProperty, String>("datField"));


    	myMessages =actions.getMyMessages(Actions.currentStudent);
    	
    	if(myMessages != null){
    		if(myMessages.size() > 0){
    			myMessages.stream()
    			.forEach(m -> messageToTheTable.add(convertMessage(m)));
    			messageTable.setItems(messageToTheTable);
    			
    		}		
    	}
    	
    	 datField.setEditable(false);
    	 replyButton.setVisible(false);
    	 sendButton.setVisible(false);
    	 subjectField.setEditable(false);
    }


	private MessageProperty convertMessage(Message m) {
		User sender = actions.getUser(Integer.toString(m.getSendor()));
		String sendorName = sender.getUsername();
		return new MessageProperty(
				m.getEnqMessageTextArea(),
				m.getToField(),
				m.getSubjectField(),
				m.getDatField().toString(),
				m.getProductField(),
				m.getSendor(),
				m.getMessageId(),
				sendorName
				);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {		
		setParams();

		
	}



}
