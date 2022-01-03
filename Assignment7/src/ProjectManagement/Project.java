package ProjectManagement;
import java.util.ArrayList;

import PriorityQueue.Auxiliary;
import PriorityQueue.MaxHeap;
import Trie.Trie;
import Trie.TrieNode;

public class Project implements Comparable<Project>{
	
	private String name;
	private int Priority;
	private int budget;
	private Trie<userTrie> projectUser = new Trie<userTrie>();
	private ArrayList<Job> UnFinished = new ArrayList<Job>(); 
	private ArrayList<Job> Finished  = new ArrayList<Job>();
	
	Project(String s,String p,String b){
		name = s;
		Priority = Integer.parseInt(p);
		budget = Integer.parseInt(b);
	}
	
	
	public int getBudget() {
		return budget;
	}
	
	public ArrayList<Job> getUnFinished(){
		return UnFinished;
	}
	
	public ArrayList<Job> getFinished(){
		return Finished;
	}
	
	public void insertUnFinishedJob(Job j) {
		UnFinished.add(j);
		TrieNode<userTrie> TN = projectUser.search(j.user());
		if(TN == null) {
			userTrie t = new userTrie(j.user());
			t.insertJob(j);
			projectUser.insert(j.user(),t);
		}
		else {
			TN.getValue().insertJob(j);
		}
	}
	
	public ArrayList<Job> projectUser(String s){
		TrieNode<userTrie> u = projectUser.search(s);
		if(u!=null) {
			return u.getValue().projectUser();
		}
		else {
			return new ArrayList<Job>();
		}
	}
 	
	public void insertFinishedJob(Job j) {
		Finished.add(j);
	}
	
	public ArrayList<Job> getJobs(){
		return UnFinished;
	}
	
	public int getPriority() {
		return Priority;
	}
	
	public String getName() {
		return name;
	}
	
	public void insertJob(Job j) {
		UnFinished.add(j);
	}
	
	public void setBudget(int n) {
		budget = n;
	}
	
	public int compareTo(Project p) {
		if(Priority<p.getPriority()) {
			return -1;
		}
		else if(Priority>p.getPriority()) {
			return 1;
		}
		else {
			return 0;
		}
	}
}
