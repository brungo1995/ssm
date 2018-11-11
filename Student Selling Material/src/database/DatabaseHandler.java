package database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Properties;

import alert.ConfirmationAlert;
import alert.ErrorAlert;
import alert.SuccessAlert;
import javafx.application.Platform;
import javafx.scene.image.Image;
import student.account.Basket;
import student.account.Message;
import student.ads.Category;
import student.ads.Post;
import user.User;

import org.apache.commons.io.FileUtils;
import org.apache.derby.impl.sql.catalog.SYSROUTINEPERMSRowFactory;

import admin.Config;

public class DatabaseHandler {
//	preparedStatement.setDate(4, new java.sql.Date(2009, 12, 11));
	private Connection connect = null;
	private PreparedStatement statement = null;
	private static DatabaseHandler handler = null;
	private Properties props;
	public Config config;
	public  ConfirmationAlert confirm = new ConfirmationAlert();
	;
	//ERROR
	ErrorAlert errorAlert = new ErrorAlert();
	
	private DatabaseHandler(){
		//settings();
		if(createConnection()){
			createTables();			
		}
	}
	
	public static DatabaseHandler getInstance(){
		if(handler == null){
			handler = new DatabaseHandler();
		}
		return handler;
	}

	private void settings() {
		//READ THE FILE TO GET ALL THE PROPS 
		props = new Properties();
		try(FileInputStream file = new FileInputStream("configdb.properties")) {		
			props.load(file);
		
		} catch (Exception e) {
			errorAlert.displayError("Database error", "Failed to read conf.derby"
					+ "file", "There was an error reading the conf.db file"
							+ " possible because: \n"
							+ " "+ e.getClass().getCanonicalName());

		}		
	}

	private boolean createConnection() {
		//CREATE CONNECTION READING FROM THE PROPS FROM THE FILE
		try {
//			Class.forName(props.getProperty("jdbc.driver.class")).newInstance();
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
//			connect = DriverManager.getConnection(props.getProperty("jdbc.url"));
			connect = DriverManager.getConnection("jdbc:derby:ssmDB;create=true");
			return true;
		}
		catch (Exception e) {
			errorAlert.displayError("Database error", "Create Connection failed"
					+ "file", "There was an error creating the connection"
							+ "possible because: \n"
							+ ""+ e.getClass().getName());
			if(confirm.displayConfirmation("Clossing...", 
					"Failed to load database", 
					"The application did not start properly"
					+ "\nCClose the application and restart")){
				Platform.exit();
				return false;
				
			}else{
				
				return false;
			}

		}
		
	}
	
	

	/**
	 * Create all the tables in needed to run the application
	 */
	private void createTables() {
		createConfigTable();
		createTempUser();
		createUsersTable();
		
		createBasketTable();
		createCategoryTable();
		createPostTable();
		createMessageTable();
	}
	
	
	/**
	 * The lock default value is 'x' so that every time the 
	 * student wants to add new config settings it will not
	 * allow that unless it updates
	 */
	private void createConfigTable() {
		String TABLE_NAME = "config";	

		try {                                     
			statement = connect.prepareStatement("CREATE TABLE "+ TABLE_NAME + "("
					+ " lock char(1) not null default 'x',\n"
					+ " email varchar(100) not null,\n"
					+ " password varchar(30) not null,\n"
					+ " imageFolder varchar(200) not null,\n"
					+ " constraint pk_config primary key(lock),"
					+ " constraint ck_config check(lock = 'x')"
					+ " )"
					);
			
			DatabaseMetaData dbm = connect.getMetaData();
			ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
			
			if(!tables.next()){
				//table does not exist
				boolean isTable = statement.execute();
				System.out.println(TABLE_NAME + " created");
				
			}else{
				System.out.println(TABLE_NAME + " exist already");
			}
		} catch (Exception e) {
			errorAlert.displayError("Error Creating Config Table", "Create  Config Table failed",
					"There was an error creating the  Config Table\n"
							+ "Possible reason : \n"+e.getClass().getCanonicalName());						

		}
	
}
	

