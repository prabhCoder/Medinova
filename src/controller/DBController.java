package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import model.Appointment;

import com.mysql.jdbc.PreparedStatement;

@ManagedBean(name="dBController")
@SessionScoped
public class DBController {
	private Integer patientId;
	private String patientName;
	private String pUsername;
	private String pPassword;
	private String email;
	private String phone;
	private Date birthDate;
	private Date appointmentDate;
	private Date date;
	private String deptId;
	private String subject;
	private String note;
	private String dUsername;
	private String dPassword;
	private int docId;
	private String docName;
	private String status;
	private ArrayList<Appointment> appointments;
	
	public String doLogout(){
		System.out.println("Logout");
		  FacesContext facesContext = FacesContext.getCurrentInstance();
		  HttpSession httpSession = (HttpSession)facesContext.getExternalContext().getSession(false);
		  httpSession.invalidate();
		  return "/login.xhtml?faces-redirect=true";
	}
	
	public String patientAuthentication(){
		
		  ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		     pUsername = ec.getRequestParameterMap().get("formId:username");
		     pPassword = ec.getRequestParameterMap().get("formId:password");
		    System.out.println("name is ;"+pUsername);
		System.out.println("checking.......");
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/medinova","root", "12345");
			Statement stat=con.createStatement();
			ResultSet set=stat.executeQuery("select * from patient");
			while(set.next()){
				patientId=set.getInt(1);
				patientName=set.getString(2);
				if(pUsername.equals(set.getString(3)) && pPassword.equals(set.getString(4))){
					
					System.out.println("Authenticated!!!!!!!!   Username: "+set.getString(3)+", Password: "+set.getString(4));
					return  "/patient/patientLoginSuccess.xhtml?faces-redirect=true&username=" + pUsername;
					
				}else{
					System.out.println("Authentication error!!!");
					return null;
				}	
			}			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
			
	}

