public class Shape implements ShapeInterface
{
	
	private RBTree<String,Triangle> trianglesRBT = new RBTree<String,Triangle>();
	private RBTree<String,Edge> edgesRBT = new RBTree<String,Edge>();
	private RBTree<String,Point> pointsRBT = new RBTree<String,Point>();
	private LinkedList<Triangle> triangles = new LinkedList<Triangle>();
	private LinkedList<Edge> edges = new LinkedList<Edge>();
	private LinkedList<Point> points = new LinkedList<Point>();
	private int globalTime = 0;
	private int Proper = 0;
	private int semiProper = 0;
	private int imProper = 0;
	
	//Ordering triangle base on insertion
	
	//INPUT [x1,y1,z1,x2,y2,z2,x3,y3,z3]
	public boolean ADD_TRIANGLE(float [] triangle_coord) {

		float xac = (triangle_coord[0]-triangle_coord[6]);
		float yac = (triangle_coord[1]-triangle_coord[7]);
		float zac = (triangle_coord[2]-triangle_coord[8]);
		
		float xab = (triangle_coord[0]-triangle_coord[3]);
		float yab = (triangle_coord[1]-triangle_coord[4]);
		float zab = (triangle_coord[2]-triangle_coord[5]);
		
		float i = (yab*zac-yac*zab);
		float j = (xab*zac-zab*xac);
		float k = (xab*yac-xac*yab);
		
		if(i == 0 && j == 0 &&  k == 0) {
			//System.out.println(false);
			//System.out.println(false);
			return false;
		}
		else {
			Point A = new Point(triangle_coord[0],triangle_coord[1],triangle_coord[2]);
			Point B = new Point(triangle_coord[3],triangle_coord[4],triangle_coord[5]);
			Point C = new Point(triangle_coord[6],triangle_coord[7],triangle_coord[8]);
			
			//Node<Point> pHead = points.getHead();
			//if(pHead!=null) {
			Point presentA = null;
			Point presentB = null;
			Point presentC = null;
			//O(N) search for the points can be optimized to O(logN) by using a red black tree.
			presentA = pointsRBT.Search(A.toString());
			presentB = pointsRBT.Search(B.toString());
			presentC = pointsRBT.Search(C.toString());
			
			if(presentA == null) {
				if(presentB == null) {
					if(presentC == null) {
						points.insert(A);
						points.insert(B);
						points.insert(C);
						pointsRBT.insert(A.toString(),A);
						pointsRBT.insert(B.toString(),B);
						pointsRBT.insert(C.toString(),C);
						Edge AB = new Edge(A,B);
						Edge BC = new Edge(B,C);
						Edge CA = new Edge(C,A);
						
						semiProper += 3;
						
						Triangle T = new Triangle(A,B,C,globalTime,AB,BC,CA);
						globalTime += 1;						//Triangle inserted in the points and edges inserted in each of the vertices.
						triangles.insert(T);
						trianglesRBT.insert(T.toString(), T);
						edgesRBT.insert(AB.toString(), AB);
						edgesRBT.insert(BC.toString(), BC);
						edgesRBT.insert(CA.toString(), CA);
						edges.insert(AB);
						edges.insert(BC);
						edges.insert(CA);
						A.insertEdge(CA);
						A.insertEdge(AB);
						B.insertEdge(BC);
						B.insertEdge(AB);
						C.insertEdge(BC);
						C.insertEdge(CA);
					}
					else {
						//C is present
						points.insert(A);
						points.insert(B);
						pointsRBT.insert(A.toString(), A);
						pointsRBT.insert(B.toString(), B);
						Edge AB = new Edge(A,B);
						Edge BC = new Edge(B,presentC);
						Edge CA = new Edge(presentC,A);
						
						semiProper += 3;
						
						Triangle T = new Triangle(A,B,presentC,globalTime,AB,BC,CA);
						globalTime += 1;
						triangles.insert(T);
						trianglesRBT.insert(T.toString(), T);
						edgesRBT.insert(AB.toString(),AB);
						edgesRBT.insert(BC.toString(),BC);
						edgesRBT.insert(CA.toString(),CA);
						edges.insert(AB);
						edges.insert(BC);
						edges.insert(CA);
						A.insertEdge(CA);
						A.insertEdge(AB);
						B.insertEdge(BC);
						B.insertEdge(AB);
						presentC.insertEdge(BC);
						presentC.insertEdge(CA);
					}
				}
				else {
					if(presentC == null) {
						//Only B present
						pointsRBT.insert(A.toString(), A);
						pointsRBT.insert(C.toString(), C);
						points.insert(A);
						points.insert(C);
						Edge AB = new Edge(A,presentB);
						Edge BC = new Edge(presentB,C);
						Edge CA = new Edge(C,A);
						
						semiProper += 3;
						
						edgesRBT.insert(AB.toString(), AB);
						edgesRBT.insert(BC.toString(), BC);
						edgesRBT.insert(CA.toString(), CA);
						Triangle T = new Triangle(A,presentB,C,globalTime,AB,BC,CA);
						globalTime += 1;
						triangles.insert(T);
						trianglesRBT.insert(T.toString(), T);
						edges.insert(AB);
						edges.insert(BC);
						edges.insert(CA);
						A.insertEdge(CA);
						A.insertEdge(AB);
						presentB.insertEdge(BC);
						presentB.insertEdge(AB);
						C.insertEdge(BC);
						C.insertEdge(CA);
					}
					else {
						// B and C present
						pointsRBT.insert(A.toString(), A);
						points.insert(A);
						Edge AB = new Edge(A,presentB);
						Edge CA = new Edge(presentC,A);
						
						semiProper += 2;
						
						Edge BC = new Edge(presentB,presentC);
						BC = presentB.searchEdge(BC);
						A.insertEdge(CA);
						A.insertEdge(AB);
						presentB.insertEdge(AB);
						presentC.insertEdge(CA);
						if(BC==null) {
							BC = new Edge(presentB,presentC);
							presentC.insertEdge(BC);
							presentB.insertEdge(BC);
							edges.insert(BC);
							edgesRBT.insert(BC.toString(),BC);
							semiProper += 1;
						}
						
						if(BC.numTriangles()==1) {
							semiProper -= 1;
							Proper += 1;
						}
						else {
							if(BC.numTriangles()==2) {
								Proper -= 1;
								imProper += 1;
							}
						}
						
						Triangle T = new Triangle(A,presentB,presentC,globalTime,AB,BC,CA);
						globalTime += 1;
						triangles.insert(T);
						trianglesRBT.insert(T.toString(), T);
						edges.insert(AB);
						edges.insert(CA);
						edgesRBT.insert(AB.toString(), AB);
						edgesRBT.insert(CA.toString(), CA);
					}
				}
			}
			else {
				if(presentB == null) {
					if(presentC == null) {
						//Only A is present
						points.insert(B);
						points.insert(C);
						pointsRBT.insert(B.toString(), B);
						pointsRBT.insert(C.toString(), C);
						Edge AB = new Edge(presentA,B);
						Edge BC = new Edge(B,C);
						Edge CA = new Edge(C,presentA);
						
						semiProper += 3;
						
						Triangle T = new Triangle(presentA,B,C,globalTime,AB,BC,CA);
						globalTime += 1;
						triangles.insert(T);
						trianglesRBT.insert(T.toString(),T);
						presentA.insertEdge(CA);
						presentA.insertEdge(AB);
						B.insertEdge(BC);
						B.insertEdge(AB);
						C.insertEdge(BC);
						C.insertEdge(CA);
						edges.insert(AB);
						edges.insert(BC);
						edges.insert(CA);
						edgesRBT.insert(AB.toString(), AB);
						edgesRBT.insert(BC.toString(), BC);
						edgesRBT.insert(CA.toString(), CA);
					}
					else {
						//A and C present
						points.insert(B);
						pointsRBT.insert(B.toString(), B);
						Edge AB = new Edge(presentA,B);
						Edge BC = new Edge(B,presentC);
						
						semiProper += 2;
						
						Edge CA = new Edge(presentC,presentA);
						presentA.insertEdge(AB);
						B.insertEdge(BC);
						B.insertEdge(AB);
						presentC.insertEdge(BC);
						CA = presentA.searchEdge(CA);
						if(CA==null) {
							CA = new Edge(presentC,presentA);
							presentC.insertEdge(CA);
							presentA.insertEdge(CA);
							edges.insert(CA);
							edgesRBT.insert(CA.toString(), CA);
							semiProper += 1;
						}
						else {
							if(CA.numTriangles()==1) {
								semiProper -= 1;
								Proper += 1;
							}
							else {
								if(CA.numTriangles()==2) {
									imProper += 1;
									Proper -= 1;
								}
							}
						}
						Triangle T = new Triangle(presentA,B,presentC,globalTime,AB,BC,CA);
						globalTime += 1;
						triangles.insert(T);
						trianglesRBT.insert(T.toString(), T);
						edges.insert(AB);
						edges.insert(BC);
						edgesRBT.insert(AB.toString(), AB);
						edgesRBT.insert(BC.toString(), BC);
					}
				}
				else {
					if(presentC == null) {
						// A and B present
						points.insert(C);
						pointsRBT.insert(C.toString(), C);
						Edge CA = new Edge(C,presentA);
						Edge BC = new Edge(presentB,C);
						
						semiProper += 2;
						
						Edge AB = new Edge(presentA,presentB);
						presentA.insertEdge(CA);
						presentB.insertEdge(BC);
						C.insertEdge(BC);
						C.insertEdge(CA);
						AB = presentA.searchEdge(AB);
						if(AB==null) {
							AB = new Edge(presentA,presentB);
							presentB.insertEdge(AB);
							presentA.insertEdge(AB);
							edges.insert(AB);
							edgesRBT.insert(AB.toString(), AB);
							semiProper += 1;
						}
						else {
							if(AB.numTriangles()==1) {
								Proper += 1;
								semiProper -= 1;
							}
							else {
								if(AB.numTriangles()==2) {
									imProper += 1;
									Proper -= 1;
								}
							}
						}
						Triangle T = new Triangle(presentA,presentB,C,globalTime,AB,BC,CA);
						globalTime += 1;
						triangles.insert(T);
						trianglesRBT.insert(T.toString(), T);
						edgesRBT.insert(BC.toString(), BC);
						edgesRBT.insert(CA.toString(), CA);
						edges.insert(BC);
						edges.insert(CA);
						
					}
					else {
						Edge CA = new Edge(C,A);
						Edge BC = new Edge(B,C);
						Edge AB = new Edge(A,B);
						AB = presentA.searchEdge(AB);
						BC = presentB.searchEdge(BC);
						CA = presentC.searchEdge(CA);
						if(AB == null) {
							AB = new Edge(presentA,presentB);
							presentA.insertEdge(AB);
							presentB.insertEdge(AB);
							edges.insert(AB);
							edgesRBT.insert(AB.toString(), AB);
							semiProper += 1;
						}
						else {
							if(AB.numTriangles()==1) {
								Proper += 1;
								semiProper -= 1;
							}
							else {
								if(AB.numTriangles()==2) {
									imProper += 1;
									Proper -= 1;
								}
							}
						}
						if(BC == null) {
							BC = new Edge(presentB,presentC);
							presentB.insertEdge(BC);
							presentC.insertEdge(BC);
							edges.insert(BC);
							edgesRBT.insert(BC.toString(),BC);
							semiProper += 1;
						}
						else {
							if(BC.numTriangles()==1) {
								semiProper -= 1;
								Proper += 1;
							}
							else {
								if(BC.numTriangles()==2) {
									imProper += 1;
									Proper -= 1;
								}
							}
						}
						if(CA == null) {
							CA = new Edge(presentC,presentA);
							presentA.insertEdge(CA);
							presentC.insertEdge(CA);
							edges.insert(CA);
							edgesRBT.insert(CA.toString(), CA);
							semiProper += 1;
						}
						else {
							if(CA.numTriangles()==1) {
								semiProper -= 1;
								Proper += 1;
							}
							else {
								if(CA.numTriangles()==2) {
									imProper += 1;
									Proper -= 1;
								}
							}
						}
						
						Triangle T = new Triangle(presentA,presentB,presentC,globalTime,AB,BC,CA);
						globalTime += 1;
						triangles.insert(T);
						trianglesRBT.insert(T.toString(), T);
					}
				}
			}
			//System.out.println("SP: "+semiProper+" "+"P: "+Proper);
			//System.out.println(true);
			return true;
		}
	}
	
	
	public int TYPE_MESH(){
		if(imProper>0) {
			return 3;
		}
		else if(semiProper>0) {
			return 2;
		}
		else {
			return 1;
		}
	}
	
	
	public EdgeInterface [] BOUNDARY_EDGES(){
		
		LinkedList<Edge> result = new LinkedList<Edge>();
		Node<Edge> eHead = edges.getHead();
		Edge curr;
		while(eHead!=null) {
			curr = eHead.getData();
			if(curr.numTriangles()==1) {
				result.insert(curr);
			}
			eHead = eHead.getNext();
		}
		
		Edge [] res = new Edge[result.getSize()];
		eHead = result.getHead();
		if(eHead!=null) {
			int i = 0;
			while(eHead!=null) {
				res[i] = eHead.getData();
				eHead = eHead.getNext();
				i += 1;
			}
		}
		Edge [] resultf = mergeSort(res,0,res.length-1);
		//printArray(resultf);
		return resultf;
	}
	
