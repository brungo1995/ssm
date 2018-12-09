package student.account;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class MessageProperty {
	private SimpleStringProperty enqMessageTextArea;
    private SimpleIntegerProperty toField;
    private SimpleStringProperty subjectField;
    private SimpleStringProperty datField;
    private SimpleStringProperty productField;
    private SimpleIntegerProperty sendor;
    private SimpleIntegerProperty messageId;
    private SimpleStringProperty sendorName;
    
	public MessageProperty(
			String enqMessageTextArea,
			int toField,
			String subjectField,
			String datField,
			String productField,
			int sendor,
			int messageId,
			String sendorName) {

		this.sendorName = new SimpleStringProperty(sendorName);
		this.enqMessageTextArea = new SimpleStringProperty( enqMessageTextArea);
		this.toField =new SimpleIntegerProperty( toField);
		this.subjectField =new SimpleStringProperty( subjectField);
		this.datField =new SimpleStringProperty( datField);
		this.productField =new SimpleStringProperty( productField);
		this.sendor =new SimpleIntegerProperty( sendor);
		this.messageId =new SimpleIntegerProperty( messageId);
	}



	public String getSendorName() {
		return sendorName.get();
	}

	public String getEnqMessageTextArea() {
		return enqMessageTextArea.get();
	}


	public int getToField() {
		return toField.get();
	}


	public String getSubjectField() {
		return subjectField.get();
	}


	public String getDatField() {
		return datField.get();
	}


	public String getProductField() {
		return productField.get();
	}


	public int getSendor() {
		return sendor.get();
	}
	
	
	public int getMessageId() {
		return messageId.get();
	}
	
	
	
    
    

}
