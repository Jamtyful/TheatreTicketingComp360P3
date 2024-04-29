package edu.ncat.aggies.theatreticketingcomp360p3;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class BookingController {

    @FXML
    private ListView<MovieShowing> showList;

    @FXML
    private Button reserveButton;

    @FXML
    private Button returnButton;

    @FXML
    private Button backButton;

    //initializes the list with the given dates and times for a showing
    private static final ObservableList<MovieShowing> showings = FXCollections.observableArrayList(
            new MovieShowing(30, new GregorianCalendar(2024, Calendar.APRIL, 20, 13, 0).getTime(), "Kung Fu Panda 4"),
            new MovieShowing(30, new GregorianCalendar(2024, Calendar.APRIL, 20, 20, 0).getTime(), "Kung Fu Panda 4"));

    private final String activeUser;

    public BookingController(String user) {
        activeUser = user;
    }

    @FXML
    public void initialize() {
        reserveButton.setDisable(true);
        returnButton.setDisable(true);

        showList.setItems(showings);

        showList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MovieShowing>() {
            @Override
            public void changed(ObservableValue<? extends MovieShowing> observableValue, MovieShowing showing, MovieShowing t1) {
                reserveButton.setDisable(false);
                returnButton.setDisable(!hasReservation(t1));
            }
        });
    }

    private boolean hasReservation(MovieShowing m) {
        return m.hasReservation(activeUser);
    }

    //Handles the NoSeatAvailable exception by showing an error popup, otherwise a popup displaying seat number and date.
    @FXML
    public void onReserveSeat() {
        MovieShowing showing = showList.getSelectionModel().getSelectedItem();
        try {
            int seatNum = showing.BuyTicket(activeUser);
            showAlert("Purchase Confirmed!\n" + "Seat Number: " + seatNum + "\nTime" + showing.getTime(), Alert.AlertType.INFORMATION);
            returnButton.setDisable(false);
        } catch (MovieShowing.NoSeatAvailableException e) {
            showAlert("Unfortunately all the seats are reserved! You request the ticket at " + LocalDateTime.now(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void onReturnSeat() {
        MovieShowing showing = showList.getSelectionModel().getSelectedItem();
        showing.returnSeat(activeUser);
        showAlert("Seat Returned!", Alert.AlertType.INFORMATION);
        returnButton.setDisable(!hasReservation(showing));
    }

    @FXML
    public void onBack() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
        try {
            backButton.getParent().getScene().setRoot(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void showAlert(String errorText, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setContentText(errorText);
        alert.show();
    }
}
