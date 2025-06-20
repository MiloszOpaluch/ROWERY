package com.example.rowery;
import com.example.rowery.klasy.Rower;
import com.example.rowery.klasy.pracownicy;
import com.example.rowery.klasy.Wypozyczenie;
import com.example.rowery.klasy.klient;
import jakarta.persistence.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.hibernate.Session;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import org.hibernate.Transaction;

import java.util.List;

public class WypozyczRowerController {

    @FXML private TableView<Rower> rowerTable;
    @FXML private TableColumn<Rower, String> model;
    @FXML private TableColumn<Rower, String> typ;
    @FXML private TableColumn<Rower, Boolean> dostepny;
    @FXML private TableColumn<Rower, String> Klient;

    @FXML
    private Button Cofnij;

    @FXML
    private void cofnij(ActionEvent event)
    {
        try {
            FXMLLoader load = new FXMLLoader(getClass().getResource("klient.fxml"));
            Parent root = load.load();
            KlientController controller = load.getController();
            controller.zalogownyklient(this.zalogowny);
            Stage stage = (Stage) Cofnij.getScene().getWindow();
            stage.setScene(new Scene(root));


        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    @FXML
    private void initialize() {
        model.setCellValueFactory(new PropertyValueFactory<>("model"));
        typ.setCellValueFactory(new PropertyValueFactory<>("typ"));
        dostepny.setCellValueFactory(new PropertyValueFactory<>("dostepny"));
        Klient.setCellValueFactory(new PropertyValueFactory<>("Klient"));
        try (Session session = HibernateUtil.getSessionFactory().openSession();){
            session.beginTransaction();
            List<Rower> rowers = session.createQuery("from Rower").list();
            rowerTable.setItems(FXCollections.observableList(rowers));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private klient zalogowny;
    public void zalogownyklient(klient klient)
    {
        this.zalogowny = klient;
        System.out.println("asd");
    }
    @FXML
    private Button logoutButton;

    @FXML
    private void wylogowanie(ActionEvent event) {
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
    private void wypozyczrower(ActionEvent event)
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
    private void zwrotroweru(ActionEvent event) {
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
    @FXML
    private Label errorLabel;
    @FXML
    private Button wypozyczrower;
    @FXML
    private void wypozycz(ActionEvent event)
    {
        Rower selectedRower =rowerTable.getSelectionModel().getSelectedItem();
        if (selectedRower == null) {
            errorLabel.setText("Nie wybrano roweru do wypożyczenia!");
            System.out.println("Brak wybranego roweru do wypozyczenia.");
            return;
        }
            if (!selectedRower.getDostepny()) {
                errorLabel.setText("Rower który wybrałeś/aś jest niedostępny!");
                System.out.println("Wybrany rower jest już wypożyczony lub nie ma go aktualnie w sklepie ");
                return;
            }

        try(Session session = HibernateUtil.getSessionFactory().openSession())
        {
            Transaction transaction = null;
            try
            {
              transaction = session.beginTransaction();
              Rower rowerdowypozyczenia = session.get(Rower.class,selectedRower.getId_roweru());
              klient ZalogowanyKlient = session.get(klient.class, zalogowny.getId_klienta());
                if (rowerdowypozyczenia != null && rowerdowypozyczenia.getDostepny() && ZalogowanyKlient != null) {

                    rowerdowypozyczenia.setDostepny(false);
                    rowerdowypozyczenia.setKlient(ZalogowanyKlient);

                    session.update(rowerdowypozyczenia);

                    transaction.commit();
                    System.out.println("Rower '" + rowerdowypozyczenia.getModel() + "' został wypożyczony przez zalogowanego klienta: "
                            + ZalogowanyKlient.getImie() + " " + ZalogowanyKlient.getNazwisko());
                    List<Rower> lista = session.createQuery("from Rower").list();
                    rowerTable.setItems(FXCollections.observableList(lista));
                    rowerTable.refresh();
                } else {
                    System.out.println("Błąd: Nie można zrealizować wypożyczenia (brak roweru/klienta lub rower już wypożyczony).");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}







