package calculadoraCompleja;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
//import javafx.scene.control.Button;



public class CalculadoraCompleja extends Application {
	
	private TextField primNumTextField;
	private TextField seguNumTextField;
	private TextField tercNumTextField;
	private TextField cuartNumTextField;
	private TextField quintNumTextField;
	private TextField sextNumTextField;
	private ComboBox<String> Combo;
	//private Button igualBoton;
	
	private Complejo primNum = new Complejo();
	private Complejo seguNum = new Complejo();
	private Complejo tercNum = new Complejo();
	private Complejo cuartNum = new Complejo();
	private Complejo quintNum = new Complejo();
	private Complejo sextNum = new Complejo();
	private StringProperty op = new SimpleStringProperty();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
	
		primNumTextField = new TextField();
		primNumTextField.setPrefColumnCount(4);
		
		
		seguNumTextField = new TextField();
		seguNumTextField.setPrefColumnCount(4);
		
		
		tercNumTextField = new TextField();
		tercNumTextField.setPrefColumnCount(4);
		
		
		cuartNumTextField = new TextField();
		cuartNumTextField.setPrefColumnCount(4);
		
		
		quintNumTextField = new TextField();
		quintNumTextField.setDisable(true);
		quintNumTextField.setPrefColumnCount(4);
		
		
		sextNumTextField = new TextField();
		sextNumTextField.setDisable(true);
		sextNumTextField.setPrefColumnCount(4);
		

		Combo = new ComboBox<String>();
		Combo.getItems().addAll("+", "-", "*", "/");
		
		//igualBoton= new Button("=");
		
				
		HBox primOpHBox = new HBox(4, primNumTextField, new Label("+"), seguNumTextField, new Label("i"));
		
		HBox seguOpHBox = new HBox(4, tercNumTextField, new Label("+"), cuartNumTextField, new Label("i"));
		
		
		HBox resultOpHBox = new HBox(4, quintNumTextField, new Label("+"), sextNumTextField, new Label("i"));
		
		VBox primVBox = new VBox(1, Combo);
		primVBox.setAlignment(Pos.CENTER);
		
		
		VBox seguVBox = new VBox(4, primOpHBox, seguOpHBox, new Separator(), resultOpHBox);
		seguVBox.setAlignment(Pos.CENTER); 
		
		/*VBox botonVBox = new VBox(1,igualBoton);
		botonVBox.setAlignment(Pos.CENTER);*/
		
		HBox root = new HBox(3, primVBox, seguVBox/*,botonVBox*/);
		root.setAlignment(Pos.CENTER);
		root.setSpacing(15);
		
		Scene scene = new Scene(root, 320, 200);

		primaryStage.setScene(scene);
		primaryStage.setTitle("Calculadora Compleja FX");
		primaryStage.show();
		
		Bindings.bindBidirectional(primNumTextField.textProperty(), primNum.realProperty(), new NumberStringConverter());
		
		Bindings.bindBidirectional(seguNumTextField.textProperty(), seguNum.imaginarioProperty(), new NumberStringConverter());
		
		Bindings.bindBidirectional(tercNumTextField.textProperty(), tercNum.realProperty(), new NumberStringConverter());
		
		Bindings.bindBidirectional(cuartNumTextField.textProperty(), cuartNum.imaginarioProperty(), new NumberStringConverter());
		
		Bindings.bindBidirectional(quintNumTextField.textProperty(), quintNum.realProperty(), new NumberStringConverter());
		
		Bindings.bindBidirectional(sextNumTextField.textProperty(), sextNum.imaginarioProperty(), new NumberStringConverter());
		
		op.bind(Combo.getSelectionModel().selectedItemProperty());
		op.addListener((o, ov, nv) -> onSelectSigno(nv));
		
		Combo.getSelectionModel().selectFirst(); 
	}
	
	private void onSelectSigno(String nv) {

		switch(nv) {
		case "+":
			
			quintNum.realProperty().bind(primNum.realProperty().add(tercNum.realProperty()));
			sextNum.imaginarioProperty().bind(seguNum.imaginarioProperty().add(cuartNum.imaginarioProperty()));
			break;
			
		case "-":
			
			quintNum.realProperty().bind(primNum.realProperty().subtract(tercNum.realProperty()));
			sextNum.imaginarioProperty().bind(seguNum.imaginarioProperty().subtract(cuartNum.imaginarioProperty()));
			break;
			
		case "*": 
			quintNum.realProperty().bind(primNum.realProperty().multiply(tercNum.realProperty()).subtract(seguNum.imaginarioProperty().multiply(cuartNum.imaginarioProperty())));
			sextNum.imaginarioProperty().bind(primNum.realProperty().multiply(cuartNum.imaginarioProperty()).add(seguNum.imaginarioProperty().multiply(tercNum.realProperty())));
			break;
		case "/":
			
			quintNum.realProperty().bind(((primNum.realProperty().multiply(tercNum.realProperty())).add(seguNum.imaginarioProperty().multiply(cuartNum.imaginarioProperty()))).divide((tercNum.realProperty().multiply(tercNum.realProperty())).add((cuartNum.imaginarioProperty()).multiply(cuartNum.imaginarioProperty()))));
			sextNum.imaginarioProperty().bind(((seguNum.imaginarioProperty().multiply(tercNum.realProperty())).subtract(primNum.realProperty().multiply(cuartNum.imaginarioProperty()))).divide((tercNum.realProperty().multiply(tercNum.realProperty())).add((cuartNum.imaginarioProperty()).multiply(cuartNum.imaginarioProperty()))));
			break;
			
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}