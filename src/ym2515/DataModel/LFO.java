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

package ym2515.DataModel;

import ym2515.Swing.ListenerHashMap;


/**
 * This Holds the LFO Values of for a Instrument
 * */
public class LFO {
	
	
	/**
	 * A Map that holds the different Controller Values<br>
	 * There KEYs for the Different Knobs are:<br>
	 * LFRQ,<br>
	 * AMD,<br>
	 * PMD,<br>
	 * WF,<br>
	 * NFRQ,<br>
	 * NE <br>
	 * */
	public final ListenerHashMap<String, Integer> map;
	
	
	/**
	 * Creates a new LFO Data Model for the representation of a Instrument in the Controller
	 * */
	protected LFO(){
		map = new ListenerHashMap<String, Integer>();
	}
	
	
	/**
	 * This is Part of the OPM File Parsing. Use OPMFile to Load a OPM File.
	 * @param s the String Values to parse.
	 * @return "true" if parsing went OK and "false" if else.
	 * */
	protected boolean setVars(String[] s){
		if(s.length != 6){
			return false;
		}
		
		map.put("LFRQ", Integer.parseInt(s[1]) >>> 1);
		
		map.put("AMD", Integer.parseInt(s[2]));
		map.put("PMD", Integer.parseInt(s[3]));
		
		map.put("WF", Integer.parseInt(s[4]) << 5);
		map.put("NFRQ", Integer.parseInt(s[5]) << 2);
		
		return true;
	}
	
	
	/**
	 * This is Part of the OPM File Creation. Use OPMFile to Save a OPM File.
	 * */
	protected String toOPM(){
		String sp = "  ";
		
		int LFRQ = map.get("LFRQ") << 1;
		int AMD = map.get("AMD");
		int PMD = map.get("PMD");
		int WF = map.get("WF") >> 5;
		int NFRQ = map.get("NFRQ") >> 2;
		
		return LFRQ+sp+AMD+sp+PMD+sp+WF+sp+NFRQ+"\n";
	}
	
	
}
