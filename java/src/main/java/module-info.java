module tw.riot.twcardselector {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.bytedeco.opencv;
    requires javafx.swing;
    requires system.hook;
    opens tw.riot.twcardselector to javafx.fxml;
    exports tw.riot.twcardselector;
}