/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Application;

import org.glassfish.jersey.server.ResourceConfig;

import com.fasterxml.jackson.core.util.JacksonFeature;

import ResourceClasses.AppointmentResource;
import ResourceClasses.BillingResource;
import ResourceClasses.DoctorResource;
import ResourceClasses.MedicalRecordResource;
import ResourceClasses.PatientResource;
import ResourceClasses.PrescriptionResource;

public class MyApplicationConfig extends ResourceConfig {
    public MyApplicationConfig() {
        register(DoctorResource.class);
        register(PatientResource.class);
        register(AppointmentResource.class);
        register(MedicalRecordResource.class);
        register(PrescriptionResource.class);
        register(BillingResource.class);
        register(JacksonFeature.class);
    }
}