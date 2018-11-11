package student.ads;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

public class PostPane {
	private ImageView image;
	private Label price;
	private TextArea description;
	
	public PostPane(
			ImageView image,
			TextArea description,
			Label price
			) {
//		super();
		this.image = image;
		this.price = price;
		this.description = description;
	}
	
	
	public ImageView getImage() {
		return image;
	}
	public Label getPrice() {
		return price;
	}
	public TextArea getDescription() {
		return description;
	}
	
	
}
