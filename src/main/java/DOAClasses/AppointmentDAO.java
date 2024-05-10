package DOAClasses;

import JavaClasses.Appointment;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;

public class AppointmentDAO {
    private static final Map<Integer, Appointment> appointments = new HashMap<>();
    private static final AppointmentDAO instance = new AppointmentDAO();

    
    public static AppointmentDAO getInstance() {
        return instance;
    }
    

    /**
     * Creates a new appointment
     * @param appointmentId for the appointment.
     * @param date of the appointment.
     * @param time of the appointment.
     * @param associatedParticipants for the appointment.
     * @param doctorId to get the doctor.
     * @param patientId to get the patient.
     * @return Newly Appointment object.
     * @throws IllegalArgumentException to check if input data is invalid or the appointment already exists.
     */
    public synchronized Appointment addAppointment(int appointmentId, String date, String time, String associatedParticipants, int doctorId, int patientId) {
        if (date == null || time == null || associatedParticipants == null) {
            throw new IllegalArgumentException("Appointment data cannot be null");
        }
        if (appointments.containsKey(appointmentId)) {
            throw new IllegalStateException("Appointment already exists with ID: " + appointmentId);
        }
        Appointment appointment = new Appointment(appointmentId, date, time, associatedParticipants, doctorId, patientId);
        appointments.put(appointmentId, appointment);
        return appointment;
    }

    
    /**
     * Retrieves an appointment by appointmentId.
     * @param appointmentId of the appointment to retrieve.
     * @return The Appointment object is returned if found.
     * @throws IllegalArgumentException errors is returned if no appointment is found.
     */
    public synchronized Appointment getAppointmentById(int appointmentId) {
        Appointment appointment = appointments.get(appointmentId);
        if (appointment == null) {
            throw new IllegalArgumentException("No appointment found with ID: " + appointmentId);
        }
        return appointment;
    }
    

    /**
     * Retrieve all appointments.
     * @return collection of all appointments.
     */
    public synchronized Collection<Appointment> getAllAppointments() {
        return appointments.values();
    }

    
    /**
     * Update existing appointment by giving the appointmentId.
     * @param appointmentId of the appointment to update.
     * @param date date for the appointment.
     * @param time time for the appointment.
     * @param associatedParticipants associatedParticipants for the associatedParticipants details.
     * @param doctorId to get the doctor.
     * @param patientId to get the  patient.
     * @return updated Appointment.
     * @throws IllegalArgumentException if no appointment is found.
     */
    public synchronized Appointment updateAppointment(int appointmentId, String date, String time, String associatedParticipants, int doctorId, int patientId) {
        Appointment existingAppointment = appointments.get(appointmentId);
        if (existingAppointment == null) {
            throw new IllegalArgumentException("No appointment found with ID: " + appointmentId);
        }
        Appointment updatedAppointment = new Appointment(appointmentId, date, time, associatedParticipants, doctorId, patientId);
        appointments.put(appointmentId, updatedAppointment);
        return updatedAppointment;
    }
    

    /**
     * Deletes an appointment by appointmentId.
     * @param appointmentId of the appointment to delete.
     * @return deleted Appointment is returned if found.
     */
    public synchronized Appointment deleteAppointment(int appointmentId) {
        Appointment appointment = appointments.remove(appointmentId);
        if (appointment == null) {
            throw new IllegalArgumentException("No appointment found to delete with ID: " + appointmentId);
        }
        return appointment;
    }
}
