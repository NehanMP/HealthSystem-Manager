package JavaClasses;

public class Person {
	private int id;
	private String name;
	private String contactInfo;
	private String address;
	
	public Person(int id, String name, String contactInfo, String address) {
		this.id = id;
		this.name = name;
		this.contactInfo = contactInfo;
		this.address = address;
	}
	
	// Getters
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getContactInfo() {
		return contactInfo;
	}
	
	public String getAddress() {
		return address;
	}
	
	
	// Setters
	public void setId(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setContactOnfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
}