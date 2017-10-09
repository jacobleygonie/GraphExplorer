package ParcoursDeGrapheSansMemoireUneGeneration;
public class Verification {
	
	public static boolean personnage(String _url) {
		
		String page =  LirePage.getTextFile(_url);
		boolean res=false;
        if (page.indexOf("Biographical information", 0)>=0)  {
                res=true;
        }
         return res;
  }	
}

