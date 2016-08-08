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

package ym2515.xml.MidiMapping;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Manages loading of the Mapping for the KEY(names) of the Controls, to the Midi CC Numbers.
 * */
public class MidiMapping {
	

	/**
	 * Mapping Data for the different Control Sections of a Instrument
	 * */
	public final HashMap<String, Integer> map_op1,map_op2,map_op3,map_op4,map_lfo, map_common;
	
	
	/**
	 * Create a new empty MidiMapping
	 * */
	public MidiMapping(){
		map_op1 = new HashMap<String, Integer>();
		map_op2 = new HashMap<String, Integer>();
		map_op3 = new HashMap<String, Integer>();
		map_op4 = new HashMap<String, Integer>();
		map_lfo = new HashMap<String, Integer>();
		map_common = new HashMap<String, Integer>();
	}	
	
	@Override
	public String toString() {
		return "[Operator 1="+map_op1+"; Operator 2="+map_op2+"; Operator 3="+map_op3+"; Operator 4="+map_op4+"; LFO="+map_lfo+"; Common="+map_common+"]";
	}
	
	
	
	/**
	 * Load a Mapping from a File
	 * @param path Path to the File
	 * */
	public static MidiMapping loadMapping(File path) throws SAXException, IOException, ParserConfigurationException, XPathExpressionException{
		 MidiMapping m = new MidiMapping();
        
		 //xml Stuff
		 DocumentBuilderFactory dbFactory  = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         
         Document doc = dBuilder.parse(path);
         doc.getDocumentElement().normalize();
         
        
         
         //parse different mapping sections
         parseHashMap(m.map_op1, doc,"/Midi/Operator_1/map");
         parseHashMap(m.map_op2, doc,"/Midi/Operator_2/map");
         parseHashMap(m.map_op3, doc,"/Midi/Operator_3/map");
         parseHashMap(m.map_op4, doc,"/Midi/Operator_4/map");
         parseHashMap(m.map_lfo, doc,"/Midi/LFO/map");
         parseHashMap(m.map_common, doc,"/Midi/Common/map");

		return m;
	}
	
	/**
	 * Parse a part of a XML file into a HashMap.
	 * @param map puts entries from the XML File into there
	 * @param doc the Document to Parse
	 * @param path Path to the HashMap in the xml File
	 * */
	private static void parseHashMap(HashMap<String, Integer> map, Document doc, String path) throws XPathExpressionException{  
		 XPath xPath =  XPathFactory.newInstance().newXPath();
		NodeList list = (NodeList) xPath.compile(path).evaluate(doc, XPathConstants.NODESET);
       
        for(int i = 0; i< list.getLength(); i++){
	         Node n = list.item(i);
	       	 if(n.getNodeType() == Node.ELEMENT_NODE){
	       		Node key = n.getAttributes().getNamedItem("key");
	       		Node value = n.getAttributes().getNamedItem("value");
	       		map.put(key.getNodeValue(), Integer.parseInt(value.getNodeValue()));	
	       	 }
        }	
	}
}

