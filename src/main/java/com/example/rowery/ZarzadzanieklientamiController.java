package com.example.rowery;

import com.example.rowery.klasy.klient;
import jakarta.persistence.Query;
import javafx.animation.PauseTransition;
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
import javafx.util.Duration;
import org.hibernate.Session;

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
    private Label errorLabel;
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

        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();

            List<klient> istnieje = session.createQuery(
                            "FROM klient k WHERE k.login = :login", klient.class)
                    .setParameter("login", login)
                    .getResultList();

            if (!istnieje.isEmpty()) {
                errorLabel.setText("Użytkownik z takim loginem już istnieje");
                PauseTransition pause = new PauseTransition(Duration.seconds(3));
                pause.setOnFinished(event -> errorLabel.setText(""));
                pause.play();
                return;
            }
            if(imieField.getText().isEmpty() || nazwiskoField.getText().isEmpty() || loginField.getText().isEmpty() || hasloField.getText().isEmpty())
            {
                errorLabel.setText("Aby dodać użytkownika musisz uzupełnić wszystkie dane");
                PauseTransition pause = new PauseTransition(Duration.seconds(3));
                pause.setOnFinished(event -> errorLabel.setText(""));
                pause.play();
                return;

            }
            klient nowyKlient = new klient();
            nowyKlient.setImie(imie);
            nowyKlient.setNazwisko(nazwisko);
            nowyKlient.setLogin(login);
            nowyKlient.setHaslo(haslo);

            session.persist(nowyKlient);
            session.getTransaction().commit();

            imieField.clear();
            nazwiskoField.clear();
            loginField.clear();
            hasloField.clear();

            List<klient> klienci = session.createQuery("FROM klient", klient.class).list();
            tablicaklient.setItems(FXCollections.observableList(klienci));

        } catch (Exception e) {
           e.printStackTrace();
            }

    }
    @FXML private TableView<klient> tablicaklient;
    @FXML private TableColumn<klient, String> Imie;
    @FXML private TableColumn<klient, String> Nazwisko;
    @FXML private TableColumn<klient, String> Login;
    @FXML private TableColumn<klient, String> Haslo;
    @FXML
    private void initialize() {
        Imie.setCellValueFactory(new PropertyValueFactory<>("imie"));
        Nazwisko.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        Login.setCellValueFactory(new PropertyValueFactory<>("login"));
        Haslo.setCellValueFactory(new PropertyValueFactory<>("haslo"));

        Session session = HibernateUtil.getSessionFactory().openSession();
        List<klient> klienci = session.createQuery("from klient", klient.class).list();
        session.close();

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
    private Button zarzadzaj;
    @FXML
    private void zarzadzanie(ActionEvent event)
    {
        try {
            FXMLLoader load = new FXMLLoader(getClass().getResource("zarzadzanieklientami.fxml"));
            Parent root = load.load();

            Stage st = (Stage) zarzadzaj.getScene().getWindow();
            st.setScene(new Scene(root));
            st.getTitle();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    private Button row;
    @FXML
    private void rowery(ActionEvent event)
    {
        try{
            FXMLLoader load = new FXMLLoader(getClass().getResource("roweryy.fxml"));
            Parent root = load.load();
            Stage stagee = (Stage) row.getScene().getWindow();
            stagee.setScene(new Scene(root));
            stagee.getTitle();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}
