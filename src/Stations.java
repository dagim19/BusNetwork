import java.util.ArrayList;

public class Stations {
    private final String STATIONS_FILE_NAME = "stations.txt";
    private int numberOfStations;
    private int avail;
    private boolean changed;
    private ArrayList<Station> stationList;

    public Stations() {
        stationList = new ArrayList<Station>();
        numberOfStations = 0;
        avail = 0;
        changed = false;
    }

    boolean buildStations() {
        int numberOfStations;
        try {
            In in = new In(STATIONS_FILE_NAME);
            numberOfStations = in.readInt();
            for (int i = 0; i < numberOfStations; i++) {
                String name = in.readString();
                int stationID = in.readInt();
                stationList.add(new Station(name, stationID));
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

    public boolean addStation(Station station) {
        if (stationList.add(station)) {
            numberOfStations++;
            changed = true;
            return true;
        }
        return false;
    }

    public boolean deleteStation(Station station) {
        if (stationList.remove(station)) {
            numberOfStations--;
            changed = true;
            return true;
        }
        return false;
    }

    public int mapStation(String stationName) {
        for (Station s: stationList) {
            if(s.getName() == stationName)
                return s.getStationID();
        }
        return -1;
    }

    public String mapStation(int stationId) {
        for (Station s: stationList) {
            if(s.getStationID() == stationId)
                return s.getName();
        }
        return null;
    }

    public void updateStnFile() {
        if (changed) {
            try {
                Out out = new Out(STATIONS_FILE_NAME);
                out.println(numberOfStations);
                for (Station s: stationList) {
                    out.println(s.getName() + " " + s.getStationID());
                }
            }catch(IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

}
