package Etape3V0.CollLexico;

import java.util.concurrent.atomic.AtomicBoolean;

public class SommePartielleL extends Thread{
	int k;//n=2^k
	int pos;
	int t[][];
	AtomicBoolean b;
	SommePartielleL(int k, int position,int tab[][]) { 
		this.k=k;
		pos = position ;
		t=tab ; 
		b=new AtomicBoolean();
		b.set(true);
	}
	public void run() { 
		int i,j;
		for (i=1;i<k+1;i++) {
			j = pos- (1<<(i-1)); // 1<<(i−1) calcule 2 a la puissance (i−1) 
			//System.out.println("j'essaye de remplir une étape de plus");
			if (j>=0) {				
				while (b.get()) {
					//System.out.println("je suis dans la boucle!!!");
					if (t[j][i-1]!=0){b.compareAndSet(true, false);}
				}  // attendre que le resultat soit pret 
				t[pos][i] = t[pos][i-1]+t[j][i-1]; // Processor next[j] does ... 
			} 
				else {
					t[pos][i] = t[pos][i-1];
					//System.out.println("je suis hors du while");
							}
			b.compareAndSet(false, true);
			} return; }



    
	
}
