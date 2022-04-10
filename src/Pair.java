public class Pair<A, B> {
    public final A y;
    public final B x;

    public Pair(A a, B b) {
        this.y = a;
        this.x = b;
    }

    @Override
    public String toString() {
        return x + " " + y;
    }
}
