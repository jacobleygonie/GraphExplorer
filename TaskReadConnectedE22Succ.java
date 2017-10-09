package ParcoursDeGrapheSansMemoireTotal;



import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import java.util.LinkedList;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.LockfreeQueue;




public class TaskReadConnectedE22Succ extends Thread {
	HashMap<String,CoupleSucc> rempli; //boolean true si c'est un personnage
	LockfreeQueue<TrioSucc> UrlEtPage;
	LockfreeQueue<CoupleResultSucc> ALire;
	AtomicInteger N;
	int NombreGeneration;
	int GenerationCourante;
	
	TaskReadConnectedE22Succ(LockfreeQueue<CoupleResultSucc> ALire,HashMap<String,CoupleSucc> rempli,LockfreeQueue<TrioSucc> UrlEtPage, AtomicInteger N,int NombreGeneration,int GenerationCourante){
		this.ALire=ALire;
		this.rempli=rempli;
		this.UrlEtPage=UrlEtPage;
		this.N=N;
		this.NombreGeneration=NombreGeneration;
		this.GenerationCourante=GenerationCourante;
	}
	
	
public void run()  {
	
    	while (!ALire.isEmpty()) {
    		
    		
    		BufferedReader reader = null;
    		try {
    			CoupleResultSucc C=ALire.take();
    					if(C==null){continue;}
    			CoupleResult c = C.id;
    			LockfreeQueue<String> parents=C.parents;
    		
    			
    			//if(c.i==3) System.out.println("bb");
    			int generation=c.i;
    			String _url=c.s;
    			if (generation>this.NombreGeneration){continue;}
    			
    			
 			
    			synchronized (rempli) {
    				if (this.rempli.containsKey(_url)){
    					
    					continue;
    					
    				}
    				// Ici on ne fait pas continue; si l'on crois obiWan en 4ème géné pour la 1ère fois, ses connaissances seront vites oubliées car trop éloignées de la source. Or si l'on avait continue et que l'on croisait ensuite ObiWan en 2ème génération, ses amis seraient également omis.
    			}// Néanmoins, on perd beaucoup de temps à rééexaminer ObiWan.
    		
        	
 			boolean pasunedate=((_url.startsWith("/wiki/")) && !(_url.equals("/wiki/Article_nominations"))&&!(_url.startsWith("/wiki/LEGO"))&&!(_url.equals("/wiki/Wizards.com"))&&
					!(_url.endsWith("/Legends")) && !(_url.endsWith("/Canon")) &&
						!(_url.startsWith("/wiki/Wookieepedia")) && 
								!(_url.startsWith("/wiki/The_Topps_Company,_Inc."))&&!(_url.startsWith("/wiki/Hasbro_Inc"))&&
							!(_url.startsWith("/wiki/Category")) &&
								!(_url.startsWith("/wiki/Forum")) &&
									!(_url.startsWith("/wiki/Special:")) &&
										!(_url.equals("/wiki/Executor")) && !(_url.equals("/wiki/Star_Wars_Legends")) && !(_url.equals("/wiki/Main_Page")) && !(_url.equals("/wiki/Local_Sitemap")) && !(_url.equals("/wiki/Help:Contents")) &&     
											!(_url.startsWith("/wiki/Talk:")) &&
												!(_url.startsWith("/wiki/List_of_Star")) && 
													!(_url.startsWith("/wiki/Template:")) &&
														!(_url.startsWith("/wiki/The_Official_Star_Wars")) &&
															!(_url.startsWith("/wiki/New_Jedi_Order")) && !(_url.startsWith("/wiki/Jedi")) && !(_url.startsWith("/wiki/Sith")) &&
																!(_url.startsWith("/wiki/Fate_of_the_Jedi")) &&
																	!(_url.startsWith("/wiki/File:")) &&
																		!(_url.startsWith("/wiki/Article")) &&
																			!(_url.equals("/wiki/Lightsaber_combat")) && !(_url.equals("/wiki/Galactic_Republic")) && !(_url.equals("/wiki/The_Force")) && !(_url.equals("/wiki/The_galaxy")) && !(_url.equals("/wiki/Timeline_of_galactic_history")) && !(_url.equals("/wiki/Clone_Wars")) && !(_url.equals("/wiki/Grand_Army_of_the_Republic")) && !(_url.equals("/wiki/Confederacy_of_Independent_Systems")) &&
																				!(_url.startsWith("_url")) && 
																					!(_url.startsWith("/wiki/Star_Wars")) && !(_url.startsWith("/wiki/StarWars")) &&
																						!(_url.startsWith("/wiki/Encyclopedia_")) &&
																							!(_url.startsWith("/wiki/Databank_")) &&
																								!(_url.startsWith("/wiki/LEGO_Star_Wars")) &&
																									!(_url.startsWith("/wiki/William_Shakespeare")) &&
																										!(_url.startsWith("/wiki/Attack_of_the_Clones_")));
        	  if(pasunedate){
            	for (int i = 1980; i<2018; i++){
            		pasunedate = pasunedate && (!_url.equals("/wiki/"+i));
            	}}
            	if(!pasunedate){
            		Couple c1=new Couple(false,-1);
    				synchronized (rempli){rempli.put(_url, new CoupleSucc(c1, new LockfreeQueue<String>()));
}
            		continue;
            	}
 		
 			
    			
 			
            URL url = new URL("http://starwars.wikia.com"+_url);
            URLConnection urlConnection = url.openConnection();
            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
           Trio T = new Trio(_url,sb.toString(),generation);
         
            UrlEtPage.add(new TrioSucc(T,parents));
          
           synchronized(N){ N.notifyAll();}
         
            
           
        } catch (IOException ex) {
            Logger.getLogger(LirePage.class.getName()).log(Level.SEVERE, null, ex);
            return;
            
        }
        finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(LirePage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    this.N.decrementAndGet();
    synchronized(this.N){N.notifyAll();}
    }
} 
	

