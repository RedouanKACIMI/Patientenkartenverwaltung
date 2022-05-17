package net.redouan.PatientsCardsManager.controller;


import net.redouan.PatientsCardsManager.entity.Patient;
import net.redouan.PatientsCardsManager.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller Class to process REST requests.
 * Where all REST API functions are mapped to call a corresponding function in the PatientService class
 */
@RestController
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("")
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientService.showAllPatients();
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable("id") Long id) {
        Patient patient = patientService.showPatient(id);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Patient> postPatient(@RequestBody Patient patient) {
        Patient newPatient  = patientService.addPatient(patient);
        return new ResponseEntity<>(newPatient, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Patient> putPatient(@RequestBody Patient patient) {
        Patient updatePatient  = patientService.updatePatient(patient);
        return new ResponseEntity<>(updatePatient, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable("id") Long id) {
        patientService.deletePatient(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

