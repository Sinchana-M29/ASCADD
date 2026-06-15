package com.ascadd.ascadd;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DeadlockDetector {

    // Detects if there is a circular wait among HOLDING aircraft
    public boolean detectDeadlock(List<Aircraft> aircraftList) {
        // Build a wait-for graph: who is waiting for whom
        Map<String, String> waitForGraph = new HashMap<>();

        for (Aircraft a : aircraftList) {
            if (a.getState() == AircraftState.HOLDING) {
                for (Aircraft b : aircraftList) {
                    if (!a.getCallsign().equals(b.getCallsign())) {
                        // A is waiting for B if A's target = B's current segment
                        if (a.getTargetSegment().equals(b.getCurrentSegment())) {
                            waitForGraph.put(a.getCallsign(), b.getCallsign());
                        }
                    }
                }
            }
        }

        // Check for cycles in the wait-for graph
        Set<String> visited = new HashSet<>();
        for (String start : waitForGraph.keySet()) {
            if (hasCycle(start, waitForGraph, visited, new HashSet<>())) {
                return true;
            }
        }
        return false;
    }

    // Recursive cycle detection using DFS
    private boolean hasCycle(String current, Map<String, String> graph,
                             Set<String> visited, Set<String> inStack) {
        if (inStack.contains(current)) return true;
        if (visited.contains(current)) return false;

        visited.add(current);
        inStack.add(current);

        String next = graph.get(current);
        if (next != null && hasCycle(next, graph, visited, inStack)) {
            return true;
        }

        inStack.remove(current);
        return false;
    }
}