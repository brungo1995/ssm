package alert;

import java.util.Optional;

import javafx.scene.control.TextInputDialog;

public class Pin {
	ErrorAlert alert =new  ErrorAlert();
	
	public int getPinFromUser(String title, String headers, String descritpion){
		int pin=0;
		//CREATE AN ALERT BOX AND GET THE NUMBER FROM THE USER
		TextInputDialog dialog = new TextInputDialog("Ex: 12345");
		dialog.setTitle(title);
		dialog.setHeaderText(headers);
		dialog.setContentText(descritpion);
		
		try{
			Optional<String> result = dialog.showAndWait();
			if(result.isPresent()){
				//convert the interger to number
				pin = Integer.parseInt(result.get());
			}			
			
			return pin;
		}catch(Exception ex){
			//alert the user there was an error inputing the pin
//			alert.displayError("Validation Error", "Error validating the pin",
//					"There seems to have an error on the value you entered. "
//					+ "\nPlease make sure that you entered the correct 5 digits "
//					+ "sent to your email");
			return 0;
		}
	}

}
