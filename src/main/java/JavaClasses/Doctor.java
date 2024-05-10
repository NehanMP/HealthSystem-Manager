package JavaClasses;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Doctor extends Person {
    private String specialization;
    private String contactDetails;
    
    @JsonCreator
    public Doctor(@JsonProperty("doctorId") int id, 
                  @JsonProperty("name") String name, 
                  @JsonProperty("contactInfo") String contactInfo, 
                  @JsonProperty("address") String address, 
                  @JsonProperty("specialization") String specialization, 
                  @JsonProperty("contactDetails") String contactDetails) {
        super(id, name, contactInfo, address);
        this.specialization = specialization;
        this.contactDetails = contactDetails;
    }
    
    // Getters
    @JsonProperty("doctorId")
    @Override
    public int getId() {
        return super.getId();
    }
    
    public String getSpecialization() {
        return specialization;
    }
    
    public String getContactDetails() {
        return contactDetails;
    }
    
    
    // Setters
    public void setSpecialization(String specialization){
        this.specialization = specialization;
    }
    
    public void setContactDetails(String contactDetails){
        this.contactDetails = contactDetails;
    }
}