	private void createTempUser() {
		//USER
		String TABLE_NAME = "tempusers";
		
		try {                                     
			statement = connect.prepareStatement("CREATE TABLE "+ TABLE_NAME + "("
					+ " pin integer not null,\n"
					+ " expDate int,\n"
					+ " studentNumber int not null,\n"
					+ " username varchar(30),\n"
					+ " password varchar(50),\n"
					+ " email varchar(60),"
					+ " PRIMARY KEY (pin, studentNumber)"
					+ " )"
					);
			
			DatabaseMetaData dbm = connect.getMetaData();
			ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
			
			if(!tables.next()){
				//table does not exist
				boolean isTable = statement.execute();
				System.out.println(TABLE_NAME + " created");
				
			}else{
				System.out.println(TABLE_NAME + " exist already");
			}
		} catch (Exception e) {
			errorAlert.displayError("Error Creating Table", "Create tempusers table failed",
					"There was an error creating the tempusers table\n"
							+ "Possible reason : \n"+e.getClass().getCanonicalName());						

		}
	}




	private void createUsersTable() {
	//USER
	String TABLE_NAME = "users";
	
	try {                                     
		statement = connect.prepareStatement("CREATE TABLE "+ TABLE_NAME + "("
				+ " studentNumber integer primary key,\n"
				+ " username varchar(30),\n"
				+ " password varchar(50),\n"
				+ " email varchar(60)"
				+ " )"
				);
		
		DatabaseMetaData dbm = connect.getMetaData();
		ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
		
		if(!tables.next()){
			//table does not exist
			boolean isTable = statement.execute();
			System.out.println(TABLE_NAME + " created");
			
		}else{
			System.out.println(TABLE_NAME + " exist already");
		}
	} catch (Exception e) {
		errorAlert.displayError("Error Creating Table", "Create User table failed",
				"There was an error creating the users table\n"
						+ "Possible reason : \n"+e.getClass().getCanonicalName());						
		
	}

	
}
	

	private void createBasketTable() {
		//Cart
		String TABLE_NAME = "basket";
		
		try {                                     
			statement = connect.prepareStatement("CREATE TABLE "+ TABLE_NAME + "("
					+ " cartId integer primary key,\n "
					+ " postId integer, \n "
					+ " sellor integer,\n"
					+ " buyer integer,\n"
					+ " unitPrice integer,\n"
					+ " isAvailable integer,\n"
					+ " productName varchar(60)"
					+ " )"
					);
			
			DatabaseMetaData dbm = connect.getMetaData();
			ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
			
			if(!tables.next()){
				//table does not exist
				boolean isTable = statement.execute();
				System.out.println(TABLE_NAME + " created");
				
			}else{
				System.out.println(TABLE_NAME + " exist already");
			}
		} catch (Exception e) {
			errorAlert.displayError("Error Creating Table", "Create Basket table failed",
					"There was an error creating the Basket table\n"
							+ "Possible reason : \n"+e.getClass().getCanonicalName());						
//			

		}
		
		
	}
	
	private void createCategoryTable() {
		//CATEGORY
		String TABLE_NAME = "category";
		
		try {                                     
			statement = connect.prepareStatement("CREATE TABLE "+ TABLE_NAME + "("
					+ " name varchar(30),\n"
					+ " code varchar(2) primary key"
					+ " )"
					);
			
			DatabaseMetaData dbm = connect.getMetaData();
			ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
			
			if(!tables.next()){
				//table does not exist
				boolean isTable = statement.execute();
				System.out.println("Loading categories to the table...");
				System.out.println(TABLE_NAME + " created");
				loadCategories();
				
			}else{
				System.out.println(TABLE_NAME + " exist already");
			}
		} catch (Exception e) {
			errorAlert.displayError("Error Creating Table", "Create category table failed",
					"There was an error creating the category table\n"
							+ "Possible reason : \n"+e.getClass().getCanonicalName());						

		}
		
	}
	

