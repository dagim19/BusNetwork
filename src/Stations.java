import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;

public class Stations {
    private final static String STATIONS_FILE_NAME = "stations.txt";
    private static int numberOfStations;
    private int avail;
    private boolean changed;
    private static DList<Station> stationList;
    static HashSet<Integer> stnIds;

    public Stations() {
        stationList = new DList<>();
        stnIds = new HashSet<>();
        numberOfStations = 0;
        avail = 0;
        changed = false;
    }

    static void buildStations() {
        try {
            In in = new In(STATIONS_FILE_NAME);
            numberOfStations = in.readInt();
            for (int i = 0; i < numberOfStations; i++) {
                int stationID = in.readInt();
                String name = in.readString();
                stnIds.add(stationID);
                stationList.add(new Station(name, stationID));
            }
        }catch(IllegalArgumentException e) {
            Out out = new Out(STATIONS_FILE_NAME);
            out.println("0");
            out.close();
            buildStations();
        }
    }

    public int getNumberOfStations() {
        return numberOfStations;
    }

    public boolean addStation(Station station) {
        if (stationList.add(station)) {
            numberOfStations++;
            changed = true;
            stnIds.add(station.getStationID());
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

    boolean updateStnFile() {
        if (changed) {
            try {
                Out out = new Out(STATIONS_FILE_NAME);
                out.println(numberOfStations);
                for (Station s: stationList) {
                    out.println(s.getName() + " " + s.getStationID());
                }
            }catch(IllegalArgumentException e) {
                return false;
            }
            changed = false;
        }
        return true;
    }

    public boolean changeStnId(int stnId, int newStnId) {
        if (stnIds.contains(stnId)) {
            for (Station s: stationList) {
                if (s.getStationID() == stnId) {
                    s.setStationID(newStnId);
                    stnIds.remove(stnId);
                    stnIds.add(newStnId);
                    changed = true;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean undoChanges() {
        if (changed) {
            buildStations();
            changed = false;
            return true;
        }
        return false;
    }
}
