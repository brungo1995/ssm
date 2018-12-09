package student.account;

import java.time.LocalDate;

public class Message {
	 private String enqMessageTextArea;
     private int toField;
     private String subjectField;
     private LocalDate datField;
     private String productField;
     private int sendor;
     private int messageId;

     
     
	public Message(
			 int sendor,
			 int toField,
			 String enqMessageTextArea, 
			 String subjectField, 
			 LocalDate datField,
			 String productField,
			 int messageId

			 ) {
		
		this.enqMessageTextArea = enqMessageTextArea;
		this.toField = toField;
		this.subjectField = subjectField;
		this.datField = datField;
		this.productField = productField;
		this.sendor = sendor;
		this.messageId = messageId;

	}
	

	
	public int getSendor() {
		return sendor;
	}
	public int getMessageId() {
		return messageId;
	}
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
	public void setSendor(int sendor) {
		this.sendor = sendor;
	}
	public String getEnqMessageTextArea() {
		return enqMessageTextArea;
	}
	public void setEnqMessageTextArea(String enqMessageTextArea) {
		this.enqMessageTextArea = enqMessageTextArea;
	}
	public int getToField() {
		return toField;
	}
	public void setToField(int toField) {
		this.toField = toField;
	}
	public String getSubjectField() {
		return subjectField;
	}
	public void setSubjectField(String subjectField) {
		this.subjectField = subjectField;
	}
	public LocalDate getDatField() {
		return datField;
	}
	public void setDatField(LocalDate datField) {
		this.datField = datField;
	}
	public String getProductField() {
		return productField;
	}
	public void setProductField(String productField) {
		this.productField = productField;
	}
     
}
