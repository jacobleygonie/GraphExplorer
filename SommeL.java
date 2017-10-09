package Etape3V0.CollLexico;

public class SommeL {
	int tab[]; // renvoie la somme de tous les éléments du tableau 
	
	
	SommeL(int tab[]) { 
		this.tab=tab ; 
	}
	
	public int retourneTrie (){
		int n = tab.length;
		double k2 = Math.log(n)/Math.log(2);
		int k =(int) k2 +1;
		int mat[][] = new int[n][k+1];
		Thread [] threads = new Thread [n];
		for( int i =0;i<n;i++){
			mat[i][0] =tab[i];
		}
		for (int pos=0; pos<n;pos++){
			SommePartielleL s = new SommePartielleL(k, pos,mat) ;
			threads[pos]=s;
			s.start();
		}
		for (int pos=0; pos<n;pos++){
			try {
				threads[pos].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return mat[n-1][k];
	}

	/*public static void main(String[] args) {
		int n = 4;
	    // Tableau Ã  trier
	    int[] t = new int[n];
	    for (int i = 0; i < n; i++) {
	      t[i] = n-i;}
	      Somme s = new Somme(t);
	      System.out.println (s.retourneTrie());
	      /*int[] t2 = new int[4];
	   
	       t2[0] = 3;
	      t2[1] = 2;
	      t2[2]=2;
	      t2[3]=3;
	      Somme s2 = new Somme(t2);
	      System.out.println (s2.retourneTrie());
	    }*/
	}

