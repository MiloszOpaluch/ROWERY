package com.example.rowery.klasy;

import jakarta.persistence.*;

@Entity
@Table(name="klienci")
public class klient {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name="id_klienta")
private int id_klienta;
@Column(name="imie")
private String imie;
@Column(name = "nazwisko", nullable = false)
private String nazwisko;
@Column(name = "login", nullable = false, unique = true)
private String login;
@Column(name ="haslo", nullable = false)
private String haslo;
public klient()
{

}

    public klient(String imie, String nazwisko, String login, String haslo) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.login = login;
        this.haslo = haslo;
    }

    public int getId_klienta() {
        return id_klienta;
    }

    public void setId_klienta(int id_klienta) {
        this.id_klienta = id_klienta;
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


    @Override
    public String toString() {
        return getImie()+" "+getNazwisko();
    }
}
