package student.account;

public class Basket {

	private String product;
	private int unitPrice;
	private int sellor;
	private int postId;
	private int basketId;
	private int bayer;
	
	
	public Basket(
			int basketId,
			String product,
			int unitPrice,
			int sellor,
			int bayer,
			int postId) {
//		super();
		this.bayer = bayer;
		this.basketId = basketId;
		this.product = product;
		this.unitPrice = unitPrice;
		this.sellor = sellor;
		this.postId = postId;
	}
	public int getBayer() {
		return bayer;
	}
	public void setBayer(int bayer) {
		this.bayer = bayer;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public int getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}
	public int getSellor() {
		return sellor;
	}
	public void setSellor(int sellor) {
		this.sellor = sellor;
	}
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	
	public int getBasketId() {
		return basketId;
	}
	public void setBasketId(int basketId) {
		this.basketId = basketId;
	}
	
	
}
