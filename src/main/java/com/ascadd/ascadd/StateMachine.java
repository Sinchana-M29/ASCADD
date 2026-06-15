package com.ascadd.ascadd;

public class StateMachine {

    // Advances the aircraft to the next state in the sequence
    public void transition(Aircraft aircraft) {
        switch (aircraft.getState()) {
            case TAXIING:
                aircraft.setState(AircraftState.HOLDING);
                System.out.println(aircraft.getCallsign() + " is now HOLDING.");
                break;
            case HOLDING:
                aircraft.setState(AircraftState.CLEARED);
                System.out.println(aircraft.getCallsign() + " is now CLEARED.");
                break;
            case CLEARED:
                System.out.println(aircraft.getCallsign() + " is already CLEARED. No further transition.");
                break;
        }
    }
}