	//INPUT [x1,y1,z1,x2,y2,z2,x3,y3,z3]
	public TriangleInterface [] NEIGHBORS_OF_TRIANGLE(float [] triangle_coord){
		
		LinkedList<Triangle> result = new LinkedList<Triangle>();
		Triangle target = triangleSearch(triangle_coord);
		if(target!=null) {
			LinkedList<Triangle> A = target.getEdges()[0].triangles();
			LinkedList<Triangle> B = target.getEdges()[1].triangles();
			LinkedList<Triangle> C = target.getEdges()[2].triangles();
			result = merge(A,B,C,target);
			Triangle [] res = new Triangle[result.getSize()];
			Node<Triangle> tHead = result.getHead();
			int i=0;
			while(tHead!=null) {
				Triangle currTriangle = tHead.getData();
				res[i] = currTriangle;
				tHead = tHead.getNext();
				i += 1;
			}
			Triangle [] r = mergeSort(res,0,res.length-1);
			//printArray(r);
			return r;
		}
		return null;
		}
	
	//INPUT [x1,y1,z1,x2,y2,z2,x3,y3,z3]
	public EdgeInterface [] EDGE_NEIGHBOR_TRIANGLE(float [] triangle_coord){
		Triangle target = triangleSearch(triangle_coord);
		if(target != null) {
			Edge [] res = target.getEdges();
			Edge [] r = mergeSort(res,0,res.length-1); 
			//printArray(r);
			return r;
		}
		return null;
		}
	
