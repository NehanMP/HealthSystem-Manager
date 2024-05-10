package ResourceClasses;

import DOAClasses.BillingDAO;
import DOAClasses.PatientDAO;
import JavaClasses.Billing;
import JavaClasses.Patient;

import java.util.Collection;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

@Path("/billing")
public class BillingResource {
    private BillingDAO billingDAO = new BillingDAO();
    private PatientDAO patientDAO = new PatientDAO();
    private static final Logger logger = Logger.getLogger(BillingResource.class.getName());

   
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addBilling(Billing billing) {
        try {
        	
        	// Create a new billing
            logger.info("Create a new billing record for patient ID: " + billing.getPatientId());
            Billing newRecord = billingDAO.addBilling(
                billing.getBillingId(),
                billing.getPatientId(),
                billing.getInvoices(),
                billing.getPayments(),
                billing.getOutstandingBalance()
            );

            Patient patient = patientDAO.getPatientById(billing.getPatientId());
            if (patient != null) {
            	// Link patient to the billing record
                newRecord.setPatient(patient); 
                return Response.status(Response.Status.CREATED).entity(newRecord).build();
                
            } else {
            	// Error message if no patient is found
                logger.warning("No patient found with ID: " + billing.getPatientId()); 
                return Response.status(Response.Status.BAD_REQUEST).entity("No patient found with ID " + billing.getPatientId()).build();
            }
        } catch (Exception e) {
            logger.severe("Error creating billing record: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Unable to create billing.").build();
        }
    }

    
    
    @GET
    @Path("/{billingId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBillingByBillingId(@PathParam("billingId") int billingId) {
        Billing billing = billingDAO.getBillingByBillingId(billingId);
        
        if (billing != null) {
            Patient patient = patientDAO.getPatientById(billing.getPatientId());
            
            // Set the patient details in the billing record
            billing.setPatient(patient); 
            
            logger.info("Retrieved billing record for billing ID: " + billingId);
            return Response.ok(billing).build();
            
        } else {
        	// Error message if there is no billing record
            logger.warning("Billing record not found for billing ID: " + billingId); 
            return Response.status(Response.Status.NOT_FOUND).entity("Billing record for billing ID " + billingId + " not found.").build();
        }
    }

    
    
    @GET
    @Path("/billings")
    @Produces(MediaType.APPLICATION_JSON)
    
    // Get all billings
    public Response getAllBillings() {
    	
        Collection<Billing> billings = billingDAO.getAllBillings();
        billings.forEach(billing -> billing.setPatient(patientDAO.getPatientById(billing.getPatientId())));
        
        logger.info("Retrieved all billing records."); 
        return Response.ok(billings).build();
    }

    
    
    @PATCH
    @Path("/update/{billingId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBilling(@PathParam("billingId") int billingId, Billing billing) {
        try {
        	// Update an existing billing
            logger.info("Updating billing record for billing ID: " + billingId); 
            Billing updatedBilling = billingDAO.updateBillings(
                billingId, 
                billing.getInvoices(), 
                billing.getPayments(), 
                billing.getOutstandingBalance()
            );

            if (updatedBilling != null) {
                Patient patient = patientDAO.getPatientById(updatedBilling.getPatientId());
                
                // Update patient details in the billing record
                updatedBilling.setPatient(patient); 
                return Response.ok(updatedBilling).build();
            } else {
            	
            	// Display error message if record does not exist
                logger.warning("Billing record for billing ID " + billingId + " not found."); 
                return Response.status(Response.Status.NOT_FOUND).entity("Billing record for billing ID " + billingId + " not found.").build();
            }
        } catch (Exception e) {
        	
        	// Display errors if there is while updating 
            logger.severe("Error updating billing record: " + e.getMessage()); 
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error processing request.").build();
        }
    }
    
    

    @DELETE
    @Path("/delete/{billingId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBilling(@PathParam("billingId") int billingId) {
        try {
        	
        	// Remove a billing
            Billing deletedBilling = billingDAO.deleteBilling(billingId);
            if (deletedBilling != null) {
                logger.info("Deleted billing record for billing ID: " + billingId); 
                return Response.ok("Billing record for billing ID " + billingId + " was deleted.").build();
            } else {
            	
            	// Display an error message if there is no record to delete
                logger.warning("Billing record for billing ID " + billingId + " not found."); 
                return Response.status(Response.Status.NOT_FOUND).entity("Billing record for billing ID " + billingId + " not found.").build();
            }
        } catch (Exception e) {
        	
        	// Display errors if there is during deletion
            logger.severe("Error deleting billing record: " + e.getMessage()); 
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error processing request.").build();
        }
    }
}
