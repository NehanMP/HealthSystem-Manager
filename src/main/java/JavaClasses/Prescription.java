package JavaClasses;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Prescription {
    private int prescriptionId;
    private int patientId;
    private String dosage;
    private String instructions;
    private String duration;
    private Patient patient;

    @JsonCreator
    public Prescription(@JsonProperty("prescriptionId") int prescriptionId,
                         @JsonProperty("patientId") int patientId,
                         @JsonProperty("dosage") String dosage,
                         @JsonProperty("instructions") String instructions,
    					 @JsonProperty("duration") String duration) {
        this.setPrescriptionId(prescriptionId);
        this.setPatientId(patientId);
        this.setDosage(dosage);
        this.setInstructions(instructions);
        this.setDuration(duration);
    }

    
    // Getters and Setters    
    public int getPrescriptionId() {
            return prescriptionId;
    }

    public void setPrescriptionId(int prescriptionId) {
            this.prescriptionId = prescriptionId;
    }

    
    @JsonIgnore
    public int getPatientId() {
            return patientId;
    }

    public void setPatientId(int patientId) {
            this.patientId = patientId;
    }

    
    public String getDosage() {
            return dosage;
    }

    public void setDosage(String dosage) {
            this.dosage = dosage;
    }


    public String getInstructions() {
            return instructions;
    }

    public void setInstructions(String instructions) {
            this.instructions = instructions;
    }


    public String getDuration() {
            return duration;
    }

    public void setDuration(String duration) {
            this.duration = duration;
    }


    public Patient getPatient() {
            return patient;
    }

    public void setPatient(Patient patient) {
            this.patient = patient;
    }
}
