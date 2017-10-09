package trisEtTest;
import java.util.HashMap;

import java.util.Set;

import trisEtTest.CoupleResult;
import util.LockfreeQueue;
import java.util.ArrayList;

public class lockFreeQueueToArray {
	public static CoupleResult[] tableur(LockfreeQueue<CoupleResult> queue){
		ArrayList<CoupleResult> res=new ArrayList<CoupleResult>();
	
		
	while(!queue.isEmpty()){
			res.add(queue.take());
		}
		int Longueur= res.size();
		CoupleResult[] res1=new CoupleResult[Longueur];
		res.toArray(res1);
		return res1;
	}

}
