package model;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class DocProfile {
	private int docId;
	private String name;
	private String username;
	private String password;
	
	public DocProfile() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getDocId() {
		return docId;
	}
	public void setDocId(int docId) {
		this.docId = docId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
