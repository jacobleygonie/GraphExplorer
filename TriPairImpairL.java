package Etape3V0.PairImpairLexico;

import Tris.PairImpair.Buffer;
import Tris.PairImpair.ComparateurPI;
import trisEtTest.CoupleResult;

public class TriPairImpairL {
	
	   

	    public static CoupleResult[] tri (CoupleResult[] tab) {
	    	int nMax=tab.length;
	    	CoupleResult[] tabTrie = new CoupleResult[nMax];
	        ComparateurPIL[] cmp = new ComparateurPIL[nMax];
	        BufferL lg,eg,ld,ed;
	        lg = null; eg = null;
	        for (int i=0;i<nMax-1;i++) { // creation des threads
	            ld = new BufferL();
	            ed = new BufferL();
	            cmp[i]= new ComparateurPIL(i,tab[i],nMax,tabTrie,lg,eg,ld,ed);
	            lg = ed; eg = ld;
	        };
	        ld = null; ed = null; // creation du dernier thread
	        cmp[nMax-1] = new ComparateurPIL(nMax-1,tab[nMax-1],nMax,tabTrie,lg,eg,ld,ed);

	        for (int i=0;i<nMax;i++) { // execution des threads
	            cmp[i].start();
	        };
	        for (int i=0;i<nMax;i++) { // attente des resultats
	            try { cmp[i].join(); } catch (InterruptedException e) {} ; 
	        };
	        /*for (int i=0;i<nMax;i++) { // affichage resultats
	            System.out.println(tabTrie[i].s +"de generation"+tabTrie[i].i);
	        };*/
	        return tabTrie;
	    }
	
	
	
	
}




