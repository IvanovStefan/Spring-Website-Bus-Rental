/** Clasa pentru vehicule
 * @author Ivanov Stefan
 * @version 12 ianuarie 2025
 */

package com.example.AplicatieBus.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "vehicule")
public class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDVehicul")
    private Integer idVehicul;

    @Column(name = "marca", nullable = false)
    private String marca;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "nr_locuri")
    private Integer nrLocuri;

    @Column(name = "Categorie")
    private Integer categorie;

    @Column(name = "tip")
    private String tip;

    @Column(name = "descriere", length = 500)
    private String descriere;

    @Column(name = "nr_inmatriculare", length = 7)
    private String nrInmatriculare;

    // Getters și Setters
    public Integer getIdVehicul() {
        return idVehicul;
    }

    public void setIdVehicul(Integer idVehicul) {
        this.idVehicul = idVehicul;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getNrLocuri() {
        return nrLocuri;
    }

    public void setNrLocuri(Integer nrLocuri) {
        this.nrLocuri = nrLocuri;
    }

    public Integer getCategorie() {
        return categorie;
    }

    public void setCategorie(Integer categorie) {
        this.categorie = categorie;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public String getNrInmatriculare() {
        return nrInmatriculare;
    }

    public void setNrInmatriculare(String nrInmatriculare) {
        this.nrInmatriculare = nrInmatriculare;
    }

    @Override
    public String toString() {
        return "Vehicul{" +
                "idVehicul=" + idVehicul +
                ", marca='" + marca + '\'' +
                ", model='" + model + '\'' +
                ", nrLocuri=" + nrLocuri +
                ", nrInmatriculare='" + nrInmatriculare + '\'' +
                '}';
    }

}