	private void createPostTable() {
		String TABLE_NAME = "post";
			
			try {                                     
				statement = connect.prepareStatement("CREATE TABLE "+ TABLE_NAME + "("
						+ " postId integer primary key,\n "
						+ "	title varchar(60),\n"
						+ "	description varchar(500),\n"
						+ "	productName varchar(100),\n"
						+ "	categoryCode varchar(200),\n"
						+ "	unitPrice integer,\n"
						+ "	quantity integer,\n"
						+ "	phone varchar(14),\n"
						+ "	postDate date not null,\n"
						+ "	expireDate date not null,\n"
						+ "	imagePath varchar(200),\n"
						+ " studentNUmber integer"
						+ " )"
						);
				
				DatabaseMetaData dbm = connect.getMetaData();
				ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
				
				if(!tables.next()){
					//table does not exist
					boolean isTable = statement.execute();
					System.out.println(TABLE_NAME + " created");
					
				}else{
					System.out.println(TABLE_NAME + " exist already");
				}
			} catch (Exception e) {
				errorAlert.displayError("Error Creating Table", "Create post table failed",
						"There was an error creating the post table\n"
								+ "Possible reason : \n"+e.getClass().getCanonicalName());						

			}
		
	}
	

	private void createMessageTable() {
		String TABLE_NAME = "message";
		
		try {                                     
			statement = connect.prepareStatement("CREATE TABLE "+ TABLE_NAME + "("
					+ " messageId integer primary key,\n "
					+ "	fromWho integer,\n"
					+ "	toWho integer,\n"
					+ "	message varchar(1000),\n"
					+ "	subject varchar(110),\n"
					+ "	dateSent date,\n"
					+ " dateReceived date,\n"
					+ "	productName varchar(100)"
					+ " )"
					);
			
			DatabaseMetaData dbm = connect.getMetaData();
			ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
			
			if(!tables.next()){
				//table does not exist
				boolean isTable = statement.execute();
				System.out.println(TABLE_NAME + " created");
				
			}else{
				System.out.println(TABLE_NAME + " exist already");
			}
		} catch (Exception e) {
			errorAlert.displayError("Error Creating Table", "Create message table failed",
					"There was an error creating the message table\n"
							+ "Possible reason : \n"+e.getClass().getCanonicalName());						

		}
		
		
	}
	

	public boolean Insert(String query, Post post){
		Date posDate = Date.valueOf(post.getPostDate());		
		Date expDate = Date.valueOf(post.getExpireDate());

		try {
			statement = connect.prepareStatement(query);
			statement.setInt(1, post.getPostId()); 
			statement.setString(2, post.getTitle());
			statement.setString(3, post.getDescription());
			statement.setString(4, post.getProductName());
			statement.setString(5, post.getCategoryCode());
			statement.setInt(6, post.getUnitPrice());
			statement.setInt(7, post.getQuantity());
			statement.setString(8, post.getPhone());			
			statement.setDate(9, posDate);		
			statement.setDate(10, expDate);
			statement.setString(11, post.getImageFile().getPath().toString());
			statement.setInt(12, post.getStudentNUmber());
			statement.execute();	
			
			return true;
		} catch (SQLException e) {
			errorAlert.displayError("Error Inserting Table", "Inserting Post failed",
			"There was an error while trying to insert the record into Post table\n"
					+ "Possible reason : \n"+e.getClass().getCanonicalName());						
			return false;
		}				
	}
	

