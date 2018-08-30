package ads;

import java.util.ArrayList;
import java.util.List;

public class Actions {
	
	//Post List
	public static ArrayList<Post> posts = new ArrayList<Post>();	
	//Database calls
	public ArrayList  getCategories(){
		ArrayList<String> list = new ArrayList<>();
		list.add("Stationary");
		list.add("Books");
		list.add("Other");
		return list;
	}
	
	public static void addMorePost(Post post){
		posts.add(post);
	}
	
	public static ArrayList getPosts(){
		return posts;
	}

}
