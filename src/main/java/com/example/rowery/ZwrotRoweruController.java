package com.example.rowery;
import com.example.rowery.klasy.klient;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.hibernate.Session;
import javafx.scene.control.TextField;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.hibernate.Transaction;

import java.util.List;

public class ZwrotRoweruController {


    private klient zalogowny;

    public void zalogownyklient(klient klient) {
        this.zalogowny = klient; // Pamietaj ten sout jest po to zeby sprawdzac czy to sie w ogole wykonuje :)
        System.out.println("Test czy wykonuje sie polecienie");

        roweryklienta();
        tabelaRowerow.refresh();
    }
    @FXML
    private void initialize()
    {
        model.setCellValueFactory(new PropertyValueFactory<>("model"));
        typ.setCellValueFactory(new PropertyValueFactory<>("typ"));
        tabelaRowerow.refresh();
    }
    @FXML
    private TableView<Rower> tabelaRowerow;
    @FXML private TableColumn<Rower, String> model;
    @FXML private TableColumn<Rower, String> typ;
    @FXML
    private Button zwrocRowerr;
    @FXML
    private void roweryklienta() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Rower> roweryKlienta = session.createQuery(
                            "FROM Rower WHERE klient.id_klienta = :klientId", Rower.class)
                    .setParameter("klientId", zalogowny.getId_klienta())
                    .list();
            tabelaRowerow.setItems(FXCollections.observableList(roweryKlienta));
            tabelaRowerow.refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void zwrocRower(ActionEvent event) {
        Rower wybranyRower = tabelaRowerow.getSelectionModel().getSelectedItem();

        if (wybranyRower == null) {
            System.out.println("Nie wybrano roweru do zwrotu.");
            return;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Rower rowerDoZwrotu = session.get(Rower.class, wybranyRower.getId_roweru());

            if (rowerDoZwrotu != null && rowerDoZwrotu.getKlient() != null &&
                    rowerDoZwrotu.getKlient().getId_klienta() == zalogowny.getId_klienta()) {

                rowerDoZwrotu.setDostepny(true);
                rowerDoZwrotu.setKlient(null);

                session.update(rowerDoZwrotu);
                transaction.commit();

                System.out.println("Zwrócono rower: " + rowerDoZwrotu.getModel());

                List<Rower> lista = session.createQuery("FROM Rower WHERE klient.id_klienta = :klientId", Rower.class)
                        .setParameter("klientId", zalogowny.getId_klienta())
                        .list();
                tabelaRowerow.setItems(FXCollections.observableList(lista));
                tabelaRowerow.refresh();

            } else {
                System.out.println("Błąd: ten rower nie należy do tego klienta.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Błąd podczas zwrotu roweru.");
        }
    }

    @FXML
    private Button Cofnij;

    @FXML
    private void cofnij(ActionEvent event) {
        try {
            FXMLLoader load = new FXMLLoader(getClass().getResource("klient.fxml"));
            Parent root = load.load();
            Stage stage = (Stage) Cofnij.getScene().getWindow();
            stage.setScene(new Scene(root));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

