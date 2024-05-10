package DOAClasses;

import JavaClasses.Patient;

import java.util.HashMap;
import java.util.Map;
import java.util.Collection;

public class PatientDAO {
    private static final Map<Integer, Patient> patients = new HashMap<>();

    // CREATE Patient
    public Patient addPatient(int patientId, String name, String contactInfo, String address, String medicalHistory, String healthStatus) {
        if (patients.containsKey(patientId)) {
            throw new IllegalArgumentException("Patient with ID " + patientId + " already exists.");
        }
        Patient patient = new Patient(patientId, name, contactInfo, address, medicalHistory, healthStatus);
        patients.put(patientId, patient);
        return patient;
    }
    

    // READ by patient's ID
    public Patient getPatientById(int patientId) {
        return patients.get(patientId);
    }

    
    // READ all Patients
    public Collection<Patient> getAllPatients() {
        return patients.values();
    }

    
    // UPDATE Patient
    public Patient updatePatient(int patientId, String name, String contactInfo, String address, String medicalHistory, String healthStatus) {
        if (!patients.containsKey(patientId)) {
            throw new IllegalArgumentException("No patient found with ID: " + patientId);
        }
        Patient patient = new Patient(patientId, name, contactInfo, address, medicalHistory, healthStatus);
        patients.put(patientId, patient);
        return patient;
    }

    
    // DELETE Patient
    public Patient deletePatient(int patientId) {
        if (!patients.containsKey(patientId)) {
            throw new IllegalArgumentException("No patient found with ID: " + patientId);
        }
        return patients.remove(patientId);
    }
}
