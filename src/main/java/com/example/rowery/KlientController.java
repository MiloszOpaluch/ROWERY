package com.example.rowery;

import com.example.rowery.klasy.klient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.hibernate.Session;

public class KlientController {

    private klient zalogowny;
    public void zalogownyklient(klient klient)
    {
        this.zalogowny = klient;
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
}
