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

package ym2151.xml.ControlFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.DefaultListModel;
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

import ym2151.DataModel.Channels;
import ym2151.DataModel.Instrument;
import ym2151.Swing.ListenerHashMap;

public class PatchFileManager {

	public PatchFileManager(){}

	
	/**
	 * Saves a document to a File
	 * @param doc the Document to save
	 * @param f the Path to save the Document to.
	 * */
	public void saveDocument(Document doc, File f) throws TransformerException, IOException{
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);

		FileWriter wr = new FileWriter(f);
		StreamResult result = new StreamResult(wr);
		transformer.transform(source, result);
		wr.close();
	}

	
	/**
	 * Create a XML Document from a instrument List.
	 * @param instrument the Instruments to add to the XMl File.
	 * */
	public Document createDocument(Instrument[] instrument) throws ParserConfigurationException{
		DocumentBuilderFactory dbFactory  = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.newDocument();


		Element instruments = doc.createElement("Instruments");
		doc.appendChild(instruments);

		for(Instrument i : instrument){
			instruments.appendChild(instrumentToXML(i,doc));
		}

		return doc;
	}

	/**
	 * gets the Instruments from a XML File
	 * @param path the Path to the xml File
	 * @return the parsed Instruments
	 * */
	public Instrument[] getInstruments(File path) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException{
		List<Instrument> inst = new LinkedList<Instrument>();
	
	
		DocumentBuilderFactory dbFactory  = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	
		Document doc = dBuilder.parse(path);
		doc.getDocumentElement().normalize();
	
		XPath xPath =  XPathFactory.newInstance().newXPath();
	
		NodeList list = (NodeList) xPath.compile("/Instruments/Instrument").evaluate(doc, XPathConstants.NODESET);
		for(int i = 0; i< list.getLength(); i++){
			Node n = list.item(i);
			if(n.getNodeType() == Node.ELEMENT_NODE){
				Instrument in = xmlToInstrument(n);
				if(in!=null)
					inst.add(in);
			}
		}
	
		return inst.toArray(new Instrument[0]);
	}

	/**
	 * adds a Instrument into a XML Element.
	 * @param i The Instrument to add
	 * @param doc the Document to add the entries to
	 * @return the Element where the Instrument has been added
	 * */
	private Element instrumentToXML(Instrument i, Document doc){
		//create Instrument Node

		Element ins = doc.createElement("Instrument");


		Attr name = doc.createAttribute("NAME");
		name.setValue(i.name);

		Attr lfo = doc.createAttribute("LFO");
		lfo.setValue(i.lfoSelected ? "1" : "0");

		Attr poly = doc.createAttribute("POLY");
		poly.setValue(i.polyInstr ? "1" : "0");

		ins.setAttributeNode(name);
		ins.setAttributeNode(lfo);
		ins.setAttributeNode(poly);



		ins.appendChild(mapToXML(i.lfo.map,doc,"LFO"));
		ins.appendChild(mapToXML(i.common.map,doc,"Common"));
		ins.appendChild(mapToXML(i.op1.map,doc,"Operator_1"));
		ins.appendChild(mapToXML(i.op2.map,doc,"Operator_2"));
		ins.appendChild(mapToXML(i.op3.map,doc,"Operator_3"));
		ins.appendChild(mapToXML(i.op4.map,doc,"Operator_4"));
		ins.appendChild(EnumeratorToXML(i.defaultListModel.elements(), doc, "Channels"));


		return ins;
	}

	
	/**
	 * adds a HashMap into a XML Element.
	 * @param map The HashMap to add
	 * @param doc the Document to add the entries to
	 * @param topElement name of the top Element
	 * @return the Element where the HashMap has been added
	 * */
	private static Element mapToXML(HashMap<String, Integer> map, Document doc, String topElement){
		Element el = doc.createElement(topElement);
		for(Entry<String, Integer> e : map.entrySet()){
			Element element = doc.createElement("Param");

			Attr name = doc.createAttribute("name");
			name.setNodeValue(e.getKey());

			Attr value = doc.createAttribute("value");
			value.setNodeValue(e.getValue().toString());

			element.setAttributeNode(name);
			element.setAttributeNode(value);

			el.appendChild(element);
		}
		return el;
	}

	
	/**
	 * adds a Enumeration into a XML Element.
	 * @param e The Enumeration to add
	 * @param doc the Document to add the entries to
	 * @param topElement name of the top Element
	 * @return the Element where the Enumeration has been added
	 * */
	private static Element EnumeratorToXML(Enumeration<? extends Enum<?>> e, Document doc, String topElement){
		Element el = doc.createElement(topElement);

		for (;e.hasMoreElements();){
			Enum<?> eleme = e.nextElement();

			Element element = doc.createElement("channel");

			Attr ch = doc.createAttribute("value");

			ch.setNodeValue(eleme.name());

			element.setAttributeNode(ch);

			el.appendChild(element);
		}
		return el;
	}

	
	/**
	 * Parses Instrument in a XML file into a Instrument.
	 * @param e Instrument Node
	 * @return parsed Instrument
	 * */
	private Instrument xmlToInstrument(Node e) throws XPathExpressionException{
		Node NAME = e.getAttributes().getNamedItem("NAME");
		Node LFO = e.getAttributes().getNamedItem("LFO");
		Node POLY = e.getAttributes().getNamedItem("POLY");

		Instrument i = new Instrument(NAME.getNodeValue());
		i.polyInstr = Integer.parseInt(POLY.getNodeValue()) > 0;
		i.lfoSelected = Integer.parseInt(LFO.getNodeValue()) > 0;

		i.lfo.map.putAll(xmlToListenerHashMap(e, "LFO/Param"));

		i.common.map.putAll(xmlToListenerHashMap(e, "Common/Param"));

		i.op1.map.putAll(xmlToListenerHashMap(e, "Operator_1/Param"));

		i.op2.map.putAll(xmlToListenerHashMap(e,"Operator_2/Param"));

		i.op3.map.putAll(xmlToListenerHashMap(e, "Operator_3/Param"));

		i.op4.map.putAll(xmlToListenerHashMap(e, "Operator_4/Param"));

		i.defaultListModel = xmlToChannelList(e, "Channels/channel");

		return i;
	}


	
	/**
	 * Parse a part of a XML file into a ListenerHashMap.
	 * @param Instr Instrument Node
	 * @param path Path to the ListenerHashMap in the xml File
	 * */
	private ListenerHashMap<String, Integer> xmlToListenerHashMap(Node Instr,String Path) throws XPathExpressionException {
		XPath xPath = XPathFactory.newInstance().newXPath();
		NodeList entries = (NodeList) xPath.compile(Path).evaluate(Instr,XPathConstants.NODESET);
		ListenerHashMap<String, Integer> map = new ListenerHashMap<String, Integer>();
		for (int i = 0; i < entries.getLength(); i++) {

			Node n = entries.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				Node NAME = n.getAttributes().getNamedItem("name");
				Node VALUE = n.getAttributes().getNamedItem("value");
				map.put(NAME.getNodeValue(),Integer.parseInt(VALUE.getNodeValue()));
			}
		}
		return map;
	}

	/**
	 * Parses the Channels List of an Instrument in a XML file into a DefaultListModel.
	 * @param Instr Instrument Node
	 * @param path Path to the xmlToChannelList in the XML File (relative to the Instrument node)
	 * */
	private DefaultListModel<Channels> xmlToChannelList(Node Instr, String Path) throws XPathExpressionException {
		DefaultListModel<Channels> m = new DefaultListModel<Channels>();

		XPath xPath = XPathFactory.newInstance().newXPath();
		NodeList entries = (NodeList) xPath.compile(Path).evaluate(Instr,XPathConstants.NODESET);

		for (int i = 0; i < entries.getLength(); i++) {
			Node n = entries.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				Node VALUE = n.getAttributes().getNamedItem("value");
				if (VALUE != null) {
					m.addElement(Channels.valueOf(VALUE.getNodeValue()));
				}
			}
		}
		return m;
	}

}

