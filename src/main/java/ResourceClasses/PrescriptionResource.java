package ResourceClasses;

import DOAClasses.PrescriptionDAO;
import DOAClasses.PatientDAO;
import JavaClasses.Prescription;
import JavaClasses.Patient;

import java.util.Collection;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/prescription")
public class PrescriptionResource {
    private PrescriptionDAO prescriptionDAO = new PrescriptionDAO();
    private PatientDAO patientDAO = new PatientDAO();
    private ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = Logger.getLogger(PrescriptionResource.class.getName());

    
    
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPrescription(Prescription prescription) {
        try {        	
        	// Create a new prescription
            logger.info("Creating prescription for patient ID: " + prescription.getPatientId());
            Prescription newRecord = prescriptionDAO.addPrescription(
                prescription.getPrescriptionId(),
                prescription.getPatientId(),
                prescription.getDosage(),
                prescription.getInstructions(),
                prescription.getDuration()
            );

            Patient patient = patientDAO.getPatientById(prescription.getPatientId());
            if (patient != null) {
            	
            	// Link patient to the prescription
                newRecord.setPatient(patient); 
                String result = objectMapper.writeValueAsString(newRecord);
                return Response.status(Response.Status.CREATED).entity(result).build();
            } else {
            	
            	// Display error message if no patient is found
                logger.warning("No patient found with ID: " + prescription.getPatientId()); 
                return Response.status(Response.Status.BAD_REQUEST).entity("No patient found with ID " + prescription.getPatientId()).build();
            }
        } catch (Exception e) {
            logger.severe("Error creating prescription: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Unable to create Prescription.").build();
        }
    }
    
    
    @GET
    @Path("/{prescriptionId}")
    @Produces(MediaType.APPLICATION_JSON)
    
    // Get a specific prescription
    public Response getPrescriptionByPrescriptionId(@PathParam("prescriptionId") int prescriptionId) {
        Prescription prescription = prescriptionDAO.getPrescriptionByPrescriptionId(prescriptionId);
        
        // check if its null
        if (prescription != null) {
            logger.info("Retrieved prescription ID: " + prescriptionId); 
            return Response.ok(prescription).build();
            
        } else {
        	// Display error message 
            logger.warning("Prescription not found for ID: " + prescriptionId); 
            return Response.status(Response.Status.NOT_FOUND).entity("Prescription for prescriptionID " + prescriptionId + " not found.").build();
        }
    }


    
    @GET
    @Path("/prescriptions")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPrescriptions() {
        Collection<Prescription> records = prescriptionDAO.getAllPrescriptions();
        records.forEach(record -> record.setPatient(patientDAO.getPatientById(record.getPatientId())));
        try {
        	
        	// Retrieve all records
            String result = objectMapper.writeValueAsString(records);
            logger.info("Retrieved all prescriptions."); 
            return Response.ok(result).build();
            
        } catch (Exception e) {
            logger.severe("Error processing JSON for all prescriptions: " + e.getMessage()); 
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error processing JSON.").build();
        }
    }
    
    
    
    @PATCH
    @Path("/update/{prescriptionId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePrescription(@PathParam("prescriptionId") int prescriptionId, Prescription prescription) {
        try {
        	
        	// Update a prescription
            logger.info("Updating prescription ID: " + prescriptionId); 
            Prescription updatedRecord = prescriptionDAO.updatePrescriptions(
                prescriptionId, 
                prescription.getDosage(), 
                prescription.getInstructions(), 
                prescription.getDuration()
            );

            if (updatedRecord != null) {
                return Response.ok(updatedRecord).build();
            } else {
            	// Display error message if the prescription is not found
                logger.warning("Prescription not found for update ID: " + prescriptionId); 
                return Response.status(Response.Status.NOT_FOUND).entity("Prescription for prescription ID " + prescriptionId + " not found.").build();
            }
        } catch (Exception e) {
        	
        	// Display errors during the update
            logger.severe("Error updating prescription: " + e.getMessage()); 
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error processing request.").build();
        }
    }

    
    
    @DELETE
    @Path("/delete/{prescriptionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePrescription(@PathParam("prescriptionId") int prescriptionId) {
        try {
        	// Delete a prescription
            logger.info("Deleting prescription ID: " + prescriptionId); 
            Prescription deletedRecord = prescriptionDAO.deletePrescription(prescriptionId);

            if (deletedRecord != null) {
                String result = objectMapper.writeValueAsString("Prescription for patient ID " + prescriptionId + " was deleted.");
                return Response.ok().entity(result).build();
                
            } else {
            	// Display an error message if the prescription is not found
                logger.warning("Prescription not found for deletion ID: " + prescriptionId); 
                return Response.status(Response.Status.NOT_FOUND).entity("Prescription for patient ID " + prescriptionId + " not found.").build();
            }
        } catch (Exception e) {
        	// Display errors during deletion
            logger.severe("Error deleting prescription: " + e.getMessage()); 
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error processing JSON.").build();
        }
    }
}