	//INPUT [x1,y1,z1,x2,y2,z2,x3,y3,z3]
	public PointInterface [] VERTEX_NEIGHBOR_TRIANGLE(float [] triangle_coord){
		Triangle target = triangleSearch(triangle_coord);
		if(target!=null) {
			//printArray(target.triangle_coord());
			return target.triangle_coord();
		}
		return null;
		}
	
	//INPUT [x1,y1,z1,x2,y2,z2,x3,y3,z3]
	public TriangleInterface [] EXTENDED_NEIGHBOR_TRIANGLE(float [] triangle_coord){
		Triangle target = triangleSearch(triangle_coord);
		if(target!=null) {
			Point A = target.vertices()[0];
			Point B = target.vertices()[1];
			Point C = target.vertices()[2];
			LinkedList<Triangle> res = merge(A.getTriangles(),B.getTriangles(),C.getTriangles(),target);
			Triangle [] result = new Triangle[res.getSize()];
			Node<Triangle> tHead = res.getHead();
			int i = 0;
			while(tHead != null) {
				result[i] = tHead.getData();
				tHead = tHead.getNext();
				i += 1;
				}
			//printArray(result);
			return result;
		}
		return null;
	}
	
	//INPUT [x,y,z]
	public TriangleInterface [] INCIDENT_TRIANGLES(float [] point_coordinates){
		Point target = pointsearch(point_coordinates);
		if(target!=null) {
			Triangle [] res = new Triangle[target.getTriangles().getSize()];
			int i=0;
			Node<Triangle> tHead = target.getTriangles().getHead();
			Triangle curr;
			while(tHead!=null) {
				curr = tHead.getData();
				tHead = tHead.getNext();
				res[i] = curr;
				i += 1;
			}
			//Triangle [] r = mergeSort(res,0,res.length-1); 
			//printArray(res);
			return res;
		}
		return null;
		}
	
