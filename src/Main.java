import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Configurator configurator = new Configurator(args[0]);
        Grid warehouse = configurator.ReadFromJobFile();
        Job job = new Job(args[1]);

        PathFinder pathFinder = new PathFinder(warehouse, job, warehouse.getX(), warehouse.getY());
        Pair<Double, ArrayList<Pair<Integer, Integer>>> bestRoute = pathFinder.findProductWithTimeAndRoute();

        WriteFile writeFile = new WriteFile(bestRoute);
        writeFile.writeData();
    }

}
