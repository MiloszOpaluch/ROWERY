package com.example.rowery.klasy;

import jakarta.persistence.*;

@Entity
@Table(name = "wypozyczenia")
public class Wypozyczenie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_wypozyczenia")
    private int idWypozyczenia;

    @ManyToOne
    @JoinColumn(name = "id_klienta", nullable = false)
    private klient klient;

    @ManyToOne
    @JoinColumn(name = "id_roweru", nullable = false)
    private Rower rower;

    @ManyToOne
    @JoinColumn(name = "id_pracownika", nullable = false)
    private pracownicy pracownik;

    public klient getKlient() {
        return klient;
    }

    public void setKlient(klient klient) {
        this.klient = klient;
    }

    public Rower getRower() {
        return rower;
    }

    public void setRower(Rower rower) {
        this.rower = rower;
    }

    public pracownicy getPracownik() {
        return pracownik;
    }

    public void setPracownik(pracownicy pracownik) {
        this.pracownik = pracownik;
    }
}