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


    /**
     * responding to a GET Request to get a list of All Patients
     * @return Http responseEntity with the list and status 200
     */
    @GetMapping("")
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientService.showAllPatients();
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }


    /**
     * responding to a GET Request to get a specific Patient
     * @param id of the wanted Patient
     * @return Http responseEntity with a JSON of the Patient and status 200
     */
    @GetMapping("/search/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable("id") Long id) {
        Patient patient = patientService.showPatient(id);
        if (patient == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }


    /**
     * Mapped to the REST Request POST to add a Patient
     * @param patient the element to be added
     * @return the saved element and status 201
     */
    @PostMapping("/add")
    public ResponseEntity<Patient> postPatient(@RequestBody Patient patient) {
        Patient newPatient = patientService.addPatient(patient);
        if (newPatient == null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(newPatient, HttpStatus.CREATED);
    }


    /**
     * Mapped to the REST Request PUT to update if existing or add a Patient
     * @param patient the element to be updated/added
     * @return the updated/saved element and status 200
     */
    @PutMapping("/update")
    public ResponseEntity<Patient> putPatient(@RequestBody Patient patient) {
        Patient updatePatient = patientService.updatePatient(patient);
        return new ResponseEntity<>(updatePatient, HttpStatus.OK);
    }


    /**
     * Mapped to the REST Request DELETE to remove  a Patient
     * @param id of the element to be deleted
     * @return Http sttus 200
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable("id") Long id) {
        patientService.deletePatient(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

