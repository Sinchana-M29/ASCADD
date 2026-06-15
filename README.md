# ✈ ASCADD — Airport Surface Congestion & Taxiway Deadlock Detector

A JavaFX desktop simulation that tracks aircraft movement across airport
taxiway networks using a **state machine** and detects deadlocks using a
**DFS-based cycle detection algorithm**.

## 🚀 Features
- Real-time aircraft state tracking (TAXIING → HOLDING → CLEARED)
- Visual taxiway map with color-coded aircraft indicators
- Deadlock detection using wait-for graph + DFS cycle detection
- CSV export for Power BI analytics dashboard
- Git version controlled with meaningful commit history

## 🛠️ Tech Stack
| Layer | Technology |
|---|---|
| Language | Java 17 |
| UI Framework | JavaFX 21 |
| Build Tool | Maven |
| Analytics | Power BI |
| Version Control | Git + GitHub |

## 🧠 Core Logic

### State Machine
Every aircraft follows this state transition:

TAXIING → HOLDING → CLEARED

### Deadlock Detection Algorithm
1. Build a **wait-for graph** from HOLDING aircraft
2. Aircraft A waits for Aircraft B if A's target = B's current segment
3. Run **DFS cycle detection** on the graph
4. If a cycle exists → **DEADLOCK DETECTED** 🔴

## 📊 Power BI Dashboard
The app exports simulation data to CSV which feeds a Power BI dashboard with:
- Aircraft state distribution (Pie Chart)
- Taxiway segment congestion (Bar Chart)
- Deadlock status KPI (Card)
- Full aircraft details (Table)

## 🏃 How to Run
1. Clone the repository
2. Open in IntelliJ IDEA
3. Run `HelloApplication.java`
4. Add aircraft, transition states, detect deadlocks
5. Export CSV and open in Power BI

## 📁 Project Structure
src/main/java/com/ascadd/

├── AircraftState.java      # Enum: TAXIING, HOLDING, CLEARED

├── Aircraft.java           # Aircraft model with state

├── StateMachine.java       # State transition logic

├── TaxiwayNetwork.java     # Graph of taxiway segments

├── DeadlockDetector.java   # DFS cycle detection algorithm

└── CSVExporter.java        # Exports data for Power BI
src/main/java/com/ascadd/ascadd/

├── HelloApplication.java   # JavaFX entry point

└── MainController.java     # UI controller

## 👨‍💻 Author
Built with ❤️ using Java + JavaFX + Power BI