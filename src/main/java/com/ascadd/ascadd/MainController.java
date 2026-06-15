package com.ascadd.ascadd;

import com.ascadd.ascadd.Aircraft;
import com.ascadd.ascadd.AircraftState;
import com.ascadd.ascadd.StateMachine;
import com.ascadd.ascadd.DeadlockDetector;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    @FXML private ListView<String> aircraftListView;
    @FXML private TextArea logArea;

    private List<Aircraft> aircraftList = new ArrayList<>();
    private StateMachine stateMachine = new StateMachine();
    private DeadlockDetector detector = new DeadlockDetector();
    private int aircraftCounter = 1;

    // Called when "+ Add Aircraft" button is clicked
    @FXML
    private void handleAddAircraft() {
        String callsign = "AC" + String.format("%03d", aircraftCounter++);
        String current = "T" + (aircraftList.size() + 1);
        String target = "T" + (aircraftList.size() + 2);

        Aircraft aircraft = new Aircraft(callsign, current, target);
        aircraftList.add(aircraft);
        aircraftListView.getItems().add(aircraft.toString());

        log("✈ Added: " + aircraft);
    }

    // Called when "Transition State" button is clicked
    @FXML
    private void handleTransition() {
        int selected = aircraftListView.getSelectionModel().getSelectedIndex();
        if (selected < 0) {
            log("⚠ Please select an aircraft from the list first.");
            return;
        }

        Aircraft aircraft = aircraftList.get(selected);
        stateMachine.transition(aircraft);
        aircraftListView.getItems().set(selected, aircraft.toString());

        log("⟶ Transitioned: " + aircraft);
    }

    // Called when "Detect Deadlock" button is clicked
    @FXML
    private void handleDetectDeadlock() {
        boolean deadlock = detector.detectDeadlock(aircraftList);
        if (deadlock) {
            log("🔴 DEADLOCK DETECTED! Circular wait found among HOLDING aircraft.");
        } else {
            log("🟢 No deadlock detected. All clear!");
        }
    }

    // Helper to append messages to the log area
    private void log(String message) {
        logArea.appendText(message + "\n");
    }
}