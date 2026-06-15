package com.ascadd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaxiwayNetwork {

    // Map of segment → list of segments it connects to
    private Map<String, List<String>> adjacencyMap;

    public TaxiwayNetwork() {
        adjacencyMap = new HashMap<>();
    }

    // Add a one-way connection between two segments
    public void addConnection(String from, String to) {
        adjacencyMap.putIfAbsent(from, new ArrayList<>());
        adjacencyMap.get(from).add(to);
    }

    // Get all segments reachable from a given segment
    public List<String> getNeighbours(String segment) {
        return adjacencyMap.getOrDefault(segment, new ArrayList<>());
    }

    // Get all segments in the network
    public Map<String, List<String>> getAdjacencyMap() {
        return adjacencyMap;
    }
}