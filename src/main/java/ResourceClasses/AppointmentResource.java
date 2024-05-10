package ResourceClasses;

import DOAClasses.AppointmentDAO;
import DOAClasses.DoctorDAO;
import DOAClasses.PatientDAO;
import JavaClasses.Appointment;
import JavaClasses.Doctor;
import JavaClasses.Patient;

import java.util.Collection;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.logging.Logger;
import javax.ws.rs.core.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

@Path("/appointment")
public class AppointmentResource {
    private static final Logger logger = Logger.getLogger(AppointmentResource.class.getName());
    private AppointmentDAO appointmentDAO = AppointmentDAO.getInstance();
    private DoctorDAO doctorDAO = new DoctorDAO();
    private PatientDAO patientDAO = new PatientDAO();
    private ObjectMapper objectMapper = new ObjectMapper();

    
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addAppointment(Appointment appointment) {
    	
        if (appointment == null || !appointment.isValid()) {
            // Ensure that appointment object is not null and is valid
        	logger.warning("Invalid appointment data");
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid appointment data").build();
        }
        try {
            // Create new appointment and retrieve doctor and patient details
            Appointment newAppointment = appointmentDAO.addAppointment(appointment.getAppointmentId(), appointment.getDate(), appointment.getTime(), appointment.getAssociatedParticipants(), appointment.getDoctorId(), appointment.getPatientId());
            
            Doctor doctor = doctorDAO.getDoctorById(appointment.getDoctorId());
            Patient patient = patientDAO.getPatientById(appointment.getPatientId());
            
            newAppointment.setDoctor(doctor);
            newAppointment.setPatient(patient);
            
            String result = objectMapper.writeValueAsString(newAppointment);
            logger.info("Appointment created successfully (ID): " + newAppointment.getAppointmentId());
            return Response.status(Response.Status.CREATED).entity(result).build();       
            
        } catch (JsonProcessingException e) {        	
            // JSON processing errors
            logger.severe("Error in creating appointment: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity("Error processing JSON: " + e.getMessage()).build();
            
        } catch (IllegalArgumentException | IllegalStateException e) {        	
            // Handles invalid arguments
            logger.severe("Error in creating appointment: " + e.getMessage());
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        } catch (Exception e) {
            logger.severe("Error in creating appointment: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Unexpected error: " + e.getMessage()).build();
        }
    }

    
    @GET
    @Path("/{appointmentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAppointmentById(@PathParam("appointmentId") int appointmentId) {
        try {
            // Retrieve appointment by appointmentId
            Appointment appointment = appointmentDAO.getAppointmentById(appointmentId);
            
            // Retrieve Doctor and Patient 
            Doctor doctor = doctorDAO.getDoctorById(appointment.getDoctorId());
            Patient patient = patientDAO.getPatientById(appointment.getPatientId());
            
            appointment.setDoctor(doctor);
            appointment.setPatient(patient);
            
            String result = objectMapper.writeValueAsString(appointment);
            return Response.ok(result).build();
            
        } catch (IllegalArgumentException e) {
            // No appointment present
        	logger.warning("Failed to fetch appointment: " + e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity("Appointment with ID " + appointmentId + " not found.").build();
            
        } catch (JsonProcessingException e) {
            // JSON processing errors
        	logger.severe("JSON processing error: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity("Error processing JSON: " + e.getMessage()).build();
        } catch (Exception e) {
        	logger.severe("Error retrieving appointment: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error retrieving appointment: " + e.getMessage()).build();
        }
    }

    
    @GET
    @Path("/appointments")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAppointments() {
        try {
            // Retrieve all appointments
            Collection<Appointment> appointments = appointmentDAO.getAllAppointments();
            appointments.forEach(app -> {
                Doctor doctor = doctorDAO.getDoctorById(app.getDoctorId());
                Patient patient = patientDAO.getPatientById(app.getPatientId());
                app.setDoctor(doctor);
                app.setPatient(patient);
            });
            String result = objectMapper.writeValueAsString(appointments);
            return Response.ok(result).build();
        } catch (JsonProcessingException e) {
            // JSON processing errors
        	logger.severe("JSON processing error: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity("Error processing JSON: " + e.getMessage()).build();
            
        } catch (Exception e) {
        	logger.severe("Error processing appointments: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error processing appointments: " + e.getMessage()).build();
        }
    }

    
    @PATCH
    @Path("/update/{appointmentId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAppointment(@PathParam("appointmentId") int appointmentId, Appointment appointment) {
        if (appointment == null || !appointment.isValid()) {
            // Ensure that the appointment object is not null and is valid
        	logger.warning("Invalid appointment data provided.");
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid appointment data.").build();
        }
        try {
            // Update appointment given by appointmentId
            Appointment updatedAppointment = appointmentDAO.updateAppointment(appointment.getAppointmentId(), appointment.getDate(), appointment.getTime(), appointment.getAssociatedParticipants(), appointment.getDoctorId(), appointment.getPatientId());
            
            // Get Doctor's and Patients details related to the appointmentId
            Doctor doctor = doctorDAO.getDoctorById(updatedAppointment.getDoctorId());
            Patient patient = patientDAO.getPatientById(updatedAppointment.getPatientId());
            updatedAppointment.setDoctor(doctor);
            updatedAppointment.setPatient(patient);
            
            String result = objectMapper.writeValueAsString(updatedAppointment);
            return Response.ok(result).build();
            
        } catch (IllegalArgumentException e) {
            // Handles invalid arguments
        	logger.warning("Failed to update appointment: " + e.getMessage());
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
            
        } catch (JsonProcessingException e) {
            // JSON processing errors
        	logger.severe("JSON processing error: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity("Error processing JSON: " + e.getMessage()).build();
            
        } catch (Exception e) {
        	logger.severe("Error updating appointment: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error updating appointment: " + e.getMessage()).build();
        }
    }
    
    
    @DELETE
    @Path("/delete/{appointmentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAppointment(@PathParam("appointmentId") int appointmentId) {
        try {
            // Delete an appointment
            appointmentDAO.deleteAppointment(appointmentId);
            String result = objectMapper.writeValueAsString("Appointment with ID " + appointmentId + " was deleted.");
            return Response.ok().entity(result).build();
            
        } catch (IllegalArgumentException e) {
            // Error handling if no appointment is found for deletion
        	logger.warning("No appointment found with ID " + appointmentId);
            return Response.status(Response.Status.NOT_FOUND).entity("Appointment with ID " + appointmentId + " not found.").build();
            
        } catch (JsonProcessingException e) {
            // JSON processing errors
        	logger.severe("JSON processing error: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity("Error processing JSON: " + e.getMessage()).build();
            
        } catch (Exception e) {
        	logger.severe("Error deleting appointment: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error deleting appointment: " + e.getMessage()).build();
        }
    }
}
