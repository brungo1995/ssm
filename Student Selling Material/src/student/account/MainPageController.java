package student.account;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXTextField;

import alert.ConfirmationAlert;
import alert.ErrorAlert;
import alert.SuccessAlert;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import navegation.ChangeView;
import student.ads.Actions;
import student.ads.MyAdsController;
import student.ads.Post;
import student.ads.PostPane;
import user.User;

public class MainPageController implements Initializable{

    @FXML private AnchorPane mainPane;
    @FXML private Pane paginationPane;
    @FXML private Label pageNumberLabel;
    @FXML private Pane topBarPane;
    @FXML private Pane cartPane;
    @FXML private Label basketLabel;
    @FXML public ScrollPane adsScrollPane;
    @FXML private JFXTextField searchBar;
    @FXML private Pane aboutPane;
    
//    CART TABLE
    @FXML private TableView<Cart> cartTable;
    @FXML private TableColumn<Cart, String> productColumn;
    @FXML private TableColumn<Cart, Integer> unitPriceColumn;
    @FXML private TableColumn<Cart, Boolean> AvailableColumn;
    
//  NAVEGATE
    public ChangeView newView;
    public MyAdsController adsCont = new MyAdsController ();
    public Actions actions = new Actions();
    public ErrorAlert error = new ErrorAlert();
    public ConfirmationAlert confirmation = new ConfirmationAlert();
    public SuccessAlert success =new SuccessAlert();
    
    
//	STUDENT NUMBER
    public int studentNumber;
    

//    USER
    User user;

    //BASKET
    Actions actionsObj = new Actions();
    private ArrayList<Basket> myCartItems = new ArrayList<Basket>();
    public ObservableList<Cart> cartList = FXCollections.observableArrayList();
    public Basket basket;
    Post clicketPost = null;
    
    //Post instances and Lists
    public static List<Post> postList;
    public List<PostPane> postsPanels = new ArrayList<PostPane>();

    //DATABSE

    @FXML
    void onCart(ActionEvent event) {
      	loadCartTable();
    	cartPane.toFront();

    }
    
    @FXML
    void onHelp(ActionEvent event){
    	aboutPane.toFront();
    }
    
    @FXML
    void onBack(ActionEvent event){
    	adsScrollPane.toFront();
    	topBarPane.toFront();
    }
    
    
    

    public void loadCartTable() {
    	//get the list of basket items and display on the table
    	cartList.clear();
    	myCartItems = actions.getBaskets();
    	if(myCartItems != null){
    		actions.setMyBasket(myCartItems.size());
    		myCartItems.stream()
    		.map(item -> convertFromBasket(item))
    		.forEach(item -> {
    			if(item != null){  				
    				cartList.add(item);    				    				
    			}
    		});    		
       		cartTable.setItems(cartList);   		
    	}
	}
    
    private Cart convertFromBasket(Basket basket){
    	Post p = actions.getPostById(basket.getPostId());
    	String  isAvailable = "Not Availbale";
    	if(p != null){
    		isAvailable = "Still available";
    	}else{
    		isAvailable = "Not Availbale";
    	}
    	return new Cart(
    			basket.getProduct(),
    			basket.getUnitPrice(),
    			basket.getSellor(),
    			basket.getPostId(),
    			basket.getBasketId(),
    			isAvailable
    			);
    }


    @FXML
    void onClose(ActionEvent event) {
    	if(myCartItems != null || cartList != null){
    		cartList.clear();
    		
    	}
    		
    	mainPane.toFront();
    }
    

    @FXML
    void onTableClicked(MouseEvent event) {
    	Cart item;
    	Basket bi;
    	if(event.getButton().equals(MouseButton.PRIMARY)
    			&& event.getClickCount() == 2){
    		item =cartTable.getSelectionModel().getSelectedItem();
    		if(item != null){
    			bi = actions.getBasketById(item.getCartId());
    			if(bi !=null){
    				 basket = bi;
    				System.out.println("item selected");
    			}else{
    				basket = null;
    				error.displayError("Error fetching cart item",
    						"Cart item not found",
    						"There was an error trying to set the item you want to delete"
    		    					+ "\nPlease try to close the Window and open again");
    			}
    		}else{
    			error.displayError("Cart Item Error",
    					"Error fecthing Cart item",
    					"There was an error trying to set the item you want to delete"
    					+ "\nPlease try to close the Window and open again");
    			
    		}
    	}
    }
    

