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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;

import javax.swing.JOptionPane;

import ym2515.Utils;


/**
 * Data representation of a OPMFile
 * */
public class OPMFile {

	
	private HashMap<Integer, Instrument> map = new HashMap<Integer, Instrument>();
	
	
	/**
	 * Create a new OPM File
	 * */
	public OPMFile(){}
	
	
	/**
	 * Adds the Instrument to the OPM File at the Slot number. Instruments at the same Number will be Overwritten.
	 * @param i the Instrument to add
	 * @param number the slot to put the Instrument in
	 * */
	public void addInstrument(Instrument i, int number){
		map.put(number, i);
	}
	
	/**
	 * @return returns all the Instruments in the OPM File
	 * */
	public Instrument[] getInstruments(){
		return map.values().toArray(new Instrument[0]);
	}
	
	
	/**
	 * Loads a File into a new OPM File
	 * @param path the Path to the File which should be loaded.
	 * */
	public static OPMFile loadFile(URL path){
		
		try {
			return loadFile(new File(path.toURI()));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Loads a File into a new OPM File
	 * @param path the Path to the File which should be loaded.
	 * */
	public static OPMFile loadFile(File path) throws IOException{
		
		//init
		OPMFile opm = new OPMFile();
		BufferedReader r;
		Instrument ins = null;
		r = new BufferedReader(new FileReader(path));
		int var = 0;
		
			
			for(String line; (line = r.readLine()) != null;) {
				line = line.trim();
				
				//comments
				if(line.startsWith("//")){
					continue;
				}else if(line.startsWith("@")){ //instrument start
					String instrHead = line.split(":")[1];
					String name = instrHead.substring(instrHead.indexOf(' '));
					var = Integer.parseInt(instrHead.substring(0, instrHead.indexOf(' ')));

					ins = new Instrument(name);	
					opm.addInstrument(ins,var);
					
				}else if(!line.equals("")){ //everything else (parts of a Instrument)
					if(ins == null) {
						r.close();
						throw new IOException("Could not Parse File");						
					}
					if(!ins.parseLine(line)){
						r.close();
						throw new IOException("Could not Parse File");
					};
				}	
					
			}	
		r.close();
		return opm;
	}
	
	/**
	 * Saves a OPMFile
	 * @param file the File to save
	 * @param path the path to save the file to.
	 * */
	public static void saveFile(OPMFile file, File path) throws IOException {
		
		//add right extension (if not existent)
		if(!Utils.getExtension(path).equalsIgnoreCase("opm")){
			path = new File(path.getPath()+".opm");
		}
		
		//dont save to a Dir
		if(path.isDirectory()){
			JOptionPane.showMessageDialog(null, "Please don't select a Folder!", "ERROR" , JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		//do File Handling if File Exists/not Exists
		if(path.exists()){
			int a = JOptionPane.showConfirmDialog(null, "Overwrite File:" +path, "Overwrite?",JOptionPane.YES_NO_OPTION ,JOptionPane.INFORMATION_MESSAGE);
			if(a == JOptionPane.CLOSED_OPTION){
				return;
			}
			path.delete();
		}else{
			File par = path.getParentFile();
			if(par!= null)
				par.mkdirs();
		}
		
		path.createNewFile();
		
		//write the File
		FileWriter wr = new FileWriter(path);
		String Head = 
				"//MiOPMdrv sound bank Paramer Ver2002.04.22 \n"+
				"//File Created with YM2151 Arduino Shield Controller \n"+
				"//LFO: LFRQ AMD PMD WF NFRQ\n"+
				"//@:[Num] [Name]\n"+
				"//CH: PAN	FL CON AMS PMS SLOT NE\n"+
				"//[OPname]: AR D1R D2R	RR D1L	TL	KS MUL DT1 DT2 AMS-EN\n\n";

		wr.write(Head);
		
		//add save Instruments
		for(Integer i : file.map.keySet()){
			Instrument ins = file.map.get(i);
			
			wr.write("@:"+i+" "+ins.name+"\n");
			wr.write(ins.toOPM()+"\n");
		}
		
		wr.flush();
		wr.close();
		
		JOptionPane.showMessageDialog(null, "Saving Done!", "DONE", JOptionPane.INFORMATION_MESSAGE);
	}
}






















