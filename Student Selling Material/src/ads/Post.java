package ads;

import java.time.LocalDate;
import java.util.Date;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.image.Image;

public class Post {
	
	private String title;
	private String description;
	
	private String productName;
	private int  unitPrice;
	private int quantity ;
	private String phone;
	private LocalDate postDate;
	private LocalDate expireDate;
	private Image productImage;	
	
		
	public Post(String title, 
			String description, 
			String productName, 
			int unitPrice, 
			int quantity,
			String phone, 
			LocalDate postDate, 
			LocalDate expireDate,
			Image productImage) {
//		super();
		this.title = title;
		this.description = description;
		
		this.productName = productName;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
		this.phone = phone;
		this.postDate = postDate;
		this.expireDate = expireDate;
		this.productImage = productImage;
		
	}
	
	public Image getProductImage() {
		return productImage;
	}
	public Post(Image productImage) {
		super();
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
	
	
	

}


