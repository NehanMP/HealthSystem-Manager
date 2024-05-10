package ResourceClasses;

import DOAClasses.PatientDAO;
import JavaClasses.Patient;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.logging.Logger;

@Path("/patient")
public class PatientResource {
    private PatientDAO patientDAO = new PatientDAO();
    private ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = Logger.getLogger(PatientResource.class.getName());


    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPatient(Patient patient) {
        try {
            // Create new patient
            Patient addedPatient = patientDAO.addPatient(patient.getId(), patient.getName(), patient.getContactInfo(), patient.getAddress(), patient.getMedicalHistory(), patient.getHealthStatus());
            
            // Convert patient to JSON string for response
            String result = objectMapper.writeValueAsString(addedPatient);
            
            logger.info("Patient created successfully with ID: " + patient.getId()); 
            return Response.status(Response.Status.CREATED).entity(result).build();
            
        } catch (IllegalArgumentException e) {
        	
        	// If patient already exist return error message
            logger.warning("Failed to add patient - duplicate ID: " + patient.getId());
            return Response.status(Response.Status.CONFLICT).entity("Patient with this ID already exists.").build();
            
        } catch (Exception e) {
        	// Log JSON processing errors
            logger.severe("Error processing JSON for new patient: " + e.getMessage()); 
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error processing JSON.").build();
        }
    }
    
    
    @GET
    @Path("/patients")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPatients() {
        try {
        	// Retrieve all patients
            Collection<Patient> patients = patientDAO.getAllPatients(); 
         // Convert patients to JSON string
            String result = objectMapper.writeValueAsString(patients); 
            
            logger.info("All patients retrieved successfully.");
            return Response.ok(result).build();
        } catch (Exception e) {
        	
        	// Log JSON processing error
            logger.severe("Error processing JSON for all patients: " + e.getMessage()); 
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error processing JSON.").build();
        }
    }
    

    @GET
    @Path("/{patientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPatientById(@PathParam("patientId") int patientId) {
        try {
        	// Retrieve patient by ID
            Patient patient = patientDAO.getPatientById(patientId); 
            
            // Check if patient is there
            if (patient == null) {
                logger.warning("Patient with ID " + patientId + " not found.");
                return Response.status(Response.Status.NOT_FOUND).entity("Patient with ID " + patientId + " not found.").build();
            }
            // Convert found patient to JSON
            String result = objectMapper.writeValueAsString(patient); 
            logger.info("Patient retrieved successfully with ID: " + patientId);
            return Response.ok(result).build(); // Return the patient
        } catch (Exception e) {
        	// Log JSON processing error
            logger.severe("Error processing JSON for patient with ID " + patientId + ": " + e.getMessage()); 
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error processing JSON.").build();
        }
    }
    

    @PATCH
    @Path("/update/{patientId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePatient(@PathParam("patientId") int patientId, Patient patient) {
        try {
            // Update the patient details
            Patient updatedPatient = patientDAO.updatePatient(patient.getId(), patient.getName(), patient.getContactInfo(), patient.getAddress(), patient.getMedicalHistory(), patient.getHealthStatus());
            // Check if patient is there
            if (updatedPatient == null) {
                logger.warning("No patient found with ID: " + patientId); 
                return Response.status(Response.Status.NOT_FOUND).entity("Patient with ID " + patientId + " not found.").build();
            }
            // Convert updated patient to JSON
            String result = objectMapper.writeValueAsString(updatedPatient);
            logger.info("Patient updated successfully with ID: " + patientId);
            // Return updated patient
            return Response.ok(result).build();
            
        } catch (Exception e) {
            logger.severe("Error processing JSON or updating patient: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error processing request.").build();
        }
    }
    

    @DELETE
    @Path("/delete/{patientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePatient(@PathParam("patientId") int patientId) {
        try {
            // Delete a patient
            if (patientDAO.deletePatient(patientId) != null) {
                String result = objectMapper.writeValueAsString("Patient with ID " + patientId + " was deleted.");
                // Return successfully patient deleted message
                logger.info("Patient deleted successfully with ID: " + patientId);
                return Response.ok().entity(result).build();
            } else {
            	// If no patient is found with that ID
                logger.warning("Attempt to delete non-existent patient with ID: " + patientId);
                return Response.status(Response.Status.NOT_FOUND).entity("Patient with ID " + patientId + " not found.").build();
            }
        } catch (Exception e) {
        	// Log JSON processing error
            logger.severe("Error processing JSON during patient deletion: " + e.getMessage()); 
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error processing JSON.").build();
        }
    }
}
