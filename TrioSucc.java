package ParcoursDeGrapheSansMemoireTotal;

import util.LockfreeQueue;

public class TrioSucc {
	Trio t;
	LockfreeQueue<String> parents;
	
	public TrioSucc(Trio t, LockfreeQueue<String> parents){
	this.t=t;
	this.parents=parents;}
}
