package com.ascadd.ascadd;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/ascadd/ascadd/hello-view.fxml")
        );

        Scene scene = new Scene(loader.load(), 900, 600);
        stage.setTitle("✈ ASCADD — Taxiway Deadlock Detector");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}