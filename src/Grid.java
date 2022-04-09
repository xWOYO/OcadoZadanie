import java.util.ArrayList;
import java.util.HashMap;

public class Grid {
    private final Character[][] storage;
    private HashMap<String, ArrayList<DesiredLocation>> products;
    private final int x;
    private final int y;
    public Grid(int x, int y){
        storage = new Character[y][x];
        products = new HashMap<>();
        this.x = x;
        this.y = y;
    }

    public void fillRow(String row, int rowIndex){
        for(int i = 0; i < x; i++){
            storage[rowIndex][i] = row.charAt(i);
        }
    }

    public void addStoredProduct(String product){
        String[] productData = product.split(" ");
        int[] coordinates;
        if(products.get(productData[0]) == null){
            products.put(productData[0], new ArrayList<>());
        }
        products.get(productData[0]).add(new DesiredLocation(Integer.parseInt(productData[1]), Integer.parseInt(productData[2]), Integer.parseInt(productData[3])));
    }

    public void printGraph(){
        for(int i = 0; i < y; i++){
            for(int j = 0; j < x; j++){
                System.out.println(storage[i][j]);
            }
            System.out.println("\n");
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Character[][] getStorage() {
        return storage;
    }

    public HashMap<String, ArrayList<DesiredLocation>> getProducts() {
        return products;
    }
}
