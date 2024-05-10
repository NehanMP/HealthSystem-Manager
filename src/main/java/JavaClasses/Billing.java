package JavaClasses;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Billing {
    private int billingId;
    private int patientId;
    private String invoices;
    private String payments;
    private String outstandingBalance;
    private Patient patient;

    @JsonCreator
    public Billing(@JsonProperty("billingId") int billingId,
                   @JsonProperty("patientId") int patientId,
                   @JsonProperty("invoices") String invoices,
                   @JsonProperty("payments") String payments,
                   @JsonProperty("outstandingBalance") String outstandingBalance) {
        this.billingId = billingId;
        this.patientId = patientId;
        this.invoices = invoices;
        this.payments = payments;
        this.outstandingBalance = outstandingBalance;
    }

    // Getters and Setters
    public int getBillingId() {
        return billingId;
    }

    public void setBillingId(int billingId) {
        this.billingId = billingId;
    }

    @JsonIgnore
    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getInvoices() {
        return invoices;
    }

    public void setInvoices(String invoices) {
        this.invoices = invoices;
    }

    public String getPayments() {
        return payments;
    }

    public void setPayments(String payments) {
        this.payments = payments;
    }

    public String getOutstandingBalance() {
        return outstandingBalance;
    }

    public void setOutstandingBalance(String outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
