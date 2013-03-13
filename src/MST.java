import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created with IntelliJ IDEA.
 * User: balaji
 * Date: 3/12/13
 * Time: 12:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class MST {
    private LinkedHashSet<String> edges = new LinkedHashSet<String>();
    private LinkedHashSet<Integer> selectedVertices = new LinkedHashSet<Integer>();
    private PriorityBlockingQueue<Point> pq;

    public LinkedHashSet<String> getEdges() {
        return edges;
    }

    public void setEdges(LinkedHashSet<String> edges) {
        this.edges = edges;
    }

    public LinkedHashSet<Integer> getSelectedVertices() {
        return selectedVertices;
    }

    public void setSelectedVertices(LinkedHashSet<Integer> selectedVertices) {
        this.selectedVertices = selectedVertices;
    }

    public PriorityBlockingQueue<Point> getPq() {
        return pq;
    }

    public void setPq(PriorityBlockingQueue<Point> pq) {
        this.pq = pq;
    }

    public void constructMST(HashMap<Integer,Point> vertices){
        CostComparator costComparator = new CostComparator();
        pq = new PriorityBlockingQueue<Point>(11,costComparator);
        Random random = new Random();
        int randomVertex = random.nextInt(vertices.size());
        Point randomPoint = vertices.get(randomVertex);
        randomPoint.setCost(0);
        makeQueue(vertices, randomPoint);
        while (pq.size() > 0){
            Point min = pq.poll();
            if(min != null){
                selectedVertices.add(min.getId());
                addEdge();
                Iterator it = pq.iterator();
                while (it.hasNext()){
                    Point point = (Point)it.next();
                    if(pq.contains(point) && !point.equals(min)){
                        long distance = getDistance(point,min);
                        if(point.getCost() > distance){
                            point.setCost(distance);
                            pq.offer(point);
                        }
                    }
                }
            }
        }
    }


    public static long getDistance(Point p1, Point p2){
        int diffX = p1.getX() - p2.getX();
        int diffY = p1.getY() - p2.getY();
        return (long)Math.abs(Math.sqrt((diffX*diffX + diffY*diffY)));
    }

    public void makeQueue(HashMap<Integer,Point> vertices,Point randomPoint){
        pq.add(randomPoint);
        Iterator it = vertices.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry entry = (Map.Entry)it.next();
            Point point = (Point)entry.getValue();
            if(!point.equals(randomPoint))
                pq.add(point);
        }
    }

    public void addEdge(){
        if(selectedVertices.size() > 1){
            int current = (Integer)selectedVertices.toArray()[selectedVertices.size() - 1];
            int prev = (Integer)selectedVertices.toArray()[selectedVertices.size() - 2];
            if(current > prev){
                String edge = prev+"-"+current;
                if(!edges.contains(edge))
                    edges.add(edge);
            }
            else if(current < prev){
                String edge = current+"-"+prev;
                if(!edges.contains(edge))
                    edges.add(edge);
            }
        }
    }
}

class CostComparator implements Comparator<Point>{
    public int compare(Point px, Point py){
        if(px.getCost() > py.getCost())
            return 1;
        else if(py.getCost() > px.getCost())
            return 1;
        return 0;
    }
}




