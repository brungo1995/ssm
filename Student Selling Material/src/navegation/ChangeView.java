package navegation;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ChangeView {
	//navegation variables
    private String viewToGo;
    public boolean blockParent;
    public  boolean fullScreen;
    public Pane parentPane;
    

	public ChangeView(String viewToGo, boolean blockParent, boolean fullScreen,Pane parentPane) {
		super();
		this.viewToGo = viewToGo;
		this.blockParent = blockParent;
		this.fullScreen = fullScreen;
		this.parentPane = parentPane;
	}



	public void changeView(){
    	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    	Scene scene;
    	
    	try {
    		Parent root= FXMLLoader.load(getClass().getResource(viewToGo));
    		
    		if(fullScreen){
    			scene = new Scene(root,dim.getWidth(),dim.getHeight()-69);
    			
    		}else{
    			scene = new Scene(root);
    			
    		}
    		
    		Stage stage = new Stage();
    		if(blockParent){
    			stage.initModality(Modality.APPLICATION_MODAL);
    			
    		}else{
    			Stage closingStage = (Stage)parentPane.getScene().getWindow();
    			closingStage.close();
    		}
    		stage.setScene(scene);
    		stage.show();

    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	
    	
   }
    

}
