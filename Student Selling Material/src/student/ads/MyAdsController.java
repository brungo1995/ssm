package student.ads;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import alert.ConfirmationAlert;
import alert.ErrorAlert;
import alert.SuccessAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import navegation.ChangeView;
import student.account.Basket;
import student.account.Message;

public class MyAdsController implements Initializable{

    @FXML private AnchorPane addPost;
    
    //add Post fields 
    @FXML private JFXTextField title;
    @FXML private JFXTextArea description;
    @FXML private JFXTextField productName;
    @FXML private JFXTextField unitPrice;
    @FXML private JFXTextField quantity;
    @FXML private JFXTextField phone;
    @FXML private JFXDatePicker postDate;
    @FXML private JFXDatePicker expireDate;
    @FXML private  JFXComboBox<String> category;
    @FXML private Label imageNameLabel;
    @FXML private JFXButton btnCreatEdit;
    @FXML private JFXButton btnUpdate;
    @FXML private JFXButton btnUpdate1;
    public static int postId;
    @FXML  private JFXButton deleteMyAdBtn;
    
    //PANES
    @FXML private Pane myAdsPane;
    @FXML private Pane previewPane;
    
    @FXML private TableView<PostProperty> myAdsTable;
    @FXML private TableColumn<PostProperty, String> productCol;
    @FXML private TableColumn<PostProperty, String> titleCol;
    @FXML private TableColumn<PostProperty, String> descriptionCol;
    @FXML private TableColumn<PostProperty, Integer> uniPriceCol;
    @FXML private TableColumn<PostProperty, Integer> quantityCol;
    @FXML private TableColumn<PostProperty, LocalDate> postDateCol;
    @FXML private TableColumn<PostProperty, LocalDate> expireDateCol;
    
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
    
    //buttons
    @FXML private Button deleteBtn;
    @FXML private  Button editBtn;
    @FXML private Button createBtn;
    
    
    //ENQUIRY 
    @FXML private Pane enquiryPanel;
    @FXML private JFXTextArea enqMessageTextArea;
    @FXML private JFXButton clear;
    @FXML private TextField toField;
    @FXML private TextField subjectField;
    @FXML private DatePicker datField;
    @FXML private JFXButton send;
    @FXML private TextField productField;
    
    //BASKET
    public Actions basketValue = new Actions();
    public Basket currentBasket;
    
    //MY POSTS
    private ArrayList<Post> myPosts = new ArrayList<Post>();
    public ObservableList<PostProperty> postsToTable = FXCollections.observableArrayList();

//    ACTIONS
    public Actions actions = new Actions();  
    
    //Category ObservableLists and Lists variables
    public ObservableList<String> categoryList = FXCollections.observableArrayList();
    public ArrayList<Category> catList = new ArrayList<Category>();
    
    //File to Upload
    public FileChooser fileChooser = new FileChooser();
    public List<String> extensionList;
    public String extension = "";
    public File imageFile;
    public Image image;
    public String oldImagePath;
    
    //Prevew and Post instances
    public Post  validatePost = null;
    public static boolean isCreating = true;
    public static boolean isUpdating = false;
    public static boolean isPreview = false;
    public static boolean isEnquiry = false; 
    public static String state ="isCreating";
    public static boolean isBasket = true;
    
    //MESSAGE INSTANCES
    private Message enquiryMessage;

	//navegation instances
    public ChangeView newView;   
    public static String paneToFront ="myAdsPane";
    public static Post clicketPost;
    public SuccessAlert success = new SuccessAlert();
    
//    POST
    public Post validPost=null;
    
//    EERORS
    public ErrorAlert error = new ErrorAlert();
    public ArrayList<String> errors =  new ArrayList<String>();
    private String allErrors="";
    public ConfirmationAlert confirmation = new ConfirmationAlert();
    
    
   
    @FXML
    void onAdd(ActionEvent event) {
    	state = "isCreating";
    	changeState();
    	clearAllFields();
    	addPost.toFront();
}
    
