import java.util.Random;
import java.util.ArrayList;

public class Maze{

    private int n;
    private int m;
    private double r;
    private boolean solveable = false;
//this is terrible, but my best current implementation of tracking what parents are in what directions, for usage see line 213    
    private ArrayList<Cell> tonorth = new ArrayList<Cell>();
    private ArrayList<Cell> towest = new ArrayList<Cell>();
    private ArrayList<Cell> tosouth = new ArrayList<Cell>();
    private ArrayList<Cell> toeast = new ArrayList<Cell>();

    
    public Maze(int en, double ar){
        n = en;
        m = en;
        r = ar;
    }

    public Maze(int en, int em, double ar){
        n = en;
        m = em;
        r = ar;
    }

    public int getn(){
        return n;
    }

    public int getm(){
        return m;
    }

    public double getr(){
        return r;
    }

    public boolean getsolveable(){
        return solveable;
    }

    public ArrayList<Cell> gettonorth(){
        return tonorth;
    }

     public ArrayList<Cell> gettowest(){
        return towest;
    }

     public ArrayList<Cell> gettosouth(){
        return tosouth;
    }

     public ArrayList<Cell> gettoeast(){
        return toeast;
    }

    
    class Cell{
        int x;
        int y;
        int distance = Integer.MAX_VALUE;
        boolean nor;
        boolean wes;
        boolean sou;
        boolean eas;
        Cell parent = null;
        public Cell(int ex, int why, boolean north, boolean west, boolean south, boolean east){
            x = ex;
            y = why;
            nor = north;
            wes = west;
            sou = south;
            eas = east;
        }
        public int getX(){
            return x;
        }

        public int getY(){
            return y;
        }
        
        public boolean getNorth(){
            return nor;
        }

        public boolean getWest(){
            return wes;
        }

        public boolean getSouth(){
            return sou;
        }

        public boolean getEast(){
            return eas;
        }

        public int getDistance(){
            return distance;
        }

        public Cell getParent(){
            return parent;
        }

        public void setNorth(boolean wall){
            nor = wall;
        }

        public void setWest(boolean wall){
            wes = wall;
        }

        public void setSouth(boolean wall){
            sou = wall;
        }

        public void setEast(boolean wall){
            eas = wall;
        }

        public void setDistance(int dis){
            distance = dis;
        }

        public void setParent(Cell c){
            parent = c;
        }

        public void incDistance(){
            distance ++;
        }
    }

    public Cell[][] generateGraph(){
        Cell[][] graph = new Cell[n][m];
        for (int i = 0; i < n; i++){
            for (int j = 0; j < m; j++){
                graph[i][j] = new Cell(i, j, false, false, false, false);
            }
        }
        int max_walls = (2*n*m) + n + m;
        int walls_to_make = (int)(r * max_walls);

        Random randall = new Random();

        for (int i = 0; i < walls_to_make; i++){
            int locx = randall.nextInt(n);
            int locy = randall.nextInt(m);
            int dir = randall.nextInt(4);

            if (dir == 0){
                if (graph[locy][locx].getNorth() == true){ i--;}
                graph[locy][locx].setNorth(true);
                if (locy > 0){
                    graph[locy - 1][locx].setSouth(true);
                }
            }

            else if (dir == 1){
                if (graph[locy][locx].getWest() == true){ i--;}
                graph[locy][locx].setWest(true);
                if (locx > 0){
                    graph[locy][locx - 1].setEast(true);
                }
            }

            else if (dir == 2){
                if (graph[locy][locx].getSouth() == true){ i--;}
                graph[locy][locx].setSouth(true);
                if (locy < n - 1){
                    graph[locy + 1][locx].setNorth(true);
                }
            }

            else if (dir == 3){
                if (graph[locy][locx].getEast() == true){ i--;}
                graph[locy][locx].setEast(true);
                if (locx < m - 1){
                    graph[locy][locx + 1].setWest(true);
                }
            }
        }
        return graph;
    }
    private void incrementadjacentcells(Cell[][] chell, int n, int m){
        System.out.println(n + " " + m);
        if (chell[n][m].getParent() != null){
            chell[n][m].setDistance(chell[n][m].getParent().getDistance() + 1);
        }
        else{
            chell[n][m].setDistance(0);
        }
        if (n > 0 && chell[n][m].getNorth() == false && chell[n-1][m].getDistance() > chell[n][m].getDistance() + 1){
            chell[n - 1][m].setParent(chell[n][m]);
            incrementadjacentcells(chell, n - 1, m);
        }
        if (m > 0 && chell[n][m].getWest() == false && chell[n][m-1].getDistance()  > chell[n][m].getDistance() + 1){
            chell[n][m - 1].setParent(chell[n][m]);
            incrementadjacentcells(chell, n, m - 1);
        }
        if (n < chell.length - 1 && chell[n][m].getSouth() == false && chell[n+1][m].getDistance()  > chell[n][m].getDistance() + 1){
            chell[n + 1][m].setParent(chell[n][m]);
            incrementadjacentcells(chell, n + 1, m);
        }
        if (m < chell[0].length - 1 && chell[n][m].getEast() == false && chell[n][m+1].getDistance()  > chell[n][m].getDistance() + 1){
            chell[n][m + 1].setParent(chell[n][m]);
            incrementadjacentcells(chell, n, m + 1);
        }
    }

   /* ArrayList<Cell> trace = new ArrayList<Cell>();
    private ArrayList<Cell> backtrace(Cell[][] chell, Cell c){
        trace.add(c);
        if (c.getParent() == null){
            return trace;
        }
        else{
            return backtrace(chell, c.getParent());
        }
    }*/
    
    private ArrayList<Cell> backtrace(Cell start){
        Cell current = start;
        ArrayList<Cell> trace = new ArrayList<Cell>();
        try{
        do{
            trace.add(current);
            if (current.getParent().getX() < current.getX()){
                tonorth.add(current);
                tosouth.add(current.getParent());
            }
            if (current.getParent().getY() < current.getY()){
                towest.add(current);
                toeast.add(current.getParent());
            }
            if (current.getParent().getX() > current.getX()){
                tosouth.add(current);
                tonorth.add(current.getParent());
            }
            if (current.getParent().getY() > current.getY()){
                toeast.add(current);
                towest.add(current.getParent());
            }
            current = current.getParent();
        }
        while (current != null);
        }
        catch (NullPointerException e) {};
        return trace;

    }

    

    public ArrayList<Cell> solveMaze(Cell[][] chell){
        ArrayList<Cell> solutionpath = new ArrayList<Cell>();
        incrementadjacentcells(chell, 0,0);
        if (chell[chell.length -1][chell[0].length - 1].getDistance() == -1){
            return null;
        }
        else{
            solveable = true;
            solutionpath = backtrace(chell[chell.length - 1][chell[0].length - 1]);
            return solutionpath;
        }
    }

}
