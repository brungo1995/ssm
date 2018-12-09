package alert;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ErrorAlert {
	
    public void displayError(String title,String header, String description){
    	Alert alert =new Alert(AlertType.ERROR);
    	alert.setTitle(title);
    	alert.setHeaderText(header);
    	alert.setContentText(description);
    	alert.showAndWait();
    }

}
