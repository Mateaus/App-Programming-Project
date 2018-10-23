package application.controller.ui;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class SearchController implements Initializable {
	@FXML private TextField groupName;
	@FXML private TextField subject;
	@FXML private TextField groupSize;
	@FXML private DatePicker date;
	@FXML private ComboBox<String> startTime;
	@FXML private ComboBox<String> duration;
	@FXML private Button createButton;
	@FXML private ImageView map;
	@FXML private Pane imagePane;
	@FXML private Label messageLabel;
	
	private Image pin;
	private ImageView view;
	
	private String nameOfGroup, studySubject, sizeOfGroup, start, length;
	private LocalDate studyDate;
	
	public SearchController() {
	}
	
	@Override
	public void initialize (URL arg0, ResourceBundle arg1) {
		ObservableList<String> times = FXCollections.observableArrayList ("7:00 am", "7:30 am", "8:00 am", "8:30 am", "9:00 am", "9:30 am",
																		  "10:00 am", "10:30 am", "11:00 am", "11:30 am", "12:00 pm", "12:30 pm",
																		  "1:00 pm", "1:30 pm", "2:00 pm", "2:30 pm", "3:00 pm", "3:30 pm",
																		  "4:00 pm", "4:30 pm", "5:00 pm", "5:30 pm", "6:00 pm", "6:30 pm",
																		  "7:00 pm", "7:30 pm", "8:00 pm", "8:30 pm", "9:00 pm", "9:30 pm");
		ObservableList<String> durations = FXCollections.observableArrayList ("00:30", "1:00", "1:30", "2:00", "2:30", "3:00", "3:30", "4:00",
																			  "4:30", "5:00", "5:30", "6:00");
		startTime.setItems (times);
		duration.setItems (durations);
		
		// make clicks on transparent parts of image detectable
		map.setPickOnBounds(true);
	}
	
	@FXML
	private void handleSearchButton (ActionEvent event) {
		nameOfGroup = groupName.getText();
		studySubject = subject.getText();
		sizeOfGroup = groupSize.getText();
		studyDate = date.getValue();
		start = startTime.getValue();
		length = duration.getValue();
		messageLabel.setText(null);
		
		boolean isValidSubject = studySubject.matches("^[a-zA-Z]{2,3}[ ][0-9]{4}$");
		boolean isValidSize = sizeOfGroup.matches("[0-9]{1,2}");
		
		if (isValidSubject && isValidSize) {
			// TODO: get the current user's UserInformation when they click save and store in the class variable user
			
		} else if (isValidSubject && (! isValidSize)) {
			messageLabel.setText("Invalid group size");
		} else if ((! isValidSubject) && isValidSize) {
			messageLabel.setText("Invalid study subject");
		} else {
			messageLabel.setText("Invalid group size and study subject");
		}
	}
	
	@FXML
	private void handleMapClick (MouseEvent event) {
		
	}
}