package JavaClasses;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Appointment {
    private int appointmentId;
    private String date;
    private String time;
    private String associatedParticipants;
    private int doctorId;
    private int patientId;
    private Doctor doctor;
    private Patient patient;
    
    
    // Default constructor
    public Appointment() {}
    

    @JsonCreator
    public Appointment(@JsonProperty("appointmentId") int appointmentId, 
                       @JsonProperty("date") String date, 
                       @JsonProperty("time") String time, 
                       @JsonProperty("associatedParticipants") String associatedParticipants, 
                       @JsonProperty("doctorId") int doctorId, 
                       @JsonProperty("patientId") int patientId) {
        this.appointmentId = appointmentId;
        this.date = date;
        this.time = time;
        this.associatedParticipants = associatedParticipants;
        this.doctorId = doctorId;
        this.patientId = patientId;
    }

    
    public boolean isValid() {
        return !(date == null || time == null || doctorId == 0 || patientId == 0 || associatedParticipants == null || associatedParticipants.isEmpty());
    }
    
    // Getters
    public int getAppointmentId() {
        return appointmentId;
    }
    
    
    public String getDate() {
        return date;
    }
    
    public String getTime() {
        return time;
    }
    

    public String getAssociatedParticipants() {
        return associatedParticipants;
    }

    
    public int getDoctorId() {
        return doctorId;
    }

    

    public int getPatientId() {
        return patientId;
    }
    

    public Doctor getDoctor() {
        return doctor;
    }
    

    public Patient getPatient() {
        return patient;
    }

    
    
    // Setters
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }
    
    
    public void setDate(String date) {
        this.date = date;
    }
    
    
    public void setTime(String time) {
        this.time = time;
    }
    
    
    public void setAssociatedParticipants(String associatedParticipants) {
        this.associatedParticipants = associatedParticipants;
    }
    
    
    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }
    
    
    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }
    
    
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
    
    
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
