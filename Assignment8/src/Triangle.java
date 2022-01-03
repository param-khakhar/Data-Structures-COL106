
public class Triangle implements TriangleInterface, Comparable<Triangle> {

	private int time;
	private Point [] coord = new Point[3];
	private Edge [] edges = new Edge[3];
	private String color;
	private int distance;
	
	public Triangle(Point a,Point b,Point c,int t,Edge AB,Edge BC,Edge CA) {
		
		coord[0] = a;
		coord[1] = b;
		coord[2] = c;
		a.insertTriangle(this);
		b.insertTriangle(this);
		c.insertTriangle(this);
		time = t;
		edges[0] = AB;
		edges[1] = BC;
		edges[2] = CA;
		AB.insertTriangle(this);
		BC.insertTriangle(this);
		CA.insertTriangle(this);
	}
	
	public void setDistance(int d) {
		distance = d;
	}
	
	public int getDistance() {
		return distance;
	}
	
	public void setColor(String c) {
		color = c;
	}
	
	public String getColor() {
		return color;
	}
	
	@Override
	public PointInterface[] triangle_coord() {
		// TODO Auto-generated method stub
		return coord;
	}
	
	public Point[] vertices() {
		return coord;
	}
	
	public int getTime() {
		return time;
	}
	
	public Edge [] getEdges(){
		return edges;
	}
	
	public int compareTo(Triangle t) {
		if(t.getTime()>time) {
			return -1;
		}
		else if(t.getTime()<time) {
			return 1;
		}
		else {
			return 0;
		}
	}
	
	public String toString() {
		Point first;
		Point second;
		Point third;
		if(coord[0].compareTo(coord[1])<0) {
			if(coord[0].compareTo(coord[2])<0) {
				if(coord[1].compareTo(coord[2])<0) {
					first = coord[0];
					second = coord[1];
					third = coord[2];
				}
				else {
					first = coord[0];
					second = coord[2];
					third = coord[1];
				}
			}
			else {
					first = coord[2];
					second = coord[0];
					third = coord[1];
			}
		}
		else {
			if(coord[1].compareTo(coord[2])<0) {
				if(coord[0].compareTo(coord[2])<0) {
					first = coord[1];
					second = coord[0];
					third = coord[2];
				}
				else {
					first = coord[1];
					second = coord[2];
					third = coord[0];
				}
			}
			else {
				first = coord[2];
				second = coord[1];
				third = coord[0];
			}
		}
		//return first.toString()+" "+second.toString()+third.toString();
		return "["+first.toString()+","+second.toString()+","+third.toString()+"]";	
	}
}
