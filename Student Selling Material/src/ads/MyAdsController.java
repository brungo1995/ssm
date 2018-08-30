package ads;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import student.MainPageController;

public class MyAdsController implements Initializable{

    @FXML private AnchorPane addPost;
    
    //add Post fields 
    @FXML private JFXTextField title;
    @FXML private JFXTextField description;
    @FXML private JFXTextField price;
    @FXML private JFXTextField productName;
    @FXML private JFXTextField unitPrice;
    @FXML private JFXTextField quantity;
    @FXML private JFXTextField phone;
    @FXML private JFXDatePicker postDate;
    @FXML private JFXDatePicker expireDate;
    @FXML private  JFXComboBox<String> category;
    @FXML private Label imageNameLabel;
    
    //PANES
    @FXML private Pane myAdsPane;
    @FXML private Pane previewPane;
    
    @FXML private TableView<?> myAdsTable;
    @FXML private TableColumn<?, ?> productCol;
    @FXML private TableColumn<?, ?> titleCol;
    @FXML private TableColumn<?, ?> descriptionCol;
    @FXML private TableColumn<?, ?> uniPriceCol;
    @FXML private TableColumn<?, ?> quantityCol;
    @FXML private TableColumn<?, ?> postDateCol;
    @FXML private TableColumn<?, ?> expireDateCol;
    
    //Preview Labels
    @FXML private JFXTextArea descriptionArea;
    @FXML private Label titleLabel;
    @FXML private Label productLabel;
    @FXML private Label quantityLabel;
    @FXML private Label UnitPriceLabel;
    @FXML private Label postDateLabel;
    @FXML private Label phoneLabel;
    @FXML private Label expireDateLabel;
    @FXML private ImageView productImage;
    
    //Category ObservableLists and Lists variables
    ObservableList<String> categoryList = FXCollections.observableArrayList();
    ArrayList<String> listOfCategoriesItems = new ArrayList<String>();
    
    //File to Upload
    FileChooser fileChooser = new FileChooser();
    List<String> extensionList;
    String extension = "";
    File imageFile;
    Image image;
    
    //Prevew and Post instances
    Post  validatePost;

    
  //navegation instances
    private String viewToGo;
    
    
    //Actions calls
    @SuppressWarnings("unchecked")
	public void getListOfCategories(){
    	Actions aObj = new Actions();
    	listOfCategoriesItems = aObj.getCategories();
    	listOfCategoriesItems.stream()
    	.forEach(c -> {
    		categoryList.add(c.toString());
    	});
    	 
    }
       
    @FXML
    void onAdd(ActionEvent event) {
    	addPost.toFront();

    }

    @FXML
    void onBackToEdit(ActionEvent event) {

    }
    
    @FXML
    void onBack(ActionEvent event) {
    	viewToGo="/student/MainPage.fxml";
    	changeView();
    }


    @FXML
    void onCancel(ActionEvent event) {
    	myAdsPane.toFront();
    }

    @FXML
    void onCreate(ActionEvent event) {
    	//get the full Post and send to the MAINPAGE
    	Actions.addMorePost(validatePost);
    	viewToGo = "/student/MainPage.fxml";
    	changeView();
    	

    }

    @FXML
    void onDelete(ActionEvent event) {

    }

    @FXML
    void onEdit(ActionEvent event) {
    	addPost.toFront();
    }

    @FXML
    void onHome(ActionEvent event) {

    }

    @FXML
    void onLogOut(ActionEvent event) {

    }

    @FXML
    void onPreview(ActionEvent event) {
    	@SuppressWarnings("unused")
		Post validPost = validateFields();
    	
    	//set all the label to the with values from the valid
    	productImage.setImage(image);
        descriptionArea.setText(validPost.getDescription());;
        titleLabel.setText(validPost.getTitle());
        productLabel.setText(validPost.getProductName());
        quantityLabel.setText(Integer.toString(validPost.getQuantity()));
        UnitPriceLabel.setText(Integer.toString(validPost.getUnitPrice()));
        postDateLabel.setText(validPost.getPostDate().toString());;
        phoneLabel.setText(validPost.getPhone());;
        expireDateLabel.setText(validPost.getExpireDate().toString());
        
    	previewPane.toFront();
    	
    	//get all values from fields and make an object of type Post
    }
    
    
    @FXML
    void onUploadImage(ActionEvent event) {
    	//upload the image with a file chooser
    	fileChooser.setTitle("Select the product Image");
    	openFile();
    	if(imageFile == null){
//    		errorBox("Bad File","You have not selected any File or the extension of the image is not "
//    				+ "allowed. Please choose the image again!");
//    		openFile();
    		
    	}else{
    		//continue coding
    	}
    	   	
    	
    }
    
    /**
     * 
     * @param file file is an empty File that is passed as an  
     */
    private void  openFile() {
		fileChooser = new FileChooser();
		List<String> extensionFilters =  new ArrayList<String>();
		String[] extensionsArray = {".tif",".tiff",".gif",".jpeg",".jpg",
									".jif",".jfif",".jp2",".jpx",".j2k",".j2c",
									".fpx",".pcd",".png"};
		extensionList = Arrays.asList(extensionsArray);		
		imageFile = fileChooser.showOpenDialog(null);
		//image is not null
		if(imageFile != null){
			String path = imageFile.getAbsolutePath();
			extension = path.substring(path.lastIndexOf("."));
			if(extensionList.contains(extension)){
//				create the Image
				try {
					String imagePath = imageFile.toURI().toURL().toString();
					image = new Image(imagePath);
					double imagePanelHeigth = productImage.getFitHeight();
					double imagePanelWidth = productImage.getFitWidth();
					
					Double imageDimensions = 
					image.getHeight();
					image.getWidth();
					
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				errorBox("Bad Extension","It seems that you have not selected a correct image file."
						+ " Plese Check again the extension of the image an try to upload again.");
				imageFile = null;
			}
		}
	}

	//VAALIDATION
 
    public  Post validateFields(){
    	//get all field values and validate them
    	if(imageFile == null){
    		openFile();   		
    	}else{
    		
    	}
    	validatePost = new Post(
    			title.getText(),
    			description.getText(),
    			productName.getText(),
    			Integer.parseInt(unitPrice.getText()),
    			Integer.parseInt(quantity.getText()),
    			phone.getText(),
    			postDate.getValue(),
    			expireDate.getValue(),
    			image
    			);
    	return validatePost;
    }
    
    //DISPLAY ERROORS
    private void errorBox(String title, String description){
    	Platform.runLater(() -> {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle(title);
    		alert.setContentText(description);
    		alert.show();
    	});
    }
    
    /**
     * Initializes all the values that are needed to be displayed
     */
    public void initializeValues(){
    	//initialize category comboBox
    	getListOfCategories();
    	category.setItems(categoryList);
    	
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
    	Stage closingStage = (Stage)myAdsPane.getScene().getWindow();
    	closingStage.close();
    	
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		myAdsPane.toFront();
		initializeValues();
		
	}

}
