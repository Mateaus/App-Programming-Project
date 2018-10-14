package application.model;

import java.time.LocalDate;
import java.util.ArrayList;

import application.model.database.UserLoginResponse;

public class Group {
	private String groupOwner;
	private ArrayList<UserLoginResponse> members = new ArrayList<UserLoginResponse>();
	private String nameOfGroup;
	private String studySubject;
	private String sizeOfGroup;
	private String start;
	private String length;
	private LocalDate studyDate;
	private double x, y;
	
	public Group(String username, String nameOfGroup, String studySubject, String sizeOfGroup, String start, String length, LocalDate studyDate, double x, double y) {
		this.groupOwner = username;
		this.nameOfGroup = nameOfGroup;
		this.studySubject = studySubject;
		this.sizeOfGroup = sizeOfGroup;
		this.start = start;
		this.length = length;
		this.studyDate = studyDate;
		this.x = x;
		this.y = y;
	}
	
	public String getGroupOwner() {
		return this.groupOwner;
	}
	
	public String getNameOfGroup() {
		return this.nameOfGroup;
	}
	
	public String getStudySubject() {
		return this.studySubject;
	}
	
	public String getSizeOfGroup() {
		return this.sizeOfGroup;
	}
	
	public String getStart() {
		return this.start;
	}
	
	public String getLength() {
		return this.length;
	}
	
	public LocalDate getStudyDate() {
		return this.studyDate;
	}
	
	public double getXLocation() {
		return this.x;
	}
	
	public double getYLocation() {
		return this.y;
	}
	
	public void setGroupOwner(String ownerUsername) {
		this.groupOwner = ownerUsername;
	}
	
	public void setNameOfGroup(String name) {
		this.nameOfGroup = name;
	}
	
	public void setStudySubject(String subject) {
		this.studySubject = subject;
	}
	
	public void setSizeOfGroup(String size) {
		this.sizeOfGroup = size;
	}
	
	public void setStart(String startTime) {
		this.start = startTime;
	}
	
	public void setLength(String lengthOfStudy) {
		this.length = lengthOfStudy;
	}
	
	public void setStudyDate(LocalDate date) {
		this.studyDate = date;
	}
	
	public void setXLocation(double xLocation) {
		this.x = xLocation;
	}
	
	public void setYLocation(double yLocation) {
		this.y = yLocation;
	}
	
	public void addMember(UserLoginResponse userLoginResponse) {
		members.add(userLoginResponse);
	}
	
	public String toString() {
		String ret = "";
		
		
		return ret;
	}
}
