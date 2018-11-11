package student.ads;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;

import admin.Config;
import database.DatabaseHandler;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import student.account.Basket;
import student.account.Message;
import user.SendEmail;
import user.User;

public class Actions {
	
	//POST
	public static ArrayList<Post> posts = new ArrayList<Post>();
	public ArrayList<Post> myPosts = new ArrayList<Post>();
	
	//Basket
	private static IntegerProperty myBasket;
	private  int basketValue = 0;
	public static ArrayList<Basket> myBasketList = new ArrayList<Basket>();
	private static Basket currentBasket;
	
	//Category
	private ArrayList<String> categoryList = new ArrayList<String>();
	
	//PIN AND EMAIL

	SendEmail sendEmail;
	Config config;
	
	//MESSAGES 
	public static ArrayList<Message> myMessages = new ArrayList<Message>();
	
	//CURRENT STUDENT 
	public static  int currentStudent;
	
	//MESSAGE
	private static Message message;
	
//	DATABASE
	private static DatabaseHandler instance;
		
	
	
	

	public static void setCurrentBasket(Basket currentBasket) {
		//databse call to add this basket to the basket table
		myBasketList.add(currentBasket);
		Actions.currentBasket = currentBasket;
	}
		
	public static Basket getCurrentBasket() {
		return currentBasket;
	}
	
	public int getBasketValue() {
		//databse call to get the first number of items at firs and set the myMasket
		if(basketValue == 0){
			//databse call
			basketValue = 1;
			this.setMyBasket(0);
		}
		return basketValue;
	}
	
	
	public Basket getBasketById(int cartId) {
		//WRITE A QUERY TO THE ERVER TO GET A SINGLE 
		String query = "select * from basket where cartId = "+cartId;
		Basket basket = null;
		basket = instance.getCartItemById(query, cartId);
		if(basket != null){
			return basket;
		}else{
			return null;
			
		}
	}
	

	public int getMyBasket(){
		return myBasket.get();
	}
	
	public  void setMyBasket(int myBasket){
		this.basketProperty().set(myBasket);
	}
	
	public final IntegerProperty basketProperty(){
		if(myBasket == null){
			myBasket = new SimpleIntegerProperty(0);
		}
		return myBasket;
	}
	

	
	//Database calls
	public ArrayList<Category>  getCategories(){
		String query;
		query = "select * from category";
		ArrayList<Category> list = new ArrayList<Category>();
		list = instance.getCategories(query);
		if(list != null){
			if(list.size() > 0){
				return list;
			}else{
				return null;
			}
			
		}else{
			return null;
		}
	}
	

	public ArrayList<Post> getMyPosts(int studentNumber){
		//DATABSE CALL TO GET AL THE POSTS BASED OON THE STUDENT NUMBER
		String query = "select * from post";
		ArrayList<Post> allPosts = new ArrayList<Post>();
		List<Post> list = new ArrayList<Post>();
		myPosts.clear();
		allPosts = instance.getAll(query, allPosts);
		if(!allPosts.isEmpty()){
			list = 
			allPosts.stream()
			.filter(post -> post.getStudentNUmber()
					==studentNumber)
			.collect(Collectors.toList());
			
//			System.out.println(list.size());
			return (ArrayList<Post>) list;
		}else{
			return null;
		}
	}
	

	public static void addMorePost(Post post){
//		QUERY TO ADD THE POST TO THE DB
		String query;
		System.out.println("creating pin with id "+post.getPostId());
    	 System.out.println("the postdate is "+post.getPostDate()+"\n"+
		"The exp date is "+post.getExpireDate());
    			 
		query = "insert into post (postId,title, description, productName, categoryCode, unitPrice, "
				+ "quantity, phone, postDate, expireDate, imagePath, studentNUmber) values(?,?,?,?,?,?,?,?,?,?,?,?)";
		if(instance.Insert(query, post)){
			instance.copyImageToFolder(post);
//			System.out.println("inserted post");
		};
		posts.add(post);
		
	}
	
