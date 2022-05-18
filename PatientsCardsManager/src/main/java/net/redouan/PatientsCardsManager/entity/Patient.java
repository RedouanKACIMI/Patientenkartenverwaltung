package net.redouan.PatientsCardsManager.entity;


import javax.persistence.*;
import java.sql.Date;

/**
 * Model Class of a Patient or patientCard
 */
@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    private String vorname;

    private Date geburtsdatum;

    @Column(nullable = false, updatable = false)
    private String versichertennummer;

    private String kassenname;

    private Long ik;

    private Date ablaufdatum;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public Date getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(Date geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public String getVersichertennummer() {
        return versichertennummer;
    }

    public void setVersichertennummer(String versichertennummer) {
        this.versichertennummer = versichertennummer;
    }

    public String getKassenname() {
        return kassenname;
    }

    public void setKassenname(String kassenname) {
        this.kassenname = kassenname;
    }

    public Long getIK() {
        return ik;
    }

    public void setIK(Long ik) { this.ik = ik; }

    public Date getAblaufdatum() { return ablaufdatum; }

    public void setAblaufdatum(Date ablaufdatum) { this.ablaufdatum = ablaufdatum; }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

}