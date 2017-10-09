package trisEtTest;

import java.util.ArrayList;

import Etape3V0.CollLexico.TriColAvecExecuteursL;
import Etape3V0.PairImpairLexico.TriPairImpairL;
import Etape3V0.TriFusionThread.TrieurLexico;
import util.LockfreeQueue;
import java.util.concurrent.ForkJoinPool;

public class TestTriLexico {
	public static void main(String[] args) {
		  LockfreeQueue<CoupleResult> queue = new LockfreeQueue<CoupleResult>();
		    CoupleResult c= new CoupleResult("blabl",1);
		    CoupleResult e=  new CoupleResult("ade",2);
		    CoupleResult d=  new CoupleResult("blabla",1);
		   
		    CoupleResult f=  new CoupleResult("blabl",2);
		    queue.add(c);
		    queue.add(d);
		    queue.add(e);
		    queue.add(f);
		    CoupleResult[] res=lockFreeQueueToArray.tableur(queue);
		   int N=res.length;
		
    // TEST DE TRI FUSION LEXICOGRAPHIQUE
  /* TrieurLexico trieur = new TrieurLexico(res);
   ForkJoinPool pool=new ForkJoinPool();
   pool.invoke(trieur);
   for (int i=0;i<N;i++){System.out.println(trieur.t[i].s +"de generation"+trieur.t[i].i);}*/
  
		   
	// TEST DE TRI DE 'COL' (qui n'en est pas vraiment un) LEXICOGRAPHIQUE avec Executeur
	/*TriColAvecExecuteursL trieur= new TriColAvecExecuteursL(res);
	trieur.Tri4();
	for (int i=0;i<N;i++){System.out.println(trieur.t[i].s +"de generation"+trieur.t[i].i);}*/
	 
	// TEST DE TRI PAIR/IMPAIR LEXICOGRAPHIQUE 
	/*	   CoupleResult[] res1=TriPairImpairL.tri(res);  
		   for (int i=0;i<N;i++){System.out.println(res1[i].s +"de generation"+res1[i].i);}
   */
}
}