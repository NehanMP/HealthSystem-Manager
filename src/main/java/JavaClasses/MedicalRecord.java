package JavaClasses;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MedicalRecord {
    private int medicalRecordID;
    private int patientId;
    private String diagnoses;
    private String treatments;
    private Patient patient;

    @JsonCreator
    public MedicalRecord(@JsonProperty("medicalRecordID") int medicalRecordID,
                         @JsonProperty("patientId") int patientId,
                         @JsonProperty("diagnoses") String diagnoses,
                         @JsonProperty("treatments") String treatments) {
        this.medicalRecordID = medicalRecordID;
        this.patientId = patientId;
        this.diagnoses = diagnoses;
        this.treatments = treatments;
    }

    // Getters
    public int getMedicalRecordID() {
        return medicalRecordID;
    }

    
    @JsonIgnore
    public int getPatientId() {
        return patientId;
    }

    
    public String getDiagnoses() {
        return diagnoses;
    }
   

    public String getTreatments() {
        return treatments;
    }

    
    public Patient getPatient() {
        return patient;
    }

    
    // Setters
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    
    
    public void setMedicalRecordID(int medicalRecordID) {
        this.medicalRecordID = medicalRecordID;
    }
    
    
    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }
    
    
    public void setDiagnoses(String diagnoses) {
        this.diagnoses = diagnoses;
    }
    
    
    public void setTreatments(String treatments) {
        this.treatments = treatments;
    }
}
