package Etape3V0.CollLexico;

import java.util.concurrent.atomic.AtomicIntegerArray;

import trisEtTest.CoupleResult;

public class ultimeL {
	CoupleResult[]l; 
	AtomicIntegerArray r ; 
	CoupleResult[]trie;
	ultimeL(AtomicIntegerArray r,CoupleResult[]l,CoupleResult[]trie) {
		this.r=r;
		this.l=l;
		this.trie=trie;
	}
	
	public void rearranger() {
		int n = r.length();
		for(int i =0;i<n;i++){
			int j =r.get(i);
			trie[j-n]=l[i];
		}
		
	}
	
}
