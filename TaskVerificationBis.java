package ParcoursDeGrapheSansMemoireUneGeneration;
import java.util.HashMap;

import java.util.LinkedList;
import java.util.Stack;
import util.LockfreeQueue;
public class TaskVerificationBis extends Thread {
	
	HashMap<String,Boolean> rempli; //boolean true si c'est un personnage
	Stack<String []> UrlEtPage;
	LockfreeQueue<String> listefinale;
	
	public TaskVerificationBis(HashMap<String,Boolean> rempli,Stack<String []> UrlEtPage,LockfreeQueue<String> listefinale) {
		this.rempli = rempli;
		this.UrlEtPage = UrlEtPage;
		this.listefinale =listefinale;
	}
	
	@Override
	public  void run() {
		// TODO Auto-generated method stub
		
		
		while (!UrlEtPage.isEmpty()){
			
			String[] t = UrlEtPage.pop();
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
