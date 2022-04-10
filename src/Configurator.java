import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Configurator {
    private final String gridFile;

    public Configurator(String gridFile) {
        this.gridFile = gridFile;
    }

    public Grid ReadFromJobFile() {
        Grid warehouse = null;
        int x;
        int y;
        String line;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(gridFile));
            line = bufferedReader.readLine();

            String[] sizes = line.split(" ");
            x = Integer.parseInt(sizes[0]);
            y = Integer.parseInt(sizes[1]);

            warehouse = new Grid(x, y);
            for (int i = 0; i < y; i++) {
                warehouse.fillRow(bufferedReader.readLine(), i);
            }

            while ((line = bufferedReader.readLine()) != null) {
                warehouse.addStoredProduct(line);
            }
        } catch (IOException e) {
            System.out.println("There was en error while reading file\n" + e);
            System.exit(1);
        }
        return warehouse;
    }

    public String getGridFile() {
        return gridFile;
    }
}
