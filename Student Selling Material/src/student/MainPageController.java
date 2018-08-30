package student;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import ads.Actions;
import ads.Post;
import ads.PostPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import user.SignUpController;

public class MainPageController implements Initializable{

    @FXML private AnchorPane mainPane;
    @FXML private ScrollPane adsPane;
    @FXML private Pane paginationPane;
    @FXML private Label pageNumberLabel;
    @FXML private Pane topBarPane;
    @FXML private JFXTextField searchBar;
    @FXML private Pane cartPane;
    @FXML private TableView<?> cartTable;
    @FXML private TableColumn<?, ?> productCplumn;
    @FXML private TableColumn<?, ?> unitPriceColumn;
    @FXML private TableColumn<?, ?> AvailableColumn;
    @FXML private Pane filterPane;
    @FXML private MenuButton category;
    @FXML private TextField maxPrice;
    @FXML private TextField minPrice;
    @FXML private DatePicker listDate;
    
    
//    @FXML private FlowPane adsFllowPane;
//    @FXML private Pane adContainer;
//    @FXML private ImageView adImage;
//    @FXML private Label priceLabel;
//    @FXML private TextArea adDescription;
    @FXML  private GridPane adGridPane;
    @FXML  private ImageView adImage;
    @FXML  private TextArea adDescription;
    @FXML  private Label adPriceLabel;

    
    
//    private ImageView image;
//	private Label price;
//	private TextArea description;
    
    //class instances
    private String viewToGo;
    
    //Post instances and Lists
    public static List<Post> postList=new ArrayList<Post>();
    public List<PostPane> postsPanels = new ArrayList<PostPane>();

    @FXML
    void onDone(ActionEvent event) {

    }

    @FXML
    void onBackward(ActionEvent event) {

    }

    @FXML
    void onCancel(ActionEvent event) {
    	mainPane.toFront();

    }

    @FXML
    void onCart(ActionEvent event) {
    	cartPane.toFront();
    	
//    	try {
//    		Parent root= FXMLLoader.load(getClass().getResource(viewToGo));
//    		Scene scene =  new Scene(root);
//    		Stage stage = new Stage();
//    		stage.initModality(Modality.WINDOW_MODAL);// prevent first window to be accessed.
//    		stage.setScene(scene);
//    		stage.show();
//    	} catch (IOException e) {
//    		
//    		e.printStackTrace();
//    	}

    }

    @FXML
    void onClear(ActionEvent event) {

    }

    @FXML
    void onClose(ActionEvent event) {
    	mainPane.toFront();

    }

    @FXML
    void onDelete(ActionEvent event) {

    }

    @FXML
    void onFilter(ActionEvent event) {
    	filterPane.toFront();
    }

    @FXML
    void onFoward(ActionEvent event) {

    }

    @FXML
    void onLogOut(ActionEvent event) {
    	viewToGo="/user/login.fxml";
    	changeView();

    }

    @FXML
    void onMessage(ActionEvent event) {
    	viewToGo="/student/Messages.fxml";
    	changeView();
    }

    @FXML
    void onMyAds(ActionEvent event) {
    	viewToGo="/ads/MyAds.fxml";
    	changeView();
    }

    @FXML
    void onProfile(ActionEvent event) {
    	String calledBy="/student/MainPage.fxml";
    	SignUpController.setCalledBy(calledBy);
    	viewToGo="/user/SignUp.fxml";
    	changeView();

    }

    @FXML
    void onSearch(ActionEvent event) {

    }

    @FXML
    void onSearchBarChange(ActionEvent event) {

    }
    
    //add a new Post to the Post List
    public static void loadPosts(){
    	postList = Actions.getPosts();

    }
    
//    @FXML  private GridPane adGridPane;
//    @FXML  private ImageView adImage;
//    @FXML  private TextArea adDescription;
//    @FXML  private Label adPriceLabel;
    private void displayPosts() {
    	Image image;
    	String description;
    	String price;
    	if(postList.size() > 0){
    		for(int x = 0; x< postList.size(); x++){
    			image = postList.get(x).getProductImage();
    			description = postList.get(x).getDescription();
    			price = Integer.toString(postList.get(x).getUnitPrice());
    			adImage.setImage(image);
    			adDescription.setText(description);
    			adPriceLabel.setText(price);
//    			adGridPane.add(adImage, 0, 0);
//    			adGridPane.add(adDescription, 1, 0);
//    			adGridPane.add(adPriceLabel, 1, 0);
    		}
    	}
    		
    }
    
    public Pane createPane(){
    	Pane pane= new Pane();
//    	pane.setPrefHeight(adContainer.getHeight());
//    	pane.setPrefWidth(adContainer.getWidth());
//       	pane.setStyle("-fx-background-color: #3498DB");
    	return pane;
    	
    	
    }
    
    public void changeView(){
    	try {
    		Parent root= FXMLLoader.load(getClass().getResource(viewToGo));
    		Scene scene =  new Scene(root);
    		Stage stage = new Stage();
    		stage.setScene(scene);
    		stage.show();
    	} catch (IOException e) {
    		
    		e.printStackTrace();
    	}
    	
    	//no error close the previous window and clear the viewToGo
    	Stage closingStage = (Stage)mainPane.getScene().getWindow();
    	closingStage.close();
    	
    }
    
    
    private void setParams() {
    	adGridPane.setVgap(20);
    	
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		mainPane.toFront();
		setParams();
		loadPosts();
		displayPosts();
		
	}




}