	public ArrayList<Post> getAll(String query, ArrayList<Post> posts){
		String getConquery;
		getConquery = "select * from config";
		config = getConfigs(getConquery);
		String path;
		path = config.getImageFolder();

		ResultSet result ;
		try {
			statement = connect.prepareStatement(query);
			result=statement.executeQuery();
		
			while(result.next()){
				String fullpath = result.getString("imagePath");
				int index = fullpath.lastIndexOf("\\")+1;
				String imageName = fullpath.substring(index);
				int imageId = result.getInt("postId");
				String imageFolder = path+"/"+imageId+"/"+imageName;
				File imageFile = new File(imageFolder);
				if(imageFile.exists()){


				}
				//READ IMAGE PATH AND GET THE IMAGE
				String imagePath;
				Image image= null;
				try {
					imagePath = imageFile.toURI().toURL().toString();
					image =new Image(imagePath);
				} catch (MalformedURLException e) {
					errorAlert.displayError("Error reading image", "Fetch image failed ",
					"There was an error while trying to read the images from the root folder\n"
							+ "Possible reason : \n"+e.getClass().getCanonicalName());

				}
				
				Date postdate = result.getDate("postDate");
				LocalDate pdate = new java.sql.Date( postdate.getTime() ).toLocalDate();				
				Date expireDate = result.getDate("expireDate");
				LocalDate expDate = new java.sql.Date( expireDate.getTime() ).toLocalDate();
				
				posts.add(
						new Post(
						result.getString("title"),
						result.getString("description"),
						result.getString("productName"),
						result.getString("categoryCode"),
						result.getInt("unitPrice"),
						result.getInt("quantity"),
						result.getString("phone"),
						pdate,
						expDate,
						image,
						result.getInt("studentNUmber"),
						imageFile,
						result.getInt("postId")
						));
				
			}
			
			return posts;
		} catch (SQLException e) {
			errorAlert.displayError("Error fetching all ads", "Fetch post failed ",
			"There was an error while trying to select all posts\n"
					+ "Possible reason : \n"+e.getClass().getCanonicalName());
//			e. ;
			return null;
		}
	}
	

	public int update(String query, Post post){
		ResultSet result;
		Date posDate = Date.valueOf(post.getPostDate());		
		Date expDate = Date.valueOf(post.getExpireDate());
		
		System.out.println("The Post date is " +posDate);
		System.out.println("The Expire date is " +expDate);
		try {
			statement = connect.prepareStatement(query);
			statement = connect.prepareStatement(query);

			statement.setString(1, post.getTitle());
			statement.setString(2, post.getDescription());
			statement.setString(3, post.getProductName());
			statement.setString(4, post.getCategoryCode());
			statement.setInt(5, post.getUnitPrice());
			statement.setInt(6, post.getQuantity());
			statement.setString(7, post.getPhone());
			statement.setDate(8, posDate);
			statement.setDate(9, expDate);
			statement.setString(10, post.getImageFile().getPath().toString());
			statement.setInt(11, post.getPostId()); 
			int r = statement.executeUpdate();
			System.out.println(r);
			
		
			return r;
		} catch (SQLException e) {
			errorAlert.displayError("Error updating the post", "Update post failed ",
			"There was an error while trying to update the post\n"
					+ "Possible reason : \n"+e.getClass().getCanonicalName());
//			e. ;
			return 0;
		}
	}
	
	
	public boolean checkPostId(String query, int postId){
		ArrayList<Integer> postIds = new ArrayList<>();
		int temp;
		boolean  isPresent =false;
			try {
				ResultSet result;
				statement =connect.prepareStatement(query);
				result = statement.executeQuery();
				while(result.next()){
					temp = result.getInt(1);
					postIds.add(temp);
				}
				if(!postIds.isEmpty()){
					for(int x=0; x<postIds.size(); x++){
						if(postIds.get(x) == postId)
							isPresent= true;
					}
				}else{
					isPresent= false;
				}

				return isPresent;
			} catch (SQLException e) {
			
				return false;
			}		

	}
	
	public Post getPost(String query, int postId) {
		String getConquery;
		getConquery = "select * from config";
		config = getConfigs(getConquery);
		String path;
		path = config.getImageFolder();
		
		Post post = null;
			try {
				ResultSet result;
				statement =connect.prepareStatement(query);
				result = statement.executeQuery();
				while(result.next()){
					
					String fullpath = result.getString("imagePath");
					int index = fullpath.lastIndexOf("\\")+1;
					String imageName = fullpath.substring(index);
					int imageId = result.getInt("postId");
					String imageFolder = path+"/"+imageId+"/"+imageName;
					File imageFile = new File(imageFolder);
					if(imageFile.exists()){
//						System.out.println("found file");

					}
					//READ IMAGE PATH AND GET THE IMAGE
					String imagePath;
					Image image= null;
					try {
						imagePath = imageFile.toURI().toURL().toString();
						image =new Image(imagePath);
					} catch (MalformedURLException e) {
						errorAlert.displayError("Error reading image", "Fetch image failed ",
						"There was an error while trying to read the images from the root folder\n"
								+ "Possible reason : \n"+e.getClass().getCanonicalName());
//						e. ;
					}
					
					Date postdate = result.getDate("postDate");
					LocalDate pdate = new java.sql.Date( postdate.getTime() ).toLocalDate();				
					Date expireDate = result.getDate("expireDate");
					LocalDate expDate = new java.sql.Date( expireDate.getTime() ).toLocalDate();
					
					post = new Post(
							result.getString("title"),
							result.getString("description"),
							result.getString("productName"),
							result.getString("categoryCode"),
							result.getInt("unitPrice"),
							result.getInt("quantity"),
							result.getString("phone"),
							pdate,
							expDate,
							image,
							result.getInt("studentNUmber"),
							imageFile,
							result.getInt("postId")
							);
				}
				
				return post;
			} catch (SQLException e) {
				
				return null;
			}

	}
	
