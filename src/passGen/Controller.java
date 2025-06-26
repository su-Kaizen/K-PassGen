package passGen;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class Controller implements Initializable{
	
	@FXML 
	private Button genButton;
	
	@FXML
	private TextArea generation;
	
	@FXML 
	private Slider slider;
	
	@FXML
	private Label nparam;
	
	@FXML
	private RadioButton rChar;
	
	@FXML
	private RadioButton rWord;
	
	@FXML
	private Label sliderText;
	
	@FXML
	private TextField delText;
	
	@FXML
	private CheckBox lowercaseCheck;
	
	@FXML
	private Label delLabel;
	
	@FXML
	private Label delLabel1;
	
	@FXML
	private Label nparam1;
	
	@FXML 
	private Slider slider1;
	
	@FXML
	private Label delLabel2;
	
	@FXML
	private ImageView minimize;
	
	@FXML
	private ImageView close;
	
	@FXML
	private HBox windowBar;
	
	
	private double x;
	
	private double y;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.println("Running!");
		toggleWords(false);
		slider.setValue(16.0);
		slider1.setValue(3.0);
		generation.setEditable(false);
		int t = (int)slider.getValue();
		nparam.setText(t+"");
		t = (int) slider1.getValue();
		nparam1.setText(t+"");
		PassGen.loadWords(true);
		slider.valueProperty().addListener(new ChangeListener<Number>() {
		
		/*Listeners at the slider bars*/
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				int t = (int)slider.getValue();
				nparam.setText(t+"");
			}
		});
		
		slider1.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				int t = (int) slider1.getValue();
				nparam1.setText(t+"");
			}
		});
		
		 
		lowercaseCheck.setSelected(true);
	}
	
	public void generate(ActionEvent e) {
		String pass = "";
		if(rWord.isSelected()) {
			String delimiter = delText.getText();
			int nNumbers = Integer.valueOf(nparam1.getText());
			boolean lowercase = lowercaseCheck.isSelected();
			pass = PassGen.gen(Integer.valueOf(nparam.getText()), delimiter, lowercase, nNumbers);
			regulateTextSize(pass.length());
			generation.setText(pass);
		}
		else {
			pass = PassGen.gen(Integer.valueOf(nparam.getText()));
			regulateTextSize(pass.length());
			generation.setText(pass);
		}
		
		
		
	}

	
	public void changedType(ActionEvent e) {
		wipeTextField();
		if(rWord.isSelected()) {
			toggleWords(true);
		}
		else { /*If its selected the random char*/			
			toggleWords(false);
		}
	}

	
	// Changes the property of the slider depending on the type of generation
	public void modifySlider(double min, double max, double def,int which) {
		if(which == 1) {
			slider.setMin(min);
			slider.setMax(max);
			slider.setValue(def);
		}
		else if(which == 2){
			slider1.setMin(min);
			slider1.setMax(max);
			slider1.setValue(def);
		}
		
	}
	
	
	public void wipeTextField() {
		generation.setText("");
	}
	
	// changes the layout of the application
	public void toggleWords(boolean a) {
		if(!a) {
			sliderText.setText("Number of chars");
			modifySlider(10.0,45.0,16.0,1);
		}
		else {
			sliderText.setText("Number of words");
			modifySlider(3.0,15.0,3.0,1);
			modifySlider(0.0,10.0,3.0,2);
			delText.setText("#");
		}
		lowercaseCheck.setVisible(a);
		delLabel.setVisible(a);
		delText.setVisible(a);
		delLabel1.setVisible(a);
		slider1.setVisible(a);
		nparam1.setVisible(a);
		delLabel2.setVisible(a);
	}
	
	
	/*Allows to only write one char into the delimiter TextField*/
	public void onlyOneLetter(KeyEvent e) {
		String t = delText.getText();
		
		if(t.length()>1){
			delText.setText(t.substring(0,1));
		}
	}
	
	
	/*Changes the size of the generation text depending of the generation length*/
	public void regulateTextSize(int len) {
		if(len > 100) {
			generation.setStyle("-fx-font-size: 12");
		}
		else if(len>70) {
			generation.setStyle("-fx-font-size: 14;");
		}
		else if(len > 45) {
			generation.setStyle("-fx-font-size: 15;");
		}
		else {
			generation.setStyle("-fx-font-size: 20;");
		}
	}
	
	/*The next methods are the handlers for custom close, minimize and movement of the window (I copied this from a yt video)*/
	public void handleClose(MouseEvent e) {
		Stage s = (Stage) close.getScene().getWindow();
		s.close();
	}
	
	public void handleMinimize(MouseEvent e) {
		Stage s = (Stage) minimize.getScene().getWindow();
		
		s.setIconified(true);
	}
	
	public void handleMovement1(MouseEvent e) {
		Stage s = (Stage) windowBar.getScene().getWindow();
		x = s.getX() - e.getScreenX();
		y = s.getY() - e.getScreenY();
	}
	
	
	public void handleMovement2(MouseEvent e) {
		Stage s = (Stage) windowBar.getScene().getWindow();
		
		s.setX(x + e.getScreenX());
		s.setY(y + e.getScreenY());
	}
	
}
