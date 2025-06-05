package com.example.rowery;

import com.example.rowery.klasy.Rower;
import com.example.rowery.klasy.klient;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
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
        tabelaDanych.setItems(FXCollections.observableArrayList(zalogowny));    }
    @FXML
    private Button logoutButton;
    @FXML
    private void wylogowanie(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Logowanie");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Button wypozycz;
    @FXML
    private void wypozyczrower(javafx.event.ActionEvent event)
    {
        try {
            FXMLLoader load = new FXMLLoader(getClass().getResource("wypozycz-rower.fxml"));
            Parent root = load.load();
            WypozyczRowerController wypozyczRowerController = load.getController();
            wypozyczRowerController.zalogownyklient(this.zalogowny);
            Stage stage = (Stage) wypozycz.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Wypozyczanie");

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @FXML
    private Button zwrot;
    @FXML
    private void zwrotroweru(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loaderr = new FXMLLoader(getClass().getResource("Zwroc_rower.fxml"));
            Parent root = loaderr.load();

            ZwrotRoweruController controller = loaderr.getController();
            controller.zalogownyklient(this.zalogowny);

            Stage stage = (Stage) zwrot.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Moje wypożyczone rowery");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @FXML
    private Button edytuj;
    @FXML
    private void edytujDane(ActionEvent event) {
        try {
            FXMLLoader loade = new FXMLLoader(getClass().getResource("EdytowanieDanych.fxml"));
            Parent root = loade.load();

            EdytowanieDanychController controller = loade.getController();
            controller.zalogownyklient(this.zalogowny);

            Stage stage = (Stage) edytuj.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Edytuj dane klienta");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}