    public void clearAllFields(){
    	descriptionArea.clear();
    	titleLabel.setText(null);
	    productLabel.setText(null);
	    quantityLabel.setText(null);
	    UnitPriceLabel.setText(null);
	    postDateLabel.setText(null);
	    phoneLabel.setText(null);
	    expireDateLabel.setText(null);
	    productImage.setImage(null);
	    image = null;
	    
	    title.clear();
	    description.clear();
	    productName.clear();
	    category.setValue(null);
	    unitPrice.clear();
	    quantity.clear();
	    phone.clear();
	    postDate.setValue(null);
	    expireDate.setValue(null);
	    imageNameLabel.setText(null);
    }

    @FXML
    void onBackToEdit(ActionEvent event) {

    }
    
    @FXML
    void onBack(ActionEvent event) {
    	newView = new ChangeView(
    			"/student/account/MainPage.fxml",
    			false,
    			true,
    			myAdsPane);
    	newView.changeView();

    }
    
    @FXML
    void onUpdate(ActionEvent event){
    	if(validPost != null){

    		if(actions.updatePost(validPost, oldImagePath) == true){
    			clearAllFields();
    			oldImagePath=null;
    			validPost = null;
    			clicketPost =null;
    			loadTable();
    			success.displayResult("Success",
    					"Post Updated ",
    					"Your Ad was updated successfully");
    			myAdsPane.toFront();
    			
    		}
    	}else{
    		error.displayError("Update error", "Could not update",
    				"There was an error while trying to update your post\n"
    				+ "Preview first before updating \nPlease try again");
    	}
    }
    
