module tw.riot.twcardselector {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires KeyboardHook;
    requires org.bytedeco.opencv;
    requires javafx.swing;
    opens tw.riot.twcardselector to javafx.fxml;
    exports tw.riot.twcardselector;
}