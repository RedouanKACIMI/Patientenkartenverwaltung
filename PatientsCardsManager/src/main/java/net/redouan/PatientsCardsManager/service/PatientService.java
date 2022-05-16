package net.redouan.PatientsCardsManager.service;

import net.redouan.PatientsCardsManager.entity.Patient;
import net.redouan.PatientsCardsManager.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Patient addPatient(Patient patient) {
        //TODO: validate data
        return patientRepository.save(patient);
    }

    public List<Patient> showAllPatients() {
        return patientRepository.findAll();
    }

    public Patient showPatient(Long id) {
        return patientRepository.findPatientById(id)
                .orElse(null);
    }

    public Patient updatePatient(Patient patient) {
        //TODO: validate data
        return patientRepository.save(patient);
    }

    public void deletePatient(Long id) {
        patientRepository.deletePatientById(id);
    }


}

