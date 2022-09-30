import java.util.ArrayList;

public class Line {
    private int lineNumber;
    private int numberOfStations;

    private ArrayList<StationData> stationList;

    // constructor
    public Line(int lineNumber, int numberOfStations) {
        this.lineNumber = lineNumber;
        this.numberOfStations = numberOfStations;
        stationList = new ArrayList<StationData>();
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public int getNumberOfStations() {
        return numberOfStations;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public void setNumberOfStations(int numberOfStations) {
        this.numberOfStations = numberOfStations;
    }

    public boolean addStation(StationData station) {
        if (stationList.add(station)) {
            numberOfStations++;
            return true;
        }
        return false;
    }

    public boolean deleteStation(StationData station) {
        if (stationList.remove(station)) {
            numberOfStations--;
            return true;
        }
        return false;
    }


    // traverse forward
    public void traverseForward() {
        for (StationData s: stationList) {
            StdOut.println(s.getStnId() + " " + s.getDistance());
        }
    }

    // traverse backward
    public void traverseBackward() {
        for (int i = stationList.size() - 1; i >= 0; i--) {
            StdOut.println(stationList.get(i).getStnId() + " " + stationList.get(i).getDistance());
        }
    }

    public int searchStation(int stnId) {
        for (int i = 0; i < numberOfStations; i++) {
            if (stationList.get(i).getStnId() == stnId)
                return i;
        }
        return -1;
    }

    public StationData getStation(int index2) {
        return stationList.get(index2);
    }
}