	// INPUT [x,y,z]
	public PointInterface [] NEIGHBORS_OF_POINT(float [] point_coordinates){
		//System.out.println("Neighbors of point");                    Search not working!
		Point target = pointsearch(point_coordinates); 
		if(target!=null) {
			Point [] res = new Point[target.getEdges().getSize()];
			int i=0;
			Node<Edge> eHead = target.getEdges().getHead();
			Edge currE;
			while(eHead!=null) {
				currE = eHead.getData();
				eHead = eHead.getNext();
				if(currE.getFirst().equals(target)) {
					res[i] = currE.getSecond();
				}
				else {
					res[i] = currE.getFirst();
				}
				i += 1;
			}
			
			//printArray(res);
			return res;
		}
		return null;
		}
	
	// INPUT[x,y,z]
	public EdgeInterface [] EDGE_NEIGHBORS_OF_POINT(float [] point_coordinates){
		Point target = pointsearch(point_coordinates);
		if(target!=null) {
			Edge [] res = new Edge[target.getEdges().getSize()];
			Node<Edge> eHead = target.getEdges().getHead();
			int i=0;
			Edge curr;
			while(eHead!=null) {
				curr = eHead.getData();
				eHead = eHead.getNext();
				res[i] = curr;
				i += 1;
			}
			Edge [] r = mergeSort(res,0,res.length-1); 
			//printArray(r);
			return r;
		}
		return null;
	}
	
	// INPUT[x,y,z]
	public TriangleInterface [] FACE_NEIGHBORS_OF_POINT(float [] point_coordinates){
		Point target = pointsearch(point_coordinates);
		if(target!=null) {
			Triangle [] res = new Triangle[target.getTriangles().getSize()];
			int i=0;
			Node<Triangle> tHead = target.getTriangles().getHead();
			Triangle curr;
			while(tHead!=null) {
				curr = tHead.getData();
				tHead = tHead.getNext();
				res[i] = curr;
				i+=1;
			}
			//printArray(res);
			return res;
		}
		return null;
		}
	
	// INPUT [x1,y1,z1,x2,y2,z2] // where (x1,y1,z1) refers to first end point of edge and  (x2,y2,z2) refers to the second.
	public TriangleInterface [] TRIANGLE_NEIGHBOR_OF_EDGE(float [] edge_coordinates){
		Edge target = edgeSearch(edge_coordinates);
		if(target!=null) {
			Triangle [] res = new Triangle[target.triangles().getSize()];
			int i=0;
			Node<Triangle> tHead = target.triangles().getHead();
			Triangle curr;
			while(tHead!=null) {
				curr = tHead.getData();
				tHead = tHead.getNext();
				res[i] = curr;
				i += 1;
			}

			//printArray(res);
			return res;
		}
		return null;
	}
	
	
	public int COUNT_CONNECTED_COMPONENTS(){
		return BFS();
	}
	
