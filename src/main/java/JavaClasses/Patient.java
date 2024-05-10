package JavaClasses;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Patient extends Person {
    private String medicalHistory;
    private String healthStatus;
    
    @JsonCreator
    public Patient(@JsonProperty("patientId") int id, 
                  @JsonProperty("name") String name, 
                  @JsonProperty("contactInfo") String contactInfo, 
                  @JsonProperty("address") String address, 
                  @JsonProperty("medicalHistory") String medicalHistory, 
                  @JsonProperty("healthStatus") String healthStatus) {
        super(id, name, contactInfo, address);
        this.medicalHistory = medicalHistory;
        this.healthStatus = healthStatus;
    }
    
    // Getters
    @JsonProperty("patientId")
    @Override
    public int getId() {
        return super.getId();
    }
    
    public String getMedicalHistory() {
        return medicalHistory;
    }
    
    public String getHealthStatus() {
        return healthStatus;
    }
    
    
    // Setters
    public void setMedicalHistory(String medicalHistory){
        this.medicalHistory = medicalHistory;
    }
    
    public void setHealthStatus(String healthStatus){
        this.healthStatus = healthStatus;
    }
}
