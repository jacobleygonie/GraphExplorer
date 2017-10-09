package ParcoursDeGrapheSansMemoireTotal;


	import java.io.BufferedReader;
	import java.io.IOException;
	import java.io.InputStreamReader;
	import java.net.URL;
	import java.net.URLConnection;
import java.util.HashMap;
import java.util.Stack;
import java.util.logging.Level;
	import java.util.logging.Logger;

import util.LockfreeQueue;
	 
public class TaskReadE2 extends Thread{
		LockfreeQueue<String> RepererMot;
		HashMap<String,Couple> rempli;
		LockfreeQueue<Trio> UrlEtPage;
		int Generation;
		
		TaskReadE2(LockfreeQueue<String> RepererMot,HashMap<String,Couple> rempli,LockfreeQueue<Trio> UrlEtPage,int Generation){
			this.RepererMot=RepererMot;
			this.rempli=rempli;
			this.UrlEtPage=UrlEtPage;
			this.Generation=Generation;
		}
		
		
	    public void run()  {
	    	while (!RepererMot.isEmpty()){
	    		
	        BufferedReader reader = null;
	        try {
	        	String _url=RepererMot.take();
	        	 if (this.rempli.containsKey(_url)){
	        		 Couple c= this.rempli.get(_url);
	        		 if (c.i>this.Generation){continue;}
	        	 }
	        	 if ((_url==null)) {
	 				continue ;
	 			}
	 			
	 			
	        	
	 			boolean pasunedate=((_url.startsWith("/wiki/")) && !(_url.equals("/wiki/Article_nominations"))&&!(_url.equals("/wiki/Wizards.com"))&&
    					!(_url.endsWith("/Legends")) && !(_url.endsWith("/Canon")) &&!(_url.startsWith("/wiki/LEGO"))&&
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
                	if(!pasunedate){
                		Couple c=new Couple(false,-1);
                		rempli.put(_url, c);
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
	            
	            String page=sb.toString();
	            Trio t= new Trio (_url,page,Generation);
	            UrlEtPage.add(t);
	            //System.out.println("+1 de generation"+this.Generation);
	           
	            
	           
	        } catch (IOException ex) {
	            Logger.getLogger(LirePage.class.getName()).log(Level.SEVERE, null, ex);
	            continue;
	            
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
	    }
	} 