	public boolean IS_CONNECTED(float [] triangle_coord_1, float [] triangle_coord_2){
		
		Point A2 = new Point(triangle_coord_2[0],triangle_coord_2[1],triangle_coord_2[2]);
		Point B2 = new Point(triangle_coord_2[3],triangle_coord_2[4],triangle_coord_2[5]);
		Point C2 = new Point(triangle_coord_2[6],triangle_coord_2[7],triangle_coord_2[8]);
		Triangle t2 = new Triangle(A2,B2,C2,globalTime,new Edge(A2,B2),new Edge(B2,C2),new Edge(C2,A2));
		
		Triangle source = triangleSearch(triangle_coord_1);
		Triangle destination = triangleSearch(triangle_coord_2);
		
		Node<Triangle> pHead = triangles.getHead();
		while(pHead!=null) {
			Triangle p  = pHead.getData();
			p.setColor("WHITE");
			p.setDistance(Integer.MAX_VALUE);
			pHead = pHead.getNext();
		}
		
		if(source!=null && destination!=null) {
			BFSvisitD(source);
			if(destination.getColor().equals("WHITE")) {
				return false;
			}
			return true;
		}
		return false;
	}
	
	
	public int MAXIMUM_DIAMETER(){
		
		int numTriangles = 0;
		int maxDist = 0;
		Node<Triangle> tHead = triangles.getHead();
		Triangle TRI;
		while(tHead!=null) {
			TRI = tHead.getData();
			TRI.setColor("WHITE");
			TRI.setDistance(Integer.MAX_VALUE);
			tHead = tHead.getNext();
		}
		LinkedList<Triangle> comp = null;
		Node<Triangle> h = triangles.getHead();
		while(h!=null) {
			Triangle source = h.getData();
			if(source.getColor().equals("WHITE")) {
				LinkedList<Triangle> curr = BFSvisitll(source);
				if(curr.getSize()>numTriangles) {
					comp = curr;
					numTriangles = curr.getSize();
				}
			}
			h = h.getNext();
		}
		
		if(comp!=null) {
			h = comp.getHead();
			while(h!=null) {
				Triangle source = h.getData();
				Node<Triangle> tHead1 = comp.getHead();
				while(tHead1!=null) {
					TRI = tHead1.getData();
					TRI.setColor("WHITE");
					TRI.setDistance(Integer.MAX_VALUE);
					tHead1 = tHead1.getNext();
				}
				int curr = BFSvisitD(source);
				if(curr>maxDist) {
					maxDist = curr;
				}
				h = h.getNext();
			}
		//System.out.println(maxDist);
		}
		return maxDist;
	}
	
	
	public PointInterface CENTROID_OF_COMPONENT (float [] point_coordinates) {
		
		int count;
		Point p = pointsearch(point_coordinates);
		float x = 0;
		float y = 0;
		float z = 0;
		if(p!=null) {

			//Reset
			
			Node<Point> ph = points.getHead();
			while(ph!=null) {
				Point p1 = ph.getData();
				p1.setComponent(0);
				ph = ph.getNext();
			}
			
			Triangle t = p.getTriangles().getHead().getData();
			count = 0;
			Triangle TRI;
			Node<Triangle> tHead = triangles.getHead();
			
			//Reset
			
			while(tHead!=null) {
				TRI = tHead.getData();
				TRI.setColor("WHITE");
				TRI.setDistance(Integer.MAX_VALUE);
				tHead = tHead.getNext();
			}

			t.setColor("GRAY");
			t.setDistance(0);
			LinkedList<Triangle> Q = new LinkedList<Triangle>();
			Q.insert(t);
			while(Q.getSize()>0) {
				TRI = Q.remove();
				Edge [] edges = TRI.getEdges();
				Edge curr;
				Point [] points = (Point [])TRI.triangle_coord();
				for(int i=0;i<3;i++) {
					Point p1 = points[i];
					if(p1.getComponent() == 0) {
						count += 1;
						p1.setComponent(1);
						x += p1.getX();
						y += p1.getY();
						z += p1.getZ();
					}
				}
				for(int i=0;i<3;i++) {
					curr = edges[i];
					tHead = curr.triangles().getHead();
					Triangle neighbour;
					while(tHead!=null) {
						if(tHead.getData().getColor().equals("WHITE")) {
							neighbour = tHead.getData();
							Q.insert(neighbour);
							neighbour.setColor("GRAY");
						}
						tHead = tHead.getNext();
					}
				}
				TRI.setColor("BLACK");
			}
			x /= count;
			y /= count;
			z /= count;
			Point res = new Point(x,y,z); 
			//System.out.println(res);
			return res;
		}
		return null;
	}
	
	
	public PointInterface [] CENTROID (){
		
		Node<Point> ph = points.getHead();
		while(ph!=null) {
			Point p = ph.getData();
			p.setComponent(0);
			ph = ph.getNext();
		}
		
		Node<Triangle> tHead = triangles.getHead();
		Triangle TRI;
		while(tHead!=null) {
			TRI = tHead.getData();
			TRI.setColor("WHITE");
			TRI.setDistance(Integer.MAX_VALUE);
			tHead = tHead.getNext();
		}
		
		tHead = triangles.getHead();
		int component = 0;
		LinkedList<Point> centroids = new LinkedList<Point>();
		
		while(tHead!=null) {
			if(tHead.getData().getColor().equals("WHITE")) {
				LinkedList<Point> allCentroids = new LinkedList<Point>();
				Triangle t = tHead.getData();
				component += 1;
				t.setColor("GRAY");
				t.setDistance(0);
				Point [] points;
				LinkedList<Triangle> Q = new LinkedList<Triangle>();
				Q.insert(t);
				while(Q.getSize()>0) {
					TRI = Q.remove();
					Edge [] edges = TRI.getEdges();
					Edge curr;
					points = (Point [])TRI.triangle_coord();
					for(int i=0;i<3;i++) {
						Point p = points[i];
						if(p.getComponent() != component) {
							p.setComponent(component);
							allCentroids.insert(p);
						}
					}
					for(int i=0;i<3;i++) {
						curr = edges[i];
						Node<Triangle> tHead1 = curr.triangles().getHead();
						Triangle neighbour;
						while(tHead1!=null) {
							if(tHead1.getData().getColor().equals("WHITE")) {
								neighbour = tHead1.getData();
								Q.insert(neighbour);
								neighbour.setColor("GRAY");
								neighbour.setDistance(TRI.getDistance()+1);
							}
						tHead1 = tHead1.getNext();
						}
					}
					TRI.setColor("BLACK");
				}
			Node<Point> tHead2 = allCentroids.getHead();
			float x = 0;
			float y = 0;
			float z = 0;
			while(tHead2 != null) {
				Point p1 = tHead2.getData();
				x += p1.getX();
				y += p1.getY();
				z += p1.getZ();
				tHead2 = tHead2.getNext();
				}
			x /= allCentroids.getSize();
			y /= allCentroids.getSize();
			z /= allCentroids.getSize();
			centroids.insert(new Point(x,y,z));
			}
			tHead = tHead.getNext();
		}
	Point [] res = new Point[centroids.getSize()];
	Node<Point> p3 = centroids.getHead();
	int i = 0;
	while(p3!=null) {
		res[i] = p3.getData();
		i += 1;
		p3 = p3.getNext();
	}
	//printArray(res);
	return res;
}

	
	public 	PointInterface [] CLOSEST_COMPONENTS(){

		Node<Triangle> tHead = triangles.getHead();
		Triangle TRI;
		while(tHead!=null) {
			TRI = tHead.getData();
			TRI.setColor("WHITE");
			TRI.setDistance(Integer.MAX_VALUE);
			tHead = tHead.getNext();
		}
		
		Node<Point> pHead = points.getHead();
		while(pHead!=null) {
			Point p = pHead.getData();
			p.setComponent(0);
			pHead = pHead.getNext();
		}
	
		int count = 0;
	
		tHead = triangles.getHead();
		while(tHead!=null) {
			if(tHead.getData().getColor().equals("WHITE")) {
				count += 1;
				Point p = BFSvisit(tHead.getData(),count);
				if(p!=null) {
					Point [] arr = new Point[2];
					arr[0] = p;
					arr[1] = p;
					//System.out.println(0);
					//printArray(arr);
					return arr;
				}
			}
		tHead = tHead.getNext();
	}
		float minDist = -1;
		Point [] point = new Point[2];
		Node<Point> h1 = points.getHead();
		while(h1!=null) {
			Point p1 = h1.getData();
			Node<Point> h2 = points.getHead();
			while(h2!=null) {
				Point p2 = h2.getData();
				if(p1.getComponent()!=p2.getComponent()) {
					float currDist = p1.distanceSqrd(p2);
					if(currDist<minDist || minDist == -1) {
						minDist = currDist;
						point[0] = p1;
						point[1] = p2;
					}
				}
			h2 = h2.getNext();
		}
		h1 = h1.getNext();
	}
	//printArray(point);
	if(minDist == -1) {
		minDist = 0;
	}
	//System.out.println(minDist);
	//printArray(point);
	return point;
}
	
	
		//Auxiliary functions
		
