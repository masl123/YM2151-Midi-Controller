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
 * This Holds the Common Values for a Instrument
 * */
public class Common {
	
	/**
	 * A Map that holds the different Controller Values<br>
	 * There KEYs for the Different Knobs are:
	 * <br><br> PAN, FL, CON, AMS, PMS, 
	 * <br>OP1_EN, OP2_EN, OP3_EN, OP4_EN, 
	 * <br>AMSEN_OP1, AMSEN_OP2, AMSEN_OP3, AMSEN_OP4
	 * */
	public final ListenerHashMap<String, Integer> map;
	
	
	/**
	 * Creates a new Common Data Model for the representation of a Instrument in the Controller
	 * */
	protected Common(){
		map = new ListenerHashMap<String, Integer>();
	}
	
	
	/**
	 * This is Part of the OPM File Parsing. Use OPMFile to Load a OPM File.
	 * @param s the String Values to parse.
	 * @return "true" if parsing went OK and "false" if else.
	 * */
	protected boolean setVars(String[] s){
		if(s.length != 8){
			return false;
		}

		map.put("PAN", Integer.parseInt(s[1]));

		map.put("FL", Integer.parseInt(s[2]) << 4); //FB

		map.put("CON", Integer.parseInt(s[3]) << 4); //ALGO

		map.put("AMS", Integer.parseInt(s[4]) << 5);//AMS

		map.put("PMS", Integer.parseInt(s[5]) << 4);//PMS

		int slot = (Integer.parseInt(s[6]) >> 3) & 0x0F;

		map.put("OP1_EN", ((slot) 	  & 0x01) * 127);
		map.put("OP3_EN", ((slot >> 1) & 0x01) * 127);
		map.put("OP2_EN", ((slot >> 2) & 0x01) * 127);
		map.put("OP4_EN", ((slot >> 3) & 0x01) * 127);
		
		return true;
	}
	
	/**
	 * This is Part of the OPM File Creation. Use OPMFile to Save a OPM File.
	 * @return the String to add to the OPM File
	 * */
	protected String toOPM(){
		
		String sp = "  ";
		int PAN = map.get("PAN");
		int FL = map.get("FL") >> 4;
		int CON = map.get("CON") >> 4;
		int AMS = map.get("AMS") >> 5;
		int PMS = map.get("PMS") >> 4;
		
		
		int OP1_EN = map.get("OP1_EN") >= 64 ? 1 : 0;
		int OP3_EN = map.get("OP3_EN") >= 64 ? 1 : 0;
		int OP2_EN = map.get("OP2_EN") >= 64 ? 1 : 0;
		int OP4_EN = map.get("OP4_EN") >= 64 ? 1 : 0;
		
		int SLOT = ((OP1_EN) | (OP3_EN << 1) | (OP2_EN << 2) |(OP4_EN << 3)) << 3;
		
		return PAN+sp+FL+sp+CON+sp+AMS+sp+PMS+sp+SLOT+sp;
	}

}
