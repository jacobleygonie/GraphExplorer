package Etape3V0.CollLexico;

import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.locks.ReentrantLock;

public class SumLineL extends Thread{
	int i; // la ligne dont le thread doit se charger 
	int [][] m;
	AtomicIntegerArray r ;
	
	SumLineL(int i, int [][] m, AtomicIntegerArray r){
		this.i= i;
		this.m=m;
		this.r = r;
	}
	
	public void run(){
			SommeL s = new SommeL(m[i]) ;
			r.set(i, s.retourneTrie());
			
			return;
			
	}
	
	
	
	
}
