package student;

import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

public class MainPageController {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private ScrollPane adsPane;

    @FXML
    private FlowPane adsFllowPane;

    @FXML
    private Pane adContainer;

    @FXML
    private ImageView adImage;

    @FXML
    private TextArea adDescription;

    @FXML
    private Label priceLabel;

    @FXML
    private Pane paginationPane;

    @FXML
    private Label pageNumberLabel;

    @FXML
    private Pane topBarPane;

    @FXML
    private JFXTextField searchBar;

    @FXML
    private Pane cartPane;

    @FXML
    private TableView<?> cartTable;

    @FXML
    private TableColumn<?, ?> productCplumn;

    @FXML
    private TableColumn<?, ?> unitPriceColumn;

    @FXML
    private TableColumn<?, ?> AvailableColumn;

    @FXML
    private Pane filterPane;

    @FXML
    private MenuButton category;

    @FXML
    private TextField maxPrice;

    @FXML
    private TextField minPrice;

    @FXML
    private DatePicker listDate;

    @FXML
    void obDone(ActionEvent event) {

    }

    @FXML
    void onBackward(ActionEvent event) {

    }

    @FXML
    void onCancel(ActionEvent event) {

    }

    @FXML
    void onCart(ActionEvent event) {

    }

    @FXML
    void onClear(ActionEvent event) {

    }

    @FXML
    void onClose(ActionEvent event) {

    }

    @FXML
    void onDelete(ActionEvent event) {

    }

    @FXML
    void onFilter(ActionEvent event) {

    }

    @FXML
    void onFoward(ActionEvent event) {

    }

    @FXML
    void onLogOut(ActionEvent event) {

    }

    @FXML
    void onMessage(ActionEvent event) {

    }

    @FXML
    void onMyAds(ActionEvent event) {

    }

    @FXML
    void onProfile(ActionEvent event) {

    }

    @FXML
    void onSearch(ActionEvent event) {

    }

    @FXML
    void onSearchBarChange(ActionEvent event) {

    }

}
