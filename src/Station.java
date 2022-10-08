public class Station implements Comparable {
    private String name;
    private int stationID;

    public Station(String name, int stationID) {
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

    // Implement the equals method
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Station)) return false;
        Station s = (Station) o;
        return s.getStationID() == this.getStationID();
    }

    @Override
    public int compareTo(Object o) {
        Station s = (Station) o;
        if (s.stationID == this.stationID) return 0;
        else if (s.stationID > this.stationID) return -1;
        else return 1;
    }
}
