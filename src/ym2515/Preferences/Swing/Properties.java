/**
*	YM2151 - Midi Controller Software for Arduino Shield
*	(C) 2016  Marcel Weiﬂ
*
*	This program is free software : you can redistribute it and / or modify
*	it under the terms of the GNU General Public License as published by
*	the Free Software Foundation, either version 3 of the License, or
*	(at your option) any later version.
*
*	This program is distributed in the hope that it will be useful,
*	but WITHOUT ANY WARRANTY; without even the implied warranty of
*	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.See the
*	GNU General Public License for more details.
*
*	You should have received a copy of the GNU General Public License
*	along with this program.If not, see <http://www.gnu.org/licenses/>.
*/

package ym2515.Preferences.Swing;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Data Representation of the props.Properties File
 * */
public class Properties {
	
	
	private List<Entry<String,String>> data;
	
	/**
	 * Create a new Properties File Representation
	 * */
	public Properties(){
		data = new LinkedList<Entry<String,String>>();
	}
	
	
	
	/**
	 * Load Properties from a File and add them to this File
	 * */
	public void load(FileInputStream fileInputStream) throws SAXException, IOException, ParserConfigurationException, XPathExpressionException{
		data = new LinkedList<Entry<String,String>>();
	
		DocumentBuilderFactory dbFactory  = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fileInputStream);
		doc.getDocumentElement().normalize();

		XPath xPath =  XPathFactory.newInstance().newXPath();

		NodeList entries = (NodeList) xPath.compile("/Properties/Param").evaluate(doc, XPathConstants.NODESET);
		for (int i = 0; i < entries.getLength(); i++) {
			Node n = entries.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				Node NAME = n.getAttributes().getNamedItem("name");
				Node VALUE = n.getAttributes().getNamedItem("value");
				data.add(new Entry<String,String>(NAME.getNodeValue(),VALUE.getNodeValue()));
			}
			
		}

		
	}
	
	
	/**
	 * Saves all the Properties to a File
	 * */
	public void storeToXML(FileOutputStream fileOutputStream, Object object) throws ParserConfigurationException, TransformerException{
		DocumentBuilderFactory dbFactory  = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.newDocument();

		doc.appendChild(listToXML(data, doc, "Properties"));
		
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);

		
		StreamResult result = new StreamResult(fileOutputStream);
		transformer.transform(source, result);
	}
	
	
	/**
	 * Converts a List to XML Representation
	 * */
	private Element listToXML(List<Entry<String,String>> list, Document doc, String topElement){
		Element el = doc.createElement(topElement);
		for(Entry<String, String> e : list){
			Element element = doc.createElement("Param");

			Attr name = doc.createAttribute("name");
			name.setNodeValue(e.key.toString());

			Attr value = doc.createAttribute("value");
			value.setNodeValue(e.value.toString());

			element.setAttributeNode(name);
			element.setAttributeNode(value);

			el.appendChild(element);
		}
		return el;
	}
	
	
	
	/**
	 * Add a new Property
	 * @param key the KEY to Add
	 * @param value the value of the KEY
	 * */
	public void put(String key, String value){
		Entry<String, String> e = new Entry<String,String>(key,value);
		if(!data.contains(e))
			data.add(e);
	}
	
	
	/**
	 * Removes a Property
	 * @param key the KEY to remove
	 * @param value the value of the KEY
	 * */
	public void remove(String key, String value){
		data.remove(new Entry<String,String>(key, value));
	}
	
	/**
	 * @return the List with all Properties
	 * */
	public List<Entry<String,String>> getList(){
		return data;
	}
	
	/**
	 * Property List Entry
	 * */
	public static class Entry<K,V>{
		public K key; 
		public V value;
		
		public Entry(K key, V value){
			this.key=key;
			this.value=value;
		}
		
		@Override
		public boolean equals(Object ob) {
			return super.equals(ob) | (ob.getClass().equals(Entry.class) && ((Entry)ob).key.equals(key) &&((Entry)ob).value.equals(value));
		}
		
	}
	
	
	
}
