package tw.riot.twcardselector;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class PopupController implements Initializable{
	@FXML private TextField popT;
	@FXML private Button popB;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}
	public void handle(ActionEvent event) {
		if(popT.getText().length()>7) {
			popT.setText("");
			popT.getScene().getWindow().hide();
		}
	}

}
