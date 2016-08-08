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

import javax.swing.DefaultListModel;


/**
 * Data Representation of a Instrument in the YM2151 Controller
 * 
 * */
public class Instrument {
	
	/**
	 * The Name of the Instrument
	 * */
	public String name;
	
	/**
	 * The different Operators
	 * */
	public Operator op1, op2, op3, op4;
	
	/**
	 * The LFO
	 * */
	public LFO lfo;
	
	/**
	 * The Common Stuff
	 * */
	public Common common;
	
	/**
	 * Selected Midi Channels
	 * */
	public DefaultListModel<Channels> defaultListModel;
	
	/**
	 * is this the selected Instrument which sends LFO Data over MIDI
	 * */
	public boolean lfoSelected = false;
	
	/**
	 * is this the Selected Instrument which sends Midi Data if PolyMode is Active
	 * */
	public boolean polyInstr = false;
	
	
	/**
	 * Creates a new Instrument
	 * @param name The name of the Instrument
	 * */
	public Instrument(String name){
		this(name, null);
	}
	
	/**
	 * Creates a new Instrument
	 * @param name The name of the Instrument
	 * @param defaultListModel the Model which contains the selected Midi-Channels for this Instrument
	 * */
	public Instrument(String name, DefaultListModel<Channels> defaultListModel){
		
		
		this.name = name.trim();
		
		lfo = new LFO();
		common = new Common();
		
		op1 = new Operator(); //M1
		op2 = new Operator(); //M2
		op3 = new Operator(); //C1
		op4 = new Operator(); //C2
		
		this.defaultListModel = defaultListModel;
	}
	
	/**
	 * Converts a String to an Integer
	 * */
	private int toInt(String s){
		return Integer.parseInt(s);
	}
	
	@Override
	public String toString() {
		String s = " ";

		if(lfoSelected){
			
			s+="[L";
		}
		if(polyInstr){
			if(!lfoSelected)
				s+="[P";
			else
				s+=" ,P";
		}
		if(lfoSelected | polyInstr){
			s+="]";
		}
		return name+s;
	}
	
	
	/**
	 * Sends all values to the Arduino
	 * */
	public void syncALL(){
		op1.map.syncAll();
		op2.map.syncAll();
		op3.map.syncAll();
		op4.map.syncAll();
		if(lfoSelected){
			lfo.map.syncAll();
		}
		common.map.syncAll();
	}

	/**
	 * This is Part of the OPM File Parsing. Use OPMFile to Load a OPM File.
	 * @param s the String Line to parse.
	 * @return "true" if parsing went OK and "false" if else.
	 */
	protected boolean parseLine(String s){
		s = s.trim();
		String [] split = s.split("\\s+");
	
		if(split[0].equalsIgnoreCase("LFO:")){
			
			if(!lfo.setVars(split)){
				return false;
			}
			
		}else if(split[0].equalsIgnoreCase("CH:")){
			if(!common.setVars(split)){
				return false;
			}
			lfo.map.put("NE", (toInt(split[7])*127) & 0x7F);
			
		}else if(split[0].equalsIgnoreCase("M1:")){
			if(!op1.setVars(split)){
				return false;
			}
			common.map.put("AMSEN_OP1", (toInt(split[11])*127) & 0x7F);
			
			
		}else if(split[0].equalsIgnoreCase("M2:")){
			if(!op2.setVars(split)){
				return false;
			}
			common.map.put("AMSEN_OP2", (toInt(split[11])*127) & 0x7F);
			
			
		}else if(split[0].equalsIgnoreCase("C1:")){
			if(!op3.setVars(split)){
				return false;
			}
			common.map.put("AMSEN_OP3", (toInt(split[11])*127) & 0x7F);
			
			
		}else if(split[0].equalsIgnoreCase("C2:")){
			if(!op4.setVars(split)){
				return false;
			}
			common.map.put("AMSEN_OP4", (toInt(split[11])*127) & 0x7F);
			
			
		}else{
			return false;
		}
		return true;
	}

	
	/**
	 * This is Part of the OPM File Creation. Use OPMFile to Save a OPM File.
	 * @return the String to add to the OPM File
	 * */
	protected String toOPM(){
		int NE = lfo.map.get("NE") >= 64 ? 1:0;
		int ams_EN_OP1 = common.map.get("AMSEN_OP1") >= 64 ? 1:0;
		int ams_EN_OP2 = common.map.get("AMSEN_OP2") >= 64 ? 1:0;
		int ams_EN_OP3 = common.map.get("AMSEN_OP3") >= 64 ? 1:0;
		int ams_EN_OP4 = common.map.get("AMSEN_OP4") >= 64 ? 1:0;
		
		String wr = "";
		wr += "LFO: "+lfo.toOPM();
		wr += "CH: " + common.toOPM() + NE         + "\n";
		wr += "M1: " + op1.toOPM()    + ams_EN_OP1 + "\n";
		wr += "C1: " + op3.toOPM()    + ams_EN_OP3 + "\n";
		wr += "M2: " + op2.toOPM()    + ams_EN_OP2 + "\n";
		wr += "C2: " + op4.toOPM()    + ams_EN_OP4 +"\n";
		return wr.toString();
	}
	
	
	
	
	
	
	
	
}
