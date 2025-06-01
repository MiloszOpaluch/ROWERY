package com.example.rowery.klasy;

import jakarta.persistence.*;

@Entity
@Table(name="rowery")
public class Rower {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id_roweru;

@Column(name="model")
private String model;
@Column(name="typ")
private String typ;
@Column(name="dostepny")
private Boolean dostepny;
@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name = "id_klienta")
private klient klient;
public Rower()
{

}
    public Rower(int id_roweru, String model, String typ, Boolean dostepny) {
        this.id_roweru = id_roweru;
        this.model = model;
        this.typ = typ;
        this.dostepny = dostepny;
    }

    public int getId_roweru() {
        return id_roweru;
    }

    public void setId_roweru(int id_roweru) {
        this.id_roweru = id_roweru;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public Boolean getDostepny() {
        return dostepny;
    }

    public void setDostepny(Boolean dostepny) {
        this.dostepny = dostepny;
    }

    public klient getKlient() {
        return klient;
    }

    public void setKlient(klient klient) {
        this.klient = klient;
    }
}