	public void printArray(Object [] arr) {
		for(int i=0;i<arr.length;i++) {
			if(i!=arr.length-1) {
			System.out.print(arr[i]+" ");
			}
			else {
				System.out.println(arr[i]);
			}
		}
	}
	
	
	public Edge edgeSearch(float [] edge_coordinates) {
		
		float [] first = new float[3];
		float [] second = new float[3];
		Point f = new Point(edge_coordinates[0],edge_coordinates[1],edge_coordinates[2]);
		Point s = new Point(edge_coordinates[3],edge_coordinates[4],edge_coordinates[5]);
		Edge curr = new Edge(f,s);
		curr = edgesRBT.Search(curr.toString());
		return curr;
	}
	
	
	public Triangle triangleSearch(float [] triangle_coord) {
		//O(log(N)) !!
		Point A = new Point(triangle_coord[0],triangle_coord[1],triangle_coord[2]);
		Point B = new Point(triangle_coord[3],triangle_coord[4],triangle_coord[5]);
		Point C = new Point(triangle_coord[6],triangle_coord[7],triangle_coord[8]);
		Triangle curr = new Triangle(A,B,C,globalTime,new Edge(A,B),new Edge(B,C),new Edge(C,A));
		curr = trianglesRBT.Search(curr.toString());
		return curr;
	}
	
	
	public Point pointsearch(float [] point_coordinates) {
		//Node<Point> pHead = points.getHead();
		
		Point curr = new Point(point_coordinates[0],point_coordinates[1],point_coordinates[2]);
		curr = pointsRBT.Search(curr.toString());
		return curr;
	}
	
	
	public Triangle [] mergeSort(Triangle [] arr,int left,int right) {
		if(arr.length>0) {
			if(left==right) {
				Triangle [] res = new Triangle[1];
				res[0] = arr[left];
				return res;
			}
			else {
				int mid = (left+right)/2;
				Triangle [] l = mergeSort(arr,left,mid);
				Triangle [] r = mergeSort(arr,mid+1,right);
				Triangle [] res = merge(l,r);
				return res;
			}
		}
		return null;
	}
	
	
	public Triangle [] merge(Triangle [] l,Triangle [] r) {
		int i = 0;
		int j = 0;
		int k = 0;
		int m = l.length;
		int n = r.length;
		Triangle [] res = new Triangle [m+n];
		
		while(k<m+n) {
			if(i==m) {
				res[k] = r[j];
				j += 1;
			}
			
			else if(j==n) {
				res[k] = l[i];
				i += 1;
			}
			
			else if(l[i].compareTo(r[j])<0) {
				res[k] = l[i];
				i += 1;
			}
			
			else {
				res[k] = r[j];
				j += 1;
			}
			k+=1;
		}
		return res;
	}
	
	
	public Edge [] mergeSort(Edge [] arr,int left,int right) {
		if(arr.length>0) {
			if(left==right) {
				Edge [] res = new Edge[1];
				res[0] = arr[left];
				return res;
			}
			else {
				int mid = (left+right)/2;
				Edge [] l = mergeSort(arr,left,mid);
				Edge [] r = mergeSort(arr,mid+1,right);
				Edge [] res = merge(l,r);
				return res;
			}
		}
		return null;
	}
	

