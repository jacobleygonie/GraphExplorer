package Etape3V0.CollLexico;

import trisEtTest.CoupleResult;
import trisEtTest.Relation;

public class ComparatorL extends Thread{
	 CoupleResult[] l ;
	 int i;
	 int j;
	 int [][] m;
	 
	 ComparatorL (CoupleResult[] l, int i, int j,int [][] m) {
		 this.l = l;
		 this.i = i;
		 this.j = j;
		 this.m=m;
	 }
	 
	 public void run(){
		 if (Relation.PlusPetitQue(l[j], l[i])) { 
			 this.m[i][j] = 2;
		}
		 else {
			 if(this.l[i]==this.l[j]&&i<j) { this.m[i][j] = 2;}
			 else { this.m[i][j] = 1;}
		 }
	 }
		 
	 
}
