package student.account;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Cart {
	private final SimpleStringProperty product;
	private final SimpleIntegerProperty unitPrice;
	private final SimpleIntegerProperty cartId;
	private final SimpleIntegerProperty sellor;
	private final SimpleIntegerProperty postId;
	private final SimpleStringProperty isAvailable;
	
	public Cart(
			String product,
			Integer unitPrice,
			Integer sellor,
			Integer postId,
			Integer cartId,
			String isAvailable) {
//		super();
		this.product =new SimpleStringProperty( product);
		this.unitPrice = new SimpleIntegerProperty(unitPrice);
		this.sellor = new SimpleIntegerProperty(sellor);
		this.postId =new SimpleIntegerProperty(postId);
		this.isAvailable = new SimpleStringProperty( isAvailable);
		this.cartId = new SimpleIntegerProperty(cartId);
	}

	public String getIsAvailable(){
		return  isAvailable.get();
	}
	public String getProduct() {
		return product.get();
	}
	
	public int getCartId(){
		return cartId.get();
	}

	public int getUnitPrice() {
		return unitPrice.get();
	}

	public Integer getSellor() {
		return sellor.get();
	}

	public Integer getPostId() {
		return postId.get();
	}
	
	
	
	

}
