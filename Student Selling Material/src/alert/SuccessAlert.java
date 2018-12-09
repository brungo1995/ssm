package alert;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class SuccessAlert {
	
	public void displayResult(String title, String header, String description){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setContentText(description);
		alert.setHeaderText(header);
		alert.showAndWait();
	}

}
