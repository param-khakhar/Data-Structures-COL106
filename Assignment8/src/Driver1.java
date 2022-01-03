import java.util.ArrayList;

import java.util.Arrays; 

import java.io.*;
class Driver1 {
	
	 public static float distance(Point a , Point b) {
		  float ans = (a.getX()-b.getX())*(a.getX()-b.getX());
			ans += (a.getY()-b.getY())*(a.getY()-b.getY());
			ans += (a.getZ()-b.getZ())*(a.getZ()-b.getZ());
			return ans;
	  }

  public static void main(String[] args) throws IOException {
	//try{
			long tme[] = new long[20];
			
       	    BufferedReader br = null;
            br = new BufferedReader(new FileReader(args[0]));
	    	ShapeInterface shape_intr = new Shape();
			String st;
			//int cnt = 0;
            while ((st = br.readLine()) != null) {
			//	System.out.println(cnt++);
                String[] cmd = st.split(" ");
		//System.out.println("cmd is "+ Arrays.toString(cmd));	

                if (cmd.length == 0) {
                    System.err.println("Error parsing:1 ");
                    return;
                }
                String project_name, user_name;
                Integer start_time, end_time;

                long qstart_time, qend_time;

		ArrayList<Float> inp = new ArrayList<>();
		 int firstskip=0;
		for (String val:cmd) {

			if(0==firstskip){
			firstskip++;
			continue;
			}

			inp.add(Float.parseFloat(val.trim()));
			//System.out.print(val + " ");
		}
		
		//System.out.println("arguments " +Arrays.toString(input.toArray()));


		float[] input = new float[inp.size()]; 
		int k = 0;

		for (Float f : inp) {
		    input[k++] = f; 
		}
                switch (cmd[0]) {
			

		

					case "ADD_TRIANGLE":
					
					System.out.println("Add TriangleInterface");	
					
					long start = System.currentTimeMillis();
					shape_intr.ADD_TRIANGLE(input);
					long  end = System.currentTimeMillis();
					tme[0] += end-start;
                        break;

                    case "TYPE_MESH":
			System.out.println("Checking mesh type");
			int mesh_type = shape_intr.TYPE_MESH();
			System.out.println("Mesh type " + mesh_type);
                        break;
					case "COUNT_CONNECTED_COMPONENTS":
					 start = System.currentTimeMillis();
			System.out.println("Finding number of connected components");
			int count_connected = shape_intr.COUNT_CONNECTED_COMPONENTS();
			System.out.println("Number of connected components = "+ count_connected);
			  end = System.currentTimeMillis();
					tme[1] += end-start;
                        break;
					case "BOUNDARY_EDGES":	
					start = System.currentTimeMillis();
			System.out.println("Getting boundary edges");	
			 
			 EdgeInterface [] boundary_edges= shape_intr.BOUNDARY_EDGES();
			   end = System.currentTimeMillis();
					tme[2] += end-start;
                        break;
					case "IS_CONNECTED":
					start = System.currentTimeMillis();
			System.out.println("CHECKING IS_CONNECTED");	
			float [] triangle1 = new float[9]; 
			float [] triangle2 = new float[9]; 
			for (int i =0;i<9;i++)
			{
				triangle1[i]=input[i];
			}
			for (int i =9;i<18;i++)
			{
				triangle2[i-9]=input[i];
			}
				


			boolean is_con = shape_intr.IS_CONNECTED(triangle1, triangle2);		
			System.out.println("Is connected = " + is_con);
			end = System.currentTimeMillis();
			tme[3] += end-start;
                        break;

					case "NEIGHBORS_OF_POINT":
					start = System.currentTimeMillis();
			System.out.println("FINDING NEIGHBORS_OF_POINT" );
			 PointInterface [] nbrs_of_point = shape_intr.NEIGHBORS_OF_POINT(input);
			 end = System.currentTimeMillis();
			tme[4] += end-start;
			break;

					case "NEIGHBORS_OF_TRIANGLE":
					start = System.currentTimeMillis();
			System.out.println("FINDING NEIGHBORS_OF_TRIANGLE" );
			shape_intr.NEIGHBORS_OF_TRIANGLE(input);
			end = System.currentTimeMillis();
			tme[5] += end-start;
			break;

					case "INCIDENT_TRIANGLES":
					start = System.currentTimeMillis();
			System.out.println("FINDING INCIDENT_TRIANGLES" );
			shape_intr.INCIDENT_TRIANGLES(input);
			end = System.currentTimeMillis();
			tme[6] += end-start;
			break;

					case "VERTEX_NEIGHBOR_TRIANGLE":
					start = System.currentTimeMillis();
			System.out.println("FINDING VERTEX_NEIGHBOR_TRIANGLE" );
			shape_intr.VERTEX_NEIGHBOR_TRIANGLE(input);
			end = System.currentTimeMillis();
			tme[7] += end-start;
                       	 break;

					case "EXTENDED_NEIGHBOR_TRIANGLE":
					start = System.currentTimeMillis();
			System.out.println("FINDING EXTENDED_NEIGHBOR_TRIANGLE" );

			TriangleInterface [] extended_neighbor_triangle = shape_intr.EXTENDED_NEIGHBOR_TRIANGLE(input);
			end = System.currentTimeMillis();
			tme[8] += end-start;
			break;

			  case "MAXIMUM_DIAMETER":
			  start = System.currentTimeMillis();
			System.out.println(" Finding diameter" );
			int diameter = shape_intr.MAXIMUM_DIAMETER();
			System.out.println(diameter);
			end = System.currentTimeMillis();
			tme[9] += end-start;
                        break;
					case "EDGE_NEIGHBOR_TRIANGLE":
					start = System.currentTimeMillis();
			System.out.println("Finding EDGE_NEIGHBOR_TRIANGLE");
			 EdgeInterface [] edge_neighbors_of_triangle = shape_intr.EDGE_NEIGHBOR_TRIANGLE(input);
			 end = System.currentTimeMillis();
			tme[10] += end-start;
                        break;

				   case "FACE_NEIGHBORS_OF_POINT":
				   start = System.currentTimeMillis();
			System.out.println("Finding FACE_NEIGHBORS_OF_POINT");
			 TriangleInterface [] face_nbrs = shape_intr.FACE_NEIGHBORS_OF_POINT(input);
			 end = System.currentTimeMillis();
			 tme[11] += end-start;
                        break;

				   case "EDGE_NEIGHBORS_OF_POINT":
				   start = System.currentTimeMillis();
			System.out.println("Finding EDGE_NEIGHBORS_OF_POINT");
			 EdgeInterface [] edge_nbrs = shape_intr.EDGE_NEIGHBORS_OF_POINT(input);
			 end = System.currentTimeMillis();
			 tme[12] += end-start;
                        break;

					case "TRIANGLE_NEIGHBOR_OF_EDGE":
					start = System.currentTimeMillis();
			System.out.println("Finding TRIANGLE_NEIGHBOR_OF_EDGE");
			 TriangleInterface [] triangle_neighbors = shape_intr.TRIANGLE_NEIGHBOR_OF_EDGE(input);
			 end = System.currentTimeMillis();
			 tme[13] += end-start;
			break;
		

			  case "CENTROID":
			  start = System.currentTimeMillis();
			System.out.println("Finding Centroid" );
			
			PointInterface [] centroid_array = shape_intr.CENTROID();
			for(int i = 0;i < centroid_array.length ; i++){
				System.out.print( ((Point)centroid_array[i]).toString());
				if(i != centroid_array.length-1)
					System.out.print(" ");
				
			}
			System.out.println();
			end = System.currentTimeMillis();
			tme[14] += end-start;
                        break;
					case "CENTROID_OF_COMPONENT":
					start = System.currentTimeMillis();
			System.out.println(" Finding CENTROID_OF_COMPONENT");
			 PointInterface centroid_of_component = shape_intr.CENTROID_OF_COMPONENT(input);
			 System.out.println( (Point)centroid_of_component);
			 end = System.currentTimeMillis();
			 tme[15] += end-start;
                        break;

			case "CLOSEST_COMPONENTS":
			start = System.currentTimeMillis();
			System.out.println("Finding CLOSEST_COMPONENTS");
			PointInterface [] closest_vertices = shape_intr.CLOSEST_COMPONENTS();
			System.out.println(distance((Point)closest_vertices[0] , (Point)closest_vertices[1]));
			end = System.currentTimeMillis();
			tme[16] += end-start;
			break;
		


	//	default :System.out.println(cmd[0] +" not found");	
	//		break;
			
                }

			}
		
			
	//}
	// catch(Exception e)
	// {
	// 	System.err.println("Error parsing: 2	 " +
	
	// }
	for(int i = 0;i < tme.length ; i++)
	System.out.println(tme[i]/1000);
	}
	
}



