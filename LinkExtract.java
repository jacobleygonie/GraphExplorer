package ParcoursDeGrapheSansMemoireUneGeneration;


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

import util.LockfreeQueue;

import javax.swing.text.html.HTMLEditorKit;
 
/**
 * Extraire les liens d'un document HTML
 */
public class LinkExtract {
	
	
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
			res.add(nouv);
			k = page.indexOf(motini,end+1);
		}
		return res;
	}
	
	
	public static LinkedList<String> getLinks(String _url) { // Extrait simplement les liens présent sur un 
		LinkedList<String> res = new LinkedList<String>();
        try {
        	
            //Charger la page
            URL url = new URL(_url);
            URLConnection uconnection = url.openConnection();
            Reader rd = new InputStreamReader(uconnection.getInputStream());
            //lire le document HTML
            EditorKit kit = new HTMLEditorKit();
            HTMLDocument doc = (HTMLDocument) kit.createDefaultDocument();
            doc.putProperty("IgnoreCharsetDirective", new Boolean(true));
            kit.read(rd, doc, 0);
            //Parcourir la balise lien
            HTMLDocument.Iterator it = doc.getIterator(HTML.Tag.A);
            while (it.isValid()) {
                SimpleAttributeSet s = (SimpleAttributeSet) it.getAttributes();
                String link = (String) s.getAttribute(HTML.Attribute.HREF);
                if (link!=null) {
                	res.add(link);
                }
                it.next();
            }
        } catch (BadLocationException ex) {
            Logger.getLogger(LinkExtract.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LinkExtract.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
	
	public static LinkedList<String> getLinks2(String _url) {
		LinkedList<String> res = new LinkedList<String>();
        try {
        	
            //Charger la page
            URL url = new URL(_url);
            URLConnection uconnection = url.openConnection();
            Reader rd = new InputStreamReader(uconnection.getInputStream());
            //lire le document HTML
            EditorKit kit = new HTMLEditorKit();
            HTMLDocument doc = (HTMLDocument) kit.createDefaultDocument();
            doc.putProperty("IgnoreCharsetDirective", new Boolean(true));
            kit.read(rd, doc, 0);
            //Parcourir la balise lien
            HTMLDocument.Iterator it = doc.getIterator(HTML.Tag.A);
            while (it.isValid()) {
                SimpleAttributeSet s = (SimpleAttributeSet) it.getAttributes();
                String link = (String) s.getAttribute(HTML.Attribute.HREF);
                if (link!=null && link.startsWith("/wiki/")) {
                	res.add(link);
                }
                it.next();
            }
        } catch (BadLocationException ex) {
            Logger.getLogger(LinkExtract.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LinkExtract.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
	
	public static LinkedList<String> getLinks3(String _url) {
		LinkedList<String> res = new LinkedList<String>();
        try {
        	
            //Charger la page
            URL url = new URL(_url);
            URLConnection uconnection = url.openConnection();
            Reader rd = new InputStreamReader(uconnection.getInputStream());
            //lire le document HTML
            EditorKit kit = new HTMLEditorKit();
            HTMLDocument doc = (HTMLDocument) kit.createDefaultDocument();
            doc.putProperty("IgnoreCharsetDirective", new Boolean(true));
            kit.read(rd, doc, 0);
            //Parcourir la balise lien
            HTMLDocument.Iterator it = doc.getIterator(HTML.Tag.A);
            while (it.isValid()) {
                SimpleAttributeSet s = (SimpleAttributeSet) it.getAttributes();
                String link = (String) s.getAttribute(HTML.Attribute.HREF);
                if (link!=null && !(link.equals("/wiki/Article_nominations")) && link.startsWith("/wiki/") && Verification.personnage("http://starwars.wikia.com"+link) ) {
                	
                	res.add(link);
                }
                it.next();
            }
        } catch (BadLocationException ex) {
            Logger.getLogger(LinkExtract.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LinkExtract.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
	
	public static LinkedList<String> getLinks4(String _url) { //avec threads
		LinkedList<String> res = new LinkedList<String>();
		HashMap<String,Boolean> rempli= new HashMap<String,Boolean>();
        try {
        	
            //Charger la page
            URL url = new URL(_url);
            URLConnection uconnection = url.openConnection();
            Reader rd = new InputStreamReader(uconnection.getInputStream());
            //lire le document HTML
            EditorKit kit = new HTMLEditorKit();
            HTMLDocument doc = (HTMLDocument) kit.createDefaultDocument();
            doc.putProperty("IgnoreCharsetDirective", new Boolean(true));
            kit.read(rd, doc, 0);
            //Parcourir la balise lien
            HTMLDocument.Iterator it = doc.getIterator(HTML.Tag.A);
            LinkedList<TaskVerification> threads=new LinkedList<TaskVerification>();
            
            while (it.isValid()) {
                SimpleAttributeSet s = (SimpleAttributeSet) it.getAttributes();
                String link = (String) s.getAttribute(HTML.Attribute.HREF);
                TaskVerification t = new TaskVerification(link,rempli);
                threads.add(t);
                it.next();
            }
            for (Thread t: threads){
            	t.start();
            }
            for (Thread t: threads){
            	t.join();
            }
            
        } catch (BadLocationException ex) {
            Logger.getLogger(LinkExtract.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LinkExtract.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        
        Set<String> inter = rempli.keySet();
        
        for (String s:inter) {
        	if (rempli.get(s)==true) {
        		res.add(s);
        	}
        }
        return res;
    }
	
	public static LinkedList<String> getLinks5(String _url, int Nthreads) { //avec Nthreads threads
		LinkedList<String> res = new LinkedList<String>();
		HashMap<String,Boolean> rempli= new HashMap<String,Boolean>();
        try {
        	
            //Charger la page
            URL url = new URL(_url);
            URLConnection uconnection = url.openConnection();
            Reader rd = new InputStreamReader(uconnection.getInputStream());
            //lire le document HTML
            EditorKit kit = new HTMLEditorKit();
            HTMLDocument doc = (HTMLDocument) kit.createDefaultDocument();
            doc.putProperty("IgnoreCharsetDirective", new Boolean(true));
            kit.read(rd, doc, 0);
            //Parcourir la balise lien
            HTMLDocument.Iterator it = doc.getIterator(HTML.Tag.A);
            TaskVerification[] threads=new TaskVerification[Nthreads] ;
            int NumeroThread=0;
            
            while (it.isValid()) {
                SimpleAttributeSet s = (SimpleAttributeSet) it.getAttributes();
                String link = (String) s.getAttribute(HTML.Attribute.HREF);
                if (threads[NumeroThread]!=null){
                threads[NumeroThread].join();
                }
                
                threads[NumeroThread] = new TaskVerification(link,rempli);
                threads[NumeroThread].start();
                NumeroThread=(NumeroThread+1)%(Nthreads);
                it.next();
            }
          
            
        } catch (BadLocationException ex) {
            Logger.getLogger(LinkExtract.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LinkExtract.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        
        Set<String> inter = rempli.keySet();
        
        for (String s:inter) {
        	if (rempli.get(s)==true) {
        		res.add(s);
        	}
        }
        return res;
    }
	
	public static LockfreeQueue<String> getLinks6(String _url, int N1) { //avec Nthreads threads et read/verification séparées en 2 threads à chaque tour
		LockfreeQueue<String> res = new LockfreeQueue<String>();
		HashMap<String,Boolean> rempli= new HashMap<String,Boolean>();
		Stack<String []> UrlEtPage=new Stack<String []>();
        try {
        	
            //Charger la page
            URL url = new URL(_url);
            URLConnection uconnection = url.openConnection();
            Reader rd = new InputStreamReader(uconnection.getInputStream());
            //lire le document HTML
            EditorKit kit = new HTMLEditorKit();
            HTMLDocument doc = (HTMLDocument) kit.createDefaultDocument();
            doc.putProperty("IgnoreCharsetDirective", new Boolean(true));
            kit.read(rd, doc, 0);
            //Parcourir la balise lien
            HTMLDocument.Iterator it = doc.getIterator(HTML.Tag.A);
            TaskRead[] threads=new TaskRead[N1] ;
            LinkedList<TaskVerificationBis> threads2=new LinkedList<TaskVerificationBis>() ;
            int NumeroThread1=0;
            
            while (it.isValid()) {
                SimpleAttributeSet s = (SimpleAttributeSet) it.getAttributes();
                String link = (String) s.getAttribute(HTML.Attribute.HREF);
                if (threads[NumeroThread1]!=null){
                	threads[NumeroThread1].join();
                }
                
                threads[NumeroThread1] = new TaskRead(link,rempli,UrlEtPage);
                threads[NumeroThread1].start();
                NumeroThread1=(NumeroThread1+1)%(N1);
                
                TaskVerificationBis thread2 = new TaskVerificationBis (rempli,UrlEtPage,res);
                threads2.add(thread2);
                thread2.start();
                
                it.next();
            }
            
            for (Thread thread2:threads2) {
            	thread2.join();
            }
          
            
        } catch (BadLocationException ex) {
            Logger.getLogger(LinkExtract.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LinkExtract.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        
        Set<String> inter = rempli.keySet();
        
        for (String s:inter) {
        	if (rempli.get(s)==true) {
        		res.add(s);
        	}
        }
        return res;
    }
	
	public static LockfreeQueue<String> getLinks7(String _url, int N1, int N2) { //avec N1 readers et N2 verificationNew ; optimum à N1=310, N2=3
		LockfreeQueue<String> res = new LockfreeQueue<String>();
		HashMap<String,Boolean> rempli= new HashMap<String,Boolean>();
		Stack<String []> UrlEtPage=new Stack<String []>();
        try {
        	
            //Charger la page
            URL url = new URL(_url);
            URLConnection uconnection = url.openConnection();
            Reader rd = new InputStreamReader(uconnection.getInputStream());
            //lire le document HTML
            EditorKit kit = new HTMLEditorKit();
            HTMLDocument doc = (HTMLDocument) kit.createDefaultDocument();
            doc.putProperty("IgnoreCharsetDirective", new Boolean(true));
            kit.read(rd, doc, 0);
            //Parcourir la balise lien
            HTMLDocument.Iterator it = doc.getIterator(HTML.Tag.A);
            TaskRead[] threads=new TaskRead[N1] ;
            TaskVerificationBis[] threads2=new TaskVerificationBis[N2] ;
            int NumeroThread1=0;
            int NumeroThread2=0;
            
            while (it.isValid()) {
                SimpleAttributeSet s = (SimpleAttributeSet) it.getAttributes();
                String link = (String) s.getAttribute(HTML.Attribute.HREF);
                if (threads[NumeroThread1]!=null){
                	threads[NumeroThread1].join();
                }
                
                threads[NumeroThread1] = new TaskRead(link,rempli,UrlEtPage);
                threads[NumeroThread1].start();
                NumeroThread1=(NumeroThread1+1)%(N1);
                if (threads[NumeroThread2]!=null){
                	threads[NumeroThread2].join();
                }
                TaskVerificationBis thread2 = new TaskVerificationBis (rempli,UrlEtPage,res);
                threads2[NumeroThread2]=thread2;
                thread2.start();
                NumeroThread2=(NumeroThread2+1)%(N2);;
                
                it.next();
            }
            
            for (Thread thread2:threads2) {
            	thread2.join();
            }
          
            
        } catch (BadLocationException ex) {
            Logger.getLogger(LinkExtract.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LinkExtract.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        
        Set<String> inter = rempli.keySet();
        
        for (String s:inter) {
        	if (rempli.get(s)==true) {
        		res.add(s);
        	}
        }
        return res;
        
	} 

    	public static LinkedList<String> getLinks8(String _url, int Nthreads) { //avec Nthreads threads
    		LinkedList<String> res = new LinkedList<String>();
    		HashMap<String,Boolean> rempli= new HashMap<String,Boolean>();
            try {
            	
                //Charger la page
                URL url = new URL(_url);
                URLConnection uconnection = url.openConnection();
                Reader rd = new InputStreamReader(uconnection.getInputStream());
                //lire le document HTML
                EditorKit kit = new HTMLEditorKit();
                HTMLDocument doc = (HTMLDocument) kit.createDefaultDocument();
                doc.putProperty("IgnoreCharsetDirective", new Boolean(true));
                kit.read(rd, doc, 0);
                //Parcourir la balise lien
                HTMLDocument.Iterator it = doc.getIterator(HTML.Tag.A);
                TaskVerificationNoLirePage[] threads=new TaskVerificationNoLirePage[Nthreads] ;
                int NumeroThread=0;
                
                while (it.isValid()) {
                    SimpleAttributeSet s = (SimpleAttributeSet) it.getAttributes();
                    String link = (String) s.getAttribute(HTML.Attribute.HREF);
                    if (threads[NumeroThread]!=null){
                    threads[NumeroThread].join();
                    }
                    
                    threads[NumeroThread] = new TaskVerificationNoLirePage(link,rempli);
                    threads[NumeroThread].start();
                    NumeroThread=(NumeroThread+1)%(Nthreads);
                    it.next();
                }
                for (int i=0;i<Nthreads;i++){
                	 threads[NumeroThread].join();
                	
                }
              
                
            } catch (BadLocationException ex) {
                Logger.getLogger(LinkExtract.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(LinkExtract.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
            
            
            
            
            
            Set<String> inter = rempli.keySet();
            
            for (String s:inter) {
            	if (rempli.get(s)==true) {
            		res.add(s);
            	}
            }
            return res;
        }
    	
    	public static LockfreeQueue<String> getLinks9(String _url, int N1, int N2) { //idem que getLinks 7 mais on charge la page initiale
    		LockfreeQueue<String> res = new LockfreeQueue<String>();				//Fonctionne rapidement avec 350 et 4-5
    		HashMap<String,Boolean> rempli= new HashMap<String,Boolean>();
    		Stack<String []> UrlEtPage=new Stack<String []>();
    		String Page = LirePage.getTextFile(_url);
    		int debut = Page.indexOf("/wiki/Template:Character",0);
    		int fin =  Page.indexOf("div>In other languages</div>",debut);
    		String motini = "<a href=\"";
    		String motfin = "\" title=";
    		LinkedList<String> RepererMot  = RepererMot (debut, fin, motini, motfin,Page);
    		//for (String r:RepererMot){System.out.println(r);}
    		
    		
                TaskRead[] threads=new TaskRead[N1] ;
                TaskVerificationBis[] threads2=new TaskVerificationBis[N2] ;
                int NumeroThread1=0;
                int NumeroThread2=0;
                int creerVerification=0;
                
                for (String perso :RepererMot ) {
        				//perso = perso.replace(' ', '_');
        				String link=(perso);
        				creerVerification++;
                    
                    if (threads[NumeroThread1]!=null){
                    	try {
							threads[NumeroThread1].join();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    }
                    
                    threads[NumeroThread1] = new TaskRead(link,rempli,UrlEtPage);
                    threads[NumeroThread1].start();
                    NumeroThread1=(NumeroThread1+1)%(N1);
                    if(creerVerification==10){
                    	creerVerification++;
                    }
                    	for (int i=0;i<N2;i++){
                    TaskVerificationBis thread2 = new TaskVerificationBis (rempli,UrlEtPage,res);
                    threads2[i]=thread2;
                    thread2.start();
                    
                    }
                   
                }
               
                
                for (Thread thread2:threads2) {
                	try {
						thread2.join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
            return res;
            
    	} 

     	
    	public static LockfreeQueue<String> getLinks10(String _url, int N1, int N2) { //avec lockfreequeue pour les pages a lire 
    		LockfreeQueue<String> res = new LockfreeQueue<String>();				
    		HashMap<String,Boolean> rempli= new HashMap<String,Boolean>();
    		Stack<String []> UrlEtPage=new Stack<String []>();
    		String Page = LirePage.getTextFile(_url);
    		int debut = Page.indexOf("/wiki/Template:Character",0);
    		int fin =  Page.indexOf("div>In other languages</div>",debut);
    		String motini = "<a href=\"";
    		String motfin = "\" title=";
    		LockfreeQueue<String> ALire  = RepererMot2 (debut, fin, motini, motfin,Page);
    	
    	
    		/*ExecutorService exec = Executors.newFixedThreadPool(N1+N2);
    		for (int i =0;i<N1;i++){
    			exec.execute(new TaskReadBis(ALire, rempli, UrlEtPage));
    		}
       		
          
    		
    		try {
      			 exec.shutdown();
      			      exec.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
      			    } catch (InterruptedException e) {
      			      e.printStackTrace();
      			    }*/
    		TaskReadBis [] threads = new TaskReadBis[N1];
    		for (int i =0;i<N1;i++) {
    			 TaskReadBis r= new  TaskReadBis(ALire, rempli, UrlEtPage);
    			 r.start();
    			 threads[i]=r;
    		}
    		for (int i =0;i<N1;i++) {
    			try {
					threads[i].join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    		TaskVerificationBis [] threads2 = new TaskVerificationBis[N2]; 
    		for (int i =0;i<N2;i++) {
    			TaskVerificationBis t= new TaskVerificationBis(rempli,UrlEtPage,res);
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
    		/*while(!UrlEtPage.isEmpty()){String[] s =UrlEtPage.pop();
    			System.out.println(s[0]);}*/
    			
       		
    		/*for (int i=0;i<N2;i++){
    			exec.execute(new TaskVerificationBis (rempli,UrlEtPage,res));
    		}
    		try {
    			 exec.shutdown();
    			      exec.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    			    } catch (InterruptedException e) {
    			      e.printStackTrace();
    			    }*/

            return res;
            
    	} 
    	
    	public static LockfreeQueue<String> getLinks11(String _url, int N1, int N2) { //avec lockfreequeue pour les pages a lire et AtomicInteger liant les readers et les vérificateurs
    		LockfreeQueue<String> res = new LockfreeQueue<String>();				
    		HashMap<String,Boolean> rempli= new HashMap<String,Boolean>();
    		LockfreeQueue<String []> UrlEtPage=new LockfreeQueue<String []>();
    		String Page = LirePage.getTextFile(_url);
    		int debut = Page.indexOf("/wiki/Template:Character",0);
    		int fin =  Page.indexOf("div>In other languages</div>",debut);
    		String motini = "<a href=\"";
    		String motfin = "\" title=";
    		LockfreeQueue<String> ALire  = RepererMot2 (debut, fin, motini, motfin,Page);
    		AtomicInteger N=new AtomicInteger(N1);
    	
    		TaskReadConnected [] threads = new TaskReadConnected[N1];
    		for (int i =0;i<N1;i++) {
    			 TaskReadConnected r= new  TaskReadConnected(ALire, rempli, UrlEtPage,N);
    			 r.start();
    			 threads[i]=r;
    		}
    		
    		TaskVerificationConnected [] threads2 = new TaskVerificationConnected[N2]; 
    		for (int i =0;i<N2;i++) {
    			TaskVerificationConnected t= new TaskVerificationConnected(rempli,UrlEtPage,res,N);
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
    		
    		return res;
    		
    	
}
    	public static LockfreeQueue<String> getLinks12(String _url, int N1, int N2) { //en mode executor
    		LockfreeQueue<String> res = new LockfreeQueue<String>();				
    		HashMap<String,Boolean> rempli= new HashMap<String,Boolean>();
    		LockfreeQueue<String []> UrlEtPage=new LockfreeQueue<String []>();
    		String Page = LirePage.getTextFile(_url);
    		int debut = Page.indexOf("/wiki/Template:Character",0);
    		int fin =  Page.indexOf("div>In other languages</div>",debut);
    		String motini = "<a href=\"";
    		String motfin = "\" title=";
    		LockfreeQueue<String> ALire  = RepererMot2 (debut, fin, motini, motfin,Page);
    		AtomicInteger N=new AtomicInteger(N1);
    	
    		ExecutorService exec = Executors.newFixedThreadPool(N1+N2);
    		for (int i =0;i<N1;i++){
    			exec.execute(new TaskReadConnected(ALire, rempli, UrlEtPage,N));
    		}
       		
		for (int i=0;i<N2;i++){
			exec.execute(new TaskVerificationConnected (rempli,UrlEtPage,res,N));
		}
		try {
			 exec.shutdown();
			      exec.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			    } catch (InterruptedException e) {
			      e.printStackTrace();
			    }
    		
    		
    		 return res;
    		 
    	}
}

