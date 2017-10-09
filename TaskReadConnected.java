package ParcoursDeGrapheSansMemoireUneGeneration;

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




public class TaskReadConnected extends Thread {
	LockfreeQueue<String> ALire;
	HashMap<String,Boolean> rempli;
	LockfreeQueue<String []> UrlEtPage;
	AtomicInteger N;
	
	TaskReadConnected(LockfreeQueue<String> ALire,HashMap<String,Boolean> rempli,LockfreeQueue<String []> UrlEtPage, AtomicInteger N){
		this.ALire=ALire;
		this.rempli=rempli;
		this.UrlEtPage=UrlEtPage;
		this.N=N;
	}
	
	
public void run()  {
	
    	while (!ALire.isEmpty()) {
    		
    		
    		BufferedReader reader = null;
    		try {
        	
    			String _url = ALire.take();
    			if ((_url==null)||this.rempli.containsKey(_url)) {
    				continue ;
 			}
 			
 			
        	
 			boolean pasunedate=((_url.startsWith("/wiki/")) && !(_url.equals("/wiki/Article_nominations"))&&
					!(_url.endsWith("/Legends")) && !(_url.endsWith("/Canon")) &&
						!(_url.startsWith("/wiki/Wookieepedia")) &&
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
            	if(!pasunedate){rempli.put(_url, false);
            		continue;}
 		
 			
 			
 			
            URL url = new URL("http://starwars.wikia.com"+_url);
            URLConnection urlConnection = url.openConnection();
            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            String[] T = new String[2];
            T[0]= _url;
            T[1]=sb.toString();
            UrlEtPage.add(T);
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
	

