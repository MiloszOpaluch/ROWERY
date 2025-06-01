package com.example.rowery;

import com.example.rowery.klasy.klient;
import jakarta.persistence.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.hibernate.Session;
import javafx.scene.control.TextField;

import javafx.scene.control.TableView;
import java.util.List;
import java.util.Queue;

public class ZarzadzanieklientamiController {


    @FXML
    private Button Cofnij;

    @FXML
    private void cofnij(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("admin.fxml"));

            Parent root = loader.load();
            Stage stage = (Stage) Cofnij.getScene().getWindow();
            stage.setScene(new Scene(root));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private TextField imieField;
    @FXML
    private TextField nazwiskoField;
    @FXML
    private TextField loginField;
    @FXML
    private TextField hasloField;
    @FXML
    private Button dodajButton;

    @FXML
    private void dodaj() {
        dodajButton.setOnAction(e -> dodajKlienta());
    }
    @FXML
    private void dodajKlienta() {
        String imie = imieField.getText();
        String nazwisko = nazwiskoField.getText();
        String login = loginField.getText();
        String haslo = hasloField.getText();

        klient nowyKlient = new klient();
        nowyKlient.setImie(imie);
        nowyKlient.setNazwisko(nazwisko);
        nowyKlient.setLogin(login);
        nowyKlient.setHaslo(haslo);

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.persist(nowyKlient);
        session.getTransaction().commit();
        session.close();

        imieField.clear();
        nazwiskoField.clear();
        loginField.clear();
        hasloField.clear();

        Session session2 = HibernateUtil.getSessionFactory().openSession();
        List<klient> klienci = session2.createQuery("from klient").list();
        session2.close();
        tablicaklient.setItems(FXCollections.observableList(klienci));
    }
    @FXML private TableView<klient> tablicaklient;
    @FXML private TableColumn<klient, String> Imie;
    @FXML private TableColumn<klient, String> Nazwisko;
    @FXML private TableColumn<klient, String> Login;
    @FXML private TableColumn<klient, String> Haslo;
    @FXML
    private void initialize() {
        // Powiązanie kolumn z właściwościami klasy klient
        Imie.setCellValueFactory(new PropertyValueFactory<>("imie"));
        Nazwisko.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        Login.setCellValueFactory(new PropertyValueFactory<>("login"));
        Haslo.setCellValueFactory(new PropertyValueFactory<>("haslo"));

        // Pobranie danych z bazy
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<klient> klienci = session.createQuery("from klient", klient.class).list();
        session.close();

        // Ustawienie danych do tabeli
        ObservableList<klient> dane = FXCollections.observableArrayList(klienci);
        tablicaklient.setItems(dane);
    }

    @FXML
    private Button usunButton;
    @FXML
    private void usunKlienta()
    {
        String loginek = loginField.getText();
        Session sessionn = HibernateUtil.getSessionFactory().openSession();
        sessionn.beginTransaction();
        Query query = sessionn.createQuery("Delete from klient where login=:loginlienta");
        query.setParameter("loginlienta", loginek);

        query.executeUpdate();
        sessionn.getTransaction().commit();
        List<klient> klienci = sessionn.createQuery("from klient").list();
        sessionn.close();
        tablicaklient.setItems(FXCollections.observableList(klienci));

    }



}
