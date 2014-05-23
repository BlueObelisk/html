package org.xmlcml.html.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

public class JsoupWrapper {

	public JsoupWrapper() {
		
	}
	
	/** parses with Jsoup and tries to correct any bad XML
	 * 
	 * see tests for examples
	 * 
	 * @param s
	 * @return well-formed XML, maybe with "JUNK" inserted locally in place of bad html
	 */
	public static String parseAndCorrect(String s) {
		Document doc= Jsoup.parse(s);
		String ss = doc.toString().replaceAll("\\\"[^\\\"]*\\\"=\\\"", "JUNK");
		return ss;
	}
}
