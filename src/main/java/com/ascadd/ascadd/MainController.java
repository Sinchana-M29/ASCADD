package com.ascadd.ascadd;

import com.ascadd.ascadd.Aircraft;
import com.ascadd.ascadd.AircraftState;
import com.ascadd.ascadd.CSVExporter;
import com.ascadd.ascadd.DeadlockDetector;
import com.ascadd.ascadd.StateMachine;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    @FXML private ListView<String> aircraftListView;
    @FXML private TextArea logArea;
    @FXML private Canvas taxiwayCanvas;

    private List<Aircraft> aircraftList = new ArrayList<>();
    private StateMachine stateMachine = new StateMachine();
    private DeadlockDetector detector = new DeadlockDetector();
    private CSVExporter exporter = new CSVExporter();
    private int aircraftCounter = 1;

    // Taxiway segment positions on canvas [x, y, width, height]
    private final String[] segments = {"T1", "T2", "T3", "T4", "T5"};
    private final double[][] segmentBounds = {
            {50,  130, 80, 40},
            {180, 130, 80, 40},
            {310, 130, 80, 40},
            {440, 130, 80, 40},
            {310, 220, 80, 40}
    };

    @FXML
    public void initialize() {
        drawTaxiwayMap();
    }

    // ─── Drawing ────────────────────────────────────────────────────────────

    private void drawTaxiwayMap() {
        GraphicsContext gc = taxiwayCanvas.getGraphicsContext2D();

        // Background
        gc.setFill(Color.web("#1a1a2e"));
        gc.fillRect(0, 0, taxiwayCanvas.getWidth(), taxiwayCanvas.getHeight());

        // Draw connections
        gc.setStroke(Color.web("#a0a0c0"));
        gc.setLineWidth(2);
        gc.strokeLine(130, 150, 180, 150); // T1 → T2
        gc.strokeLine(260, 150, 310, 150); // T2 → T3
        gc.strokeLine(390, 150, 440, 150); // T3 → T4
        gc.strokeLine(350, 170, 350, 220); // T3 → T5

        // Draw segments
        for (int i = 0; i < segments.length; i++) {
            double x = segmentBounds[i][0];
            double y = segmentBounds[i][1];
            double w = segmentBounds[i][2];
            double h = segmentBounds[i][3];

            gc.setFill(Color.web("#0f3460"));
            gc.fillRoundRect(x, y, w, h, 10, 10);
            gc.setStroke(Color.web("#e94560"));
            gc.strokeRoundRect(x, y, w, h, 10, 10);

            gc.setFill(Color.WHITE);
            gc.fillText(segments[i], x + 30, y + 25);
        }
    }

    private void drawAircraftOnMap() {
        drawTaxiwayMap();
        GraphicsContext gc = taxiwayCanvas.getGraphicsContext2D();

        for (Aircraft a : aircraftList) {
            for (int i = 0; i < segments.length; i++) {
                if (segments[i].equals(a.getCurrentSegment())) {
                    double x = segmentBounds[i][0];
                    double y = segmentBounds[i][1];

                    Color color = switch (a.getState()) {
                        case TAXIING -> Color.web("#00ff00");
                        case HOLDING -> Color.web("#ffff00");
                        case CLEARED -> Color.web("#00aaff");
                    };

                    gc.setFill(color);
                    gc.fillOval(x + 60, y - 15, 20, 20);
                    gc.setFill(Color.BLACK);
                    gc.fillText(a.getCallsign(), x + 55, y - 2);
                }
            }
        }
    }

    // ─── Button Handlers ────────────────────────────────────────────────────

    @FXML
    private void handleAddAircraft() {
        if (aircraftCounter > 5) {
            log("⚠ Maximum 5 aircraft supported in this simulation.");
            return;
        }
        int idx = aircraftCounter - 1;
        String callsign = "AC" + String.format("%03d", aircraftCounter);
        String current = segments[idx];
        String target = segments[Math.min(idx + 1, segments.length - 1)];

        Aircraft aircraft = new Aircraft(callsign, current, target);
        aircraftList.add(aircraft);
        aircraftListView.getItems().add(aircraft.toString());
        log("✈ Added: " + aircraft);
        aircraftCounter++;
        drawAircraftOnMap();
    }

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
        drawAircraftOnMap();
    }

    @FXML
    private void handleDetectDeadlock() {
        boolean deadlock = detector.detectDeadlock(aircraftList);
        if (deadlock) {
            log("🔴 DEADLOCK DETECTED! Circular wait found among HOLDING aircraft.");
        } else {
            log("🟢 No deadlock detected. All clear!");
        }
    }

    @FXML
    private void handleExportCSV() {
        if (aircraftList.isEmpty()) {
            log("⚠ No aircraft to export. Add some aircraft first.");
            return;
        }

        boolean deadlock = detector.detectDeadlock(aircraftList);
        String filename = exporter.exportToCSV(aircraftList, deadlock);

        if (filename.startsWith("ERROR")) {
            log("❌ Export failed: " + filename);
        } else {
            log("📊 Exported successfully to: " + filename);
            log("📂 File saved in your project root folder.");
            log("💡 Open this CSV in Power BI for visual analysis!");
        }
    }

    // ─── Helper ─────────────────────────────────────────────────────────────

    private void log(String message) {
        logArea.appendText(message + "\n");
    }
}