    @FXML
    void onDelete(ActionEvent event) {
    	
    	if(basket != null){
    		if(confirmation.displayConfirmation(
    				"Deleting item...",
    				"Do you want to the delete te item\n"
    				+ ""+basket.getProduct() + "?"
    				, "Click Ok to Delete or Cancel to abort")){
    			
    			if(actions.deleteBasket(basket.getBasketId()
    					)){
    				loadCartTable();
    				success.displayResult(
    						"Success",
    						"Item Deleted",
    						"Your item was deleted");
    			}else{
    				error.displayError("Failed to delete",
    						"Could not delete the item",
    						"There was an error while trying to delete this item\n"
    						+ "Please try again");
    			};
    			
    		}else{
    			basket =null;
    		}
    	}else{
    		error.displayError("No item selected",
    				"Could not delete",
    				"Pleae double click an item first then click Delete");
    	}
    	
    }
    
    
    @FXML
    void onRefresh(ActionEvent event) {
    	searchBar.clear();
		loadPosts();
		displayPosts();
    	
    }
   
    @FXML
    void onLogOut(ActionEvent event) {
    	if(confirmation.displayConfirmation(
    			"Loging Out...", 
    			"Are you sure you want to log out ?", 
    			"Please note that any unsaved work"
    			+ "\nwill be lost !")){
    		
    		postList.clear();
    		newView = new ChangeView(
    				"/user/login.fxml",
    				false,
    				false,
    				mainPane);
    		newView.changeView();
    	}else{
    		
    	}
    }

    @FXML
    void onMessage(ActionEvent event) {
    	
    	newView = new ChangeView(
    			"/student/account/Messages.fxml",
    			true,
    			false,
    			mainPane);
    	newView.changeView();
    }

    @FXML
    void onMyAds(ActionEvent event) {
    	MyAdsController.setPaneToFront("myAdsPane");
    	MyAdsController.setIsCreating(true);
    	newView = new ChangeView(
    			"/student/ads/MyAds.fxml",
    			false,
    			true,
    			mainPane);
    	newView.changeView();
      }

    @FXML
    void onProfile(ActionEvent event) {
    	newView = new ChangeView(
    			"/student/profile/Profile.fxml",
    			true,
    			false,
    			mainPane);
    	newView.changeView();

    }

    @FXML
    void onSearch(ActionEvent event) {
    	String searchQuery;
    	searchQuery = validateQuery();
    	if(searchQuery != null){

    		fileterPosts(searchQuery.trim());
    	}else{
    		searchBar.clear();
			loadPosts();
			displayPosts();
    		error.displayError(
    				"No results",
    				"There was an error validating your search",
    				"There was no posts found with the words you entered."
    				+ "\n Please make sure that you enter key words"
    				+ "\nSuch as book or calculator or tutorial etc."
    				+ "\nMake sure that you do not only fill the field with numbers or non-alphabetic words"
    				+ "\n DO NOT LEAVE THE SEARCH BOX EMPTY !");
    	}
    }

    private void fileterPosts(String searchQuery) {
    	List<Post> result = new ArrayList<Post>();

    	Predicate<Post> condition = e ->
    		(e.getProductName().toString().contains(searchQuery)
    				||
    		e.getTitle().toString().contains(searchQuery)
    		||
    		searchQuery.contains(e.getCategoryCode()));
    	
    	result = 
    			postList.stream()
    			.filter(condition)
    			.sorted(Comparator.comparing(Post::getProductName))
    			.collect(Collectors.toList());
    	if(result !=null){
    		if(!result.isEmpty()){
    			searchBar.clear();
    			postList = result;
    			displayPosts();
    		}else{
    			searchBar.clear();
    			loadPosts();
    			displayPosts();
    			error.displayError(
    					"No results found",
    					"Nothing found",
    					"Please make sure that you only type a single word"
    					+ "\nfor better results"
    					+ "\nType worlds such as book, or calculator,"
    					+ "or tutorial etc..."
    					+ "\n");
    		}
    	}
		
	}


	private String validateQuery() {
    	if(!searchBar.getText().isEmpty()){
      		//if user only typed numbers
      		if(searchBar.getText().toString().matches("-?\\d+")){
      			return null;      			
      		}else	return searchBar.getText();
    	}else return null;	
    	
    }
    
    //add a new Post to the Post List
    @SuppressWarnings("unchecked")
	public static void loadPosts(){
    	
    	postList = new ArrayList<Post>();
    	if(postList != null){
    		postList.clear();
    	}
    	postList = Actions.getPosts();
    	
    }
    

