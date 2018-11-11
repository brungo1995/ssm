package user;

public class User {
	   
       private int studentNumber;
       private String username;
       private String password;
       private String email;
       
	public User(
			int studentNumber,
			String username,
			String password, 
			String email) {
		
		this.studentNumber = studentNumber;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	public User(int studentNumber, String password) {

		this.studentNumber = studentNumber;
		this.password = password;
	}

	
	

	public void setStudentNumber(int studentNumber) {
		this.studentNumber = studentNumber;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public void setEmail(String email) {
		this.email = email;
	}

	public int getStudentNumber() {
		return studentNumber;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}
	
	
       
       

}
