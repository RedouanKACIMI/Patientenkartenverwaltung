package net.redouan.PatientsCardsManager.repository;

import net.redouan.PatientsCardsManager.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Transactional
    void deletePatientById(Long id);

    Optional<Patient> findPatientById(Long id);

}
