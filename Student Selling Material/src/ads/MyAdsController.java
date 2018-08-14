package ads;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class MyAdsController {

    @FXML
    private AnchorPane addPost;

    @FXML
    private JFXTextField title;

    @FXML
    private JFXTextField description;

    @FXML
    private JFXTextField price;

    @FXML
    private JFXTextField productName;

    @FXML
    private JFXTextField unitPrice;

    @FXML
    private JFXTextField quantity;

    @FXML
    private JFXTextField phone;

    @FXML
    private MenuButton category;

    @FXML
    private JFXDatePicker postDate;

    @FXML
    private JFXDatePicker expireDate;

    @FXML
    private Pane myAdsPane;

    @FXML
    private TableView<?> myAdsTable;

    @FXML
    private TableColumn<?, ?> productCol;

    @FXML
    private TableColumn<?, ?> titleCol;

    @FXML
    private TableColumn<?, ?> descriptionCol;

    @FXML
    private TableColumn<?, ?> uniPriceCol;

    @FXML
    private TableColumn<?, ?> quantityCol;

    @FXML
    private TableColumn<?, ?> postDateCol;

    @FXML
    private TableColumn<?, ?> expireDateCol;

    @FXML
    private Pane previewPane;

    @FXML
    private Label previewTitleLabel;

    @FXML
    private JFXTextArea descriptionArea;

    @FXML
    private Label descptionLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private Label productLabel;

    @FXML
    private Label quantityLabel;

    @FXML
    private Label UnitPriceLabel;

    @FXML
    private Label postDateLabel;

    @FXML
    void onAdd(ActionEvent event) {

    }

    @FXML
    void onBackToEdit(ActionEvent event) {

    }

    @FXML
    void onCancel(ActionEvent event) {

    }

    @FXML
    void onCreate(ActionEvent event) {

    }

    @FXML
    void onDelete(ActionEvent event) {

    }

    @FXML
    void onEdit(ActionEvent event) {

    }

    @FXML
    void onHome(ActionEvent event) {

    }

    @FXML
    void onLogOut(ActionEvent event) {

    }

    @FXML
    void onPreview(ActionEvent event) {

    }

}