	public Edge [] merge(Edge [] l,Edge [] r) {
		int i = 0;
		int j = 0;
		int k;
		int m = l.length;
		int n = r.length;
		Edge [] res = new Edge [m+n];
		
		while(i+j<m+n) {
			k = i+j;
			if(i==m) {
				res[k] = r[j];
				j += 1;
			}
			
			else if(j==n) {
				res[k] = l[i];
				i += 1;
			}
			
			else if(l[i].compareTo(r[j])<0) {
				res[k] = l[i];
				i += 1;
			}
			
			else {
				res[k] = r[j];
				j += 1;
			}
		}
		return res;
	}
		
	
	public int BFS() {
		
		Node<Triangle> tHead = triangles.getHead();
		Triangle TRI;
		while(tHead!=null) {
			TRI = tHead.getData();
			TRI.setColor("WHITE");
			TRI.setDistance(Integer.MAX_VALUE);
			tHead = tHead.getNext();
		}
		
		int count = 0;
		
		tHead = triangles.getHead();
		while(tHead!=null) {
			if(tHead.getData().getColor().equals("WHITE")) {
				count += 1;
				BFSvisitD(tHead.getData());
			}
			tHead = tHead.getNext();
		}
		return count;
	}
	
	//Used for calculating diameter and max_distance
	
