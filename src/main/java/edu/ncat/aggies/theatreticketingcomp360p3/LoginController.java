package edu.ncat.aggies.theatreticketingcomp360p3;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.lang.reflect.Constructor;

public class LoginController {
    @FXML
    private TextField nameEntry;

    @FXML
    protected void onLoginClicked() throws IOException {
        if (nameEntry.getText().isEmpty() || nameEntry.getText().isBlank()) return;

        //dependency injection in javafx
        FXMLLoader loader = new FXMLLoader(getClass().getResource("booking-view.fxml"));
        String user = nameEntry.getText();
        loader.setControllerFactory(type -> {
            try {
                // look for constructor taking MyService as a parameter
                for (Constructor<?> c : type.getConstructors()) {
                    if (c.getParameterCount() == 1) {
                        if (c.getParameterTypes()[0]==String.class) {
                            return c.newInstance(user);
                        }
                    }
                }
                // didn't find appropriate constructor, just use default constructor:
                return type.newInstance();
            } catch (Exception exc) {
                throw new RuntimeException(exc);
            }
        });

        nameEntry.getParent().getScene().setRoot(loader.load());

    }
}