package model;

import java.util.Objects;

public abstract class User {
	protected String email;
	protected String name;
	protected String passWord;
	
	public User(String email, String name, String passWord) {
		this.email = email;
		this.name = name;
		this.passWord = passWord;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	
	/**
	@Override
	public int hashCode() {
		return Objects.hash(email);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(email, other.email);
	}
	**/
	
	
	
	

}