	public boolean deletePost(String query, int postId) {
		try {
			statement =connect.prepareStatement(query);
			statement.setInt(1, postId);
			int result = statement.executeUpdate();
			if(result > 0){
				return true;				
			}else{
				return false;
			}
		} catch (SQLException e) {
			errorAlert.displayError("Error deleting post", "Delete post failed ",
			"There was an error while trying to delete the post\n"
					+ "Possible reason : \n"+e.getClass().getCanonicalName());
			
//			e. ;
			return false;
		}
		
	}


public  void copyImageToFolder(Post post){
	String query;
	query = "select * from config";
	config = getConfigs(query);
	if(config != null){
		File image =  post.getImageFile();
		String path = config.getImageFolder();
		try {
			File destinationFolder = new File(path+"/"
					+ Integer.toString(post.getPostId())
					);	
			FileUtils.copyFileToDirectory(image, destinationFolder);
			
		} catch (Exception e) {
//			e. ;
		}
		
		
	}else{
		
	}
}


public boolean Insert(String query, Basket item){
	try {
		statement = connect.prepareStatement(query);
		statement.setInt(1, item.getBasketId());
		statement.setInt(2, item.getPostId());
		statement.setInt(3, item.getSellor());
		statement.setInt(4, item.getBayer());
		statement.setInt(5, item.getUnitPrice());
		statement.setString(6, item.getProduct());
		statement.execute();		
		return true;
	} catch (SQLException e) {
		errorAlert.displayError("Error Inserting Table", "Inserting Post failed",
		"There was an error while trying to insert the record into Post table\n"
				+ "Possible reason : \n"+e.getClass().getCanonicalName());						

		return false;
	}				
}


public boolean chekCartId(String query, int cartId) {
	ArrayList<Integer> cartIds = new ArrayList<>();
	int temp;
	boolean  isPresent =false;
		try {
			ResultSet result;
			statement =connect.prepareStatement(query);
			result = statement.executeQuery();
			while(result.next()){
				temp = result.getInt(1);
				cartIds.add(temp);
			}
			if(!cartIds.isEmpty()){
				for(int x=0; x<cartIds.size(); x++){
					if(cartIds.get(x) == cartId)
						isPresent= true;
				}
			}else{
				isPresent= false;
			}

			return isPresent;
		} catch (SQLException e) {
			
			return false;
		}
}



public ArrayList<Basket> getAllBasket(String query) {
	Basket basket = null;
	ArrayList<Basket> myBasketList = new ArrayList<Basket>();
	try {
		ResultSet result;
		statement =connect.prepareStatement(query);
		result = statement.executeQuery();
		while(result.next()){

			basket = new Basket(
					result.getInt("cartId"),
					result.getString("productName"),
					result.getInt("unitPrice"),
					result.getInt("sellor"),
					result.getInt("buyer"),
					result.getInt("postId")
					);
			myBasketList.add(basket);
		}
		
		return myBasketList;
	} catch (SQLException e) {
		
		return null;
	}
}



public Basket getCartItemById(String query, int cartId) {
	Basket basket = null;
	try {
		ResultSet result;
		statement =connect.prepareStatement(query);
		result = statement.executeQuery();
		while(result.next()){

			basket = new Basket(
					result.getInt("cartId"),
					result.getString("productName"),
					result.getInt("unitPrice"),
					result.getInt("sellor"),
					result.getInt("buyer"),
					result.getInt("postId")
					);
		}
		
		return basket;
	} catch (SQLException e) {
		
		return null;
	}
}

public boolean deleteBasket(String query, int basketId) {
	try {
		statement =connect.prepareStatement(query);
		statement.setInt(1, basketId);
		int result = statement.executeUpdate();
		if(result > 0){
			return true;				
		}else{
			return false;
		}
	} catch (SQLException e) {
		errorAlert.displayError("Error deleting the item", "Delete post failed ",
		"There was an error while trying to delete the post\n"
				+ "Possible reason : \n"+e.getClass().getCanonicalName());
		return false;
	}
	
	
}

public boolean checkMessageId(String query, int mId) {
	ArrayList<Integer> mIds = new ArrayList<>();
	int temp;
	boolean  isPresent =false;
		try {
			ResultSet result;
			statement =connect.prepareStatement(query);
			result = statement.executeQuery();
			while(result.next()){
				temp = result.getInt(1);
				mIds.add(temp);
			}
			if(!mIds.isEmpty()){
				for(int x=0; x<mIds.size(); x++){
					if(mIds.get(x) == mId)
						isPresent= true;
				}
			}else{
				isPresent= false;
			}

			return isPresent;
		} catch (SQLException e) {
			
			return false;
		}		
}


public boolean InsertEnquiry(String query, Message enquiryMessage) {
	Date date = Date.valueOf(enquiryMessage.getDatField());
	try {
		statement = connect.prepareStatement(query);
		statement.setInt(1, enquiryMessage.getMessageId());
		statement.setInt(2, enquiryMessage.getSendor() );
		statement.setInt(3, enquiryMessage.getToField() );
		statement.setString(4, enquiryMessage.getEnqMessageTextArea());
		statement.setString(5, enquiryMessage.getSubjectField());
		statement.setDate(6, date);
		statement.setDate(7, date);
		statement.setString(8, enquiryMessage.getProductField());
		statement.execute();		
		return true;
	} catch (SQLException e) {
		errorAlert.displayError("Error Inserting Table", "Sending message failed",
		"There was an error while trying to send your message\n"
				+ "Possible reason : \n"+e.getClass().getCanonicalName());						
		return false;
	}				
}




public ArrayList<Message> getMyMessages(String query, int sn) {
	Message message = null;
	ArrayList<Message> messages = new ArrayList<Message>();
	try {
		ResultSet result;
		statement =connect.prepareStatement(query);
		result = statement.executeQuery();
		while(result.next()){

			Date d = result.getDate("dateSent");
			LocalDate date = new java.sql.Date( d.getTime() ).toLocalDate();
			
			messages.add(new Message(
					result.getInt("fromWho"),
					result.getInt("toWho"),
					result.getString("message"),
					result.getString("subject"),
					date,
					result.getString("productName"),
					result.getInt("messageId")					
					));
		}
		
		return messages;
	} catch (SQLException e) {
		
		return null;
	}
}

