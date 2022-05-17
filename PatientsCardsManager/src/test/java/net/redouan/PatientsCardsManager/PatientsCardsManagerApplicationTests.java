package net.redouan.PatientsCardsManager;

import net.redouan.PatientsCardsManager.entity.Patient;
import net.redouan.PatientsCardsManager.repository.PatientRepository;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class PatientsCardsManagerApplicationTests {

	@Autowired
	private PatientRepository patientRepository;

	private Patient patient = new Patient();

	@BeforeEach
	private void initializeDB() {
		patient.setName("Musterman");
		patient.setVorname("Max");
		patient.setIK(108310400);
		patient.setKassenname("AOK Bayern");
		patient.setVersichertennummer("U123456789");
		patient.setGeburtsdatum(Date.valueOf("1998-01-01"));
		patient.setAblaufdatum(Date.valueOf("2022-12-31"));

		patientRepository.save(patient);
	}

	@AfterEach
	public void resetDB() {
		patientRepository.deleteAll();
	}


	//++++++++++ Test-Area ++++++++++//
	@Test
	void testGetAll() {
		TestRestTemplate restTemplate = new TestRestTemplate();

		ResponseEntity<Patient[]> responseEntity
				= restTemplate.getForEntity("http://localhost:8080/patient", Patient[].class);

		Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		Assertions.assertNotNull(responseEntity.getBody());
	}

	@Test
	void testGetById() {
		TestRestTemplate restTemplate = new TestRestTemplate();

		ResponseEntity<Patient> responseEntity
				= restTemplate.getForEntity("http://localhost:8080/patient/search/1", Patient.class);

		Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		Assertions.assertNotNull(responseEntity.getBody());
		Assertions.assertEquals(responseEntity.getBody().getVersichertennummer(), "U123456789");
		Assertions.assertEquals(responseEntity.getBody().getIK(), 108310400);
		Assertions.assertEquals(responseEntity.getBody().getId(), 1);
	}

//	@Test
//	void testPost() {
//		TestRestTemplate restTemplate = new TestRestTemplate();
//
//		ResponseEntity<Patient> responseEntity = restTemplate.postForObject();
//
//	}

}
