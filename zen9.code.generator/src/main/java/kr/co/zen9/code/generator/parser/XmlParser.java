package kr.co.zen9.code.generator.parser;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class XmlParser {

	private File path;
	private Document doc;
	
	public XmlParser(File path) {
		this.path = path;
		init();
	}
	
	public XmlParser(String path) {
		this(new File(path));
	}
	
	public void init() {
		
         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			this.doc = dBuilder.parse(path);
			this.doc.getDocumentElement().normalize();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Document getDoc() {
		return doc;
	}
	
	
}
