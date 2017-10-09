package ParcoursDeGrapheSansMemoireTotal;

import util.LockfreeQueue;

public class CoupleResultSucc {
		CoupleResult id;
		LockfreeQueue parents;

 public CoupleResultSucc(CoupleResult id,LockfreeQueue parents){
	 this.id=id;
	 this.parents=parents;
 }
 
}