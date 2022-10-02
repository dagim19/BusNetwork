import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Network {
    private final String NETWORK_FILE_NAME = "network.txt";
    int nubLines;
    int avail;
    boolean changed;

    ArrayList<Line> lines;
    HashSet<Integer> lineNumbers;

    public Network() {
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
                    int distance = in.readInt();
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

    public int lineIndex(int lineNumber) {
        for (int i = 0; i < nubLines; i++) {
            if (lines.get(i).getLineNumber() == lineNumber)
                return i;
        }
        return -1;
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

    public int noLinesPerStation(int stnId) {
        int count = 0;
        for (Line line: lines) {
            if (line.searchStation(stnId) != -1)
                count++;
        }
        return count;
    }

    public Line getLine(int lineNumber) {
        for(Line l: lines)
            if(l.getLineNumber() == lineNumber)
                return l;
        return null;
    }

    public int LineThroughStations(int stnId1, int stnId2) {
        for (Line line: lines) {
            if (line.searchStation(stnId1) != -1 && line.searchStation(stnId2) != -1)
                return line.getLineNumber();
        }
        return -1;
    }

    public int directLine(int stnId1, int stnId2) {
        for (Line line: lines) {
            if (line.searchStation(stnId1) != -1 && line.searchStation(stnId2) != -1)
                return line.getLineNumber();
        }
        return -1;
    }

    public void displayLinesForward() {
        for (Line line: lines) {
            line.traverseForward();
            StdOut.println("===========================================");
        }
    }

    public void displayLinesBackward() {
        for (Line line: lines) {
            line.traverseBackward();
        }
    }

    public float distance(int stnId1, int stnId2) {
        int lineNo = LineThroughStations(stnId1, stnId2);
        if (lineNo == -1)
            return -1;
        Line line = lines.get(lineIndex(lineNo));
        int index1 = line.searchStation(stnId1);
        int index2 = line.searchStation(stnId2);
        if (index1 == -1 || index2 == -1)
            return -1;
        return line.getStation(index2).getDistance() - line.getStation(index1).getDistance();
    }

    // return the shortest path between two stations
    public List<StationData> shortestPath(int stnId1, int stnId2) {
        List<StationData> path = new ArrayList<StationData>();
        int lineNo = LineThroughStations(stnId1, stnId2);
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
