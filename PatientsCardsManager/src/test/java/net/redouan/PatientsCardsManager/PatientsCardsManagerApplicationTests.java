package net.redouan.PatientsCardsManager;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import net.redouan.PatientsCardsManager.entity.Patient;
import net.redouan.PatientsCardsManager.repository.PatientRepository;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;


import java.io.IOException;
import java.sql.Date;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class PatientsCardsManagerApplicationTests {

	@Autowired
	private PatientRepository patientRepository;

	private JSONObject patientJson;
	private HttpHeaders headers;
	private ObjectMapper objectMapper;

	@BeforeEach
	private void initializeDB() {
		Patient patient = new Patient();
		patient.setName("Musterman");
		patient.setVorname("Max");
		patient.setIK(108310400L);
		patient.setKassenname("AOK Bayern");
		patient.setVersichertennummer("U123456789");
		patient.setGeburtsdatum(Date.valueOf("1998-01-01"));
		patient.setAblaufdatum(Date.valueOf("2022-12-31"));

		patientRepository.save(patient);
	}

	@BeforeEach
	private void initialisePatientJson() {
		patientJson = new JSONObject();
		headers = new HttpHeaders();
		objectMapper = new ObjectMapper();
		headers.setContentType(MediaType.APPLICATION_JSON);
		patientJson.put("name", "Krankerman");
		patientJson.put("vorname", "Karl");
		patientJson.put("versichertennummer", "U123456789");
		patientJson.put("kassenname", "R+V BKK");
		patientJson.put("geburtsdatum", "1998-04-03");
		patientJson.put("ablaufdatum", "2023-12-10");
		patientJson.put("ik", 105823040L);
	}

	@AfterEach
	public void resetDB() {
		patientRepository.deleteAllInBatch();
	}


	//++++++++++ Test-Area ++++++++++//

	/**
	 * getting All elements as an Array
	 */
	@Test
	void testGetAll() {
		TestRestTemplate restTemplate = new TestRestTemplate();

		ResponseEntity<Patient[]> responseEntity
				= restTemplate.getForEntity("http://localhost:8080/patient", Patient[].class);

		Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assertions.assertNotNull(responseEntity.getBody());
		Assertions.assertEquals(1, responseEntity.getBody().length);
	}

	/**
	 * getting only one element
	 */
	@Test
	void testGetById() {
		TestRestTemplate restTemplate = new TestRestTemplate();

		//get an id of an existing element
		Long testId = patientRepository.findAll().get(0).getId();
		ResponseEntity<Patient> responseEntity
				= restTemplate.getForEntity("http://localhost:8080/patient/search/99", Patient.class);

		Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);

		responseEntity
				= restTemplate.getForEntity("http://localhost:8080/patient/search/"+testId, Patient.class);

		Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assertions.assertNotNull(responseEntity.getBody());
		Assertions.assertEquals("U123456789", responseEntity.getBody().getVersichertennummer());
		Assertions.assertEquals(108310400, responseEntity.getBody().getIK());
		Assertions.assertEquals(testId, responseEntity.getBody().getId());
	}

	/**
	 * adding an element
	 * @throws IOException in case objectMapper couldn't readTree
	 */
	@Test
	void testPost() throws IOException {
		TestRestTemplate restTemplate = new TestRestTemplate();

		HttpEntity<String> request
				= new HttpEntity<>(patientJson.toString(), headers);

		String postResult =
				restTemplate.postForObject("http://localhost:8080/patient/add", request, String.class);
		JsonNode postedPatient = objectMapper.readTree(postResult);

		Assertions.assertNotNull(postResult);
		Assertions.assertNotNull(postedPatient);
		Assertions.assertNotNull(postedPatient.path("name").asText());
		Assertions.assertEquals(patientJson.getAsNumber("ik"), postedPatient.path("ik").asLong());
	}

	/**
	 * updating an element
	 */
	@Test
	void testPUT(){
		TestRestTemplate restTemplate = new TestRestTemplate();

		patientJson.put("id", 1L);

		HttpEntity<String> request
				= new HttpEntity<>(patientJson.toString(), headers);

		restTemplate.put("http://localhost:8080/patient/update", request);

		Assertions.assertNotNull(patientRepository.findPatientById(1L));
		Patient patient1 = patientRepository.findPatientById(1L).orElseThrow();
		Assertions.assertEquals(patientJson.getAsString("name"), patient1.getName());
		Assertions.assertEquals(patientJson.getAsString("versichertennummer"), patient1.getVersichertennummer());
		Assertions.assertEquals(patientJson.getAsNumber("ik"), patient1.getIK());
	}

	/**
	 * deleting an element
	 */
	@Test
	void testDelete() {
		TestRestTemplate restTemplate = new TestRestTemplate();
		restTemplate.delete("http://localhost:8080/patient/delete/1");
		Assertions.assertEquals(Optional.empty(), patientRepository.findPatientById(1L));
	}
}
