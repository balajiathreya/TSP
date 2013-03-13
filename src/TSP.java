import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: balaji
 * Date: 3/12/13
 * Time: 12:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class TSP {
    public static String fileName  = "/home/balaji/Downloads/example3.txt";
    public static HashMap<Integer,Point> vertices = new HashMap<Integer, Point>();
    public static ArrayList<Integer> verticesWithOddDegree = new ArrayList<Integer>();
    public static TreeSet<String> completeGraphVertices = new TreeSet<String>();
    public static TreeSet<String> multiGraph = new TreeSet<String>();
    public static TreeMap<Double,String> edgesByWeight = new TreeMap<Double,String>();

    public static void main(String[] args){
        readInput();
        MST T = new MST();
        T.constructMST(vertices);
        LinkedHashSet selectedVertices = T.getSelectedVertices();
        Iterator it1 = selectedVertices.iterator();
        while (it1.hasNext()){
            System.out.println(it1.next().toString());
        }
        System.out.println();
        getVerticesWithOddDegree(T);
        getPerfectMatching(T);
        calculateCost();

    }

    public static void readInput(){
        BufferedReader br = null;
        int i = 0;
        try {
            String line;
            br = new BufferedReader(new FileReader(fileName));
            while ((line = br.readLine()) != null) {
                i++;
                Point point = new Point();
                String[] arr = line.split(" ");
                int id = Integer.valueOf(arr[0]);
                point.setId(id);
                point.setX(Integer.valueOf(arr[1]));
                point.setY(Integer.valueOf(arr[2]));
                point.setDegree(0);
                vertices.put(id,point);
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void getVerticesWithOddDegree(MST T){
        Iterator it = T.getSelectedVertices().iterator();
        verticesWithOddDegree = new ArrayList<Integer>();
        int prev = -1;
        while (it.hasNext()){
            String e = (String) it.next();
            String[] arr = e.split("-");
            int v1 = Integer.valueOf(arr[0]);
            int v2 = Integer.valueOf(arr[1]);
            int d1 = vertices.get(v1).getDegree();
            int d2 = vertices.get(v2).getDegree();
            vertices.get(v1).setDegree(++d1);
            vertices.get(v2).setDegree(++d2);
        }
        it = vertices.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry entry = (Map.Entry)it.next();
            Point p = (Point)entry.getValue();
            if(p.getDegree()%2 == 1)
                verticesWithOddDegree.add(p.getId());
        }
    }

    public static void getPerfectMatching(MST T){
        Iterator it1 = verticesWithOddDegree.iterator();
        Iterator it2 = verticesWithOddDegree.iterator();
        while (it1.hasNext()){
            int i = (Integer)it1.next();
            while (it2.hasNext()){
                int j = (Integer) it2.next();
                if(i != j){
                    Point p1 = vertices.get(i);
                    Point p2 = vertices.get(j);
                    long distance = MST.getDistance(p1,p2);
                    if(p2.getId() > p1.getId())
                        completeGraphVertices.add(distance+","+p1.getId()+"-"+p2.getId());
                    else
                        completeGraphVertices.add(distance+","+p2.getId()+"-"+p1.getId());
                }
            }
        }
        Iterator it = T.getEdges().iterator();
        while (it.hasNext()){
            String e = it.next().toString();
            multiGraph.add(e);
        }

        it = completeGraphVertices.iterator();
        while (it.hasNext()){
            String e = it.next().toString();
            e = e.split(",")[1];
            multiGraph.add(e);
        }
    }

    public static void calculateCost(){
        Iterator it = multiGraph.iterator();
        long distance = 0;
        while (it.hasNext()){
            String e = it.next().toString();
            System.out.println(e);
            String[] arr = e.split("-");
            Point p1 = vertices.get(Integer.valueOf(arr[0]));
            Point p2 = vertices.get(Integer.valueOf(arr[1]));
            long d = MST.getDistance(p1,p2);
            distance += d;

        }
        System.out.println(distance);
    }
}

class Point{
    private int id;
    private int x;
    private int y;
    private int degree;
    private long cost = Long.MAX_VALUE;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

}

