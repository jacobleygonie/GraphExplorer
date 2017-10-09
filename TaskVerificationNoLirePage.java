package ParcoursDeGrapheSansMemoireUneGeneration;
import java.net.URL;
import java.util.HashMap;


import javax.swing.text.EditorKit;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.EditorKit;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.util.logging.Level;
import java.util.logging.Logger;





public class TaskVerificationNoLirePage extends Thread{
	String  _url;
	HashMap<String,Boolean> rempli; //boolean true si c'est un personnage
	
	public TaskVerificationNoLirePage(String url,HashMap<String,Boolean> rempli)
	 {
		this._url = url;
		this.rempli = rempli;
	}
	
	
	
	 
	@Override
	public  void run() {
		// TODO Auto-generated method stub
			if ((_url==null)||this.rempli.containsKey(_url)) {
				return ;
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
            		return;}
            	
			TrouverLienNoLirePage find= new TrouverLienNoLirePage("http://starwars.wikia.com"+_url); 
			
			try {
				boolean b = find.EstPerso();
				if (b) {
		              rempli.put(_url, true);
		             return;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
				
			rempli.put(_url, false);
			return;
				
			
	}

}