    public void displayPosts() {
//    	System.out.println("loaging again");
//    	CREATE AN ARRAYLIST OF OF THE POSTS TO LOAD WITH THE GRIDPANE IF YOU AD A POST FIRST CLEACR
//    	THE ARRAY AND THEM REFILL WITH THE NEW DATA COMMING FROM THE DB///////////
    	Image image;
    	String description;
    	String price;
    	
//    	GridPane grid = new GridPane();
    	GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 0, 10));

    	if(postList.size() > 0){
    			for(int x = 0; x< postList.size(); x++){
					image = postList.get(x).getProductImage();
	    			description = postList.get(x).getDescription();
	    			price = Integer.toString(postList.get(x).getUnitPrice());
	    			
	    			ImageView imageView = new ImageView();
	    	        imageView.setFitHeight(120);
	    	        imageView.setFitWidth(250);
	    	        imageView.setImage(image);
	    	        imageView.setPreserveRatio(true);
	    	        imageView.addEventFilter(MouseEvent.MOUSE_PRESSED,
	    	        		e ->{
//	    	        			System.out.println(GridPane.getRowIndex( imageView));
	    	        			clicketPost = postList.get(GridPane.getRowIndex( imageView));
	    	        			previewPost();
	    	        		});
	    	        
	    	        
	    	        TextArea prodDesc = new TextArea();
	    	        prodDesc.setText(description);
	    	        prodDesc.setEditable(false);
	    	        prodDesc.addEventFilter(MouseEvent.MOUSE_PRESSED,
	    	        		e ->{
//	    	        			System.out.println(GridPane.getRowIndex( prodDesc));
//	    	        			System.out.println(postList.get(0).getProductImage());
	    	        			clicketPost = postList.get(GridPane.getRowIndex( prodDesc));
	    	        			previewPost();
	    	        		});
	    	        
	    	        
	    	        Label prodPrice = new Label();
	    	        prodPrice.setTextFill(Color.web("#5DADE2"));
	    	        prodPrice.setFont(new Font("Arial Black",30));
	    	        prodPrice.setStyle("-fx-font-weight: bold");
	    	        prodPrice.setText("R"+price);
//	    	        prodPrice.focu
	    	        
	    	        prodPrice.addEventFilter(MouseEvent.MOUSE_PRESSED,
	    	        		event -> {
//	    	        			System.out.println(GridPane.getRowIndex( prodPrice));
	    	        			clicketPost = postList.get(GridPane.getRowIndex( prodPrice));
	    	        			previewPost();
	    	        			
	    	        		}
	    	        		);
	    	       	    	        
	    			grid.add(imageView , 0, x);
	    			grid.add(prodDesc, 1, x);
	    			grid.add(prodPrice, 2, x);
	    			adsScrollPane.setContent(grid);   	
	    			
    		}
    			clicketPost=null;
    			
    	}
    		
    }
    
    
    public void previewPost(){
   	   	MyAdsController.setPaneToFront("previewPane");
    	MyAdsController.setClicketPost(clicketPost);
    	MyAdsController.state ="isEnquiry";
  	
    	newView = new ChangeView(
    			"/student/ads/MyAds.fxml",
    			true,
    			false,
    			mainPane);
    	newView.changeView();
    	
//    	adsCont.changeState("isPreview");
    }


    private void setParams() {
    	
    	//CHECK Image folders fir
    	
    			
    	actionsObj.getBasketValue();
    	basketLabel.setText(new Integer(Integer.toString(
    			actionsObj.getMyBasket())).toString());
    	actionsObj.basketProperty().addListener(new ChangeListener<Object>(){
    		
			@Override
			public void changed(ObservableValue<? extends Object> observable,
					Object oldValue, Object newValue) {
				basketLabel.setText(new Integer(Integer.toString(
						actionsObj.getMyBasket())).toString());
				
			}
    		
    	});
    	int num=0;   	
    	num = actions.getBaskets() != null ?
    			actions.getBaskets().size()
    			:num;
    	actions.setMyBasket(num);
    	
    	//load table values
    	productColumn.setCellValueFactory(new PropertyValueFactory<Cart, String>("product"));
        unitPriceColumn.setCellValueFactory(new PropertyValueFactory<Cart, Integer>("unitPrice"));
        AvailableColumn.setCellValueFactory(new PropertyValueFactory<Cart, Boolean>("isAvailable"));
    }
        
   
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		mainPane.toFront();
		adsScrollPane.toFront();
    	topBarPane.toFront();

		setParams();
		loadPosts();
		displayPosts();
		
	}




}
