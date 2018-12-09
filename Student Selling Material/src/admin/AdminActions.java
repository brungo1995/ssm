package admin;

import java.io.File;

public class AdminActions {
	
	
	public void createImageFolder() {
		// CREATE A FOLDER N THE ROOT TO STORE THE IMAGES
		String directory = "C:/Users/edvaldo/git/ssm/Student Selling Material/images";
		File file =  new File(directory);
		
		 if (! file.exists()){
		        file.mkdir();
		        System.out.println("ceated image folders");
		    }				
	}

}
