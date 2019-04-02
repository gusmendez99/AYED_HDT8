import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

//This is the important import, bro
import java.util.PriorityQueue;
/**
 * Class that manages the operations between the user interface and the logic of the program
 * manages the JavaFx stages and operates in the BinarySearchTree instance to feed the user
 * @author Gustavo Méndez
 * @author Luis Urbina
 * @version 1.0
 * @since  21/03/2019
 */
public class HospitalView {

    /**
     * TextAreas for show the results
     */
    private TextArea inputTextArea;
    private TextArea outputTextArea;
    private FileChooser fileChooser;
    private ComboBox<String> heapTypesComboBox;

    /**
     * Control variables
     */
    private boolean isPatientListLoaded, isVectorHeapSelected = false;
    private static final String VECTOR_HEAP = "VectorHeap";
    private static final String PRIORITY_QUEUE = "PriorityQueue";
    private VectorHeap<Patient> patientVectorHeap;
    private PriorityQueue<Patient> patientPriorityQueue;


    /**
     * Show the stage
     * @param stage the stage to show in the current context
     */
    public void show(Stage stage) {
        //Init UI components
        inputTextArea = new TextArea("");
        outputTextArea = new TextArea("...");
        fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );

        String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
        fileChooser.setInitialDirectory(new File(currentPath));

        //Init list of heaps
        heapTypesComboBox = new ComboBox<>();
        heapTypesComboBox.getItems().add(VECTOR_HEAP);
        heapTypesComboBox.getItems().add(PRIORITY_QUEUE);

        //Initialized as true, cause this is the default
        isVectorHeapSelected = true;
        heapTypesComboBox.getSelectionModel().selectFirst();

        //Init heaps
        patientVectorHeap = new VectorHeap<>();
        patientPriorityQueue = new PriorityQueue<>();


        BorderPane border = new BorderPane();
        HBox hbox = addHBox(stage);
        border.setTop(hbox);
        border.setLeft(addVBox());

        Scene scene = new Scene(border, 800, 600);
        stage.setTitle("Hospital UVG");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * To return a HBox with two buttons for read file and clear TextArea
     *
     * @param stage Stage of JavaFX where we're gonna render the UI
     * @return a filled HBox to add to the UI
     */
    public HBox addHBox(Stage stage) {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(6);
        hbox.setStyle("-fx-background-color: #455a64;");

        Button buttonLoadPatientText = new Button("Load Patients...");
        buttonLoadPatientText.setPrefSize(200, 20);
        buttonLoadPatientText.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                BufferedReader bufferedReader = null;
                try {
                    bufferedReader = new BufferedReader(new FileReader(selectedFile));
                    String text;
                    while ((text = bufferedReader.readLine()) != null) {
                        inputTextArea.appendText("\n" + text);

                    }

                } catch (FileNotFoundException ex) {
                    Logger.getLogger(HospitalView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(HospitalView.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        bufferedReader.close();
                    } catch (IOException ex) {
                        Logger.getLogger(HospitalView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                //TODO: Show a dialog with successful message
                isPatientListLoaded = true;

                //Dictionary and/or text haven't been loaded
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Text file loaded!");
                alert.setHeaderText("Text file loaded successfully");
                alert.setContentText("Try to translate your text...");
                alert.showAndWait();

            }
        });

        heapTypesComboBox.valueProperty().addListener((obs, oldItem, newItem) -> {
            if (newItem != null) {
                switch(newItem){
                    case VECTOR_HEAP:
                        isVectorHeapSelected = true;
                        break;
                    case PRIORITY_QUEUE:
                        isVectorHeapSelected = false;
                        break;
                }
            }
        });

        //This is the debug button
        Button buttonOrder = new Button("Order");
        buttonOrder.setPrefSize(100, 20);
        buttonOrder.setStyle("-fx-background-color: #388e3c;");
        buttonOrder.setOnAction(e -> {

            if(isPatientListLoaded){
                //Split the inputTextArea by lines and store in an Array
                patientVectorHeap.clear();
                patientPriorityQueue.clear();
                List<String> initLines = Arrays.asList(inputTextArea.getText().split("\n"));

                //Saving input data as new patients
                for (String line : initLines) {
                    //Adding patients to a new ArrayList
                    if(!line.isEmpty()){
                        String linePatientArray[] = line.split(",");
                        patientVectorHeap.add(new Patient(linePatientArray[0], linePatientArray[1], linePatientArray[2]));
                        patientPriorityQueue.add(new Patient(linePatientArray[0], linePatientArray[1], linePatientArray[2]));
                    }

                }

                //Clearing last output
                outputTextArea.clear();
                outputTextArea.appendText("Output" + "\n");

                if(isVectorHeapSelected){
                    //VectorHeap Logic
                    outputTextArea.appendText(getPatientVectorHeap());
                } else {
                    //PriorityQueue Logic
                    outputTextArea.appendText(getPatientPriorityQueue());
                }

            } else {
                //Dictionary and/or text haven't been loaded
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText("Load files");
                alert.setContentText("Text to translate or Dictionary haven't been loaded...");
                alert.showAndWait();

            }

        });

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        outputTextArea.appendText("Output" + "\n");

        //Button for clear the inputTextArea data
        Button buttonClear = new Button("Clear");
        buttonClear.setPrefSize(100, 20);
        buttonClear.setOnAction(e -> {
            inputTextArea.clear();
            outputTextArea.clear();
            outputTextArea.appendText("Output" + "\n");
            isPatientListLoaded = false;
        });

        hbox.getChildren().addAll(buttonLoadPatientText, heapTypesComboBox, buttonClear, buttonOrder);

        return hbox;
    }

    private String getPatientPriorityQueue() {
        //TODO: Urbina's work
        return "";
    }

    private String getPatientVectorHeap() {
        String patientsStr = "";
        Patient tempPatient = null;

        while(!patientVectorHeap.isEmpty()){
            tempPatient = patientVectorHeap.remove();
            patientsStr += tempPatient.toString() + "\n";
        }
        return patientsStr;
    }


    /**
     * For add a TextArea to the screen, and show the outputTextArea
     *
     * @return a filled HBox to add to the UI
     */
    public VBox addVBox() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);

        //Adding the TextArea to the VBox
        inputTextArea.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        vbox.getChildren().add(inputTextArea);

        outputTextArea.setFont(Font.font("Arial", FontWeight.MEDIUM, 14));
        vbox.getChildren().add(outputTextArea);


        return vbox;
    }





}