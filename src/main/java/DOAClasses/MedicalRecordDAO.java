package DOAClasses;

import JavaClasses.MedicalRecord;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;

public class MedicalRecordDAO {
	
	// Instantiate MedicalRecordDAO class
	private static MedicalRecordDAO instance;	
    private static Map<Integer, MedicalRecord> medicalRecords = new HashMap<>();
    
    
    /**
     * Returns the singleton instance of MedicalRecordDAO.
     * Uses double-checked locking for thread safety.
     */
    public static MedicalRecordDAO getInstance() {
        if (instance == null) {
            synchronized (MedicalRecordDAO.class) {
                if (instance == null) {
                    instance = new MedicalRecordDAO();
                }
            }
        }
        return instance;
    }

    
    /**
     * Adds a medical record.
     * @throws IllegalArgumentException if the record already exists.
     */
    public MedicalRecord addMedicalRecord(int medicalRecordID, int patientId, String diagnoses, String treatments) {
        if (medicalRecords.containsKey(medicalRecordID)) {
            throw new IllegalArgumentException("Medical record already exists");
        }
        MedicalRecord medicalRecord = new MedicalRecord(medicalRecordID, patientId, diagnoses, treatments);
        medicalRecords.put(medicalRecordID, medicalRecord);
        return medicalRecord;
    }


    /**
     * Get a medicalRecord by ID
     */
    public MedicalRecord getMedicalRecordByMedicalRecordID(int medicalRecordID) {
    	return medicalRecords.get(medicalRecordID);
    }

    
    /**
     * Get all medicalRecords as a Collection
     */
    public Collection<MedicalRecord> getAllMedicalRecords() {
        return medicalRecords.values();
    }
    
    
    /**
     * Updates an existing medical record.
     * @throws IllegalArgumentException if the medical record does not exist.
     */
    public MedicalRecord updateMedicalRecord(int medicalRecordID, String diagnoses, String treatments) {
        MedicalRecord existingRecord = medicalRecords.get(medicalRecordID);
        if (existingRecord == null) {
            throw new IllegalArgumentException("No medical record found");
        }
        existingRecord.setDiagnoses(diagnoses);
        existingRecord.setTreatments(treatments);
        return existingRecord;
    }


    /**
     * Deletes a medical record.
     * @throws IllegalArgumentException if the medical record does not exist.
     */
    public MedicalRecord deleteMedicalRecord(int medicalRecordID) {
        if (!medicalRecords.containsKey(medicalRecordID)) {
            throw new IllegalArgumentException("No medical record found");
        }
        return medicalRecords.remove(medicalRecordID);
    }
}
