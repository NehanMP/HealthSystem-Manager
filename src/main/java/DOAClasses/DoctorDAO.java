package DOAClasses;

import JavaClasses.Doctor;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;

public class DoctorDAO {
    private static final Map<Integer, Doctor> doctors = new HashMap<>();

    // CREATE Doctor
    public Doctor addDoctor(int doctorId, String name, String contactInfo, String address, String specialization, String contactDetails) {
    	if (doctors.containsKey(doctorId)) {
            throw new IllegalArgumentException("Doctor with ID " + doctorId + " already exists.");
        }
    	
        Doctor doctor = new Doctor(doctorId, name, contactInfo, address, specialization, contactDetails);
        doctors.put(doctorId, doctor);
        return doctor;
    }

    
    // READ Doctor by ID
    public Doctor getDoctorById(int doctorId) {
        return doctors.get(doctorId);
    }

    
    // READ all doctors as a collection 
    public Collection<Doctor> getAllDoctors() {
        return doctors.values();
    }

    
    // UPDATE
    public Doctor updateDoctor(int doctorId, String name, String contactInfo, String address, String specialization, String contactDetails) {   	
    	// Get specific doctor by Id
    	if (!doctors.containsKey(doctorId)) {
            throw new IllegalArgumentException("No doctor found with ID: " + doctorId);
        }
    	
        Doctor doctor = new Doctor(doctorId, name, contactInfo, address, specialization, contactDetails);
        // Update the doctors HashMap
        doctors.put(doctorId, doctor);    
        return doctor;
    }
    

    // DELETE a Doctor
    public Doctor deleteDoctor(int doctorId) {
    	if (!doctors.containsKey(doctorId)) {
            throw new IllegalArgumentException("No doctor found with ID: " + doctorId);
        }
        return doctors.remove(doctorId);
    }
}
