package ProjectManagement;
import java.util.ArrayList;

public class userTrie {
	private String name;
	private ArrayList<Job> projectUser = new ArrayList<Job>();
	userTrie(String s){
		name = s;
	}
	
	public ArrayList<Job> projectUser(){
		return projectUser;
	}
	
	public String getName() {
		return name;
	}
	
	public void insertJob(Job j) {
		projectUser.add(j);
	}
}	
