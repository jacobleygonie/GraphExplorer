package ParcoursDeGrapheSansMemoireTotal;

import java.io.BufferedReader;




import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.EditorKit;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import ParcoursDeGrapheSansMemoireUneGeneration.LinkExtract;
import ParcoursDeGrapheSansMemoireUneGeneration.LirePage;
import ParcoursDeGrapheSansMemoireUneGeneration.TaskRead;
import ParcoursDeGrapheSansMemoireUneGeneration.TaskReadBis;
import ParcoursDeGrapheSansMemoireUneGeneration.TaskReadConnected;
import ParcoursDeGrapheSansMemoireUneGeneration.TaskVerificationBis;
import ParcoursDeGrapheSansMemoireUneGeneration.TaskVerificationConnected;
import ParcoursDeGrapheSansMemoireUneGeneration.TaskVerificationNoLirePage;
import util.LockfreeQueue;

import javax.swing.text.html.HTMLEditorKit;

public class LinkExtractE2 {
	
	public static LinkedList<String> RepererMot (int debut, int fin, String motini, String motfin, String page) {
		int k = page.indexOf(motini,debut);
		LinkedList<String> res = new LinkedList<String>();
		while(k<fin && k>=0) {
			int begin = k;
			int end  = page.indexOf(motfin,begin+1);
			String nouv = page.substring(begin + motini.length(), end);
			res.add(nouv);
			k = page.indexOf(motini,end+1);
		}
		return res;
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
	
	
	
    	
    	public static LockfreeQueue<CoupleResult> getLinks9E2(String _url, int N1, int N2,int NombreGeneration) { //idem que getLinks 7 mais on charge la page initiale
    		LockfreeQueue<CoupleResult> res = new LockfreeQueue<CoupleResult>();				//Fonctionne rapidement avec 350 et 4-5
    		HashMap<String,Couple> rempli= new HashMap<String,Couple>();
    		Couple initial=new Couple (true,0);
    		rempli.put(_url, initial);
    		LockfreeQueue<Trio> UrlEtPage=new LockfreeQueue<Trio>();
    		String Page = LirePage.getTextFile("http://starwars.wikia.com" +_url);
    		int debut = Page.indexOf("/wiki/Template:Character",0);
    		int fin =  Page.indexOf("div>In other languages</div>",debut);
    		String motini = "<a href=\"";
    		String motfin = "\" title=";
    		LockfreeQueue<String> RepererMot  = RepererMot2 (debut, fin, motini, motfin,Page);
    		//for (String r:RepererMot){System.out.println(r);}
    		
    		
                TaskReadE2[] threads=new TaskReadE2[N1] ;
                TaskVerificationBisE2[] threads2=new TaskVerificationBisE2[N2] ;
           
               for (int i=1; i<=NombreGeneration;i++){ 
             
        			
        				
                for (int NumeroThread1=0;NumeroThread1<N1;NumeroThread1++){
                    threads[NumeroThread1] = new TaskReadE2(RepererMot,rempli,UrlEtPage,i);
                    threads[NumeroThread1].start();
                   
                }
                    
              

              for (int I=0;I<N1;I++) {
               
               		try {
               			threads[I].join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
               	}
               	
             
                   
               for (int j=0;j<N2;j++){
                    TaskVerificationBisE2 thread2 = new TaskVerificationBisE2 (rempli,UrlEtPage,RepererMot);
                    threads2[j]=thread2;
                    thread2.start();
                }
                
                for (Thread thread2:threads2) {
                	try {
						thread2.join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
               }
               
               Set<String> S= rempli.keySet();
               for (String s:S){
            	   Couple c= rempli.get(s);
            	   if (c.b){
            		   CoupleResult R= new CoupleResult(s,c.i);
            		   res.add(R);
            	   }
               }
               return res;
               
    	} 
    	
    	public static LockfreeQueue<CoupleResult> getLinks11E2(String _url, int N1, int N2,int NombreGeneration) { //avec lockfreequeue pour les pages a lire et AtomicInteger liant les readers et les vérificateurs
    		LockfreeQueue<CoupleResult> res = new LockfreeQueue<CoupleResult>();				
    		HashMap<String,Couple> rempli= new HashMap<String,Couple>();
    		Couple initial=new Couple (true,0);
    		rempli.put(_url, initial);
    		LockfreeQueue<Trio> UrlEtPage=new LockfreeQueue<Trio>();
    		String Page = LirePage.getTextFile("http://starwars.wikia.com"+_url);
    		int debut = Page.indexOf("/wiki/Template:Character",0);
    		int fin =  Page.indexOf("div>In other languages</div>",debut);
    		String motini = "<a href=\"";
    		String motfin = "\" title=";
    		LockfreeQueue<String> ALire1  = RepererMot2 (debut, fin, motini, motfin,Page);
    		LockfreeQueue<CoupleResult> ALire= new LockfreeQueue<CoupleResult>();
    		while(!ALire1.isEmpty()){ALire.add(new CoupleResult(ALire1.take(),1));}
    		AtomicInteger N=new AtomicInteger(N1);
    	
    		TaskReadConnectedE2 [] threads = new TaskReadConnectedE2[N1];
    		TaskVerificationConnectedE2 [] threads2 = new TaskVerificationConnectedE2[N2]; 
    		
    		while (!ALire.isEmpty()){
    		for (int i =0;i<N1;i++) {
    			 TaskReadConnectedE2 r= new TaskReadConnectedE2(ALire, rempli, UrlEtPage,N,NombreGeneration);
    			 r.start();
    			 threads[i]=r;
    		}
    		
    		
    		for (int i =0;i<N2;i++) {
    			TaskVerificationConnectedE2 t= new TaskVerificationConnectedE2(rempli,ALire,UrlEtPage,N,NombreGeneration);
    			threads2[i] = t;
    			threads2[i].start();
    		}
    	
    		for (int i =0;i<N2;i++) {
    			try {
					threads2[i].join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    		N.set(N1);
    		}
    		 Set<String> S= rempli.keySet();
             for (String s:S){
          	   Couple c= rempli.get(s);
          	   if (c.b){
          		   CoupleResult R= new CoupleResult(s,c.i);
          		   res.add(R);
          		   
          	   }
             }
             return res;
             
  	} 
    	
    	public static LockfreeQueue<CoupleResult> getLinks12E2(String _url, int N1, int N2,int NombreGeneration) { //avec lockfreequeue pour les pages a lire et AtomicInteger liant les readers et les vérificateurs
    		LockfreeQueue<CoupleResult> res = new LockfreeQueue<CoupleResult>();				
    		HashMap<String,Couple> rempli= new HashMap<String,Couple>();
    		Couple initial=new Couple (true,0);
    		rempli.put(_url, initial);
    		LockfreeQueue<Trio> UrlEtPage=new LockfreeQueue<Trio>();
    		String Page = LirePage.getTextFile("http://starwars.wikia.com"+_url);
    		int debut = Page.indexOf("/wiki/Template:Character",0);
    		int fin =  Page.indexOf("div>In other languages</div>",debut);
    		String motini = "<a href=\"";
    		String motfin = "\" title=";
    		LockfreeQueue<String> ALire1  = RepererMot2 (debut, fin, motini, motfin,Page);
    		LockfreeQueue<CoupleResult> ALire= new LockfreeQueue<CoupleResult>();
    		while(!ALire1.isEmpty()){ALire.add(new CoupleResult(ALire1.take(),1));}
    		AtomicInteger N=new AtomicInteger(N1);
    	
    		TaskReadConnectedE22 [] threads = new TaskReadConnectedE22[N1];
    		TaskVerificationConnectedE22 [] threads2 = new TaskVerificationConnectedE22[N2]; 
    		for(int GenerationCourante=0;GenerationCourante<NombreGeneration;GenerationCourante++){
    		
    		for (int i =0;i<N1;i++) {
    			 TaskReadConnectedE22 r= new TaskReadConnectedE22(ALire, rempli, UrlEtPage,N,NombreGeneration,GenerationCourante);
    			 r.start();
    			 threads[i]=r;
    		}
    		
    		
    		for (int i =0;i<N2;i++) {
    			TaskVerificationConnectedE22 t= new TaskVerificationConnectedE22(rempli,ALire,UrlEtPage,N,NombreGeneration,GenerationCourante);
    			threads2[i] = t;
    			threads2[i].start();
    		}
    	
    		for (int i =0;i<N2;i++) {
    			try {
					threads2[i].join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    		N.set(N1);
    		ALire=threads2[0].Verifier;
    		}
    		 Set<String> S= rempli.keySet();
             for (String s:S){
          	   Couple c= rempli.get(s);
          	   if (c.b){
          		   CoupleResult R= new CoupleResult(s,c.i);
          		   res.add(R);
          		   
          	   }
             }
             return res;
             
  	} 
     	
    	public static LockfreeQueue<CoupleResultSucc> getLinks13E2(String _url, int N1, int N2,int NombreGeneration) { //avec lockfreequeue pour les pages a lire et AtomicInteger liant les readers et les vérificateurs
    		LockfreeQueue<CoupleResultSucc> res = new LockfreeQueue<CoupleResultSucc>();				
    		HashMap<String,CoupleSucc> rempli= new HashMap<String,CoupleSucc>();
    		Couple initial=new Couple (true,0);
    		CoupleSucc init= new CoupleSucc(initial,new LockfreeQueue<String>());
    		rempli.put(_url, init);
    		LockfreeQueue<TrioSucc> UrlEtPage=new LockfreeQueue<TrioSucc>();
    		String Page = LirePage.getTextFile("http://starwars.wikia.com"+_url);
    		int debut = Page.indexOf("/wiki/Template:Character",0);
    		int fin =  Page.indexOf("div>In other languages</div>",debut);
    		String motini = "<a href=\"";
    		String motfin = "\" title=";
    		LockfreeQueue<String> ALire1  = RepererMot2 (debut, fin, motini, motfin,Page);
    		LockfreeQueue<CoupleResultSucc> ALire= new LockfreeQueue<CoupleResultSucc>();
    		while(!ALire1.isEmpty()){
    			CoupleResult CR=new CoupleResult(ALire1.take(),1);
    			LockfreeQueue<String> queue=new LockfreeQueue<String>();
    			queue.add(_url);
    			ALire.add(new CoupleResultSucc(CR,queue));}
    		AtomicInteger N=new AtomicInteger(N1);
    	
    		TaskReadConnectedE22Succ [] threads = new TaskReadConnectedE22Succ[N1];
    		TaskVerificationConnectedE22Succ [] threads2 = new TaskVerificationConnectedE22Succ[N2]; 
    		int GenerationCourante=0;
    		while(GenerationCourante<NombreGeneration){
    		
    		for (int i =0;i<N1;i++) {
    			 TaskReadConnectedE22Succ r= new TaskReadConnectedE22Succ(ALire, rempli, UrlEtPage,N,NombreGeneration,GenerationCourante);
    			 r.start();
    			 threads[i]=r;
    		}
    		
    		
    		for (int i =0;i<N2;i++) {
    			TaskVerificationConnectedE22Succ t= new TaskVerificationConnectedE22Succ(rempli,ALire,UrlEtPage,N,NombreGeneration,GenerationCourante);
    			threads2[i] = t;
    			threads2[i].start();
    		}
    	
    		for (int i =0;i<N2;i++) {
    			try {
					threads2[i].join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    		N.set(N1);
    		ALire=threads2[0].Verifier;
    		GenerationCourante++;
    		}
    		 Set<String> S= rempli.keySet();
             for (String s:S){
          	   CoupleSucc C= rempli.get(s);
          	   Couple c= C.id;
          	   if (c.b){
          		   CoupleResult R= new CoupleResult(s,c.i);
          		   res.add(new CoupleResultSucc(R,C.parents));
          		   
          	   }
             }
             return res;
             
  	} 
    	
}   	   	

