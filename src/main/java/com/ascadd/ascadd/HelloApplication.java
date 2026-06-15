package com.ascadd.ascadd;

import javafx.application.Application;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) {
        // Create two aircraft
        Aircraft a1 = new Aircraft("AI101", "T1", "T2");
        Aircraft a2 = new Aircraft("AI202", "T2", "T3");

        // Create the state machine
        StateMachine sm = new StateMachine();

        // Test transitions for AI101
        System.out.println("Before: " + a1);
        sm.transition(a1);
        System.out.println("After 1st transition: " + a1);
        sm.transition(a1);
        System.out.println("After 2nd transition: " + a1);
        sm.transition(a1);
        System.out.println("After 3rd transition: " + a1);
    }

    public static void main(String[] args) {
        launch();
    }
}