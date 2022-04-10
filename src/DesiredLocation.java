public class DesiredLocation {
    private Pair<Integer, Integer> coordinates;
    private int zPosition;

    public DesiredLocation(int x, int y, int z) {
        this.coordinates = new Pair<>(y, x);
        this.zPosition = z;
    }

    public Pair<Integer, Integer> getCoordinates() {
        return coordinates;
    }

    public int getzPosition() {
        return zPosition;
    }
}
