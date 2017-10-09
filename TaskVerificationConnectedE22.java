package ParcoursDeGrapheSansMemoireTotal;



import java.util.HashMap;


import java.util.LinkedList;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import util.LockfreeQueue;

public class TaskVerificationConnectedE22 extends Thread{
	HashMap<String,Couple> rempli; //boolean true si c'est un personnage
	LockfreeQueue<Trio> UrlEtPage;
	LockfreeQueue<CoupleResult> Verifier;
	AtomicInteger N;
	int GenerationCourante;
	int NombreGeneration;
	
	public TaskVerificationConnectedE22(HashMap<String,Couple> rempli,LockfreeQueue<CoupleResult> Verifier,LockfreeQueue<Trio> UrlEtPage, AtomicInteger N,int NombreGeneration,int GenerationCourante) {
		this.rempli = rempli;
		this.UrlEtPage = UrlEtPage;
		this.Verifier =Verifier;
		this.N=N;
		this.NombreGeneration=NombreGeneration;
		this.GenerationCourante=GenerationCourante;
	}
	
	@Override
	public  void run() {
		// TODO Auto-generated method stub
		while (!this.N.compareAndSet(0, 0)){
		
		if (UrlEtPage.isEmpty()){
			try {       // la méthode wait peut marcher avec synchronisation, on peut également commenter cette partie ...
			
			synchronized(this.N){this.N.wait();}   //mettre un temps limite pour le wait peut être plus rapide
			continue;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} }                                    //... jusque là
			
			Trio t = UrlEtPage.take();
			if (t==null){continue;}
			String _url = t.url;
			String page = t.page;
			int generation=t.Generation;
			if (generation>this.NombreGeneration){continue;}
			if(rempli.containsKey(_url)){continue;}
			if ((page.indexOf("Biographical information", 40000)>=0)&&page.indexOf("<h3 class=\"pi-data-label pi-secondary-font\">Species</h3>",30000)>=0)  {
				synchronized(rempli){
			if(!rempli.containsKey(_url)) {
					rempli.put(_url, new Couple(true,generation));
				}
		
				
			}
			
				if(generation==this.NombreGeneration){continue;}
				int debut = page.indexOf("/wiki/Template:Character",0);
	    		int fin =  page.indexOf("div>In other languages</div>",debut);
	    		String motini = "<a href=\"";
	    		String motfin = "\" title=";
			    //System.out.println(_url+"de generation"+generation);
				LockfreeQueue<String> RepererMotToAdd  = LinkExtractE2.RepererMot2 (debut, fin, motini, motfin,page);
	    		while(!RepererMotToAdd.isEmpty()) {
	    			Verifier.add(new CoupleResult(RepererMotToAdd.take(),generation+1));
	    			
	    		}	
			}
			else {synchronized (rempli){rempli.put(_url, new Couple(false,-1));}}
			}
			
		 
		//Quand les readers ont fini, on dépile.
		while (!UrlEtPage.isEmpty()){
			Trio t = UrlEtPage.take();
			if (t==null){continue;}
			String _url = t.url;
			String page = t.page;
			int generation=t.Generation;
			if (generation>this.NombreGeneration){continue;}
			if ((page.indexOf("Biographical information", 40000)>=0)&&page.indexOf("<h3 class=\"pi-data-label pi-secondary-font\">Species</h3>",30000)>=0)  {
				synchronized(rempli){
				if(!rempli.containsKey(_url)) {
					rempli.put(_url, new Couple(true,generation));
					
				}
				
			}
				if(generation==this.NombreGeneration){continue;}
				int debut = page.indexOf("/wiki/Template:Character",0);
	    		int fin =  page.indexOf("div>In other languages</div>",debut);
	    		String motini = "<a href=\"";
	    		String motfin = "\" title=";
	    		// System.out.println(_url+"de generation"+generation);
				LockfreeQueue<String> RepererMotToAdd  = LinkExtractE2.RepererMot2 (debut, fin, motini, motfin,page);
	    		while(!RepererMotToAdd.isEmpty()) {
	    			Verifier.add(new CoupleResult(RepererMotToAdd.take(),generation+1));
	    		}	
			}
			else {synchronized (rempli){rempli.put(_url, new Couple(false,-1));}}
			
			}
			
		
	}
	
}
	
	


