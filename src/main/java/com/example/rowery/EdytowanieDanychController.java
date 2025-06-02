package com.example.rowery;

import com.example.rowery.klasy.Rower;
import com.example.rowery.klasy.klient;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.awt.event.ActionEvent;
import java.util.List;

public class EdytowanieDanychController {


    @FXML private TextField loginField;
    @FXML private PasswordField hasloField;
    @FXML private TableView<klient> tabelaDanych;
    @FXML private TableColumn<klient, String> kolumnaImie;
    @FXML private TableColumn<klient, String> kolumnaNazwisko;
    @FXML private TableColumn<klient, String> kolumnaLogin;
    @FXML private TableColumn<klient, String> kolumnaHaslo;
    private klient zalogowny;

    public void zalogownyklient(klient klient) {
        this.zalogowny = klient;
        System.out.println("Test czy wykonuje sie polecienie");
        wyswietl();

    }
    @FXML
    private Button Cofnij;

    @FXML
    private void cofnijj(javafx.event.ActionEvent actionEvent) {
        try {
            FXMLLoader load = new FXMLLoader(getClass().getResource("klient.fxml"));
            Parent root = load.load();
            KlientController controller = load.getController();
            controller.zalogownyklient(this.zalogowny);
            Stage stage = (Stage) Cofnij.getScene().getWindow();
            stage.setScene(new Scene(root));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private Button zapisz;
    @FXML
    private void zapiszZmiany() {
        String nowyLogin = loginField.getText();
        String noweHaslo = hasloField.getText();

        if (!nowyLogin.isEmpty()) {
            zalogowny.setLogin(nowyLogin);
        }

        if (!noweHaslo.isEmpty()) {
            zalogowny.setHaslo(noweHaslo);
        }
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        session.update(zalogowny);

        tabelaDanych.refresh();
        session.getTransaction().commit();


        List<klient> lista = session.createQuery("FROM klient WHERE klient.id_klienta = :klientId")
                .setParameter("klientId", zalogowny.getId_klienta())
                .list();
        tabelaDanych.setItems(FXCollections.observableList(lista));
        tabelaDanych.refresh();
        loginField.clear();
        hasloField.clear();
        session.close();
    }
@FXML
    private void initialize() {
        kolumnaImie.setCellValueFactory(new PropertyValueFactory<>("imie"));
        kolumnaNazwisko.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        kolumnaLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        kolumnaHaslo.setCellValueFactory(new PropertyValueFactory<>("haslo"));

        tabelaDanych.setItems(FXCollections.observableArrayList(zalogowny));
    }

    private void wyswietl() {
        kolumnaImie.setCellValueFactory(new PropertyValueFactory<>("imie"));
        kolumnaNazwisko.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        kolumnaLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        kolumnaHaslo.setCellValueFactory(new PropertyValueFactory<>("haslo"));

        tabelaDanych.setItems(FXCollections.observableArrayList(zalogowny));
    }
}






