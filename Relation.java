package trisEtTest;

public class Relation {
	public static boolean PlusPetitQue(CoupleResult C1,CoupleResult C2){
		String s1=C1.s;
		String s2=C2.s;
		int gen1=C1.i;
		int gen2=C2.i;
		if(gen1<gen2){return true;}
		int comp =s1.compareToIgnoreCase(s2);
		if (comp<=0){return true;}
		return false;
		}
		
	}