    @FXML
    void onMyAdasTableCliecked(MouseEvent  event){ 	
    	if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
    		PostProperty item = myAdsTable.getSelectionModel().getSelectedItem();
    		if(item != null){
    			postId = item.getPostId();
    			clicketPost = actions.getPostById(postId);
    			if(clicketPost != null){
    				postId = clicketPost.getPostId();
    				setEditFiledValues(clicketPost);
    				validatePost = clicketPost;
    				oldImagePath = clicketPost.getImageFile().getParent();
    				state ="isUpdating";
    				changeState();
    				addPost.toFront();    				
    			}else{
    				error.displayError("Post Not Found", "Error to fecth the post",
    						"There was an error while trying to acces this post. It migth have "
    						+ "happaned because the Post was delete or it is not available anymore"
    						+ "\nPlease close the windown and open again");
//    				POST NOT FOUND DISPLAY ERROR
    				
    			}
    		}
    		
    	}
    }


    private void setEditFiledValues(Post post) {
        title.setText(post.getTitle());;
        description.setText(post.getDescription());
        productName.setText(post.getProductName());
        unitPrice.setText(Integer.toString(post.getUnitPrice()));;
        quantity.setText(Integer.toString(post.getQuantity()));;
        phone.setText(post.getPhone());
        postDate.setValue(post.getPostDate());
        expireDate.setValue(post.getExpireDate());
        category.setValue(post.getCategoryCode());
        productImage.setImage(post.getProductImage());
        image = post.getProductImage();
        imageFile = post.getImageFile();

		
	}

	@FXML
    void onCancel(ActionEvent event) {
    	myAdsPane.toFront();   	
    }
    
	@FXML
	void onCancelEnqAndCreate(ActionEvent event){
		if(isEnquiry == true){
			Stage closingStage = (Stage)myAdsPane.getScene().getWindow();
			closingStage.close();
		}else{
			validPost = null;
			addPost.toFront();
		}
	}
	
    @FXML
    void onCancelEnquiry(ActionEvent event) {
    	previewPane.toFront();
    }
    
    @FXML
    void onClear(ActionEvent event) {
    	//clear all the message fields
    	enqMessageTextArea.setText(null);
        subjectField.setText(null);
    }
    
    @FXML
    void onDelete(ActionEvent event) {
    	//check if user is creating or enquiring
    	if(clicketPost == null){ 
    		Stage closingStage = (Stage)myAdsPane.getScene().getWindow();
			closingStage.close();
    	}else{
    		if(isUpdating){
    			if(confirmation.displayConfirmation(
    	    			"Deleting...", 
    	    			"Are you sure you want to delite ?", 
    	    			"Please note that ad will be lost"
    	    			)){
    				if(actions.deletePost(postId)){
    				}
    				loadTable();
    				myAdsPane.toFront();

    	    	
    	    	}else{
    	    		
    	    	}
    		}else{
    			Stage closingStage = (Stage)myAdsPane.getScene().getWindow();
    			closingStage.close();
    		}
	
    	}
    			
   
    }



    @FXML
    void onCreate(ActionEvent event) {
//    	check if it is creating or adding to the basket
    	if(isCreating == true){
    		if(validPost != null){
    			Actions.addMorePost(validPost);
    			validPost=null;
    			loadTable();
    			success.displayResult(
    					"Creation Success", 
    					"Your add was successfully created", 
    					"You can view it on the Main Page");
    			myAdsPane.toFront();
    		}else{
    			error.displayError("Creating post error",
    					"Create post error", 
    					"There was an error trying to create the post\n"
    					+ "Please enter all the field values again\n"
    					+ "Make sure that you Preview first before creating the post");
    		}

    	}
    	if(isBasket == true){
    		int cartId;
    		Post currentPost = actions.getPostById(clicketPost.getPostId());
    		boolean isPresent =false;
    		if(currentPost != null){
    			cartId = actions.generateCartId();
    			if(cartId >= 0){

    				currentBasket = new Basket(
    						cartId,
    						currentPost.getProductName(),
        					currentPost.getUnitPrice(),
        					currentPost.getStudentNUmber(),
        					Actions.currentStudent,
        					clicketPost.getPostId()
        					);
    				ArrayList<Basket> list = new ArrayList<Basket>();
    				list = actions.getBaskets();
    				if(list !=null){
    					isPresent = list.stream()
    							.anyMatch(item ->
    							item.getPostId() == clicketPost.getPostId()
    							&& 
    							item.getBayer() == Actions.currentStudent
    							);
    					if(isPresent == true){
    						error.displayError("Adding item to cart error ",
    								"Could not add the post to your Cart",
    								"This Post is already on your Cart List\n"
    								+ "Please choose another item ");
    						currentBasket =null;  				
							Stage closingStage = (Stage)myAdsPane.getScene().getWindow();
							closingStage.close();
    					}else{
    						if (actions.adBasket(currentBasket, cartId)){
    							success.displayResult("Basket updated", "New Item",
    									"A new item was added to your Cart\n"
    											+ "You can check the item by clicking on the basket button from"
    											+ "the Main Page");
    							
    							
    							basketValue.setMyBasket(list.size());
    							currentBasket =null;
    							
    							Stage closingStage = (Stage)myAdsPane.getScene().getWindow();
    							closingStage.close();
    							
    						}
    						
    					}
    				}else{
    					if (actions.adBasket(currentBasket, cartId)){
							success.displayResult("Basket updated", "New Item",
									"A new item was added to your Cart\n"
											+ "You can check the item by clicking on the basket button from"
											+ "the Main Page");
							
							
							basketValue.setMyBasket(basketValue.getMyBasket() +1);
							Actions.setCurrentBasket(currentBasket);
							currentBasket =null;  				
							Stage closingStage = (Stage)myAdsPane.getScene().getWindow();
							closingStage.close();
							
						}
    				}
 
    				
    				
    			}
    		}  			


    	}
    	

    }
    
    @FXML
    void onSend(ActionEvent event) {
    	//Validae messagge first
    	enquiryMessage = validateMessage();
    	if(enquiryMessage != null){
    		
    		System.out.println(enquiryMessage.getMessageId());
    		clearEnqFields();
    		if(actions.sendMessage(enquiryMessage)){
    			success.displayResult("Success",
    					"Yor message was sent Successfully",
    					"The recepient has received your enquiry!");
    			enquiryMessage=null;   		
    			Stage closingStage = (Stage)myAdsPane.getScene().getWindow();
    			closingStage.close();
    		};

    		
    	}else{
    	}
    	//create Meggase object and send to Action
    }

   
    @FXML
    void onEdit(ActionEvent event) {
    	if(isCreating || isUpdating == true){
    		addPost.toFront();
    		validPost = null;
    	}
    	if(isEnquiry == true ){
    		if(clicketPost != null){
    			setEnqLabelValues(clicketPost);    			
    		}
    		enquiryPanel.toFront();
    	}
    }

    private void setEnqLabelValues(Post post) {
    	//SET FIELD VALUES TO FRONT
    	LocalDate date =  LocalDate.now();
    	productField.setText(post.getProductName());
    	toField.setText(Integer.toString(post.getStudentNUmber())
    			+"@mycput.ac.za");   	
    	datField.setValue(date);
    	
		
	}

	@FXML
    void onHome(ActionEvent event) {
		
		newView = new ChangeView(
    			"/student/account/MainPage.fxml",
    			false,
    			true,
    			myAdsPane);
    	newView.changeView();

    }

    @FXML
    void onLogOut(ActionEvent event) {
    	
    	if(confirmation.displayConfirmation(
    			"Loging Out...", 
    			"Are you sure you want to log out ?", 
    			"Please note that any unsaved work"
    			+ "\nwill be lost !")){

    		newView = new ChangeView(
    				"/user/login.fxml",
    				false,
    				false,
    				myAdsPane);
    		newView.changeView();
    	}else{
    		
    	}

    }

    @FXML
    void onPreview(ActionEvent event) {
    		validPost = validateFields();
    		if(validPost != null){
    			setFieldValues(validPost);
    			previewPane.toFront();    			
    		}
    }
    
    void setFieldValues(Post post){
    	imageFile =post.getImageFile();

    	productImage.setImage(post.getProductImage());
    	descriptionArea.setEditable(false);
    	descriptionArea.setText(post.getDescription());
    	titleLabel.setText(post.getTitle());
    	productLabel.setText(post.getProductName());
    	quantityLabel.setText(Integer.toString(post.getQuantity()));
    	UnitPriceLabel.setText(Integer.toString(post.getUnitPrice()));
    	postDateLabel.setText(post.getPostDate().toString());;
    	phoneLabel.setText(post.getPhone());;
    	expireDateLabel.setText(post.getExpireDate().toString());
    	
    }
    
    
    @FXML
    void onUploadImage(ActionEvent event) {
    	//upload the image with a file chooser
    	fileChooser.setTitle("Select the product Image");
    	openFile();
    	validPost = null;
 }
    
    /**
     * 
     * @param file file is an empty File that is passed as an  
     */
    private void  openFile() {
		fileChooser = new FileChooser();
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
				} catch (MalformedURLException e) {

					error.displayError(
							"Load Image Error", 
							"Could not load the image", 
							"Please try again");
				}
			}else{
				error.displayError("File Error","Bad Image Extension","It seems that you have not selected a correct image file."
						+ " Plese Check again the extension of the image an try to upload again.");
				imageFile = null;
			}
		}
	}

	//VAALIDATION
 
    public  Post validateFields(){
    	//TITLE
    	if(!title.getText().isEmpty()){
    		
    		if(title.getText().length() <= 100){
    		
    			if(!title.getText().matches("-?\\d+")){
    		
    		}
    		else{
    				errors.add("Post title must have letters on it");
    			
    		}
    	}
    		else{
    			errors.add("Post title can not have more than 100 characters");		
    			}
//    		
    	}
    	else{
    		errors.add("Post title can not be left blank");
    	}
    	
    	//DESCRIPTION
    	if(!description.getText().isEmpty()){
    		if(description.getText().length() >= 500){
    			errors.add("Descriptioon can not have more than 500 characters");
    		}else{
    			if(description.getText().matches("-?\\d+")){
    				errors.add("Post title must contain words");
    			}			
    		}
    	}else{
    		errors.add("Please describe your add");
    	}
    	
    	
    	//PRODUCT NAME
    	if(!productName.getText().isEmpty()){
    		if(productName.getText().length() >= 100){
    			errors.add("Product name can not have more than 100 characters");
    		}else{
    			if(productName.getText().matches("-?\\d+")){
    				errors.add("Product name must contain words");
    			}			
    		}
    	}else{
    		errors.add("Please provide Product name");
    	}
    	
    	//CATEGORY
    	if(category.getValue() == null){
    		errors.add("Category can not be empty");
    	}else{
    		
    		if(!category.getValue().isEmpty()){
    			
    		}else{
    			errors.add("Please select acategory");
    		}
    	}
    	
    	//UNIT PRICE
    	if(!unitPrice.getText().isEmpty()){
    		if(!unitPrice.getText().toString().matches("-?\\d+")){
    			errors.add("Unite price must be only numbers");
    			
    		}else{
    			try{
    				if(Integer.parseInt(unitPrice.getText()) >= 2000 || Integer.parseInt(unitPrice.getText()) <= 0){
    					errors.add("Unit  price must be between (R1-R2000");
    				}
    				
    			}catch(Exception ex){

    				errors.add("Esure that the price must be between (R1-R2000)");
    			}

    		}
    		
    	}else{
    		errors.add("Unit price must be filled");
    	}
    	
    	//QUANTITY
    	if(!quantity.getText().isEmpty()){
    		if(!quantity.getText().toString().matches("-?\\d+")){
    			errors.add("Quantity must be only numbers");
    		}else{
    			try{
    				if(Integer.parseInt(quantity.getText()) >= 5 ||Integer.parseInt(quantity.getText()) <=0){
    					errors.add("Your quantity must be between (1-5");
    				}
    				
    			}catch(Exception ex){
    				errors.add("Your quantity must be between (1-5)");
    			}
    			
    		}
    		
    	}else{
    		errors.add("Quantity must be filled");
    	}
    	
    	
    	//PHONE
    	if(!phone.getText().isEmpty()){
    		if(phone.getText().length() > 10){
    			errors.add("Phone can not have more than 10 characters");
    		}else{
    			if(!phone.getText().matches("-?\\d+")){
    				errors.add("Phone must only be numbers Ex: 0817126930");
    			}else{
    				if(!phone.getText().startsWith("0")){
    					errors.add("Please provide a valid cellphone number");
    				}
    			}
    		}
    	}else{
    		errors.add("Please provide Phone number");
    	}
    	
    	//POST DATE
		if(postDate.getValue() != null){
		  			
		  }else{
		  		errors.add("Please enter post date");
		  	}
		//EXPIRE DATE
		if(expireDate.getValue() != null){
  			
		  }else{
		  		errors.add("Please enter Expire date");
		  	}
		
		if(image == null || imageFile == null){
			errors.add("Please select an image from your computer");
		}
    	   	
		if(errors.isEmpty()){
			if(isCreating == true){
				LocalDate posDate = LocalDate.now();
				LocalDate expDate = posDate.plusDays(7);
				expireDate.setEditable(false);
				postDate.setValue(posDate);
				expireDate.setValue(expDate);
				postId = actions.generatePostId();
				
			}
			
			if(isUpdating == true){
				postDate.setValue(clicketPost.getPostDate());
				expireDate.setValue(clicketPost.getExpireDate());
				expireDate.setEditable(false);
				postDate.setEditable(false);
				postId = clicketPost.getPostId();
			}

			validatePost = new Post(
					title.getText(),
					description.getText(),
					productName.getText(),
					category.getValue().toString().trim(),
					Integer.parseInt(unitPrice.getText()),
					Integer.parseInt(quantity.getText()),
					phone.getText(),
					postDate.getValue(),
					expireDate.getValue(),
					image,
					Actions.currentStudent,
					imageFile,
					postId
					);
			return validatePost;
			
		}else{
			//DISPLAY ALL THE ERROR ON THE ALERT
	  		errors.stream().forEach(error -> allErrors += error +"\n");
	  		error.displayError("Input Error", "Error Reading Search Parameters", allErrors);
	  		allErrors="";
				errors.clear();
			return null;
		}	
    }
    
    
    public Message validateMessage(){
    	if(toField != null
    			&& enqMessageTextArea != null
    			&& subjectField != null
    			&& datField != null
    			&& productField != null){
    	}else{
    		errors.add("One of the message fields is empty\n"
    				+ "Please check again");    		
    	}
    	
    	//SUBJECT
    		if(subjectField.getText() != null){
        		if(subjectField.getText().length() >= 100){
        			errors.add("Yor suject  cannot have more than 100 characters");
        		}else{
        			if(subjectField.getText().matches("-?\\d+")){
        				errors.add("Suject must contain words");
        			}			
        		}
        	}else{
        		errors.add("Please provide Subject field");
        	}
    		
    		
    		if(enqMessageTextArea.getText() != null){
        		if(enqMessageTextArea.getText().length() >= 500){
        			errors.add("Your  message cannot have more than 500 characters");
        		}else{
        			if(enqMessageTextArea.getText().matches("-?\\d+")){
        				errors.add("Message must contain words");
        			}			
        		}
        	}else{
        		errors.add("Please provide Message ");
        	}

    		if(errors.isEmpty()){
    			int mId = actions.generateMessageId();
    			LocalDate date = LocalDate.now();
    			return new Message(
    					Actions.currentStudent,
    					clicketPost.getStudentNUmber(),
    					enqMessageTextArea.getText(),
    					subjectField.getText(),
    					date,
    					productField.getText(),
    					mId
    					);
    			
    			
    		}else{
    			//DISPLAY ALL THE ERROR ON THE ALERT
    	  		errors.stream().forEach(error -> allErrors += error +"\n");
    	  		error.displayError("Message Error", "Error Validating Message Parameters", allErrors);
    	  		allErrors="";
    			errors.clear();
    			return null;
    		}
    		
    }
    

    
   private void clearEnqFields() {
	    enqMessageTextArea.clear();
	    toField.clear();
	    subjectField.clear();
	    datField.setValue(null);
	    productField.clear();
		
	}
   
   public void loadTable(){
	   postsToTable.clear();
	   myPosts = actions.getMyPosts(Actions.currentStudent);  
	   if(myPosts !=null){
		   myPosts.stream()
		   .forEach(post -> postsToTable.add(convertToPostProperty(post)));    	
		   myAdsTable.setItems(postsToTable);   		
	   }
	   
   }

 /**
     * Initializes all the values that are needed to be displayed
     */
    public void initializeValues(){
    	getListOfCategories();
    	loadTable();
    }
    
    
  /**
   * Get all the categories from the the getCategories method in 
   * the Actions class.
   */
    public void getListOfCategories(){
    	catList = actions.getCategories();
    	catList.stream()
    	.forEach(c -> {
    		categoryList.add(c.getName());
    	});    	
		category.setItems(categoryList);
    	 
    }
      
   
    
    private PostProperty convertToPostProperty(Post post) {
		return new PostProperty(
				post.getTitle(),
				post.getDescription(),
				post.getProductName(),
				post.getUnitPrice(),
				post.getQuantity(),
				post.getPhone(),
				post.getPostDate(),
				post.getExpireDate(),
				post.getProductImage(),
				post.getStudentNUmber(),
				post.getPostId()
				);
	}

	public static String getPaneToFront() {
  		return paneToFront;
  	}

  	public static void setPaneToFront(String paneToFront) {
  		MyAdsController.paneToFront = paneToFront;
  		
  	}
  	
  	public static Post getClicketPost() {
		return clicketPost;
	}

	public static void setClicketPost(Post clicketPost) {
			MyAdsController.clicketPost = clicketPost;
	}
	
	public static boolean getIsCreating() {
		return isCreating;
	}
	
	public static boolean getIsUpdating() {
		return isUpdating;
	}
	public static void setIsUpdating(boolean isUpdating) {
		MyAdsController.isUpdating = isUpdating;
	}

	public static void setIsCreating(boolean isCreating) {
		MyAdsController.isCreating = isCreating;
	}



    
    private void setParams() {
    	changeState();
    	
    	//ENQUIRY VARIABLES
    	toField.setEditable(false);
    	datField.setEditable(false);
    	productField.setEditable(false);
    	enqMessageTextArea.setText(null);
        subjectField.setText(null);
        
//    	GET ALL MYPOSTS    	 	
    	btnUpdate.setVisible(false);
		btnUpdate1.setVisible(false);

        productCol.setCellValueFactory(new PropertyValueFactory<PostProperty,String>("productName"));
        titleCol.setCellValueFactory(new PropertyValueFactory<PostProperty,String>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<PostProperty,String>("description"));
        uniPriceCol.setCellValueFactory(new PropertyValueFactory<PostProperty,Integer>("unitPrice"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<PostProperty,Integer>("quantity"));
        postDateCol.setCellValueFactory(new PropertyValueFactory<PostProperty,LocalDate>("postDate"));
        expireDateCol.setCellValueFactory(new PropertyValueFactory<PostProperty,LocalDate>("expireDate"));
    	
    	//Which pane to front
        myAdsPane.toFront();
    	if(paneToFront.equalsIgnoreCase("myAdsPane")){
    		myAdsPane.toFront();
    		
    	}
    		
    	if(paneToFront.equalsIgnoreCase("previewPane")){
    		previewPane.toFront();
    		
    	}
    	
    	//what field values to display
    	if(clicketPost != null){
    		setFieldValues(clicketPost);
    	}

    
    	
    	if(isCreating){
			LocalDate posDate = LocalDate.now();
			LocalDate expDate = posDate.plusDays(7);
			expireDate.setEditable(false);
			postDate.setValue(posDate);
			expireDate.setValue(expDate);
		}else{
			expireDate.setEditable(false);
			postDate.setEditable(false);
		}
    	
    }
    
  
    public  void changeState() {
		switch(state){
		case "isCreating" :
			createBtn.setVisible(true);
	    	btnCreatEdit.setVisible(true);
	    	btnUpdate1.setVisible(false);
	    	btnUpdate.setVisible(false);
	    	deleteMyAdBtn.setVisible(false);
	    	editBtn.setText("Edit");
	    	createBtn.setText("Create");
	    	oldImagePath = null;
	    	
	    	isCreating = true;
			isUpdating = false;
			isPreview = false;
			isEnquiry = false;
			isBasket = false;
			break;
			
		case "isUpdating" :
			editBtn.setText("Edit");
			createBtn.setVisible(false);
	    	btnCreatEdit.setVisible(false);
	    	btnUpdate1.setVisible(true);
	    	btnUpdate.setVisible(true);
	    	deleteMyAdBtn.setVisible(true);
	    	
			isUpdating = true;
			isCreating= false;
			isPreview= false;
			isEnquiry =false;
			isBasket = false;
			break;
			
		case "isPreview" :
			createBtn.setText("+ Basket");
			
			
			isPreview = true;
			isCreating = false;
			isUpdating = false;
			isEnquiry =false;
			isBasket = false;
			break;
			
		case "isEnquiry" :
			editBtn.setText("Enquiry");
			createBtn.setText("+ Basket");
			oldImagePath = null;
			isEnquiry =true;
			isPreview = false;
			isCreating = false;
			isUpdating = false;
			isBasket = true;
			break;
		}

	}
    
    
    

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setParams();
		initializeValues();	
		
	}

	

}
