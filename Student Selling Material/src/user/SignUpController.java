package user;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class SignUpController {

    @FXML
    private Pane signUpPane;

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
    private JFXTextField email;

    @FXML
    private Pane resetPassword;

    @FXML
    private JFXTextField studentNumberRen;

    @FXML
    private JFXPasswordField passReno;

    @FXML
    private JFXTextField emailRen;

    @FXML
    private JFXPasswordField reEnterPassRen;

    @FXML
    void onCancel(ActionEvent event) {

    }

    @FXML
    void onCancelReset(ActionEvent event) {

    }

    @FXML
    void onLogin(ActionEvent event) {

    }

    @FXML
    void onReset(ActionEvent event) {

    }

}
