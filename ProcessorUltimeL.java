package Etape3V0.CollLexico;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicIntegerArray;

import trisEtTest.CoupleResult;

public class ProcessorUltimeL  implements Runnable {
	
	CoupleResult[]l; 
	AtomicIntegerArray r ; 
	CoupleResult[]trie;
	private final ExecutorService pool;
	int poolSize;
	
	public ProcessorUltimeL(CoupleResult[]l, AtomicIntegerArray r, CoupleResult[]trie, int poolSize) throws IOException{
		this.r=r;
		this.l=l;
		this.trie=trie;
		this.poolSize =poolSize;
		pool = Executors.newFixedThreadPool(poolSize);
		}

			  
	 public void run() { // run the service
		 for(int i =0;i<this.r.length();i++){
			 this.pool.execute(new utilmeThreadL(i,l,r,trie));	
		 }
		this.pool.shutdown();
	}
			 
}
