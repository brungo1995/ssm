package alert;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class ConfirmationAlert {
	
	
	
	public boolean displayConfirmation(String title, String header,String description){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(description);
		
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK)
			return true;
		else  return false;
	}

}
