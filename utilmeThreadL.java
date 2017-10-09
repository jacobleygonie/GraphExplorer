package Etape3V0.CollLexico;

import java.util.concurrent.atomic.AtomicIntegerArray;

import trisEtTest.CoupleResult;

public class utilmeThreadL extends Thread {
	 int i;
	 CoupleResult[]l; 
	AtomicIntegerArray r ; 
	CoupleResult[]trie;
		
public utilmeThreadL (int i ,CoupleResult[]l , AtomicIntegerArray r,CoupleResult[]trie)		 {
	
	this.i = i;
	this.l = l;
	this.r = r;
	this.trie = trie;
}
	 public void run(){
		 int j = r.get(i);
		 trie[j-r.length()-1]=l[i];
	 }

}
