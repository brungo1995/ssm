package student.ads;

import java.time.LocalDate;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

public class PostProperty {
	private SimpleStringProperty title;
	private SimpleStringProperty description;	
	private SimpleStringProperty productName;
	private SimpleIntegerProperty  unitPrice;
	private SimpleIntegerProperty quantity ;
	private SimpleStringProperty phone;
	private LocalDate postDate;
	private LocalDate expireDate;
	private Image productImage;	
	private SimpleIntegerProperty studentNUmber;
	private SimpleIntegerProperty postId;
	
	
	public PostProperty(
			String title,
			String description,
			String productName,
			int unitPrice,
			int quantity,
			String phone,
			LocalDate postDate,
			LocalDate expireDate,
			Image productImage,
			int studentNUmber,
			int postId) {
//		super();
		this.title =new SimpleStringProperty(title) ;
		this.description =new SimpleStringProperty(description) ;
		this.productName =new SimpleStringProperty(productName) ;
		this.unitPrice = new SimpleIntegerProperty(unitPrice);
		this.quantity = new SimpleIntegerProperty(quantity);
		this.phone = new SimpleStringProperty(phone);
		this.postDate = postDate;
		this.expireDate = expireDate;
		this.productImage = productImage;
		this.studentNUmber = new SimpleIntegerProperty(studentNUmber);
		this.postId = new SimpleIntegerProperty(postId);
	}


	public String getTitle() {
		return title.get();
	}


	public String getDescription() {
		return description.get();
	}


	public String getProductName() {
		return productName.get();
	}


	public int getUnitPrice() {
		return unitPrice.get();
	}


	public int getQuantity() {
		return quantity.get();
	}


	public String getPhone() {
		return phone.get();
	}


	public LocalDate getPostDate() {
		return postDate;
	}


	public LocalDate getExpireDate() {
		return expireDate;
	}


	public Image getProductImage() {
		return productImage;
	}


	public int getStudentNUmber() {
		return studentNUmber.get();
	}


	public int getPostId() {
		return postId.get();
	}
	
	

}
