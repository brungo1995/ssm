package admin;

public class Config {
	private String lock;
	private String email;
	private String password;
	private String imageFolder;
	
	public Config(
			String lock, 
			String email, 
			String password, 
			String imageFolder) {
//		super();
		this.lock = "X";
		this.email = email;
		this.password = password;
		this.imageFolder = imageFolder;
	}

	public String getLock() {
		return lock;
	}

	public void setLock(String lock) {
		this.lock = lock;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImageFolder() {
		return imageFolder;
	}

	public void setImageFolder(String imageFolder) {
		this.imageFolder = imageFolder;
	}
	
	


}
