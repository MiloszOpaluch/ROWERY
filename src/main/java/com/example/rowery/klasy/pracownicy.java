package com.example.rowery.klasy;

import jakarta.persistence.*;
@Entity
@Table(name ="pracownicy")
public class pracownicy {

    @Column(name = "imie", nullable = false)
    private String imie;
    @Column(name = "nazwisko", nullable = false)
    private String nazwisko;
    @Column(name = "login", nullable = false, unique = true)
    private String login;
    @Column(name ="haslo", nullable = false)
    private String haslo;
    @Id
    private int id_pracownika;
public pracownicy()
{

}
    public pracownicy(String imie, String nazwisko, String login, String haslo) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.login = login;
        this.haslo = haslo;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public int getId_pracownika() {
        return id_pracownika;
    }

    public void setId_pracownika(int id_pracownika) {
        this.id_pracownika = id_pracownika;
    }

}
