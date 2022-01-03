
public class Edge implements EdgeInterface,Comparable<Edge> {

	private LinkedList<Triangle> triangles = new LinkedList<Triangle>();
	private Point [] EndPoints = new Point[2];
	private float lenSqrd;
	
	public Edge(Point p1,Point p2) {
		
		if(p1.compareTo(p2)<0) {
			EndPoints[0] = p1;
			EndPoints[1] = p2;
		}
		else {
			EndPoints[1] = p1;
			EndPoints[0] = p2;
		}
		lenSqrd = p1.distanceSqrd(p2);
	}
	
	public Point getFirst() {
		return EndPoints[0];
	}
	
	public Point getSecond() {
		return EndPoints[1];
	}
	
	@Override
	public PointInterface[] edgeEndPoints() {
		// TODO Auto-generated method stub
		return EndPoints;
	}
	
	public void insertTriangle(Triangle t) {
		triangles.insert(t);
	}
	
	public int numTriangles() {
		return triangles.getSize();
	}
	
	public LinkedList<Triangle> triangles(){
		return triangles;
	}
	
	public float getLenSqrd() {
		return lenSqrd;
	}
	
	public int compareTo(Edge e) {
		if(e.getLenSqrd()<lenSqrd) {
			return 1;
		}
		else if(e.getLenSqrd() == lenSqrd) {
			return 0;
		}
		else {
			return -1;
		}
	}
	
	public String toString() {
		if(EndPoints[0].compareTo(EndPoints[1])<0) {
			return "["+EndPoints[0].toString()+","+EndPoints[1].toString()+"]";
		}
		else {
			return "["+EndPoints[1].toString()+","+EndPoints[0].toString()+"]";
		}
	}
}
