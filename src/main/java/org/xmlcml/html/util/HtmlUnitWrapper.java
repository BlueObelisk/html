package org.xmlcml.html.util;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.html.HtmlFactory;
import org.xmlcml.xml.XMLUtil;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import nu.xom.Element;

public class HtmlUnitWrapper {

	
	private static final Logger LOG = Logger.getLogger(HtmlUnitWrapper.class);
	
	private WebClient webClient;
	private HtmlPage rawHtmlPage;
	private String pageAsXml;
	private Element xmlElement;

	private HtmlElement htmlElement;
	
	public HtmlUnitWrapper() {
		
	}
	
	/** NYI - headless browser .
	 * 
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public HtmlElement readAndCreateElement(URL url) throws Exception {
		
	    webClient = new WebClient();
	    rawHtmlPage = webClient.getPage(url.toString());
	    pageAsXml = rawHtmlPage.asXml();
	    int l = pageAsXml.length();
	    pageAsXml = HtmlUtil.removeBMCHorror(pageAsXml);
	    if (l != pageAsXml.length()) {
	    	LOG.debug("Removed BMC Horror");
	    }
	    FileUtils.write(new File("target/pageAsXml.xml"), pageAsXml);
	    webClient.closeAllWindows();
		htmlElement = null;
		try {
			HtmlFactory htmlFactory = new HtmlFactory();
			htmlFactory.setIgnoreNamespaces(true);
			xmlElement = XMLUtil.parseXML(pageAsXml);
			XMLUtil.debug(xmlElement, new FileOutputStream("target/htmlUnit.xml"), 1);
			htmlElement = htmlFactory.parse(xmlElement);
		} catch (Exception e) {
			LOG.error("cannot parse HTML "+pageAsXml, e);
		}
		return htmlElement;
	}


}
