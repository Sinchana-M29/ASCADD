package com.ascadd.ascadd;

import javafx.application.Application;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) {

        // Create two aircraft in a deadlock scenario
        // AI101 is on T1, wants to go to T2
        // AI202 is on T2, wants to go to T1
        Aircraft a1 = new Aircraft("AI101", "T1", "T2");
        Aircraft a2 = new Aircraft("AI202", "T2", "T1");

        // Both are HOLDING (waiting)
        StateMachine sm = new StateMachine();
        sm.transition(a1); // TAXIING → HOLDING
        sm.transition(a2); // TAXIING → HOLDING

        // Add to list
        List<Aircraft> aircraftList = new ArrayList<>();
        aircraftList.add(a1);
        aircraftList.add(a2);

        // Run deadlock detection
        DeadlockDetector detector = new DeadlockDetector();
        boolean deadlockFound = detector.detectDeadlock(aircraftList);

        System.out.println("=== DEADLOCK DETECTION TEST ===");
        System.out.println(a1);
        System.out.println(a2);
        System.out.println("Deadlock detected: " + deadlockFound);
    }

    public static void main(String[] args) {
        launch();
    }
}