	public int generatePostId(){
		int postId;
		//GENERATE POST ID AND LOOK IF IT IS ALREADY IN THE DB
		boolean isPresent=true;
    	 do{
    		 Random r = new Random( System.currentTimeMillis() );
    		 postId =   ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));    	 
    		 String checkPostId ="select postId from post";
    		 isPresent = instance.checkPostId(checkPostId, postId);
    		 
    	 }while(isPresent==true);
    	 return postId;
	}
	
	@SuppressWarnings("rawtypes")
	public static ArrayList getPosts(){
		//WRITE QUERY TO GET ALL POSTS
		String query = "select * from post";
		posts.clear();
		posts = instance.getAll(query, posts);
		if(posts != null){
			return posts;
		}
		
		return posts;
	}
	
	public Post getPostById(int postId) {
		//WRITE A QUERY TO THE ERVER TO GET A SINGLE 
		String query = "select * from post where postId = "+postId;
		Post currentPost = null;
		currentPost = instance.getPost(query, postId);
		if(currentPost != null){
			return currentPost;
		}else{
			return null;
			
		}
	}
	
	
	public boolean updatePost(Post validPost, String oldpath) {
//		QUERY TO UPDATE ALL THE FIELDS	
		String query;
		if(oldpath.equalsIgnoreCase(validPost.getImageFile().getParent().toString())){
			
		}
		query = "update post "
				+ " set title = ?"
				+ " ,description = ?"
				+ ",productName = ?"
				+ ",categoryCode = ?"
				+ ",unitPrice = ?"
				+ ",quantity = ?"
				+ ",phone = ?"
				+ ",postDate = ?"
				+ ",expireDate = ?"
				+ ",imagePath = ?"
				+ " where postId= ?";
		
		if(instance.update(query, validPost) > 0){
//			//DELETE OLD FOLDER AND ADD NEW ONE
			if(oldpath.equalsIgnoreCase(validPost.getImageFile().getParent().toString())){
				return true;
			}else{
				try {
					FileUtils.deleteDirectory(new File(oldpath));
					instance.copyImageToFolder(validPost);
					return true;
				} catch (IOException e) {
					
					return false;
				}
				
			}
		}
		return false;
	
	}
	
	public boolean deletePost(int postId) {
		//QUERY DB
		String query;
		Post post;
		post = getPostById(postId);
		if(post != null){
//			DELETE FROM tableName WHERE criteria
			query = "delete from post where postId = ?";
			instance.deletePost(query, postId);
			return true;
		}else{
			return false;			
		}
	}
	
		
	public Message getMessage() {
		return message;
	}


	public  void removeCartItem(int itemNUmber) {
//		DATABSE CALL TO REMOVE FROM THE TABLE
		myBasketList.remove(itemNUmber);
		
		//REDUCE FROM THE ITEMS COLLECTION -1
		setMyBasket(getMyBasket() -1);
		
		//DATABSE CALL TO GET THE FRESH LIST FROM THE CART TABLE
		
	}
	
	
	public int generateCartId(){
		int cartId;
		//GENERATE POST ID AND LOOK IF IT IS ALREADY IN THE DB
		boolean isPresent=true;
    	 do{
    		 Random r = new Random( System.currentTimeMillis() );
    		 cartId =   ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));    	 
    		 String query ="select cartId from basket";
    		 isPresent = instance.chekCartId(query, cartId);
    		 
    	 }while(isPresent==true);
    	 return cartId;
	}
	

	public boolean adBasket(Basket basket,int cartId) {
//		QUERY TO ADD THE CART TO THE DB
		String query;  			 
		query = "insert into basket (cartId, postId, sellor, buyer, unitPrice, productName)"
				+ " values(?,?,?,?,?,?)";
		if(instance.Insert(query, basket)){
			System.out.println("adding an item");
			myBasketList.add(basket);
			return true;
		}else{
			return false;
		}	
	}

	public ArrayList<Basket> getBaskets() {
		String query;
		query = "select * from basket where buyer = "+ currentStudent;
		myBasketList.clear();
		myBasketList = instance.getAllBasket(query);
		if(myBasketList.size() > 0){
			return myBasketList;
		}else{
			return null;			
		}
	}
	
	
	public boolean deleteBasket(int basketId) {
		//QUERY DB
		String query;
		Basket basket;
		basket = getBasketById(basketId);
		if(basket != null){
			query = "delete from basket where cartId = ?";
		if(instance.deleteBasket(query, basketId)){
			return true;
			
		}else{
			return false;
			}
		}else{
			return false;			
		}
	}
	
	
	public int generateMessageId() {
		int mId;
		//GENERATE POST ID AND LOOK IF IT IS ALREADY IN THE DB
		boolean isPresent=true;
    	 do{
    		 Random r = new Random( System.currentTimeMillis() );
    		 mId =   ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));    	 
    		 String query ="select messageId from message";
    		 isPresent = instance.checkMessageId(query, mId);
    		 
    	 }while(isPresent==true);
    	 return mId;
	}
	

	public  boolean sendMessage(Message enquiryMessage) {
//		QUERY TO ADD THE CART TO THE DB
		String query;  			 
		query = "insert into message (messageId, fromWho, toWho,"
				+ " message, subject, dateSent, "
				+ "dateReceived, productName)"
				+ " values(?,?,?,?,?,?,?,?)";
		if(instance.InsertEnquiry(query, enquiryMessage)){
			System.out.println("adding an item");
			return true;
		}else{
			return false;
		}
	}

	

	public ArrayList<String> getCategoryList() {
		// DATABSE CALL TO GET ALL THE CATEGORIES
		categoryList.add("Book");
		categoryList.add("Stationary");
		categoryList.add("Other");
		
		return categoryList;
	}
	

	public User getUser(String sn) {
		String query;
		User user=null;
		query = "select * from users where studentNumber = "+sn;
		user = instance.getUser(query);
		if(user !=null){
			return user;
		}else{
			return null;
			
		}
	}
	
		
	public boolean isUserOnDb(int sn) {
		//DATABASE CALL TO ALL TEMP USERS TO CHECK IF THIS STUDENT NUMBER IS THERE
		String query;
		query = "select studentNumber from users where studentNumber = "+
				sn;
		
		if(instance.checkUser(query)){
			return true;
		}else{
			return false;
		}
	}
	
	
	public boolean updateUser(User updtedUser) {
		String query;
		query = "update users set"
				+ "  username = ?,"
				+ " password = ?"
				+ " where studentNumber = ?";
		if(instance.updateProfile(query, updtedUser)){
			return true;
		}else return false;	
	}
	

	public boolean addUser(User user){
		//ADD THIS USER TO THE TEMP USER DB AND ASSIGN A TIME OF 5 MINUTES
				//AFTER  MINUTES LET THE RECORD EXPIRE AND DELETE THE USER
				String query;
				query = "insert into users (studentNumber,"
						+ " username, password, email) "
						+ "values(?,?,?,?)";
				if(instance.insert(query, user)){
					return true;
				}else{
					return false;
				}
				
	}

	public int generatePin() {
		int pin;
		//GENERATE POST ID AND LOOK IF IT IS ALREADY IN THE DB
		boolean isPresent=true;
    	 do{
    		 Random r = new Random( System.currentTimeMillis() );
    		 pin =   ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));    	 
    		 String query ="select pin from tempusers";
    		 isPresent = instance.checkTempUserPin(query, pin);    		 
    	 }while(isPresent==true);
    	 return pin;
	}
	

	public void sendPin( String to, String subject, String content) {
		//SEND EMAIL TO THE STUDENT WITH HIS SECRET PIN
		sendEmail = new SendEmail();
		sendEmail.send(to, subject,content);
		
	}

	public boolean getTempUser(int sn) {
		//DATABASE CALL TO ALL TEMP USERS TO CHECK IF THIS STUDENT NUMBER IS THERE
		String query;
		query = "select studentNumber from tempusers where studentNumber = "+
				sn;
		
		if(instance.checkTempStudent(query)){
			return true;
		}else{
			return false;
		}

	}
	
	public boolean getTempUserAndCheckPin(int sn, int pin) {
		//DATABASE CALL TO ALL TEMP USERS TO CHECK IF THIS STUDENT NUMBER IS THERE
		String query;
		query = "select studentNumber from tempusers where studentNumber = "+
				sn;		
		if(instance.checkTempStudent(query)){
			
			instance.isPinValidStill(sn);
			return true;
		}else{
			return false;
		}

	}
	
	
	public boolean checkTempUserPin(User user, int pin){
		String query;
		query = "select pin, studentNumber from"
				+ " tempusers where (pin = "+
				pin+")" + " and (studentNumber = "+user.getStudentNumber()+")";
		
		if(instance.checkTempUserPin(query, pin)){			
			instance.isPinValidStill(pin);			
			return true;
		}else{
			return false;
		}
	}
	
	
	public User getTempUserById(String sn) {
		//FINDS THE USER THE TEMP TABLE BY ID AND RETURNS IT
		String query;
		User user=null;
		query = "select * from tempusers where studentNumber = "+sn;
		user = instance.getTempUser(query);
		if(user !=null){
			return user;
		}else{
			return null;
			
		}
	}


	public boolean addTempUser(User user, int pin) {
		//ADD THIS USER TO THE TEMP USER DB AND ASSIGN A TIME OF 5 MINUTES
		//AFTER  MINUTES LET THE RECORD EXPIRE AND DELETE THE USER
		String query;
		query = "insert into tempusers (pin, expDate, studentNumber,"
				+ " username, password, email) "
				+ "values(?,?,?,?,?,?)";
		if(instance.insert(query, user, pin)){
			return true;
		}else{
			return false;
		}
		
	}


	public boolean deleteTempUser(int sn) {
		//QUERY DB
		String query;
		query = "delete from tempusers where studentNumber = ?";
		if(instance.deleteTempUser(query, sn)){
			return true;
		}else return false;			
	
	}

	public void sendEmail(String to, String subject, String content) {
		//SEND EMAIL WITH STUDENT PASSPORD
		sendEmail = new SendEmail();
		sendEmail.send(to, subject,content);
		
	}

	

	public ArrayList<Message> getMyMessages(int sn) {
		String query;
		query = "select * from message where toWho = "+sn;
		myMessages.clear();
		myMessages = instance.getMyMessages(query, sn);
		
		if(myMessages.size() > 0){
			return myMessages;
		}else{
			return null;			
		}

		
	}
	
	public static void setInstance(DatabaseHandler instance) {
		Actions.instance = instance;
		
	}


	public String getPassword(int sn) {
		String query;
		String pass=null;
		query = "select password from users where studentNumber = "+sn;
		pass = instance.getPassword(query);
		if(pass != null) return pass;
		else return null;

	}
	

	public Config getConfig() {
		String query;
		query = "select * from config";
		config = instance.getConfigs(query);
		if(config != null){
			System.out.println(config.getEmail());
			if(config.getEmail() == null 
					|| config.getImageFolder() == null){
				return null;
			}else{
				return config;
			}
			
		}else{
			return null;
		}
	}
	

	public boolean createConfig(Config updatedConfig){
		String query;
		query = "insert into config (email, password, imageFolder) values(?,?,?)";
				
				if(instance.createConfig(query, updatedConfig) > 0){
					return true;
				}
				else return false;

	}

}
