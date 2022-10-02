import java.util.ArrayList;

public class Line {
    private int lineNumber;
    private int numberOfStations;
    private StationData start;
    ArrayList<StationData> stationList;

    // constructor
    public Line(int lineNumber, int numberOfStations) {
        this.lineNumber = lineNumber;
        this.numberOfStations = numberOfStations;
        stationList = new ArrayList<StationData>();
        start = null;
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

    public StationData getStart() {
        return start;
    }

    public void setStart(StationData start) {
        this.start = start;
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

    public boolean containsStation(int stnId) {
        for (StationData s: stationList) {
            if (s.getStnId() == stnId)
                return true;
        }
        return false;
    }

    public float distanceBetweenStations(int stnId1, int stnId2) {
        int index1 = searchStation(stnId1);
        int index2 = searchStation(stnId2);
        if (index1 == -1 || index2 == -1)
            return -1;
        if (index1 > index2) {
            int temp = index1;
            index1 = index2;
            index2 = temp;
        }
        float distance = 0;
        for (int i = index1; i < index2; i++) {
            distance += stationList.get(i).getDistance();
        }
        return distance;
    }



    public StationData getStation(int index2) {
        return stationList.get(index2);
    }
}
