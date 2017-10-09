package ParcoursDeGrapheSansMemoireTotal;

import util.LockfreeQueue;

public class CoupleSucc {
	Couple id;
	LockfreeQueue<String> parents;
	
	public CoupleSucc(Couple c,LockfreeQueue<String> parents){
		this.id=c;
		this.parents=parents;
	}
}
