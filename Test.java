package ParcoursDeGrapheSansMemoireUneGeneration;
import java.util.LinkedList;

import util.LockfreeQueue;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Test pour un personnage avec moins d'hyperliens sur sa page : ("http://starwars.wikia.com/wiki/Athgar_Heece") 
		long startTime = System.currentTimeMillis();
		
		
	// Changer le nombre, ici "11", derrièregetLinks dans la ligne ci-dessous. Voir la classe LinkExctract pour des détails sur chacune des fonctions getLinksX. 
		
		LockfreeQueue<String> res =  LinkExtract.getLinks11("http://starwars.wikia.com/wiki/Yoda",400,30); //ici sont utilisés 400 threads reader et 30 threads vérificateurs qui sont des valeurs efficaces
		int c =0;
		while(!res.isEmpty()){
			System.out.println(res.take());
			c=c+1;
		}
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime; 
		System.out.println(totalTime);
		System.out.print(c);
		
	}

}
