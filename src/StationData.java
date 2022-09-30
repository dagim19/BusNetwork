public class StationData {
    private int stnId;
    private float distance;

    public StationData(int stnId, float distance) {
        this.stnId = stnId;
        this.distance = distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void setStnId(int stnId) {
        this.stnId = stnId;
    }

    public float getDistance() {
        return distance;
    }

    public int getStnId() {
        return   stnId;
    }

}
