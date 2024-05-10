package DOAClasses;

import JavaClasses.Prescription;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;

public class PrescriptionDAO {
	
	private static PrescriptionDAO instance;
    private static Map<Integer, Prescription> prescriptions = new HashMap<>();
    

    public static PrescriptionDAO getInstance() {
        if (instance == null) {
            synchronized (PrescriptionDAO.class) {
                if (instance == null) {
                    instance = new PrescriptionDAO();
                }
            }
        }
        return instance;
    }
    
    
    
    /**
     * Ads a new prescription
     * @throws IllegalArgumentException if prescription  already exists.
     */
    public Prescription addPrescription(int prescriptionId, int patientId, String dosage, String instructions, String duration) {
    	
    	// Check if the prescription already exists
        if (prescriptions.containsKey(prescriptionId)) { 
            throw new IllegalArgumentException("Prescription already exists with ID: " + prescriptionId);
        }
        Prescription prescription = new Prescription(prescriptionId, patientId, dosage, instructions, duration);
        
        // Add the new prescription
        prescriptions.put(prescriptionId, prescription); 
        return prescription;
    }

    
    
    /**
     * Retrieves a prescription.
     */
    public Prescription getPrescriptionByPrescriptionId(int prescriptionId) {
        return prescriptions.get(prescriptionId); // Return the prescription or null if not found
    }

    
    
    /**
     * Returns all prescriptions as a collection which is in the map.
     */
    public Collection<Prescription> getAllPrescriptions() {
        return prescriptions.values(); 
    }

    
    
    /**
     * Update a prescription.
     * @throws IllegalArgumentException if the prescription does not exist.
     */
    public Prescription updatePrescriptions(int prescriptionId, String dosage, String instructions, String duration) {
    	
    	// Get the prescription
        Prescription existingRecord = prescriptions.get(prescriptionId); 
        
        // Check if the prescription exists
        if (existingRecord == null) { 
            throw new IllegalArgumentException("No prescription found with ID: " + prescriptionId);
        }
        
        existingRecord.setDosage(dosage);
        existingRecord.setInstructions(instructions);
        existingRecord.setDuration(duration);
        
        // Return the updated prescription
        return existingRecord; 
    }


    /**
     * Delete a prescription.
     * @throws IllegalArgumentException if the prescription does not exist.
     */
    public Prescription deletePrescription(int prescriptionId) {
    	
    	// Check if the prescription exists
        if (!prescriptions.containsKey(prescriptionId)) { 
            throw new IllegalArgumentException("No prescription found with ID: " + prescriptionId);
        }
        // Remove and return the prescription
        return prescriptions.remove(prescriptionId);
    }
}
