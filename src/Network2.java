import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Network2 {
    private final String NETWORK_FILE_NAME = "network.txt";
    int nubLines;
    int avail;
    boolean changed;

    ArrayList<Line> lines;
    HashSet<Integer> lineNumbers;

    public Network2() {
        lines = new ArrayList<Line>();
        lineNumbers = new HashSet<>();
        nubLines = 0;
        avail = 0;
        changed = false;
    }

    public boolean buildNetwork() {
        int nubLines;
        try {
            In in = new In(NETWORK_FILE_NAME);
            nubLines = in.readInt();
            for (int i = 0; i < nubLines; i++) {
                int lineNumber = in.readInt();
                int numberOfStations = in.readInt();
                lineNumbers.add(lineNumber);
                Line line = new Line(lineNumber, numberOfStations);
                for (int j = 0; j < numberOfStations; j++) {
                    int stnId = in.readInt();
                    float distance = in.readFloat();
                    StationData stn = new StationData(stnId, distance);
                    line.addStation(stn);
                    if(j == 0) line.setStart(stn);
                }
                lines.add(line);
            }
        }catch(IllegalArgumentException e) {
            return false;
        }
        this.nubLines = nubLines;
        return true;
    }

    public boolean updateNetworkFile() {
        if (changed) {
            try {
                Out out = new Out(NETWORK_FILE_NAME);
                out.println(nubLines);
                for (Line line: lines) {
                    out.println(line.getLineNumber() + " " + line.getNumberOfStations());
                    line.traverseForward();
                }
            }catch(IllegalArgumentException e) {
                return false;
            }
            changed = false;
        }
        return true;
    }

    // The function determine the number of lines passing through a station
    public int noLinesPerStation(int stationID) {
        int count = 0;
        for (Line line: lines) {
            if (line.containsStation(stationID)) count++;
        }
        return count;
    }

    // The function returns the first line, the second line and so on passing through a station controlled by the order value
    public int LineThrStation(int stationID, int order) {
        int count = 0;
        for (Line line: lines) {
            if (line.containsStation(stationID)) {
                count++;
                if (count == order) return line.getLineNumber();
            }
        }
        return -1;
    }

    private int lineIndex(int lineNumber) {
        for (int i = 0; i < nubLines; i++) {
            if (lines.get(i).getLineNumber() == lineNumber)
                return i;
        }
        return -1;
    }

    // The function returns whether there is a direct line between two stations using a given line number/id
    public boolean directLine(int stationID1, int stationID2, int lineNumber) {
        Line line = lines.get(lineIndex(lineNumber));
        return line.containsStation(stationID1) && line.containsStation(stationID2);
    }


    // The function returns the distance between two stations in terms of actual distance
    public float distance(int stationID1, int stationID2) {
        float distance = 0;
        for (Line line: lines) {
            if (line.containsStation(stationID1) && line.containsStation(stationID2)) {
                distance = line.distanceBetweenStations(stationID1, stationID2);
                break;
            }
        }
        return distance;
    }

    // the function returns sub part of a line controlled by start station and destination station
    public Line subLine(int lineNumber, int startStation, int destinationStation) {
        Line line = lines.get(lineIndex(lineNumber));
        Line subLine = new Line(lineNumber, 0);
        boolean start = false;
        for (StationData station: line.stationList) {
            if (station.getStnId() == startStation) start = true;
            if (start) subLine.addStation(station);
            if (station.getStnId() == destinationStation) break;
        }
        return subLine;
    }


    // return the shortest path between two stations
    public List<StationData> shortestPath(int stnId1, int stnId2) {
        List<StationData> path = new ArrayList<StationData>();
        int lineNo = LineThrStation(stnId1, stnId2);
        if (lineNo == -1)
            return null;
        Line line = lines.get(lineIndex(lineNo));
        int index1 = line.searchStation(stnId1);
        int index2 = line.searchStation(stnId2);
        if (index1 == -1 || index2 == -1)
            return null;
        if (index1 < index2) {
            for (int i = index1; i <= index2; i++) {
                path.add(line.getStation(i));
            }
        } else {
            for (int i = index1; i >= index2; i--) {
                path.add(line.getStation(i));
            }
        }
        return path;
    }


}
