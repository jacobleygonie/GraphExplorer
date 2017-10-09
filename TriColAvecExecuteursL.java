package Etape3V0.CollLexico;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicIntegerArray;

import trisEtTest.CoupleResult;

public class TriColAvecExecuteursL {
	public CoupleResult[] t ;
	
	public TriColAvecExecuteursL(CoupleResult[]t ){
		this.t=t;
	}
	
	public CoupleResult[] Tri1 () {
		int n = this.t.length;
		int[][] m = new int[n][n];
		   
		Thread[][] comparators = new Thread[n][n]; 
	    for (int i=0;i<n;i++){
	    	 for (int j=0;j<n;j++){	
	    		 	comparators[i][j] = new ComparatorL (t,i,j,m);
	    		 	comparators[i][j].start();
	    	 }
	    }
	    
	    
	    for (int i=0;i<n;i++){
	    	 for (int j=0;j<n;j++){
	    		 	try {
						comparators[i][j].join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    	 }
	    }
	    
	    Thread[] sums = new Thread[n];
	    AtomicIntegerArray r = new AtomicIntegerArray(n);
	    for (int i=0;i<n;i++){
	    	SumLineL s=new SumLineL(i,m,r);
	    	sums[i]  = s;
	    	sums[i].start();
	    }
	    for (int i=0;i<n;i++){
	    	try {
				sums[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	    CoupleResult[] trie = new CoupleResult [n];
	    ultimeL ultimeaction = new ultimeL(r,t,trie);
	    ultimeaction.rearranger();
	    
	    return trie;
}
	
	public CoupleResult[] Tri2 (int Nthreads1, int NAutorises, int Nthreads3) {
		int n =t.length;
		int[][] m = new int[n][n];
		   
		   // avec ProcessorCol (Exec à l'intérieur)
		    ProcessorColL comparators;
			try {
				comparators = new ProcessorColL (t,m,Nthreads1);
				comparators.run();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			 //Contrôle des threads
			 
				  int indice=0;
				  AtomicIntegerArray r = new AtomicIntegerArray(n);
				  
				  SumLineL[] sums = new SumLineL[NAutorises];
				
				   for (int i=0;i<n;i++){
					 if (sums[indice]==null) {
						 SumLineL s= new SumLineL(i,m,r);
						 sums[indice]=s;
						 s.start();
						 indice=(indice+1)%NAutorises;
					 }
					 else{
						 try {
							sums[indice].join();
						} catch (InterruptedException e) {
				
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						 SumLineL s= new SumLineL(i,m,r);
						 sums[indice]=s;
						 s.start();
						 indice=(indice+1)%NAutorises;
					 }
				   
			    	
				   	}
				   for (int i=0;i<NAutorises;i++){
					   try {
							sums[i].join();
						} catch (InterruptedException e) {
				
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				   }
				   CoupleResult[] trie = new CoupleResult [n];
				    ProcessorUltimeL remplisseurs;
				  		try {
				  			remplisseurs = new ProcessorUltimeL (t,r,trie,Nthreads3);
				  			remplisseurs.run();
				  		} catch (IOException e1) {
				  			// TODO Auto-generated catch block
				  			e1.printStackTrace();
				  		}
				  return trie;
	}
	 
	public CoupleResult[] Tri3 (int Nthreads, int Nautorises){
		return Tri2 (Nthreads, Nautorises, Nthreads);
	}
	public CoupleResult[] Tri4 (){
		int Nthreads = Runtime.getRuntime().availableProcessors();
		return Tri2 (Nthreads, 2, Nthreads);
	}
	
	
	 
}
