package ResourceClasses;

import DOAClasses.DoctorDAO;
import JavaClasses.Doctor;

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

@Path("/doctor")
public class DoctorResource {
    private DoctorDAO doctorDAO = new DoctorDAO();
    private ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = Logger.getLogger(DoctorResource.class.getName());


    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addDoctor(Doctor doctor) {
        try {
            // Add doctor and return success response
            Doctor addedDoctor = doctorDAO.addDoctor(doctor.getId(), doctor.getName(), doctor.getContactInfo(), doctor.getAddress(), doctor.getSpecialization(), doctor.getContactDetails());         
            // Convert doctor to JSON string
            String result = objectMapper.writeValueAsString(addedDoctor); 
            
            logger.info("Doctor created with ID: " + doctor.getId());
            return Response.status(Response.Status.CREATED).entity(result).build();
            
        } catch (IllegalArgumentException e) {
            // Display error message if doctor already exists
            logger.warning("Doctor already exists (Id): " + doctor.getId());
            return Response.status(Response.Status.CONFLICT).entity("Doctor with this ID already exists.").build();
            
        } catch (Exception e) {
            // Handle exception errors
            logger.severe("Error adding doctor: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error processing request.").build();
        }
    }
    
    
    @GET
    @Path("/doctors")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDoctors() {
        try {
        	// Retrieve all doctors
            Collection<Doctor> doctors = doctorDAO.getAllDoctors(); 
            // Convert doctors list to JSON string
            String result = objectMapper.writeValueAsString(doctors); 
            
            logger.info("Retrieved all doctors.");
            return Response.ok(result).build();
            
        } catch (Exception e) {
            // Handle JSON processing errors
            logger.severe("Error processing JSON for all doctors: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error processing JSON.").build();
        }
    }
    

    @GET
    @Path("/{doctorId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDoctorById(@PathParam("doctorId") int doctorId) {
        try {
        	// Retrieve doctor by doctorId
            Doctor doctor = doctorDAO.getDoctorById(doctorId); 
            if (doctor == null) {
                // If no doctor is found, return error message
                logger.warning("Doctor not found with ID: ");
                return Response.status(Response.Status.NOT_FOUND).entity("Doctor with ID " + doctorId + " not found.").build();
            }
         // Convert doctor to JSON string
            String result = objectMapper.writeValueAsString(doctor); 
            logger.info("Doctor retrieved with ID: " + doctorId);
            return Response.ok(result).build();
        } catch (Exception e) {
            // Handle JSON processing error
            logger.severe("Error processing JSON for doctor " + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error processing JSON.").build();
        }
    }
    

    @PATCH
    @Path("/update/{doctorId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDoctor(@PathParam("doctorId") int doctorId, Doctor doctor) {
        try {
            Doctor updatedDoctor = doctorDAO.updateDoctor(doctorId, doctor.getName(), doctor.getContactInfo(), doctor.getAddress(), doctor.getSpecialization(), doctor.getContactDetails()); // Update doctor
            if (updatedDoctor == null) {
                // If doctor not found, return error message
                logger.warning("Doctor with ID: " + doctorId + " does not exist.");
                return Response.status(Response.Status.NOT_FOUND).entity("Doctor with ID " + doctorId + " not found.").build();
            }
            // Convert updated doctor to JSON string
            String result = objectMapper.writeValueAsString(updatedDoctor); 
            logger.info("Doctor updated (Id): " + doctorId);
            return Response.ok(result).build();
            
        } catch (Exception e) {
            // Handle JSON processing errors
            logger.severe("Error updating doctor: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error processing request.").build();
        }
    }
    

    @DELETE
    @Path("/delete/{doctorId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteDoctor(@PathParam("doctorId") int doctorId) {
        try {
            if (doctorDAO.deleteDoctor(doctorId) != null) {
            	// Doctor deleted successfully
                String result = objectMapper.writeValueAsString("Doctor with ID " + doctorId + " was deleted."); 
                return Response.ok().entity(result).build();
            } else {
                // Return doctor not found if id is invalid
                return Response.status(Response.Status.NOT_FOUND).entity("Doctor with ID " + doctorId + " not found.").build();
            }
        } catch (Exception e) {
            // Handle JSON processing errors
            logger.severe("Error processing JSON during deletion: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error processing JSON.").build();
        }
    }
}
