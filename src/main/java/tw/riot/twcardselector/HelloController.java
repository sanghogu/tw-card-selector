package tw.riot.twcardselector;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Popup;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML private Button on_off;
    @FXML private Button popOpen;
    public KeyboardHook kh;
    private Popup popup;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        popup=new Popup();
        try {
            popup.getContent().add(FXMLLoader.load(getClass().getResource("feedback.fxml")));
        } catch (IOException e) {
            // TODO 자동 생성된 catch 블록
            e.printStackTrace();
        }
        kh=new KeyboardHook();
    }

    public void popHandle(ActionEvent event) {
        popup.setAutoHide(true);
        popup.show(popOpen.getScene().getWindow());
    }

    public void btnHandle(ActionEvent event) {
        if(on_off.getText().equals("켜기")) {
            start();
        }else {
            stop();
        }
    }

    private void start() {
        on_off.setText("끄기");
        kh.setCheck(true);
    }
    private void stop() {
        on_off.setText("켜기");
        kh.setCheck(false);
    }
}