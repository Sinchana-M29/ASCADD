package com.ascadd.ascadd;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CSVExporter {

    // Exports aircraft data to a CSV file for Power BI analysis
    public String exportToCSV(List<Aircraft> aircraftList, boolean deadlockDetected) {
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String filename = "ascadd_report_" + timestamp + ".csv";

        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {

            // Write CSV header
            writer.println("Callsign,CurrentSegment,TargetSegment,State,DeadlockDetected,Timestamp");

            // Write each aircraft as a row
            for (Aircraft a : aircraftList) {
                writer.printf("%s,%s,%s,%s,%s,%s%n",
                        a.getCallsign(),
                        a.getCurrentSegment(),
                        a.getTargetSegment(),
                        a.getState().toString(),
                        deadlockDetected ? "YES" : "NO",
                        LocalDateTime.now().format(
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                        )
                );
            }

        } catch (IOException e) {
            return "ERROR: Could not write CSV - " + e.getMessage();
        }

        return filename;
    }
}