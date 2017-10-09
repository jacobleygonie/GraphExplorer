package ParcoursDeGrapheSansMemoireUneGeneration;

import java.util.HashMap;


import java.util.LinkedList;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import util.LockfreeQueue;

public class TaskVerificationConnected extends Thread{
	HashMap<String,Boolean> rempli; //boolean true si c'est un personnage
	LockfreeQueue<String []> UrlEtPage;
	LockfreeQueue<String> listefinale;
	AtomicInteger N;
	
	public TaskVerificationConnected(HashMap<String,Boolean> rempli,LockfreeQueue<String []> UrlEtPage,LockfreeQueue<String> listefinale, AtomicInteger N) {
		this.rempli = rempli;
		this.UrlEtPage = UrlEtPage;
		this.listefinale =listefinale;
		this.N=N;
	}
	
	@Override
	public  void run() {
		// TODO Auto-generated method stub
		while (!this.N.compareAndSet(0, 0)){
		
		if (UrlEtPage.isEmpty()){try {       // la méthode wait peut marcher avec synchronisation, on peut également commenter cette partie ...
			
			synchronized(this.N){this.N.wait();}   //mettre un temps limite pour le wait peut être plus rapide
			continue;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}                                     //... jusque là
			
			String[] t = UrlEtPage.take();
			if (t==null){continue;}
			String _url = t[0];
			String page = t[1];
		
			if ((page.indexOf("Biographical information", 40000)>=0)&&page.indexOf("<h3 class=\"pi-data-label pi-secondary-font\">Species</h3>",30000)>=0)  {
				if(!rempli.containsKey(_url)) {
					listefinale.add(_url);	
				}
				rempli.put(_url, true);
				continue;
			}
			rempli.put(_url, false);
			
			}
		 }
		//Quand les readers ont fini, on dépile.
		while (!UrlEtPage.isEmpty()){
			String[] t = UrlEtPage.take();
			if (t==null){continue;}
			String _url = t[0];
			String page = t[1];
		
			if ((page.indexOf("Biographical information", 40000)>=0)&&page.indexOf("<h3 class=\"pi-data-label pi-secondary-font\">Species</h3>",30000)>=0)  {
				if(!rempli.containsKey(_url)) {
					listefinale.add(_url);	
				}
				rempli.put(_url, true);
				continue;
			}
			rempli.put(_url, false);
			
			}
			
		
	}
	
}
	
	

