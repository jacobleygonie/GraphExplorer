package Etape3V0.PairImpairLexico;

import trisEtTest.CoupleResult;

public class BufferL {
	CoupleResult val;
    public BufferL() { val = new CoupleResult ("non",-1); } 

    synchronized void write(CoupleResult v) {
    	
        while (!val.s.equals("non")) {
            try { wait(); } catch (InterruptedException e) {};
        }; 
        val = v;
        notifyAll();
    }

    synchronized CoupleResult read() {
        while (val.s.equals("non")) {
            try { wait(); } catch (InterruptedException e) {};
        }; 
        CoupleResult n = val;
        val = new CoupleResult ("non",-1);
        notifyAll();
        return n;
    }
}
