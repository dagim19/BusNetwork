import java.util.ArrayList;

public class stations {
    private int numberOfStations;
    private int avail;
    private boolean changed;
    private ArrayList<station> stationList;

    public stations() {
        stationList = new ArrayList<station>();
        numberOfStations = 0;
        avail = 0;
        changed = false;
    }

    boolean buildStations(String stfFileName) {
        int numberOfStations;
        try {
            In in = new In(stfFileName);
            numberOfStations = in.readInt();
            for (int i = 0; i < numberOfStations; i++) {
                String name = in.readString();
                int stationID = in.readInt();
                stationList.add(new station(name, stationID));
            }
        }catch(IllegalArgumentException e) {
            return false;
        }
        this.numberOfStations = numberOfStations;
        return true;
    }

    public int getNumberOfStations() {
        return numberOfStations;
    }

    public boolean addStation(station station) {
        if (stationList.add(station)) {
            numberOfStations++;
            changed = true;
            return true;
        }
        return false;
    }

    public boolean deleteStation(station station) {
        if (stationList.remove(station)) {
            numberOfStations--;
            changed = true;
            return true;
        }
        return false;
    }

    public int mapStation(String stationName) {
        for (station s: stationList) {
            if(s.getName() == stationName)
                return s.getStationID();
        }
        return -1;
    }

    public String mapStation(int stationId) {
        for (station s: stationList) {
            if(s.getStationID() == stationId)
                return s.getName();
        }
        return null;
    }

}
