
public class Point implements PointInterface, Comparable<Point> {
	
	private float [] XYZ = new float[3];
	//private LinkedList<Edge> degrees = new LinkedList<Edge>();
	private LinkedList<Triangle> faceNeighbours = new LinkedList<Triangle>();
	private RBTree<String,Triangle> faceNeighboursRBT = new RBTree<String,Triangle>();
	//private LinkedList<Point> edgeNeighbours = new LinkedList<Point>();
	private LinkedList<Edge> edges = new LinkedList<Edge>();
	private RBTree<String,Edge> edgesRBT = new RBTree<String,Edge>();
	private int component = 0;
	
	Point(float x,float y,float z) {
		
		XYZ[0] = x;
		XYZ[1] = y;
		XYZ[2] = z;
	}
	
	public void setComponent(int i) {
		component = i;
	}
	
	public int getComponent() {
		return component;
	}
	
	public LinkedList<Triangle> getTriangles(){
		return faceNeighbours;
	}
	
	public LinkedList<Edge> getEdges(){
		return edges;
	}
	
	/*public void insertPoint(Point p) {
		edgeNeighbours.insert(p);
	}*/
	
	public void insertEdge(Edge e) {
		edges.insert(e);
		edgesRBT.insert(e.toString(), e);
	}
	
	public Edge searchEdge(Edge e) {
		return edgesRBT.Search(e.toString());
	}
	
	public void insertTriangle(Triangle t) {
		faceNeighbours.insert(t);
		faceNeighboursRBT.insert(t.toString(), t);
	}
	@Override
	public float getX() {
		// TODO Auto-generated method stub
		return XYZ[0];
	}

	@Override
	public float getY() {
		// TODO Auto-generated method stub
		return XYZ[1];
	}

	@Override
	public float getZ() {
		// TODO Auto-generated method stub
		return XYZ[2];
	}

	@Override
	public float[] getXYZcoordinate() {
		// TODO Auto-generated method stub
		return XYZ;
	}
	
	
	public boolean equals(Point p) {
		float [] coord = p.getXYZcoordinate();
		if(coord[0]==XYZ[0]) {
			if(coord[1]==XYZ[1]) {
				if(coord[2] == XYZ[2]) {
					return true;
				}
			}
		}
		return false;
	}
	
	public int compareTo(Point p) {
		if(XYZ[0]<p.getX()) {
			return -1;
		}
		else if(XYZ[0]>p.getX()) {
			return 1;
		}
		else {
			if(XYZ[1]<p.getY()) {
				return -1;
			}
			else if(XYZ[1]>p.getY()) {
				return 1;
			}
			else {
				if(XYZ[2]<p.getZ()) {
					return -1;
				}
				else if(XYZ[2]>p.getZ()) {
					return 1;
				}
				else {
					return 0;
				}
			}
		}
	}
	
	public float distanceSqrd(Point p) {
		float sqX = (XYZ[0]-p.getX())*(XYZ[0]-p.getX());
		float sqY = (XYZ[1]-p.getY())*(XYZ[1]-p.getY());
		float sqZ = (XYZ[2]-p.getZ())*(XYZ[2]-p.getZ());
		return sqX+sqY+sqZ;
	}
	
	public String toString() {
		return "("+XYZ[0]+","+XYZ[1]+","+XYZ[2]+")";
	}
}
