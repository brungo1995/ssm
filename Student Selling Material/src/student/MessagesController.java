package student;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class MessagesController {

    @FXML
    private AnchorPane messagesPane;

    @FXML
    private TableView<?> messageTable;

    @FXML
    private TableColumn<?, ?> fromCol;

    @FXML
    private TableColumn<?, ?> subjectCol;

    @FXML
    private TableColumn<?, ?> dateCol;

    @FXML
    private Pane messageDetailsPane;

    @FXML
    private JFXTextArea messageContentTextArea;

    @FXML
    private JFXButton replyButton;

    @FXML
    private TextField fromField;

    @FXML
    private TextField subjectField;

    @FXML
    private DatePicker datField;

    @FXML
    void onBack(ActionEvent event) {

    }

    @FXML
    void onCancel(ActionEvent event) {

    }

    @FXML
    void onReply(ActionEvent event) {

    }

}
