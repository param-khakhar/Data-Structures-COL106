package ProjectManagement;

import java.util.ArrayList;

import PriorityQueue.Auxiliary;
import PriorityQueue.MaxHeap;

public class User implements UserReport_ ,Comparable<User>{
			
		private String name;
		private int consumed;
		private ArrayList<Job> UnFinished = new ArrayList<Job>();
		private ArrayList<Job> Finished = new ArrayList<Job>();
		User(String s){
			name = s;
			consumed = 0;
		}
		
		public ArrayList<Job> getUnFinished(){
			return UnFinished;
		}
		
		public ArrayList<Job> getFinished(){
			return Finished;
		}
		
		public void insertUnFinishedJob(Job j) {
			UnFinished.add(j);
		}
		
		public void insertFinishedJob(Job j) {
			Finished.add(j);
		}
		
		/*public ArrayList<Job> getJobs(){
			return UnFinished;
		}*/
		
		public void incrementConsumption(int inc) {
			consumed += inc;
		}
		
		public String user() {
			return name;
		}
		
		public int consumed() {
			return consumed;
		}
		
	    @Override
	    public int compareTo(User user) {
	        if(consumed>user.consumed()) {
	        	return 1;
	        }
	        else if(consumed<user.consumed()) {
	        	return -1;
	        }
	        else {
	        	if(Finished.size() != 0 && user.getFinished().size() !=0) {
	        	int t1 = Finished.get(Finished.size()-1).completion_time();
	        	int t2 = user.getFinished().get(user.getFinished().size()-1).completion_time();
	        	if(t1 > t2) {
	        		return -1;
	        	}
	        	else if(t1 < t2) {
	        		return 1;
	        	}
	        	else {
	        		return 0;
	        	}
	        		}
	        	else {
	        		if(Finished.size() == 0) {
	        			if(user.getFinished().size() == 0) {
	        				return 0;
	        			}
	        			else {
	        				return -1;
	        			}
	        		}
	        	else {
	        			return 1;
	        		}
	        }
	    }
	}
	public String toString() {
		return name +" "+ consumed;
	}
}
	


