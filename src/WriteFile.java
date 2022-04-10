import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;

public class WriteFile {
    private final Pair<Double, ArrayList<Pair<Integer, Integer>>> timeWithRoute;

    public WriteFile(Pair<Double, ArrayList<Pair<Integer, Integer>>> timeWithRoute) {
        this.timeWithRoute = timeWithRoute;
    }

    public void writeData() {
        double time = timeWithRoute.y;
        String timeString = String.format(Locale.US, "%.1f", time);
        ArrayList<Pair<Integer, Integer>> route = timeWithRoute.x;
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("results.txt", false));
            writer.println(route.size() - 1);
            writer.println(timeString);
            for (Pair<Integer, Integer> pair : route) {
                writer.println(pair.toString());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("There was en error while writing file\n" + e);
            System.exit(1);
        }
    }
}
