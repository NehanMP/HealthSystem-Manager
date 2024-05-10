package ResourceClasses;

import DOAClasses.MedicalRecordDAO;
import DOAClasses.PatientDAO;
import JavaClasses.MedicalRecord;
import JavaClasses.Patient;

import java.util.Collection;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/medicalRecord")
public class MedicalRecordResource {
    private MedicalRecordDAO medicalRecordDAO = new MedicalRecordDAO();
    private PatientDAO patientDAO = new PatientDAO();
    private ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = Logger.getLogger(MedicalRecordResource.class.getName());


    
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addMedicalRecord(MedicalRecord medicalRecord) {
        try {
        	// Creating a medical Record
            logger.info("Creating medical record for patient ID: " + medicalRecord.getPatientId());
            MedicalRecord newRecord = medicalRecordDAO.addMedicalRecord(
                medicalRecord.getMedicalRecordID(),
                medicalRecord.getPatientId(),
                medicalRecord.getDiagnoses(),
                medicalRecord.getTreatments()
            );

            // Retrieve the linked patient
            Patient patient = patientDAO.getPatientById(medicalRecord.getPatientId());
            if (patient != null) {
            	// Set patient for the medical record
                newRecord.setPatient(patient); 
                
                // Convert the new record to JSON
                String result = objectMapper.writeValueAsString(newRecord); 
                return Response.status(Response.Status.CREATED).entity(result).build();
                
            } else {
            	// Error message if no patient is found
                logger.warning("No patient found with ID: " + medicalRecord.getPatientId());
                return Response.status(Response.Status.BAD_REQUEST).entity("No patient found with ID " + medicalRecord.getPatientId()).build(); 
            }
        } catch (Exception e) {
        	
            logger.severe("Error in creating medical record: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error processing JSON.").build();
        }
    }


    
    @GET
    @Path("/{medicalRecordID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMedicalRecordByMedicalRecordID(@PathParam("medicalRecordID") int medicalRecordID) {
    	
    	// Retrieve a medical record
        MedicalRecord medicalRecord = medicalRecordDAO.getMedicalRecordByMedicalRecordID(medicalRecordID); 
        
        // Display the retrieved medical record
        if (medicalRecord != null) {
            logger.info("Medical record retrieved: ID " + medicalRecordID);
            return Response.ok(medicalRecord).build();
            
        } else {
        	// Error message if medicalRecord not found
            logger.warning("Medical record not found: ID " + medicalRecordID); 
            return Response.status(Response.Status.NOT_FOUND).entity("Medical record not found for ID " + medicalRecordID).build(); 
        }
    }
    
    
    
    @GET
    @Path("/medicalRecords")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMedicalRecords() {
    	
    	// Get all  medical records
        Collection<MedicalRecord> records = medicalRecordDAO.getAllMedicalRecords(); 
        
        // Set patients for each record
        records.forEach(record -> record.setPatient(patientDAO.getPatientById(record.getPatientId()))); 
        try {
        	// Convert all records to JSON
            String result = objectMapper.writeValueAsString(records); 
            logger.info("All medical records retrieved successfully.");
            return Response.ok(result).build();
            
        } catch (Exception e) {
            logger.severe("Error processing JSON for all medical records: " + e.getMessage()); 
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error processing JSON.").build();
        }
    }
    
    
    
    @PATCH
    @Path("/update/{medicalRecordID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateMedicalRecord(@PathParam("medicalRecordID") int medicalRecordID, MedicalRecord medicalRecord) {
        try {
        	// Update a medical Record
            logger.info("Updating medical record ID: " + medicalRecordID); 
            MedicalRecord updatedRecord = medicalRecordDAO.updateMedicalRecord(
                medicalRecordID, medicalRecord.getDiagnoses(), medicalRecord.getTreatments()
            );

            if (updatedRecord != null) {
                return Response.ok(updatedRecord).build();
            } else {
            	// Return error message if medical record not found
                logger.warning("Failed to find medical record to update: ID " + medicalRecordID);
                return Response.status(Response.Status.NOT_FOUND).entity("Medical record not found for ID " + medicalRecordID).build(); 
            }
        } catch (Exception e) {
            logger.severe("Error updating medical record: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error processing request.").build();
        }
    }
    
    
    
    @DELETE
    @Path("/delete/{medicalRecordID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteMedicalRecord(@PathParam("medicalRecordID") int medicalRecordID) {
        try {
        	// Delete a medical record
            logger.info("Deleting medical record ID: " + medicalRecordID);
            MedicalRecord deletedRecord = medicalRecordDAO.deleteMedicalRecord(medicalRecordID);

            if (deletedRecord != null) {
            	// Return success response after removing
                String result = objectMapper.writeValueAsString("Medical record deleted for ID: " + medicalRecordID);
                return Response.ok().entity(result).build(); 
            } else {
            	// Return error message if medical record is not found
                logger.warning("Failed to find medical record to delete: ID " + medicalRecordID); 
                return Response.status(Response.Status.NOT_FOUND).entity("Medical record not found for ID " + medicalRecordID).build();
            }
        } catch (Exception e) {
            logger.severe("Error deleting medical record: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error processing request.").build();
        }
    }
}