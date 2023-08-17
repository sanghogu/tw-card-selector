package tw.riot.twcardselector;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Popup;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML private Button on_off;
    @FXML private Button popOpen;
    @FXML private TextField xo;
    @FXML private TextField yo;
    @FXML private TextField width;
    @FXML private TextField height;
    @FXML private Button posApplyBtn;
    @FXML private ImageView captureView;
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
        xo.setText("839");
        yo.setText("982");
        width.setText("40");
        height.setText("41");

        Histogram.getInstance(captureView);

        try {
            captureView.setImage(new Image(new FileInputStream("capture/screen.png")));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void posBtnHandle(ActionEvent event){
        int numX = Integer.parseInt(xo.getText());
        int numY = Integer.parseInt(yo.getText());
        int numWidth = Integer.parseInt(width.getText());
        int numHeight = Integer.parseInt(height.getText());
        if(numX > 0 && numY > 0 && numWidth > 0 && numHeight > 0){
            Histogram.getInstance(null).adjustPosition(numX, numY, numWidth, numHeight);
        }
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