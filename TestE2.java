package ParcoursDeGrapheSansMemoireTotal;


import java.util.LinkedList;



import util.LockfreeQueue;

public class TestE2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.print(LirePage.getTextFile("http://starwars.wikia.com/wiki/Athgar_Heece"));
		long startTime = System.currentTimeMillis();
	   // Méthode sans conservation des connexions entre personnages, changer le 12 de GetLinks12E2 en 11 et faire varier le nombre de threads et de generation
		LockfreeQueue<CoupleResult> res =  LinkExtractE2.getLinks12E2("/wiki/Yoda",300,34,3);
		int c =0;
		while(!res.isEmpty()){
			CoupleResult R= res.take();
			System.out.println(R.s +" apparaît à la génération"+R.i);
			
			c=c+1;

		}
		//Méthode plus lente permettant de conserver en mémoire les connections directes entre personnages
		/*LockfreeQueue<CoupleResultSucc> res =  LinkExtractE2.getLinks13E2("/wiki/Athgar_Heece",300,34,2);
		int c =0;
		while(!res.isEmpty()){
			CoupleResultSucc r=res.take();
			CoupleResult R= r.id;
			LockfreeQueue<String> parents=r.parents;
			System.out.println(R.s +" apparaît à la génération"+R.i);
			while (!parents.isEmpty()){
				System.out.println("l'un de ses prédécesseurs est"+parents.take());
			}
			
			c=c+1;

		}*/
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime; //5 threads->89s / 10 threads->82s / 20 threads->88s
		System.out.println(totalTime);
		System.out.print(c);
		/*String link = "/wiki/Yoda";
		System.out.print(Verification.personnage("http://starwars.wikia.com" + link));*/
	}

}
