package Etape3V0.CollLexico;



import java.io.IOException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import trisEtTest.CoupleResult;

public class ProcessorColL implements Runnable {
	CoupleResult[] l ;
	int [][] m;
	private final ExecutorService pool;
	int poolSize;

	 public ProcessorColL(CoupleResult []l, int[][] m, int poolSize)  throws IOException{
	    	this.l=l;
	    	this.m=m;
	    	this.poolSize =poolSize;
	    	 pool = Executors.newFixedThreadPool(poolSize);
	    }

		  
		   public void run() { // run the service
		     for (int i=0;i<l.length;i++){
				 for (int j=0;j<l.length;j++){	
					 this.pool.execute(new  ComparatorL (this.l, i, j,this.m));
				 }
			 }
		     pool.shutdown();
		   }
		 
}

