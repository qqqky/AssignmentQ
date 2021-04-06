package display;
import java.io.IOException;
//import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
//import java.util.ResourceBundle;

import app.CustomerClient;
import app.DisplayController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
//import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CustomerGUIController {
	
	
	/*   @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    */
	private CustomerClient client;
	private Map<Integer, List<Label>> labelMap;
    @FXML
    private VBox vbox_left_side_main;

    @FXML
    private Label teller_1;

    @FXML
    private Label teller_2;

    @FXML
    private Label teller_3;

    @FXML
    private Label teller_4;

    @FXML
    private Label teller_5;

    @FXML
    private Label label_X1Y1;

    @FXML
    private Label label_X1Y2;

    @FXML
    private Label label_X1Y3;

    @FXML
    private Label label_X1Y4;

    @FXML
    private Label label_X1Y5;

    @FXML
    private Label label_X2Y1;

    @FXML
    private Label label_X2Y2;

    @FXML
    private Label label_X2Y3;

    @FXML
    private Label label_X2Y4;

    @FXML
    private Label label_X2Y5;

    @FXML
    private Label label_X3Y1;

    @FXML
    private Label label_X3Y2;

    @FXML
    private Label label_X3Y3;

    @FXML
    private Label label_X3Y4;

    @FXML
    private Label label_X3Y5;

    @FXML
    private Label label_X4Y1;

    @FXML
    private Label label_X4Y2;

    @FXML
    private Label label_X4Y3;

    @FXML
    private Label label_X4Y4;

    @FXML
    private Label label_X4Y5;

    @FXML
    private Label label_X5Y1;

    @FXML
    private Label label_X5Y2;

    @FXML
    private Label label_X5Y3;

    @FXML
    private Label label_X5Y4;

    @FXML
    private Label label_X5Y5;

    @FXML
    private Label timer;

    @FXML
    private Button plusBtn;

    @FXML
    private Button spawn;

    @FXML
    private Button minusBtn;

    @FXML
    private Tab spec_login_tab;

    @FXML
    private TextField name_specialist_login;

    @FXML
    private PasswordField pass_specialist_login;

    @FXML
    private Button loginBtn;

    @FXML
    private TextField name_cust_register;

    @FXML
    private TextField last_name_cust_reg;

    @FXML
    private Button registerBtn;

    @FXML
    private Label label_res_code;

    @FXML
    private TextField code_cust_cancel;

    @FXML
    private TextField last_name_cust_cancel;

    @FXML
    private Button cancel_cust_reservation;

    @FXML
    private TextField code_time_left;

    @FXML
    private TextField last_name_time_left;

    @FXML
    private Button submitBtn;

    @FXML
    private Label label_time_to_wait;
    
    @FXML
    private Label label_cust_cancel;
    
    @FXML
    private Label label_login;
    
    @FXML
    private Label label_reg_result;
    
	private LocalTime current;
	private LocalTime base = LocalTime.of(8, 55);
	private int minutesClicked;
	private int secCount;
	private int minCount;
	private Timeline clock;
	
    
    public void setCustomerClient(CustomerClient client) {
    	this.client = client;
    }
    /*
     * Must use this one, otherwise controller is not retained when GUI is created
     */
    public void setController(DisplayController control) {

    	client.setController(control);
    }

    @FXML
    void cancelReservationCustomer(ActionEvent event) {
    	String code = code_cust_cancel.getText();
    	String lastName = last_name_cust_cancel.getText();
    	
    	if(client.markVisitCancelled(code, lastName)) {
    		label_cust_cancel.setText("Your visit was cancelled");
    		this.removeCustomerFromGUI(code);
    	}else {
    		label_cust_cancel.setText("No such visit found or visit ended already");
    	}
    }

    @FXML
    void decrementTimer(ActionEvent event) {
    	minutesClicked--;
    }

    @FXML
    void incrementTimer(ActionEvent event) {
    	minutesClicked++;
    }

    @FXML
    void loginSpecialist(ActionEvent event) {
    	
    	String name = name_specialist_login.getText();
    	
    	//for testing purposes we retrieve password in cleartext (all passwords are 12345)
    	String pw = pass_specialist_login.getText();
    	
    	if(name.equals("") || pw.equals("")) {
    		label_login.setText("Both fields are required");
    	}else {
    		boolean result = client.authenticate(name, pw);
    		if(result) {
    			label_login.setText("Success. Launching specialist client");
    			
    			//switch scenes
    			Stage stage = new Stage();
    			FXMLLoader loader = new FXMLLoader();
    			
    			//hide main window
    			stopClock();
    			((Node)event.getSource()).getScene().getWindow().hide();
    			
    			try {	
    				Pane root = loader.load(getClass().getResource("/display/SpecialistGUI.fxml").openStream());
    				SpecialistGUIController controller = loader.getController();
    				Scene scene = new Scene(root);
    				client.synchronize(controller);
    				stage.setScene(scene);
    				stage.show();
    			}catch(IOException e) {
    				System.err.println("Error opening stream to CustomerGUI.fxml");
    			}
    			
    		}else {
    			label_login.setText("Authentication unsuccessful");
    		}
    	}
    }

    @FXML
    void registerCustomer(ActionEvent event) {
    	
    	String name = name_cust_register.getText();
    	String lastName = last_name_cust_reg.getText();
    	
    	//getText() does not return null
    	
    	if(!name.equals("") && !lastName.equals("")) {
    		String retrievedCode = client.register(name, lastName);
    		if(retrievedCode != null && retrievedCode.length() == 3) {
    			label_res_code.setText(retrievedCode);
    			label_reg_result.setText("Success");
    		}else {
    			label_reg_result.setText(retrievedCode);
    		}
    		
    	}else if(name.equals("") || lastName.equals("")){
    		label_reg_result.setText("Both fields are required for registration!");
    	}else {
    		label_reg_result.setText("Sorry, no more free space for today");
    	}
    	
    }

    @FXML
    void seeTimeToWait(ActionEvent event) {
    	String code = code_time_left.getText();
    	String lastName = last_name_time_left.getText();
    	
    	String time = client.getWaitingTime(code, lastName);
    	
    	label_time_to_wait.setText(time);
    }

    @FXML
    void spawnRandomCustomer(ActionEvent event) {
    	int number = client.getRegCount();
    	String lastName = "Bobson"+number;
    	String code = client.register("Bobby"+number, lastName);
    	System.out.println("Random customer generated. Code: "+code+" Last Name: "+lastName);
    	
    	
    }
    public LocalTime getCurrent() {
    	return current;
    }
    /*
     * Haven't found a more convenient way to save all labels in one place
     */
    void initializeLabelMap() {
    	
    	labelMap = new HashMap<>();
    	for(int i=1; i<6; i++) {
    		labelMap.put(i, new ArrayList<>());
    	}
    		labelMap.get(1).add(label_X1Y1);
    		labelMap.get(1).add(label_X1Y2);
    		labelMap.get(1).add(label_X1Y3);
    		labelMap.get(1).add(label_X1Y4);
    		labelMap.get(1).add(label_X1Y5);
 
    		labelMap.get(2).add(label_X2Y1);
    		labelMap.get(2).add(label_X2Y2);
    		labelMap.get(2).add(label_X2Y3);
    		labelMap.get(2).add(label_X2Y4);
    		labelMap.get(2).add(label_X2Y5);
    		
    		labelMap.get(3).add(label_X3Y1);
    		labelMap.get(3).add(label_X3Y2);
    		labelMap.get(3).add(label_X3Y3);
    		labelMap.get(3).add(label_X3Y4);
    		labelMap.get(3).add(label_X3Y5);
    		
    		labelMap.get(4).add(label_X4Y1);
    		labelMap.get(4).add(label_X4Y2);
    		labelMap.get(4).add(label_X4Y3);
    		labelMap.get(4).add(label_X4Y4);
    		labelMap.get(4).add(label_X4Y5);
    		
    		labelMap.get(5).add(label_X5Y1);
    		labelMap.get(5).add(label_X5Y2);
    		labelMap.get(5).add(label_X5Y3);
    		labelMap.get(5).add(label_X5Y4);
    		labelMap.get(5).add(label_X5Y5);

    }
    public void insertCustomerToGUI(int id, String code) {
    	
    	List<Label> selectedList = labelMap.get(id);
    	for(int i=0; i<selectedList.size();i++) {
    		if(selectedList.get(i).getText().equals("")) {
    			selectedList.get(i).setText(code);
    			break;
    		}
    	}
    }
    public void removeCustomerFromGUI(String code) {
    	searchInMapAndRemove(code);
    }
    /*
     * Searches for correct label given a customer code
     */
    private void searchInMapAndRemove(String code) {
    	
    	Optional<Label> target = labelMap.keySet().stream().flatMap(key -> labelMap.get(key).stream())
    	.filter(label -> label.getText().equals(code)).findFirst();
    	
    	if(target.isPresent()) {
    		target.get().setText("");
    	}
    }
    /*
     * For more convenient retrieval of GUI labels that represent customer queues {1,1} - {5,5}
     */
    private Label getLabelInCoords(int x, int y) {
    	return labelMap.get(x).get(y-1);
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
	    		current = base.plusMinutes(minutesClicked);	
	    		
	    		if(client!=null) {
	    			client.synchronize(this);
	    		}
	    		
	    		//System.out.println("MinutesClicked value is: "+minutesClicked);
	    		
	    	    timer.setText(String.format("%02d : %02d", current.getHour(), current.getMinute()));
	    	     
	    		}),
	    		new KeyFrame(Duration.seconds(1)));
	    clock.setCycleCount(Animation.INDEFINITE);
	    clock.play();
	    
	}
    private void stopClock() {
    	clock.stop();
    }
    public int getMinutesClicked() {
    	return minutesClicked;
    }
    public int getMinutesPassed() {
    	return minCount;
    }

    @FXML
    void initialize() {
        assert vbox_left_side_main != null : "fx:id=\"vbox_left_side_main\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert teller_1 != null : "fx:id=\"teller_1\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert teller_2 != null : "fx:id=\"teller_2\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert teller_3 != null : "fx:id=\"teller_3\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert teller_4 != null : "fx:id=\"teller_4\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert teller_5 != null : "fx:id=\"teller_5\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert label_X1Y1 != null : "fx:id=\"label_X1Y1\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert label_X1Y2 != null : "fx:id=\"label_X1Y2\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert label_X1Y3 != null : "fx:id=\"label_X1Y3\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert label_X1Y4 != null : "fx:id=\"label_X1Y4\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert label_X1Y5 != null : "fx:id=\"label_X1Y5\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert label_X2Y1 != null : "fx:id=\"label_X2Y1\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert label_X2Y2 != null : "fx:id=\"label_X2Y2\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert label_X2Y3 != null : "fx:id=\"label_X2Y3\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert label_X2Y4 != null : "fx:id=\"label_X2Y4\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert label_X2Y5 != null : "fx:id=\"label_X2Y5\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert label_X3Y1 != null : "fx:id=\"label_X3Y1\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert label_X3Y2 != null : "fx:id=\"label_X3Y2\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert label_X3Y3 != null : "fx:id=\"label_X3Y3\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert label_X3Y4 != null : "fx:id=\"label_X3Y4\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert label_X3Y5 != null : "fx:id=\"label_X3Y5\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert label_X4Y1 != null : "fx:id=\"label_X4Y1\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert label_X4Y2 != null : "fx:id=\"label_X4Y2\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert label_X4Y3 != null : "fx:id=\"label_X4Y3\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert label_X4Y4 != null : "fx:id=\"label_X4Y4\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert label_X4Y5 != null : "fx:id=\"label_X4Y5\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert label_X5Y1 != null : "fx:id=\"label_X5Y1\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert label_X5Y2 != null : "fx:id=\"label_X5Y2\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert label_X5Y3 != null : "fx:id=\"label_X5Y3\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert label_X5Y4 != null : "fx:id=\"label_X5Y4\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert label_X5Y5 != null : "fx:id=\"label_X5Y5\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert timer != null : "fx:id=\"timer\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert plusBtn != null : "fx:id=\"plusBtn\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert spawn != null : "fx:id=\"spawn\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert minusBtn != null : "fx:id=\"minusBtn\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert spec_login_tab != null : "fx:id=\"spec_login_tab\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert name_specialist_login != null : "fx:id=\"name_specialist_login\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert pass_specialist_login != null : "fx:id=\"pass_specialist_login\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert loginBtn != null : "fx:id=\"loginBtn\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert label_login != null : "fx:id=\"label_login\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert name_cust_register != null : "fx:id=\"name_cust_register\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert last_name_cust_reg != null : "fx:id=\"last_name_cust_reg\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert registerBtn != null : "fx:id=\"registerBtn\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert label_res_code != null : "fx:id=\"label_reg_code\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert label_reg_result != null : "fx:id=\"label_reg_result\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert code_cust_cancel != null : "fx:id=\"code_cust_cancel\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert last_name_cust_cancel != null : "fx:id=\"last_name_cust_cancel\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert cancel_cust_reservation != null : "fx:id=\"cancel_cust_reservation\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert label_cust_cancel != null : "fx:id=\"label_cust_cancel\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert code_time_left != null : "fx:id=\"code_time_left\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert last_name_time_left != null : "fx:id=\"last_name_time_left\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert submitBtn != null : "fx:id=\"submitBtn\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
        assert label_time_to_wait != null : "fx:id=\"label_time_to_wait\" was not injected: check your FXML file 'CustomerGUI.fxml'.";
       
        initializeLabelMap();
        startClock();
    }
}
