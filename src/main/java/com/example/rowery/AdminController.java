package com.example.rowery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class AdminController {
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
