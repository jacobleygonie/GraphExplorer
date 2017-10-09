
package Etape3V0.PairImpairLexico;

import Etape3V0.PairImpairLexico.BufferL;
import trisEtTest.CoupleResult;
import trisEtTest.Relation;

public class ComparateurPIL extends Thread{
	  int proc;  // numero de processeur
	    CoupleResult val;   // valeur courante
	    int nMax;  // nb de valeurs a trier
	    CoupleResult[] tab; // pour stocker les resultats finaux
	    BufferL lg,ld,eg,ed; // lecture-ecriture, droite-gauche

	    public ComparateurPIL(int p,CoupleResult v,int n,CoupleResult[] tt,BufferL llg,BufferL eeg,BufferL lld,BufferL eed) {
	        proc = p; val = v; nMax = n; tab = tt; 
	        lg = llg; ld = lld; eg = eeg; ed = eed;
	    }

	    public void run() {
	        CoupleResult dv;
	        CoupleResult gv; // valeurs lues a gauche et a droite

	        for (int etape=0;etape<nMax;etape++) { 
	            if ((etape%2)==0) {
	                if ((proc%2)==0) {
	                    if (ed!=null&&!ed.val.s.equals("non")) ed.write(val);
	                    if (ld!=null&&!ld.val.s.equals("non")) dv = ld.read(); else dv = val;
	                    if (Relation.PlusPetitQue(dv, val)&&!val.s.equals("non")) { val = dv; }
	                } else {
	                    if (eg!=null&&!eg.val.s.equals("non")) eg.write(val);
	                    if (lg!=null&&!lg.val.s.equals("non")) gv = lg.read(); else gv = val;
	                    if (Relation.PlusPetitQue(val, gv)||val.s.equals("non")) { val = gv; }
	                }
	            } else {
	                if ((proc%2)==0) {
	                    if (eg!=null&&!eg.val.s.equals("non")) eg.write(val);
	                    if (lg!=null&&!lg.val.s.equals("non")) gv = lg.read(); else gv = val;
	                    if (Relation.PlusPetitQue(val, gv)||val.s.equals("non")) { val = gv; }
	                } else {
	                    if (ed!=null&&!ed.val.s.equals("non")) ed.write(val);
	                    if (ld!=null&&!ld.val.s.equals("non")) dv = ld.read(); else dv = val;
	                    if (Relation.PlusPetitQue(dv, val)&&!val.s.equals("non")) { val = dv; }
	                }
	            } 
	        }            
	        tab[proc] = val;
	    }
	}



