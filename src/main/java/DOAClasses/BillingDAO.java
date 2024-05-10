package DOAClasses;

import JavaClasses.Billing;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;

public class BillingDAO {
    private static BillingDAO instance;
    private static Map<Integer, Billing> billings = new HashMap<>();
    

    /**
     * Access the singleton instance of BillingDAO.
     */
    public static BillingDAO getInstance() {
        if (instance == null) {
        	// Prevent multiple instantiations
            synchronized (BillingDAO.class) { 
                if (instance == null) {
                    instance = new BillingDAO();
                }
            }
        }
        return instance;
    }
    
    

    /**
     * Add a billing record.
     * @throws IllegalArgumentException if billing ID already exists.
     */
    public Billing addBilling(int billingId, int patientId, String invoices, String payments, String outstandingBalance) {
    	
    	// Check if the billing ID already exists
        if (billings.containsKey(billingId)) { 
            throw new IllegalArgumentException("Billing already exists with ID: " + billingId);
        }
        Billing billing = new Billing(billingId, patientId, invoices, payments, outstandingBalance);
        
        // Store the new billing in the map
        billings.put(billingId, billing); 
        return billing;
    }
    
    

    /**
     * Retrieves a billing record by its ID.
     */
    public Billing getBillingByBillingId(int billingId) {
        return billings.get(billingId); // Return the billing if found, or null
    }
    
    

    /**
     * Return all billing records.
     */
    public Collection<Billing> getAllBillings() {
        return billings.values(); // Return all values from the billing map
    }

    
    
    /**
     * Update a billing record.
     * @throws IllegalArgumentException if billing ID is not found.
     */
    public Billing updateBillings(int billingId, String invoices, String payments, String outstandingBalance) {
    	
    	// Get the existing billing
        Billing existingBilling = billings.get(billingId); 
        
        // Check if the billing exists
        if (existingBilling == null) { 
            throw new IllegalArgumentException("No billing found with ID: " + billingId);
        }
        
        // Update invoices
        existingBilling.setInvoices(invoices); 
        
        // Update payments
        existingBilling.setPayments(payments); 
        
        // Update outstanding balance
        existingBilling.setOutstandingBalance(outstandingBalance);
        
     // Return the updated billing
        return existingBilling; 
    }
    
    

    /**
     * Delete a billing by ID.
     * @throws IllegalArgumentException if no billing is found with the given ID.
     */
    public Billing deleteBilling(int billingId) {
    	
    	// Check if the billing exists
        if (!billings.containsKey(billingId)) { 
            throw new IllegalArgumentException("No billing found with ID: " + billingId);
        }
        
        // Remove and return the deleted billing
        return billings.remove(billingId);
    }
}
