package ParcoursDeGrapheSansMemoireTotal;



import java.util.HashMap;


import java.util.LinkedList;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import util.LockfreeQueue;

public class TaskVerificationConnectedE22Succ extends Thread{
	HashMap<String,CoupleSucc> rempli; //boolean true si c'est un personnage
	LockfreeQueue<TrioSucc> UrlEtPage;
	LockfreeQueue<CoupleResultSucc> Verifier;
	AtomicInteger N;
	int GenerationCourante;
	int NombreGeneration;

	
	public TaskVerificationConnectedE22Succ(HashMap<String,CoupleSucc> rempli,LockfreeQueue<CoupleResultSucc> Verifier,LockfreeQueue<TrioSucc> UrlEtPage, AtomicInteger N,int NombreGeneration,int GenerationCourant) {
		this.rempli = rempli;
		this.UrlEtPage = UrlEtPage;
		this.Verifier =Verifier;
		this.N=N;
		this.NombreGeneration=NombreGeneration;
		this.GenerationCourante=GenerationCourant;

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
			
			TrioSucc T = UrlEtPage.take();
			if (T==null){continue;}
			Trio t=T.t;
			
			LockfreeQueue<String> parents=T.parents;
			
			String _url = t.url;
			String page = t.page;
			int generation=t.Generation;
			
			if (generation>this.NombreGeneration){continue;}
			synchronized(rempli){if(rempli.containsKey(_url)){
				if(!rempli.get(_url).id.b){continue;}
				}
			
			if ((page.indexOf("Biographical information", 40000)>=0)&&page.indexOf("<h3 class=\"pi-data-label pi-secondary-font\">Species</h3>",30000)>=0)  {
				synchronized(rempli){
			if(!rempli.containsKey(_url)) {
				Couple c= new Couple(true,generation);
					rempli.put(_url, new CoupleSucc(c,parents));
				}
			else{
				CoupleSucc DejaVu=rempli.get(_url);
				if((DejaVu.id.i==generation)&&!(generation==1)){
					
					//System.out.println("je veux ajouter des parents à "+_url);
					while(!parents.isEmpty()){
						String pere= parents.take();
						rempli.get(_url).parents.add(pere);
						//System.out.println("je veux ajouter "+pere+" comme parent à "+_url);
						}		
				}
				
				continue;}}
			
				if(generation==this.NombreGeneration){continue;}
				int debut = page.indexOf("/wiki/Template:Character",0);
	    		int fin =  page.indexOf("div>In other languages</div>",debut);
	    		String motini = "<a href=\"";
	    		String motfin = "\" title=";
			   
				LockfreeQueue<String> RepererMotToAdd  = LinkExtractE2.RepererMot2 (debut, fin, motini, motfin,page);
	    		while(!RepererMotToAdd.isEmpty()) {
	    			CoupleResult CR=new CoupleResult(RepererMotToAdd.take(),generation+1);
	    			LockfreeQueue<String> queue=new LockfreeQueue<String>();
	    			queue.add(_url);
	    			Verifier.add(new CoupleResultSucc(CR,queue));
	    			
	    		}	
			}
			else {Couple c=new Couple(false,-1);
				synchronized (rempli){rempli.put(_url, new CoupleSucc(c, new LockfreeQueue<String>()));}}
			}
		}
			
		 
		//Quand les readers ont fini, on dépile.
		while (!UrlEtPage.isEmpty()){
			if (UrlEtPage.isEmpty()){
				try {       // la méthode wait peut marcher avec synchronisation, on peut également commenter cette partie ...
				
				synchronized(this.N){this.N.wait();}   //mettre un temps limite pour le wait peut être plus rapide
				continue;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} }                                    //... jusque là
				
				TrioSucc T = UrlEtPage.take();
				if (T==null){continue;}
				Trio t=T.t;
				LockfreeQueue<String> parents=T.parents;
				
				String _url = t.url;
				String page = t.page;
				int generation=t.Generation;
				if (generation>this.NombreGeneration){continue;}
				synchronized(rempli){if(rempli.containsKey(_url)){
					if(!rempli.get(_url).id.b){continue;}
					}
				
				if ((page.indexOf("Biographical information", 40000)>=0)&&page.indexOf("<h3 class=\"pi-data-label pi-secondary-font\">Species</h3>",30000)>=0)  {
					synchronized(rempli){
				if(!rempli.containsKey(_url)) {
					Couple c= new Couple(true,generation);
						rempli.put(_url, new CoupleSucc(c,parents));
					}
				else{
				CoupleSucc DejaVu=rempli.get(_url);
				if((DejaVu.id.i==generation)&&!(generation==1)){
				
					//System.out.println("je veux ajouter des parents à "+_url);
					while(!parents.isEmpty()){
						String pere= parents.take();
						rempli.get(_url).parents.add(pere);
						//System.out.println("je veux ajouter "+pere+" comme parent à "+_url);
						}
				}
				
				continue;}}
			
					
				
				
					if(generation==this.NombreGeneration){continue;}
					int debut = page.indexOf("/wiki/Template:Character",0);
		    		int fin =  page.indexOf("div>In other languages</div>",debut);
		    		String motini = "<a href=\"";
		    		String motfin = "\" title=";
				    //System.out.println(_url+"de generation"+generation);
					LockfreeQueue<String> RepererMotToAdd  = LinkExtractE2.RepererMot2 (debut, fin, motini, motfin,page);
					while(!RepererMotToAdd.isEmpty()) {
		    			CoupleResult CR=new CoupleResult(RepererMotToAdd.take(),generation+1);
		    			LockfreeQueue<String> queue=new LockfreeQueue<String>();
		    			queue.add(_url);
		    			Verifier.add(new CoupleResultSucc(CR,queue));
		    			
		    		}	
				}
				else {Couple c=new Couple(false,-1);
					synchronized (rempli){rempli.put(_url, new CoupleSucc(c, new LockfreeQueue<String>()));}}
				}
	
		}}
}
	


