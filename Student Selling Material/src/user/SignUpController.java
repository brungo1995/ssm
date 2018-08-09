package user;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class SignUpController {

    @FXML
    private JFXTextField studentNumber;

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXPasswordField retypePassword;

    @FXML
    private JFXButton cancel;

    @FXML
    private JFXButton login;

    @FXML
    void onCancel(ActionEvent event) {

    }

    @FXML
    void onLogin(ActionEvent event) {

    }

}