	private void loadCategories() {
		String query;
		query = "insert into category (name, code) "
				+ "values(?, ?)";
		ArrayList<Category> list = new ArrayList<Category>();
		list.add(new Category("Stationary","ST"));
		list.add(new Category("Books","BK"));
		list.add(new Category("Electronics","EL"));
		list.add(new Category("Services","SE"));
		list.add(new Category("Other","OT"));		
		list.stream().forEach(i -> insert(query, i));

	}

	private boolean insert(String query, Category i) {
		try {
			statement = connect.prepareStatement(query);
			statement.setString(1, i.getName());
			statement.setString(2, i.getCode());
			statement.execute();
			return true;
		} catch (SQLException e) {
			errorAlert.displayError("Error Inserting Table", "Loading categories failed",
			"There was an error while trying to load all the categories \n"
					+ "Possible reason : \n"+e.getClass().getCanonicalName());						
			return false;
		}
		
	}
	
	public ArrayList<Category> getCategories(String query){
		ResultSet result;
		ArrayList<Category> list = new ArrayList<Category>();
		try {
			statement = connect.prepareStatement(query);			
			result = statement.executeQuery();
			while(result.next()){
				list.add(new Category(
						result.getString("name"),
						result.getString("code")));
			}
			
			return list;
		} catch (SQLException e) {
			errorAlert.displayError("Fecth Error  ", "Failed to get all categories",
			"There was an error while trying to get all the categories \n"
					+ "Possible reason : \n"+e.getClass().getCanonicalName());						
	
			return null;
		}
		
	}

