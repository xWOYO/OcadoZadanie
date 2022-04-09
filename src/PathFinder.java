import java.util.*;

public class PathFinder {
    private final Grid warehouse;
    private final Job job;

    public PathFinder(Grid warehouse, Job job, int x, int y) {
        this.warehouse = warehouse;
        this.job = job;
    }


    public Pair<Double, ArrayList<Pair<Integer, Integer>>> findProducts(){
        HashMap<Double, ArrayList<Pair<Integer, Integer>>> foundRoutes = new HashMap<>();
        String productName = job.getProduct();
        ArrayList<DesiredLocation> locations = warehouse.getProducts().get(productName);
        Pair<Double, ArrayList<Pair<Integer, Integer>>> outcome;
        for (DesiredLocation location : locations) {
            outcome = sumTraveltimes(location);
            foundRoutes.put(outcome.y, outcome.x) ;
        }
        double lowestTime = Double.MAX_VALUE;
        for(Double time : foundRoutes.keySet()){
            if(time < lowestTime){
                lowestTime = time;
            }
        }
        return new Pair<>(lowestTime, foundRoutes.get(lowestTime));
    }

    private Pair<Double, ArrayList<Pair<Integer, Integer>>> sumTraveltimes(DesiredLocation target){
        Pair<Integer, Integer> startingBlock = new Pair<>(job.getStartY(), job.getStartX());
        Pair<Integer, Integer> productBlock = new Pair<>(target.getCoordinates().y, target.getCoordinates().x);
        DesiredLocation endBlock = new DesiredLocation(job.getEndX(), job.getEndY(), 0);

        Pair<Double, ArrayList<Pair<Integer, Integer>>> toProduct = calculateTime(startingBlock, target);
        Pair<Double, ArrayList<Pair<Integer, Integer>>> toEnd = calculateTime(productBlock, endBlock);
        double overallTime = extractTime(warehouse.getStorage()[target.getCoordinates().y][target.getCoordinates().x], target.getzPosition()) + toProduct.y + toEnd.y;
        ArrayList<Pair<Integer, Integer>> fullRoute =  toEnd.x;
        fullRoute.remove(toEnd.x.size() - 1);
        fullRoute.addAll(toProduct.x);
        Collections.reverse(fullRoute);
        return new Pair<>(overallTime, fullRoute);
    }



    public Pair<Double, ArrayList<Pair<Integer, Integer>>> calculateTime(Pair<Integer, Integer> start, DesiredLocation end){
        Set<ArrayList<Pair<Integer, Integer>>> paths = findPaths(start, end, null);
        double[] pathTimes = new double[paths.size()];
        int incrementer = 0;
        HashMap<Double, ArrayList<Pair<Integer, Integer>>> timeWithPaths = new HashMap<>();
        for (ArrayList<Pair<Integer, Integer>> arr : paths) {
            double time = 0;
            int y = -1;
            int x = -1;
            for (Pair<Integer, Integer> p : arr) {
                if (x != -1 && y != -1) {
                    time += findWeight(warehouse.getStorage()[y][x], warehouse.getStorage()[p.y][p.x]);
                }
                y = p.y;
                x = p.x;
            }
            timeWithPaths.put(time, arr);
            incrementer++;
        }
        double lowestTime = Double.MAX_VALUE;
        for (Double d : timeWithPaths.keySet()) {
            if(d < lowestTime){
                lowestTime = d;
            }
        }
        return new Pair<>(lowestTime, timeWithPaths.get(lowestTime));
    }



    private Set<ArrayList<Pair<Integer, Integer>>> findPaths(Pair<Integer, Integer> position, DesiredLocation product, boolean[][] visited) {
        boolean[][] localVisited;
        if(visited == null){
            localVisited = new boolean[warehouse.getY()][warehouse.getX()];
        }else{
            localVisited = copyVisited(visited);
        }
        localVisited[position.y][position.x] = true;

        if(position.y.equals(product.getCoordinates().y) && position.x.equals(product.getCoordinates().x)){
            ArrayList<Pair<Integer, Integer>> trail = new ArrayList<>();
            trail.add(new Pair<>(position.y, position.x));
            return Set.of(trail);
        }

        Set<ArrayList<Pair<Integer, Integer>>> trailSet = new java.util.HashSet<>(Set.of());

        for(Pair<Integer, Integer> pair : findNeighbours(position.y, position.x, localVisited)){
            trailSet.addAll(findPaths(pair, product, localVisited));
        }
        for(ArrayList<Pair<Integer, Integer>> list : trailSet){
            list.add(new Pair<>(position.y, position.x));
        }
        return trailSet;
    }

    public boolean[][] copyVisited(boolean[][] visited){
        int y = visited.length;
        int x = visited[0].length;
        boolean[][] copiedVisited = new boolean[y][x];
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                copiedVisited[i][j] = visited[i][j];
            }
        }
        return copiedVisited;
    }

    private ArrayList<Pair<Integer, Integer>> findNeighbours(int yPosition, int xPosition, boolean[][] visited){
        ArrayList<Pair<Integer, Integer>> neighbours = new ArrayList<>();
        if(yPosition - 1 >= 0 && warehouse.getStorage()[yPosition - 1][xPosition] != 'O' && !visited[yPosition - 1][xPosition]){
            neighbours.add(new Pair<>(yPosition - 1, xPosition));
        }
        if(yPosition + 1 < warehouse.getY() && warehouse.getStorage()[yPosition + 1][xPosition] != 'O' && !visited[yPosition + 1][xPosition]){
            neighbours.add(new Pair<>(yPosition + 1, xPosition));
        }

        if(xPosition - 1 >= 0 && warehouse.getStorage()[yPosition][xPosition - 1] != 'O' && !visited[yPosition][xPosition - 1]){
            neighbours.add(new Pair<>(yPosition, xPosition - 1));
        }
        if(xPosition + 1 < warehouse.getX() && warehouse.getStorage()[yPosition][xPosition + 1] != 'O' && !visited[yPosition][xPosition + 1]){
            neighbours.add(new Pair<>(yPosition, xPosition + 1));
        }

        return neighbours;
    }

    private double findWeight(char from, char to){
        double a = convertWeights(from);
        double b = convertWeights(to);
        return Math.max(a, b);
    }

    private double extractTime(char code, int depth){
        switch (code) {
            case 'H':
                return 3 * depth + 4;
            case 'B':
                return 2 * depth + 2;
            case 'S':
                return depth + 1;
            default:
                return 0;

        }
    }

    private double convertWeights(char code){
        switch (code) {
            case 'H':
                return 0.5;
            case 'B':
                return 1.0;
            case 'S':
                return 2.0;
            default:
                return 0;

        }
    }
}
