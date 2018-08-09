package user;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class loginController {

    @FXML
    private ImageView logo;

    @FXML
    private Label appNameLabel;

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXButton cancel;

    @FXML
    private JFXButton login;

    @FXML
    private Label wrongPassLabel;

    @FXML
    private JFXButton signUp;

    @FXML
    void onCancel(ActionEvent event) {

    }

    @FXML
    void onForgotPassword(ActionEvent event) {

    }

    @FXML
    void onLogin(ActionEvent event) {

    }

    @FXML
    void onSignUp(ActionEvent event) {

    }

}
