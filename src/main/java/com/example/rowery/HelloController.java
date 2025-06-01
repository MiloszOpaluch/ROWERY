package com.example.rowery;

import com.example.rowery.klasy.klient;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.Query;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.sqm.spi.DelegatingSqmSelectionQueryImplementor;

public class HelloController {

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label errorLabel;

    private final String adminLogin = "admin";
    private final String adminPassword = "admin";




    @FXML
    private void handleLoginButtonAction(ActionEvent event)
    {
        String login=loginField.getText().trim();
        String haslo=passwordField.getText().trim();

        if(login.equals("admin") && haslo.equals("admin"))
        {
            przejdzDoPanelu("admin.fxml","Admin");
            return;
        }
        try (Session sesssion = HibernateUtil.getSessionFactory().openSession()){
            sesssion.beginTransaction();
            Query query = sesssion.createQuery("From klient where login=:login", klient.class);
            query.setParameter("login", login);
            klient foundKlient = null;
            foundKlient = (klient) query.getSingleResult();
            if (foundKlient.getHaslo().equals(haslo)) {
                sesssion.getTransaction().commit();
                przejdzDoPanelu("klient.fxml", "Klient", foundKlient);

            }
        }catch (Exception e)
        {
            errorLabel.setText("Hasło lub login jest niepoprawne");
            e.printStackTrace();
        }
    }
@FXML
public void initialize() {
    errorLabel.setText("");
}
    private void przejdzDoPanelu(String fxmlNazwa, String tytulOkna, klient zalogowanyKlient) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlNazwa));
            Parent root = loader.load();

            // Przekazujemy klienta do KlientController
            KlientController klientController = loader.getController();
            klientController.zalogownyklient(zalogowanyKlient);

            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(tytulOkna);
        } catch (Exception e) {
            errorLabel.setText("Nie udało się załadować " + fxmlNazwa);
            e.printStackTrace();
        }
    }

    private void przejdzDoPanelu(String fxmlNazwa, String tytulOkna) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlNazwa));
            Parent root = loader.load();

            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(tytulOkna);
        } catch (Exception e) {
            errorLabel.setText("Nie udało się załadować " + fxmlNazwa);
            e.printStackTrace();
        }
    }
}