	public int BFSvisitD(Triangle t) {
		
		int diam = 0;
		Triangle TRI;
		Node<Triangle> tHead;
		t.setColor("GRAY");
		t.setDistance(0);
		LinkedList<Triangle> Q = new LinkedList<Triangle>();
		Q.insert(t);
		while(Q.getSize()>0) {
			TRI = Q.remove();
			Edge [] edges = TRI.getEdges();
			Edge curr;
			for(int i=0;i<3;i++) {
				curr = edges[i];
				tHead = curr.triangles().getHead();
				Triangle neighbour;
				while(tHead!=null) {
					neighbour = tHead.getData();
					if(neighbour.getColor().equals("WHITE") && neighbour.compareTo(TRI)!=0) {
						Q.insert(neighbour);
						neighbour.setColor("GRAY");
						neighbour.setDistance(TRI.getDistance()+1);
						if(TRI.getDistance()+1>diam) {
							diam = TRI.getDistance()+1;
						}
					}
					tHead = tHead.getNext();
				}
			}
			TRI.setColor("BLACK");
		}
		return diam;
	}
	
	
	public LinkedList<Triangle> BFSvisitll(Triangle t) {
		
		LinkedList<Triangle> res = new LinkedList<Triangle>();
		Triangle TRI;
		Node<Triangle> tHead;
		t.setColor("GRAY");
		t.setDistance(0);
		LinkedList<Triangle> Q = new LinkedList<Triangle>();
		Q.insert(t);
		while(Q.getSize()>0) {
			TRI = Q.remove();
			res.insert(TRI);
			Edge [] edges = TRI.getEdges();
			Edge curr;
			for(int i=0;i<3;i++) {
				curr = edges[i];
				tHead = curr.triangles().getHead();
				Triangle neighbour;
				while(tHead!=null) {
					neighbour = tHead.getData();
					if(neighbour.getColor().equals("WHITE") && neighbour.compareTo(TRI)!=0) {
						Q.insert(neighbour);
						neighbour.setColor("GRAY");
						neighbour.setDistance(TRI.getDistance()+1);
					}
					tHead = tHead.getNext();
				}
			}
			TRI.setColor("BLACK");
		}
		return res;
	}
	
	
	public Point BFSvisit(Triangle t, int comp) {
		
		Triangle TRI;
		Node<Triangle> tHead;
		t.setColor("GRAY");
		t.setDistance(0);
		Point [] points;
		LinkedList<Triangle> Q = new LinkedList<Triangle>();
		Q.insert(t);
		while(Q.getSize()>0) {
			TRI = Q.remove();
			Edge [] edges = TRI.getEdges();
			Edge curr;
			points = (Point [])TRI.triangle_coord();
			for(int i=0;i<3;i++) {
				Point p = points[i];
				if(p.getComponent() == 0) {
					p.setComponent(comp);
				}
				else if(p.getComponent()!= comp) {
					return p;
				}
			}
			for(int i=0;i<3;i++) {
				curr = edges[i];
				tHead = curr.triangles().getHead();
				Triangle neighbour;
				while(tHead!=null) {
					if(tHead.getData().getColor().equals("WHITE")) {
						neighbour = tHead.getData();
						Q.insert(neighbour);
						neighbour.setColor("GRAY");
						neighbour.setDistance(TRI.getDistance()+1);
					}
				tHead = tHead.getNext();
				}
			}
			TRI.setColor("BLACK");
		}
		return null;
	}

	
	public Point [] mergeSort(Point [] arr,int left,int right) {
		if(arr.length>0) {
			if(left==right) {
				Point [] res = new Point[1];
				res[0] = arr[left];
				return res;
			}
			else {
				int mid = (left+right)/2;
				Point [] l = mergeSort(arr,left,mid);
				Point [] r = mergeSort(arr,mid+1,right);
				Point [] res = merge(l,r);
				return res;
			}
		}
		return null;
	}
	
	public LinkedList<Triangle> merge(LinkedList<Triangle> A, LinkedList<Triangle> B, LinkedList<Triangle> C, Triangle target){
		
		Node<Triangle> a = A.getHead();
		Node<Triangle> b = B.getHead();
		Node<Triangle> c = C.getHead();
		LinkedList<Triangle> res = new LinkedList<Triangle>();
		while(a!=null || b!=null) {
			if(a==null) {
				if(b.getData().compareTo(target)!=0) {
					res.insert(b.getData());
				}
				b = b.getNext();
			}
			else if(b==null) {
				if(a.getData().compareTo(target)!=0) {
					res.insert(a.getData());
				}
				a = a.getNext();
			}
			else if(a.getData().compareTo(b.getData())<0) {
				if(a.getData().compareTo(target)!=0) {
					res.insert(a.getData());
				}
				a = a.getNext();
			}
			else if(b.getData().compareTo(a.getData())<0){
				if(b.getData().compareTo(target)!=0) {
					res.insert(b.getData());
				}
				b = b.getNext();
			}
			else {
				if(a.getData().compareTo(target)!=0) {
					res.insert(a.getData());
				}
				a = a.getNext();
				b = b.getNext();
			}
		}
	
		LinkedList<Triangle> result = new LinkedList<Triangle>();
		Node<Triangle> d = res.getHead();
	
		while(c!=null || d!=null) {
			if(c==null) {
				if(d.getData().compareTo(target)!=0) {
					result.insert(d.getData());
				}
				d = d.getNext();
			}
			else if(d==null) {
				if(c.getData().compareTo(target)!=0) {
					result.insert(c.getData());
				}
				c = c.getNext();
			}
			else if(c.getData().compareTo(d.getData())<0) {
				if(c.getData().compareTo(target)!=0) {
					result.insert(c.getData());
				}
				c = c.getNext();
			}
			else if(d.getData().compareTo(c.getData())<0) {
				if(d.getData().compareTo(target)!=0) {
					result.insert(d.getData());
				}
				d = d.getNext();
			}
			else {
				if(c.getData().compareTo(target)!=0) {
					result.insert(c.getData());
				}
				c = c.getNext();
				d = d.getNext();
			}
		}
	return result;
	}
	
	
	public Point [] merge(Point [] l,Point [] r) {
		int i = 0;
		int j = 0;
		int k = 0;
		int m = l.length;
		int n = r.length;
		Point [] res = new Point [m+n];
		
		while(k<m+n) {
			if(i==m) {
				res[k] = r[j];
				j += 1;
			}
			
			else if(j==n) {
				res[k] = l[i];
				i += 1;
			}
			
			else if(l[i].compareTo(r[j])<0) {
				res[k] = l[i];
				i += 1;
			}
			
			else {
				res[k] = r[j];
				j += 1;
			}
			k+=1;
		}
		return res;
	}
	
}
//Diam
//Diameter -1 to be corrected.
//Validity of Triangles in isConnected
//Duplicate Queries ADD_TRIANGLE that is triangle which is already present.
//equals for array not working, change that