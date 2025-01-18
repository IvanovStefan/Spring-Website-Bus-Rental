/** Clasa pentru utilizatorii rezervari
 * @author Ivanov Stefan
 * @version 12 ianuarie 2025
 */
package com.example.AplicatieBus.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "programare")
public class Programare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDProgramare")
    private int idProgramare;

    private String nume;
    private String prenume;
    private String telefon;
    private String inceput; // Folosește formatul `String` sau `LocalDate` pentru date
    private String sfarsit;
    private String descriere;
    private int kilometri;
    private int idVehicul;
    private int idUser;

    // Getters și Setters
    public int getIdProgramare() {
        return idProgramare;
    }

    public void setIdProgramare(int idProgramare) {
        this.idProgramare = idProgramare;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getInceput() {
        return inceput;
    }

    public void setInceput(String inceput) {
        this.inceput = inceput;
    }

    public String getSfarsit() {
        return sfarsit;
    }

    public void setSfarsit(String sfarsit) {
        this.sfarsit = sfarsit;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public int getKilometri() {
        return kilometri;
    }

    public void setKilometri(int kilometri) {
        this.kilometri = kilometri;
    }

    public int getIdVehicul() {
        return idVehicul;
    }

    public void setIdVehicul(int idVehicul) {
        this.idVehicul = idVehicul;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
