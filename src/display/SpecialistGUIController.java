package display;

import java.io.IOException;
//import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.util.ResourceBundle;

//import app.CustomerClient;
//import app.DisplayController;
import dto.Customer;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
//import dto.ResponseCustList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SpecialistGUIController {

 /*   @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
*/
	private Map<Integer, List<Label>> labelMap;
	private final String[] times = {"09:00", "09:15", "09:30", "09:45", "10:00", "10:15", "10:30", "10:45", 
			"11:00", "11:15", "11:30", "11:45", "12:00", "12:15", "12:30", "12:45",
			"13:00", "13:15", "13:30", "13:45", "14:00", "14:15", "14:30", "14:45",
			"15:00", "15:15", "15:30", "15:45", "16:00", "16:15", "16:30", "16:45"};
    @FXML
    private Label spec_id;

    @FXML
    private Label status_09_00;

    @FXML
    private Label code_09_00;

    @FXML
    private Label status_09_15;

    @FXML
    private Label code_09_15;

    @FXML
    private Label status_09_30;

    @FXML
    private Label code_09_30;

    @FXML
    private Label status_09_45;

    @FXML
    private Label code_09_45;

    @FXML
    private Label status_10_00;

    @FXML
    private Label code_10_00;

    @FXML
    private Label status_10_15;

    @FXML
    private Label code_10_15;

    @FXML
    private Label status_10_30;

    @FXML
    private Label code_10_30;

    @FXML
    private Label status_10_45;

    @FXML
    private Label code_10_45;

    @FXML
    private Label status_11_00;

    @FXML
    private Label code_11_00;

    @FXML
    private Label status_11_15;

    @FXML
    private Label code_11_15;

    @FXML
    private Label status_11_30;

    @FXML
    private Label code_11_30;

    @FXML
    private Label status_11_45;

    @FXML
    private Label code_11_45;

    @FXML
    private Label status_12_00;

    @FXML
    private Label code_12_00;

    @FXML
    private Label status_12_15;

    @FXML
    private Label code_12_15;

    @FXML
    private Label status_12_30;

    @FXML
    private Label code_12_30;

    @FXML
    private Label status_12_45;

    @FXML
    private Label code_12_45;

    @FXML
    private Label status_13_00;

    @FXML
    private Label code_13_00;

    @FXML
    private Label status_13_15;

    @FXML
    private Label code_13_15;

    @FXML
    private Label status_13_30;

    @FXML
    private Label code_13_30;

    @FXML
    private Label status_13_45;

    @FXML
    private Label code_13_45;

    @FXML
    private Label status_14_00;

    @FXML
    private Label code_14_00;

    @FXML
    private Label status_14_15;

    @FXML
    private Label code_14_15;

    @FXML
    private Label status_14_30;

    @FXML
    private Label code_14_30;

    @FXML
    private Label status_14_45;

    @FXML
    private Label code_14_45;

    @FXML
    private Label status_15_00;

    @FXML
    private Label code_15_00;

    @FXML
    private Label status_15_15;

    @FXML
    private Label code_15_15;

    @FXML
    private Label status_15_30;

    @FXML
    private Label code_15_30;

    @FXML
    private Label status_15_45;

    @FXML
    private Label code_15_45;

    @FXML
    private Label status_16_00;

    @FXML
    private Label code_16_00;

    @FXML
    private Button refreshListBtn;

    @FXML
    private Label status_16_15;

    @FXML
    private Label code_16_15;

    @FXML
    private Button markStartedBtn;
    
    @FXML
    private Button logoutBtn;

    @FXML
    private Label status_16_30;

    @FXML
    private Label code_16_30;

    @FXML
    private Button markCancelledBtn;

    @FXML
    private Label status_16_45;

    @FXML
    private Label code_16_45;

    @FXML
    private Button markEndedBtn;
    
    private SpecialistClient client;
    private Label currentSelection;
    private String currentSelectionTime;
    private Timeline clock;
    private LocalTime base;
    private LocalTime current;
    private int secCount;
    private int minCount;
    
    private Border blueBorder = new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2)));
    
    public void setSpecialistClient(SpecialistClient client) {
    	
    	this.client = client;
    }
 
    @FXML
    void refreshList(ActionEvent event) {
    	
    	LocalTime time;
    	String code;
    	String status;
    	int convertedTime;
    	
    	List<Customer> list = client.getCurrentList().getList();
    	for(Customer cust: list) {
    		time = cust.getTime();
    		code = cust.getCode();
    		status = cust.getStatus();
    		
    		convertedTime = convertToInt(time);
    		
    		this.selectAndSetStatusLabel(convertedTime, status);
    		this.selectAndSetCodeLabel(convertedTime, code);	
    	}
    	
    }
    void setSpecialistID(int id) {
    	spec_id.setText(String.valueOf(id));
    }
    @FXML
    void markStarted(ActionEvent event) {
    	
    	if(client.markVisitBegan(currentSelectionTime, Integer.parseInt(spec_id.getText()))) {
    		this.selectAndSetStatusLabel(convertToInt(LocalTime.parse(currentSelectionTime)), "ONGOING");
    	}
    }
    @FXML
    void markCancelled(ActionEvent event) {
    	
    	if(client.markVisitCancelled(currentSelectionTime, Integer.parseInt(spec_id.getText()))) {
    		this.selectAndSetStatusLabel(convertToInt(LocalTime.parse(currentSelectionTime)), "CANCELLED");
    	}
    }
    @FXML
    void markEnded(ActionEvent event) {
    	
    	if(client.markVisitEnded(currentSelectionTime, Integer.parseInt(spec_id.getText()))) {
    		this.selectAndSetStatusLabel(convertToInt(LocalTime.parse(currentSelectionTime)), "SERVICED");
    	}
    }
    @FXML
    void logout(ActionEvent event) {
    	
    	//hide main window
		((Node)event.getSource()).getScene().getWindow().hide();
		
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		
		try {	
			Pane root = loader.load(getClass().getResource("/display/CustomerGUI.fxml").openStream());
			CustomerGUIController controller = loader.getController();
			Scene scene = new Scene(root);
			client.synchronize(controller);
			client.synchronize(this);
			stage.setScene(scene);
			stage.show();
		}catch(IOException e) {
			System.err.println("Error opening stream to CustomerGUI.fxml");
		}
    }
    
    @FXML
    void status0900clicked(MouseEvent event) {
    	findAndRemoveExistingBorder();
    	addBlueBorder(1);
    }

    @FXML
    void status0915clicked(MouseEvent event) {
    	findAndRemoveExistingBorder();
    	addBlueBorder(2);
    }

    @FXML
    void status0930clicked(MouseEvent event) {
    	findAndRemoveExistingBorder();
    	addBlueBorder(3);
    }

    @FXML
    void status0945clicked(MouseEvent event) {
    	findAndRemoveExistingBorder();
    	addBlueBorder(4);
    }

    @FXML
    void status1000clicked(MouseEvent event) {
    	findAndRemoveExistingBorder();
    	addBlueBorder(5);
    }

    @FXML
    void status1015clicked(MouseEvent event) {
    	findAndRemoveExistingBorder();
    	addBlueBorder(6);
    }

    @FXML
    void status1030clicked(MouseEvent event) {
    	findAndRemoveExistingBorder();
    	addBlueBorder(7);
    }

    @FXML
    void status1045clicked(MouseEvent event) {
    	findAndRemoveExistingBorder();
    	addBlueBorder(8);
    }

    @FXML
    void status1100clicked(MouseEvent event) {
    	findAndRemoveExistingBorder();
    	addBlueBorder(9);
    }

    @FXML
    void status1115clicked(MouseEvent event) {
    	findAndRemoveExistingBorder();
    	addBlueBorder(10);
    }

    @FXML
    void status1130clicked(MouseEvent event) {
    	findAndRemoveExistingBorder();
    	addBlueBorder(11);
    }

    @FXML
    void status1145clicked(MouseEvent event) {
    	findAndRemoveExistingBorder();
    	addBlueBorder(12);
    }

    @FXML
    void status1200clicked(MouseEvent event) {
    	findAndRemoveExistingBorder();
    	addBlueBorder(13);
    }

    @FXML
    void status1215clicked(MouseEvent event) {
    	findAndRemoveExistingBorder();
    	addBlueBorder(14);
    }

    @FXML
    void status1230clicked(MouseEvent event) {
    	findAndRemoveExistingBorder();
    	addBlueBorder(15);
    }

    @FXML
    void status1245clicked(MouseEvent event) {
    	findAndRemoveExistingBorder();
    	addBlueBorder(16);
    }

    @FXML
    void status1300clicked(MouseEvent event) {
    	findAndRemoveExistingBorder();
    	addBlueBorder(17);
    }

    @FXML
    void status1315clicked(MouseEvent event) {
    	findAndRemoveExistingBorder();
    	addBlueBorder(18);
    }

    @FXML
    void status1330clicked(MouseEvent event) {
    	findAndRemoveExistingBorder();
    	addBlueBorder(19);
    }

    @FXML
    void status1345clicked(MouseEvent event) {
    	findAndRemoveExistingBorder();
    	addBlueBorder(20);
    }

    @FXML
    void status1400clicked(MouseEvent event) {
    	findAndRemoveExistingBorder();
    	addBlueBorder(21);
    }

    @FXML
    void status1415clicked(MouseEvent event) {
    	findAndRemoveExistingBorder();
    	addBlueBorder(22);
    }

    @FXML
    void status1430clicked(MouseEvent event) {
    	findAndRemoveExistingBorder();
    	addBlueBorder(23);
    }

    @FXML
    void status1445clicked(MouseEvent event) {
    	findAndRemoveExistingBorder();
    	addBlueBorder(24);
    }

    @FXML
    void status1500clicked(MouseEvent event) {
    	findAndRemoveExistingBorder();
    	addBlueBorder(25);
    }

    @FXML
    void status1515clicked(MouseEvent event) {
    	findAndRemoveExistingBorder();
    	addBlueBorder(26);
    }

    @FXML
    void status1530clicked(MouseEvent event) {
    	findAndRemoveExistingBorder();
    	addBlueBorder(27);
    }

    @FXML
    void status1545clicked(MouseEvent event) {
    	findAndRemoveExistingBorder();
    	addBlueBorder(28);
    }

    @FXML
    void status1600clicked(MouseEvent event) {
    	findAndRemoveExistingBorder();
    	addBlueBorder(29);
    }

    @FXML
    void status1615clicked(MouseEvent event) {
    	findAndRemoveExistingBorder();
    	addBlueBorder(30);
    }

    @FXML
    void status1630clicked(MouseEvent event) {
    	findAndRemoveExistingBorder();
    	addBlueBorder(31);
    }

    @FXML
    void status1645clicked(MouseEvent event) {
    	findAndRemoveExistingBorder();
    	addBlueBorder(32);
    }
    void initializeLabelMap() {
    	
    	labelMap = new HashMap<>();
    	for(int i=1; i<33; i++) {	//1-32 will represent time fields
    		labelMap.put(i, new ArrayList<>());
    	}
    		labelMap.get(1).add(status_09_00);
    		labelMap.get(1).add(code_09_00);
    		labelMap.get(2).add(status_09_15);
    		labelMap.get(2).add(code_09_15);
    		labelMap.get(3).add(status_09_30);
    		labelMap.get(3).add(code_09_30);
    		labelMap.get(4).add(status_09_45);
    		labelMap.get(4).add(code_09_45);
    		labelMap.get(5).add(status_10_00);
    		labelMap.get(5).add(code_10_00);
    		labelMap.get(6).add(status_10_15);
    		labelMap.get(6).add(code_10_15);
    		labelMap.get(7).add(status_10_30);
    		labelMap.get(7).add(code_10_30);
    		labelMap.get(8).add(status_10_45);
    		labelMap.get(8).add(code_10_45);
    		
    		labelMap.get(9).add(status_11_00);
    		labelMap.get(9).add(code_11_00);
    		labelMap.get(10).add(status_11_15);
    		labelMap.get(10).add(code_11_15);
    		labelMap.get(11).add(status_11_30);
    		labelMap.get(11).add(code_11_30);
    		labelMap.get(12).add(status_11_45);
    		labelMap.get(12).add(code_11_45);
    		labelMap.get(13).add(status_12_00);
    		labelMap.get(13).add(code_12_00);
    		labelMap.get(14).add(status_12_15);
    		labelMap.get(14).add(code_12_15);
    		labelMap.get(15).add(status_12_30);
    		labelMap.get(15).add(code_12_30);
    		labelMap.get(16).add(status_12_45);
    		labelMap.get(16).add(code_12_45);
    		
    		labelMap.get(17).add(status_13_00);
    		labelMap.get(17).add(code_13_00);
    		labelMap.get(18).add(status_13_15);
    		labelMap.get(18).add(code_13_15);
    		labelMap.get(19).add(status_13_30);
    		labelMap.get(19).add(code_13_30);
    		labelMap.get(20).add(status_13_45);
    		labelMap.get(20).add(code_13_45);
    		labelMap.get(21).add(status_14_00);
    		labelMap.get(21).add(code_14_00);
    		labelMap.get(22).add(status_14_15);
    		labelMap.get(22).add(code_14_15);
    		labelMap.get(23).add(status_14_30);
    		labelMap.get(23).add(code_14_30);
    		labelMap.get(24).add(status_14_45);
    		labelMap.get(24).add(code_14_45);
    		
    		labelMap.get(25).add(status_15_00);
    		labelMap.get(25).add(code_15_00);
    		labelMap.get(26).add(status_15_15);
    		labelMap.get(26).add(code_15_15);
    		labelMap.get(27).add(status_15_30);
    		labelMap.get(27).add(code_15_30);
    		labelMap.get(28).add(status_15_45);
    		labelMap.get(28).add(code_15_45);
    		labelMap.get(29).add(status_16_00);
    		labelMap.get(29).add(code_16_00);
    		labelMap.get(30).add(status_16_15);
    		labelMap.get(30).add(code_16_15);
    		labelMap.get(31).add(status_16_30);
    		labelMap.get(31).add(code_16_30);
    		labelMap.get(32).add(status_16_45);
    		labelMap.get(32).add(code_16_45);
    }
    
    /*
     * Selects a label by time. Time format eg.: 16_45
     */
    private void selectAndSetStatusLabel(int number, String value) {
    	labelMap.get(number).get(0).setText(value);
    }
    private void selectAndSetCodeLabel(int number, String value) {
    	labelMap.get(number).get(1).setText(value);
    }
    private int convertToInt(LocalTime time) {
    	
    	String value = padTo2(String.valueOf(time.getHour()))+":"+padTo2(String.valueOf(time.getMinute()));
    	int answer = -1;
    	for(int i=0; i<times.length; i++) {
    		if(times[i].equals(value)) {
    			answer = i+1;	//in labelMap, we start from 1
    			break;
    		}
    	}
    	return answer;
    }
    private String padTo2(String input) {
    	if(input!=null) {
    		if(input.length()==1) {
    			return "0"+input;
    		}else {
    			return input;
    		}
    	}else {
    		return "";
    	}
    }
    private void findAndRemoveExistingBorder() {
    
     if(currentSelection != null) {
    	currentSelection.setBorder(Border.EMPTY);
    	}
    }
    /*
     * Allowed indexes 1-32
     */
    private void addBlueBorder(int number) {
    	if(number >= 1 && number <= 32) {
    		currentSelection = labelMap.get(number).get(0);
    		currentSelection.setBorder(blueBorder);
    		currentSelectionTime = times[number-1];	//also remember selected time
    	}
    }
    private void startClock() {
 	   clock = new Timeline(
 	    		new KeyFrame(Duration.ZERO, 
 	    		e -> {
 	    				
 	    		secCount++;
 	    		if(secCount == 60) {
 	    			secCount = 0;
 	    			minCount++;
 	    			base = base.plusMinutes(1);
 	    			
 	    		}
 	    		
 	    		if(client!=null) {
 	    			client.synchronize(this);
 	    		}
 	    		
 	    	   // timer.setText(String.format("%02d : %02d", current.getHour(), current.getMinute()));
 	    	     
 	    		}),
 	    		new KeyFrame(Duration.seconds(1)));
 	    clock.setCycleCount(Animation.INDEFINITE);
 	    clock.play();
 	    
 	}
   public void setBase(LocalTime time) {
    	base = time;
    }
   LocalTime getTime() {
	   return current;
   }

    @FXML
    void initialize() {
        assert spec_id != null : "fx:id=\"spec_id\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert status_09_00 != null : "fx:id=\"status_09_00\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert code_09_00 != null : "fx:id=\"code_09_00\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert status_09_15 != null : "fx:id=\"status_09_15\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert code_09_15 != null : "fx:id=\"code_09_15\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert status_09_30 != null : "fx:id=\"status_09_30\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert code_09_30 != null : "fx:id=\"code_09_30\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert status_09_45 != null : "fx:id=\"status_09_45\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert code_09_45 != null : "fx:id=\"code_09_45\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert status_10_00 != null : "fx:id=\"status_10_00\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert code_10_00 != null : "fx:id=\"code_10_00\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert status_10_15 != null : "fx:id=\"status_10_15\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert code_10_15 != null : "fx:id=\"code_10_15\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert status_10_30 != null : "fx:id=\"status_10_30\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert code_10_30 != null : "fx:id=\"code_10_30\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert status_10_45 != null : "fx:id=\"status_10_45\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert code_10_45 != null : "fx:id=\"code_10_45\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert status_11_00 != null : "fx:id=\"status_11_00\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert code_11_00 != null : "fx:id=\"code_11_00\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert status_11_15 != null : "fx:id=\"status_11_15\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert code_11_15 != null : "fx:id=\"code_11_15\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert status_11_30 != null : "fx:id=\"status_11_30\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert code_11_30 != null : "fx:id=\"code_11_30\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert status_11_45 != null : "fx:id=\"status_11_45\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert code_11_45 != null : "fx:id=\"code_11_45\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert status_12_00 != null : "fx:id=\"status_12_00\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert code_12_00 != null : "fx:id=\"code_12_00\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert status_12_15 != null : "fx:id=\"status_12_15\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert code_12_15 != null : "fx:id=\"code_12_15\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert status_12_30 != null : "fx:id=\"status_12_30\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert code_12_30 != null : "fx:id=\"code_12_30\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert status_12_45 != null : "fx:id=\"status_12_45\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert code_12_45 != null : "fx:id=\"code_12_45\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert status_13_00 != null : "fx:id=\"status_13_00\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert code_13_00 != null : "fx:id=\"code_13_00\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert status_13_15 != null : "fx:id=\"status_13_15\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert code_13_15 != null : "fx:id=\"code_13_15\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert status_13_30 != null : "fx:id=\"status_13_30\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert code_13_30 != null : "fx:id=\"code_13_30\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert status_13_45 != null : "fx:id=\"status_13_45\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert code_13_45 != null : "fx:id=\"code_13_45\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert status_14_00 != null : "fx:id=\"status_14_00\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert code_14_00 != null : "fx:id=\"code_14_00\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert status_14_15 != null : "fx:id=\"status_14_15\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert code_14_15 != null : "fx:id=\"code_14_15\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert status_14_30 != null : "fx:id=\"status_14_30\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert code_14_30 != null : "fx:id=\"code_14_30\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert status_14_45 != null : "fx:id=\"status_14_45\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert code_14_45 != null : "fx:id=\"code_14_45\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert status_15_00 != null : "fx:id=\"status_15_00\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert code_15_00 != null : "fx:id=\"code_15_00\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert status_15_15 != null : "fx:id=\"status_15_15\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert code_15_15 != null : "fx:id=\"code_15_15\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert status_15_30 != null : "fx:id=\"status_15_30\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert code_15_30 != null : "fx:id=\"code_15_30\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert status_15_45 != null : "fx:id=\"status_15_45\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert code_15_45 != null : "fx:id=\"code_15_45\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert status_16_00 != null : "fx:id=\"status_16_00\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert code_16_00 != null : "fx:id=\"code_16_00\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert refreshListBtn != null : "fx:id=\"refreshListBtn\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert status_16_15 != null : "fx:id=\"status_16_15\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert code_16_15 != null : "fx:id=\"code_16_15\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert markStartedBtn != null : "fx:id=\"markStartedBtn\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert status_16_30 != null : "fx:id=\"status_16_30\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert code_16_30 != null : "fx:id=\"code_16_30\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert markCancelledBtn != null : "fx:id=\"markCancelledBtn\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert status_16_45 != null : "fx:id=\"status_16_45\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert code_16_45 != null : "fx:id=\"code_16_45\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert markEndedBtn != null : "fx:id=\"markEndedBtn\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        assert logoutBtn != null : "fx:id=\"logoutBtn\" was not injected: check your FXML file 'SpecialistGUI.fxml'.";
        initializeLabelMap();
        //startClock();
    }
}
