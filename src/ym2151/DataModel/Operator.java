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

package ym2151.DataModel;

import ym2151.Swing.ListenerHashMap;

/**
 * This Holds the Operator Values of one Operator for a Instrument
 * */
public class Operator {

	/**
	 * A Map that holds the different Operator Values<br>
	 * There KEYs for the Different Knobs are:<br>
	 * AR, D1R,<br>
	 * D2R, RR,<br>
	 * D1L, TL <br>
	 * KS, MUL<br>
	 * DT1, DT2
	 * */
	public final ListenerHashMap<String, Integer> map;
	
	/**
	 * Creates a new Operator Data Model for the representation of a Instrument in the Controller
	 * */
	protected Operator(){
		map = new ListenerHashMap<String, Integer>();
	}
	
	
	/**
	 * This is Part of the OPM File Parsing. Use OPMFile to Load a OPM File.
	 * @param s the String Values to parse.
	 * @return "true" if parsing went OK and "false" if else.
	 * */
	protected boolean setVars(String[] s){
		if(s.length != 12){
			return false;
		}
		
		map.put("AR", Integer.parseInt(s[1]) << 2);
		map.put("D1R", Integer.parseInt(s[2]) << 2);
		map.put("D2R", Integer.parseInt(s[3]) << 3);
		map.put("RR", Integer.parseInt(s[4]) << 3);
		map.put("D1L", Integer.parseInt(s[5]) << 3);
		map.put("TL", Integer.parseInt(s[6]));
		map.put("KS", Integer.parseInt(s[7]) << 5);
		map.put("MUL", Integer.parseInt(s[8]) << 3);
		map.put("DT1", Integer.parseInt(s[9]) << 4);
		map.put("DT2", Integer.parseInt(s[10]) << 5);
		//map.put("AMSEN", Integer.parseInt(s[11]));
	
		return true;
	}
	
	/**
	 * This is Part of the OPM File Parsing. Use OPMFile to Load a OPM File.
	 * @return the String to add to the OPM File
	 * */
	protected String toOPM(){
		String sp = "  ";
		int AR = map.get("AR") >> 2;
		int D1R = map.get("D1R") >> 2;
		int D2R = map.get("D2R") >> 3;
		int RR = map.get("RR") >> 3;
		int D1L = map.get("D1L") >> 3;
		int TL = map.get("TL") ;
		int KS = map.get("KS") >> 5;
		int MUL = map.get("MUL") >> 3;
		int DT1 = map.get("DT1") >> 4;
		int DT2 = map.get("DT2") >> 5;
		
		return AR+sp+D1R+sp+D2R+sp+RR+sp+D1L+sp+TL+sp+KS+sp+MUL+sp+DT1+sp+DT2+sp;
	}
	
	
	@Override
	public String toString() {
		return map.toString();
	}
}
