package ParcoursDeGrapheSansMemoireUneGeneration;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.text.AttributeSet;
import javax.swing.text.Element;
import javax.swing.text.ElementIterator;
import javax.swing.text.StyleConstants;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

public class TrouverLienNoLirePage {
 static String _url;

 
 public TrouverLienNoLirePage(String _url){
	 this._url=_url;	
	
 }
 public boolean EstPerso() throws Exception{

	    boolean b1=false;
	    boolean b2=false;
	    // Load HTML file synchronously
	    URL url = new URL(_url);
	    URLConnection connection = url.openConnection();
	    InputStream is = connection.getInputStream();
	    InputStreamReader isr = new InputStreamReader(is);
	    BufferedReader br = new BufferedReader(isr);

	    HTMLEditorKit htmlKit = new HTMLEditorKit();
	    HTMLDocument htmlDoc = (HTMLDocument) htmlKit.createDefaultDocument();
	    HTMLEditorKit.Parser parser = new ParserDelegator();
	    HTMLEditorKit.ParserCallback callback = htmlDoc.getReader(0);
	    parser.parse(br, callback, true);

	    // Parse
	    ElementIterator iterator = new ElementIterator(htmlDoc);
	    Element element;
	    while ((element = iterator.next()) != null) {
	      AttributeSet attributes = element.getAttributes();
	      Object name = attributes.getAttribute(StyleConstants.NameAttribute);
	      if ((name instanceof HTML.Tag) && ((name == HTML.Tag.H3)||(name == HTML.Tag.H2))) {
	        // Build up content text as it may be within multiple elements
	        StringBuffer text = new StringBuffer();
	        int count = element.getElementCount();
	        for (int i = 0; i < count; i++) {
	          Element child = element.getElement(i);
	          AttributeSet childAttributes = child.getAttributes();
	          if (childAttributes
	              .getAttribute(StyleConstants.NameAttribute) == HTML.Tag.CONTENT) {
	            int startOffset = child.getStartOffset();
	            int endOffset = child.getEndOffset();
	            int length = endOffset - startOffset;
	            text.append(htmlDoc.getText(startOffset, length));
	          }
	        }
	        String potentiel = text.toString();
	        if (potentiel.contains("Species")){b1=true;}
	        if (potentiel.contains("Biographical information")){b2=true;}
	        if (b1&&b2){ return true;}
	     
	      }
	    }
	    return (b1&&b2);
	    //System.exit(0);
	   
	   
	  }
 
 /*public static void main(String[] args) throws Exception {
	 TrouverLienNoLirePage Lien= new TrouverLienNoLirePage("http://starwars.wikia.com/wiki/Yoda");
	 
	 System.out.print(Lien.EstPerso());
 }*/
 
}

