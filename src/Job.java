import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Job {
    private int startY;
    private int startX;
    private int endY;
    private int endX;
    private String product;

    public Job(String jobFile){
        String line;
        String[] coordinates;
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(jobFile));
            line = bufferedReader.readLine();
            coordinates = line.split(" ");
            startY = Integer.parseInt( coordinates[0]);
            startX = Integer.parseInt( coordinates[1]);

            line = bufferedReader.readLine();
            coordinates = line.split(" ");
            endY = Integer.parseInt( coordinates[0]);
            endX = Integer.parseInt( coordinates[1]);

            product = bufferedReader.readLine();
        } catch(IOException e){
            System.out.println("There was en error while reading file\n" + e);
            System.exit(1);
        }
    }

    public int getStartY() {
        return startY;
    }

    public int getStartX() {
        return startX;
    }

    public int getEndY() {
        return endY;
    }

    public int getEndX() {
        return endX;
    }

    public String getProduct() {
        return product;
    }
}
