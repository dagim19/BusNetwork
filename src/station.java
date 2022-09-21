public class station implements Comparable {
    private String name;
    private int stationID;

    public station(String name, int stationID) {
        this.name = name;
        this.stationID = stationID;
    }

    public String getName() {
        return name;
    }

    public int getStationID() {
        return stationID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStationID(int stationID) {
        this.stationID = stationID;
    }

    @Override
    public int compareTo(Object o) {
        station s = (station) o;
        if (s.stationID == this.stationID) return 0;
        else if (s.stationID > this.stationID) return -1;
        else return 1;
    }
}
