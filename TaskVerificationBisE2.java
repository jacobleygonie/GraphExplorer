package ParcoursDeGrapheSansMemoireTotal;
import java.util.HashMap;



import java.util.LinkedList;
import java.util.Stack;

import ParcoursDeGrapheSansMemoireUneGeneration.LirePage;
import util.LockfreeQueue;

public class TaskVerificationBisE2 extends Thread {
	
	HashMap<String,Couple> rempli; //boolean true si c'est un personnage
	LockfreeQueue<Trio> UrlEtPage;
	LockfreeQueue<String> RepererMot; 
	
	public TaskVerificationBisE2(HashMap<String,Couple> rempli,LockfreeQueue<Trio> UrlEtPage,LockfreeQueue<String> RepererMot) {
		this.rempli = rempli;
		this.UrlEtPage = UrlEtPage;
		this.RepererMot= RepererMot;
	}
	public static LockfreeQueue<String> RepererMot2 (int debut, int fin, String motini, String motfin, String page) {
		int k = page.indexOf(motini,debut);
		LockfreeQueue<String> res = new LockfreeQueue<String>();
		while(k<fin && k>=0) {
			int begin = k;
			int end  = page.indexOf(motfin,begin+1);
			String nouv = page.substring(begin + motini.length(), end);
			//System.out.println(nouv);
			res.add(nouv);
			k = page.indexOf(motini,end+1);
		}
		return res;
	}
	@Override
	public  void run() {
		// TODO Auto-generated method stub
		
		
		while (!UrlEtPage.isEmpty()){
			
			Trio t = UrlEtPage.take();
			String _url = t.url;
			String page = t.page;
			int debut = page.indexOf("/wiki/Template:Character",0);
    		int fin =  page.indexOf("div>In other languages</div>",debut);
    		String motini = "<a href=\"";
    		String motfin = "\" title=";
		
			if ((page.indexOf("Biographical information", 40000)>=0)&&page.indexOf("<h3 class=\"pi-data-label pi-secondary-font\">Species</h3>",30000)>=0)  {
				if(!rempli.containsKey(_url)) {
					Couple c= new Couple (true,t.Generation);
					synchronized(rempli){rempli.put(_url, c);}
					
						
				}
				
	    		
	    		LockfreeQueue<String> RepererMotToAdd  = RepererMot2 (debut, fin, motini, motfin,page);
	    		while(!RepererMotToAdd.isEmpty()) {
	    			RepererMot.add(RepererMotToAdd.take());
	    		}
				continue;
			}
			Couple c= new Couple(false,-1);
			synchronized(rempli){rempli.put(_url, c);}
			
			}
		
		
	}
}

