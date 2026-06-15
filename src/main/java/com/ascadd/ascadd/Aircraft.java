package com.ascadd.ascadd;

public class Aircraft {

    private String callsign;
    private String currentSegment;
    private String targetSegment;
    private AircraftState state;

    public Aircraft(String callsign, String currentSegment, String targetSegment) {
        this.callsign = callsign;
        this.currentSegment = currentSegment;
        this.targetSegment = targetSegment;
        this.state = AircraftState.TAXIING;
    }

    public String getCallsign() { return callsign; }
    public String getCurrentSegment() { return currentSegment; }
    public String getTargetSegment() { return targetSegment; }
    public AircraftState getState() { return state; }

    public void setState(AircraftState state) { this.state = state; }
    public void setCurrentSegment(String segment) { this.currentSegment = segment; }

    @Override
    public String toString() {
        return callsign + " [" + currentSegment + " → " + targetSegment + "] " + state;
    }
}