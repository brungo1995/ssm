package student.ads;

import java.io.File;
import java.time.LocalDate;

import javafx.scene.image.Image;

public class Post {
	
	private String title;
	private String description;
	private String categoryCode;
	private String productName;
	private int  unitPrice;
	private int quantity ;
	private String phone;
	private LocalDate postDate;
	private LocalDate expireDate;
	private Image productImage;	
	private int studentNUmber;
	private File imageFile;
	

	private int postId;
		
	public Post(
			String title, 
			String description, 
			String productName,
			String categoryCode,
			int unitPrice, 
			int quantity,
			String phone, 
			LocalDate postDate, 
			LocalDate expireDate,
			Image productImage,
			int studentNUmber,
			File imageFile,			
			int postId) {
//		super();
		this.title = title;
		this.description = description;
		this.postId = postId;
		this.productName = productName;
		this.categoryCode = categoryCode;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
		this.phone = phone;
		this.postDate = postDate;
		this.expireDate = expireDate;
		this.imageFile = imageFile;
		this.productImage = productImage;
		this.studentNUmber = studentNUmber;
	}
	
	public String getCategoryCode() {
		return categoryCode;
	}
	
	
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public File getImageFile() {
		return imageFile;
	}


	public void setImageFile(File imageFile) {
		this.imageFile = imageFile;
	}


	public int getPostId() {
		return postId;
	}


	public void setPostId(int postId) {
		this.postId = postId;
	}


	public Image getProductImage() {
		return productImage;
	}
	public Post(Image productImage) {
//		super();
		this.productImage = productImage;
	}
	public String getTitle() {
		return title;
	}
	public String getDescription() {
		return description;
	}
	
	public String getProductName() {
		return productName;
	}
	public int getUnitPrice() {
		return unitPrice;
	}
	public int getQuantity() {
		return quantity;
	}
	public String getPhone() {
		return phone;
	}
	public LocalDate getPostDate() {
		return postDate;
	}
	public LocalDate getExpireDate() {
		return expireDate;
	}
	
	public int getStudentNUmber() {
		return studentNUmber;
	}
	
	

}


