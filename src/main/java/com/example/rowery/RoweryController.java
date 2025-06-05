package com.example.rowery;

import com.example.rowery.klasy.Rower;
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
import com.example.rowery.klasy.Wypozyczenie;
import java.util.List;

public class RoweryController {


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
    private TextField modelField;
    @FXML
    private TextField typField;
    @FXML
    private CheckBox dostepnyField;
    @FXML
    private Button dodaj;

    @FXML
    private void dodanieroweru(ActionEvent event) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String model = modelField.getText();
        String typ_roweru = typField.getText();
        if(modelField.getText().isEmpty() || typField.getText().isEmpty())
        {
            errorLabel.setText("Aby dodać rower musisz podac wszystkie dane");
            return;
        }
        Rower nowy_rower = new Rower();
        nowy_rower.setModel(model);
        nowy_rower.setTyp(typ_roweru);
        nowy_rower.setDostepny(dostepnyField.isSelected());



        session.beginTransaction();
        session.persist(nowy_rower);
        session.getTransaction().commit();
        List<Rower> rower = session.createQuery("from Rower").list();
        session.close();
        tablicarower.setItems(FXCollections.observableList(rower));

    }

    @FXML
    private TableView<Rower> tablicarower;
    @FXML
    private TableColumn<Rower, String> model;
    @FXML
    private TableColumn<Rower, String> typ_roweru;
    @FXML
    private TableColumn<Rower, Boolean> dostepny;
    @FXML
    private TableColumn<Rower, String> klient;

    @FXML
    private void initialize() {
        model.setCellValueFactory(new PropertyValueFactory<>("model"));
        typ_roweru.setCellValueFactory(new PropertyValueFactory<>("typ"));
        dostepny.setCellValueFactory(new PropertyValueFactory<>("dostepny"));
        try {
            Session session2 = HibernateUtil.getSessionFactory().openSession();
            session2.beginTransaction();
            List<Rower> rowers = session2.createQuery("from Rower").list();
            tablicarower.setItems(FXCollections.observableList(rowers));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    @FXML
    private Button usunrower;

    //    @FXML
//    private void usunrower(ActionEvent event)
//    {
//        String model=modelField.getText();
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        session.beginTransaction();
//        Query query = session.createQuery("delete from Rower where model=:model");
//        query.setParameter("model", model);
//
//        query.executeUpdate();
//        session.getTransaction().commit();
//        List<Rower> lista = session.createQuery("from Rower").list();
//        session.close();
//        tablicarower.setItems(FXCollections.observableList(lista));
//
//    }
    @FXML
    private Label errorLabel;
    @FXML
    private void usunrower(ActionEvent event) {
        Rower selectedRower = tablicarower.getSelectionModel().getSelectedItem();
        if (selectedRower == null) {
            errorLabel.setText("Nie wybrano roweru do usunięcia");
            System.out.println("Brak wybranego roweru do usunięcia.");
            return;
        }
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Long rentalCount = (Long) session.createQuery("select count(w) from Wypozyczenie w where w.rower.id_roweru = :rowerId")
                .setParameter("rowerId", selectedRower.getId_roweru())
                .uniqueResult();
            Rower rowerToDelete = session.get(Rower.class, selectedRower.getId_roweru());

            if (rowerToDelete != null) {
                session.remove(rowerToDelete);
                session.getTransaction().commit();
                System.out.println("Rower o ID: " + selectedRower.getId_roweru() + " został usunięty.");
            }
        List<Rower> rowers = session.createQuery("from Rower").list();
        tablicarower.setItems(FXCollections.observableList(rowers));

    }
}