	public String doctorAuthentication(){
		
		  ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		     dUsername = ec.getRequestParameterMap().get("formId1:username");
		     dPassword = ec.getRequestParameterMap().get("formId1:password");
		    System.out.println("name is ;"+dUsername);
		System.out.println("checking.......");
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/medinova","root", "12345");
			Statement stat=con.createStatement();
			ResultSet set=stat.executeQuery("select * from doctor");
			while(set.next()){
				docId=set.getInt(1);
				docName=set.getString(2);
				if(dUsername.equals(set.getString(3)) && dPassword.equals(set.getString(4))){
					
					System.out.println("Authenticated!!!!!!!!   Username: "+set.getString(3)+", Password: "+set.getString(4));
					return  "/doctor/doctorLoginSuccess.xhtml?faces-redirect=true&username=" + dUsername;
					
				}else{
					System.out.println("Authentication error!!!");
					return null;
				}	
			}			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
			
	}

	
	public String submitForm(){
		String appStatus="Pending";
			System.out.println("Inserting.."+birthDate+","+appointmentDate+","+patientId+","+email+","+subject);
		
			 DateFormat df = new SimpleDateFormat("MM/dd/yyyy  HH:mm:ss a");       
			String strbirthDate = df.format(birthDate);
			String strappDate = df.format(appointmentDate);
			System.out.println("string date: "+strbirthDate);
			
			 SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a"); // your template here
			 java.util.Date formattedBirthDate = null;
			try {
				formattedBirthDate = formatter.parse(strbirthDate);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 java.sql.Date sqlBirthDate = new java.sql.Date(formattedBirthDate.getTime());
			 
			 java.util.Date formattedAppDate = null;
			try {
				formattedAppDate = formatter.parse(strappDate);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 java.sql.Date sqlAppDate = new java.sql.Date(formattedAppDate.getTime());
			
		    System.out.println("Values:"+patientId+","+sqlBirthDate+","+sqlAppDate);
		    Connection con = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			 con=DriverManager.getConnection("jdbc:mysql://localhost:3306/medinova","root", "12345");
			 Statement stat=con.createStatement();
			 con.setAutoCommit(true);
			 stat = con.createStatement();
			 String sql = "INSERT INTO appointments " + "VALUES ('"+patientId+"','"+patientName+"','"+email+"','"+sqlBirthDate+"','"+phone+"','"+sqlAppDate+"','"+deptId+"','"+subject+"','"+note+"','"+appStatus+"')";
		     stat.executeUpdate(sql);
			      
			return "/patient/patientLoginSuccess.xhtml?faces-redirect=true";	
			
		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
		    try
		    {
		        con.close();
		        System.out.println("Connection closed");
		    }
		    catch(SQLException e)
		    {
		        System.err.println(e);
		        System.exit(1);
		    }

		 
		}
		return null;
			
		}

	public String searchAppointments(){
		
		 DateFormat df = new SimpleDateFormat("MM/dd/yyyy  HH:mm:ss a");       
			String appDate = df.format(date);
			
			System.out.println("string date: "+appDate);
			
			 SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a"); // your template here
			 java.util.Date formattedDate = null;
			try {
				formattedDate = formatter.parse(appDate);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 java.sql.Date sqlDate = new java.sql.Date(formattedDate.getTime());
			 
		System.out.println("date:"+sqlDate+","+patientId);
		
		try{
		Class.forName("com.mysql.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/medinova","root", "12345");
		//Statement stat=con.createStatement();
		String query = "SELECT * FROM appointments WHERE patientId=? and appointment_date=?";
		PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);

		ps.setInt(1, patientId);
		ps.setDate(2, sqlDate);

		ResultSet set = ps.executeQuery();
		
		appointments=new ArrayList<Appointment>();
		Format formater = new SimpleDateFormat("dd/MM/yyyy");
		
		while(set.next()){
			Appointment obj=new Appointment();
			
					String appointmntDate = formater.format(set.getDate(6));
					String birthDate = formater.format(set.getDate(4));
					obj.setId(set.getInt(1));
					obj.setName(set.getString(2));
					obj.setEmail(set.getString(3));
					obj.setPhone(set.getString(5));
					obj.setDept(set.getString(7));
					obj.setSubject(set.getString(8));
					obj.setNote(set.getString(9));
					obj.setBirthDate(birthDate);
					obj.setAppDate(appointmntDate);
					obj.setStatus(set.getString(10));
					appointments.add(obj);  	
		          
		}
			System.out.println("appointment status:"+status);
			
		return "/doctor/editAppointment.xhtml?faces-redirect=true";						
	}catch(Exception e){
		e.printStackTrace();
	}
	return null;
	}
	
	public String editAppointmentStatus(){
		  ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		String s= ec.getRequestParameterMap().get("form:sid");
		System.out.println("statius from form: "+s);
		 DateFormat df = new SimpleDateFormat("MM/dd/yyyy  HH:mm:ss a");       
			String appDate = df.format(date);
			
			System.out.println("string date: "+appDate);
			
			 SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a"); // your template here
			 java.util.Date formattedDate = null;
			try {
				formattedDate = formatter.parse(appDate);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 java.sql.Date sqlDate = new java.sql.Date(formattedDate.getTime());
			 
		System.out.println("status:"+status);		 
		 Connection con = null;
		 try{
			 
				Class.forName("com.mysql.jdbc.Driver");
				 con=DriverManager.getConnection("jdbc:mysql://localhost:3306/medinova","root", "12345");
				Statement stat=con.createStatement();
				 con.setAutoCommit(true);
				 stat = con.createStatement();
				 
			     String sql = "UPDATE appointments " +
			                  "SET status = '"+status+"' Where patientId="+patientId+" and appointment_date='"+sqlDate+"'";
			     stat.executeUpdate(sql);
			     return "/doctor/doctorLoginSuccess.xhtml?faces-redirect=true";		
			    
			}catch(Exception e){
				e.printStackTrace();
			}
		 return null;
	}	

	

	public Integer getPatientId() {
		return patientId;
	}

	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}

	public String getPatientName() {
		return patientName;
	}


	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}


	public String getpUsername() {
		return pUsername;
	}


	public void setpUsername(String pUsername) {
		this.pUsername = pUsername;
	}


	public String getpPassword() {
		return pPassword;
	}


	public void setpPassword(String pPassword) {
		this.pPassword = pPassword;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public Date getBirthDate() {
		return birthDate;
	}


	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}


	public Date getAppointmentDate() {
		return appointmentDate;
	}


	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}


	public String getDeptId() {
		return deptId;
	}


	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getdUsername() {
		return dUsername;
	}

	public void setdUsername(String dUsername) {
		this.dUsername = dUsername;
	}

	public String getdPassword() {
		return dPassword;
	}

	public void setdPassword(String dPassword) {
		this.dPassword = dPassword;
	}

	public int getDocId() {
		return docId;
	}

	public void setDocId(int docId) {
		this.docId = docId;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public ArrayList<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(ArrayList<Appointment> appointments) {
		this.appointments = appointments;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