	public String getPassword(String query) {
		String pass = null;
		try {
			ResultSet result;
			statement =connect.prepareStatement(query);
			result = statement.executeQuery();
			while(result.next()){
				pass = result.getString("password");
			}			
			return pass;
		} catch (SQLException e) {
			
			return null;
		}
	}
	
	
	public boolean insert(String query, User user, int pin) {
		int time = Math.abs((int)System.currentTimeMillis());
		
		System.out.println(time);
		try {
			statement = connect.prepareStatement(query);
			statement.setInt(1, pin);
			statement.setInt(2, time);
			statement.setInt(3, user.getStudentNumber());
			statement.setString(4, user.getUsername());
			statement.setString(5, user.getPassword());
			statement.setString(6, user.getEmail());
			statement.execute();
			return true;
		} catch (SQLException e) {
			errorAlert.displayError("Error Inserting Table", "Inserting student failed",
			"There was an error while trying to add the user to the databse\n"
					+ "Possible reason : \n"+e.getClass().getCanonicalName());						
	
			return false;
		}
		
	}

	

	public boolean checkTempUserPin(String query, int pin) {
		ArrayList<Integer> pins = new ArrayList<>();
		int temp;
		boolean  isPresent =false;
			try {
				ResultSet result;
				statement =connect.prepareStatement(query);
				result = statement.executeQuery();
				while(result.next()){
					temp = result.getInt(1);
					pins.add(temp);
				}
				if(!pins.isEmpty()){
					for(int x=0; x<pins.size(); x++){
						if(pins.get(x) == pin)
							isPresent= true;
					}
				}else{
					isPresent= false;
				}
				//check if pin is not expired
				
				return isPresent;
			} catch (SQLException e) {
				
				return false;
			}		
	}


public void isPinValidStill(int sn) {
	int time1=0;
	int time2=Math.abs((int)System.currentTimeMillis());
	String query = "select * from tempusers where studentNumber = "+sn;

	try {
		ResultSet result;
		statement =connect.prepareStatement(query);
		result = statement.executeQuery();
		while(result.next()){
			time1 = result.getInt("expDate");
		}
		if(time1 > 0){
			time2 = Math.abs((int)System.currentTimeMillis());
			double difference = (((time1 - time2)) / 60000);
			if(difference > 5){
				if(deleteTempUser("delete from tempusers where studentNumber = ?", sn)){
					System.out.println("temp user deleted");
				}else{
					System.out.println("could not delete the temp user");
				}
			}else{
				System.out.println("user is still valid\n"+
						Math.abs(time2 - time1));
			}
		}
	} catch (SQLException e) {
		

	}
}


	public boolean checkTempStudent(String query) {
		int pin=-1;
			try {
				ResultSet result;
				statement =connect.prepareStatement(query);
				result = statement.executeQuery();
				while(result.next()){
					pin = result.getInt(1);					
				}
				if(pin == -1){			
					 return false;				
				}else{
					return true;
				}
			} catch (SQLException e) {
				
				return false;
			}		
	}

	

	public User getTempUser(String query) {
		User user = null;
		try {
			ResultSet result;
			statement =connect.prepareStatement(query);
			result = statement.executeQuery();
			while(result.next()){

				user = new User(
						result.getInt("studentNumber"),
						result.getString("username"),
						result.getString("password"),
						result.getString("email")
						);
			}
			
			return user;
		} catch (SQLException e) {
			
			return null;
		}
	}
	
	
	public boolean deleteTempUser(String query, int sn){
		try {
			statement =connect.prepareStatement(query);
			statement.setInt(1, sn);
			int result = statement.executeUpdate();
			if(result > 0){
				System.out.println("user deleted");
				return true;				
			}else{
				return false;
			}
		} catch (SQLException e) {
			errorAlert.displayError("Error deleting tempuser", "Delete tempuser failed ",
			"There was an error while trying to delete the tempuser\n"
					+ "Possible reason : \n"+e.getClass().getCanonicalName());
			
			
			return false;
		}
	}

