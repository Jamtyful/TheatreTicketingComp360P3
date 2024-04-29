module edu.ncat.aggies.theatreticketingcomp360p3 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens edu.ncat.aggies.theatreticketingcomp360p3 to javafx.fxml;
    exports edu.ncat.aggies.theatreticketingcomp360p3;
}