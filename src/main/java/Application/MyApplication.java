/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Application;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import ResourceClasses.AppointmentResource;
import ResourceClasses.BillingResource;
import ResourceClasses.DoctorResource;
import ResourceClasses.MedicalRecordResource;
import ResourceClasses.PatientResource;
import ResourceClasses.PrescriptionResource;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("api")
public class MyApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(DoctorResource.class);
        classes.add(PatientResource.class);
        classes.add(AppointmentResource.class);
        classes.add(MedicalRecordResource.class);
        classes.add(PrescriptionResource.class);
        classes.add(BillingResource.class);
        return classes;
    }
}