	public User getUser(String query) {
		User user = null;
		try {
			ResultSet result;
			statement =connect.prepareStatement(query);
			result = statement.executeQuery();
			while(result.next()){
				user = new User(
						result.getInt("studentNumber"),
						result.getString("username"),
						result.getString("password"),
						result.getString("email")
						);
			}
			
			return user;
		} catch (SQLException e) {
			
			return null;
		}
	}


	public boolean checkUser(String query) {
		int sn=-1;
		try {
			ResultSet result;
			statement =connect.prepareStatement(query);
			result = statement.executeQuery();
			while(result.next()){
				sn = result.getInt(1);					
			}
			if(sn == -1){
				
				 return false;				
			}else{
				return true;
			}
		} catch (SQLException e) {
			
			return false;
		}	
	}
	

	public boolean insert(String query, User user){
		try {
			statement = connect.prepareStatement(query);
			statement.setInt(1, user.getStudentNumber());
			statement.setString(2, user.getUsername());
			statement.setString(3, user.getPassword());
			statement.setString(4, user.getEmail());
			statement.execute();
			return true;
		} catch (SQLException e) {
			errorAlert.displayError("Error Inserting Table", "Inserting student failed",
			"There was an error while trying to add the user to the databse\n"
					+ "Possible reason : \n"+e.getClass().getCanonicalName());						

			return false;
		}
	}
	

	public boolean updateProfile(String query, User user){
		try {
			statement = connect.prepareStatement(query);
			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());
			statement.setInt(3, user.getStudentNumber());
			int r = statement.executeUpdate();
			System.out.println(r);
			if(r>0){
				return true;
			}else return false;
		} catch (SQLException e) {
			errorAlert.displayError("Error updating your profile", "Update your profile failed ",
			"There was an error while trying to update your profile\n"
					+ "Possible reason : \n"+e.getClass().getCanonicalName());
//			e. ;
			return false;
		}
	}

	

	public Config getConfigs(String query) {
		ResultSet result;
		Config config = null;
		
		try {
			statement  = connect.prepareStatement(query);
			result = statement.executeQuery();						

			while(result.next()){
				config = new Config(
						"x",
						result.getString("email"),
						result.getString("password"),
						result.getString("imageFolder"));
			}
			return config;
		} catch (SQLException e) {
			
			return null;
		}
	}

	

	public boolean updateConfig(String query, Config updatedConfig) {
		try {
			statement = connect.prepareStatement(query);
			statement.setString(1, updatedConfig.getEmail());
			statement.setString(2, updatedConfig.getPassword());
			statement.setString(3, updatedConfig.getImageFolder());
			statement.setString(4, "x");
			if(statement.executeUpdate() > 0)
				return true;
			else return false;
			
		} catch (SQLException e) {

			return false;
		}
	}
	
	public int createConfig(String query, Config updatedConfig){
		
		try {
			statement = connect.prepareStatement(query);
			statement.setString(1, updatedConfig.getEmail());
			statement.setString(2, updatedConfig.getPassword());
			statement.setString(3, updatedConfig.getImageFolder());
			if(statement.execute() == true)
				return 1;
			else {
				
				query = "update config set"
						+ " email = ?"
						+ " ,password = ?"
						+ " ,imageFolder = ?"
						+ " where lock = ?";
				if(updateConfig(query, updatedConfig)){
					return 1;
				}else{
					return 0;
				}
			}
			
		} catch (SQLException e) {
			query = "update config set"
					+ " email = ?"
					+ " ,password = ?"
					+ " ,imageFolder = ?"
					+ " where lock = ?";
			if(updateConfig(query, updatedConfig)){
				
				return 1;
			}else{
				return 0;
			}
//			e. ;
			
		}
	}
	
}

