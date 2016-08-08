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

package ym2515.Swing;

import ym2515.Swing.ListenerHashMap.HashMapListener;


/**
 * A Knob with a key. 
 * It sets the value at the key in the provided map. 
 * */
public class NamedKnob extends Knob implements HashMapListener<String, Integer>{
	private final ListenerHashMap<String, Integer> map; //map to map the Keys to the Values.
	private final String[] KEY; //Keys to Set if the Knob Changes
	
	/**
	 * A Knob with a key. 
	 * It sets the value at the key in the provided map. 
	 * @param map map to set the value at the key
	 * @param KEY key to set the Value in the map
	 * */
	public NamedKnob(ListenerHashMap<String, Integer> map , String KEY) {
		super();
		this.KEY = new String[]{KEY};
		this.map=map;
		map.addHashMapListener(this);
	}
	
	/**
	 * A Knob with multiple keys. 
	 * It sets the value at the keys in the provided map. 
	 * @param map map to set the value at the key
	 * @param KEYs keys to set the Value in the map
	 * */
	public NamedKnob(ListenerHashMap<String, Integer> map, String... KEYs) {
		super();
		this.KEY = KEYs;
		this.map=map;
		map.addHashMapListener(this);
	}
	
	/**
	 * A Knob with multiple keys. 
	 * It sets the value at the keys in the provided map. 
	 * @param map map to set the value at the key
	 * @param min minimum Value of the knob
	 * @param max maximum Value of the knob
	 * @param stepsize the step size
	 * @param KEYs keys to set the Value in the map
	 * */
	public NamedKnob(ListenerHashMap<String, Integer> map, double min, double max, double stepsize, String... KEYs) {
		super(min, max, stepsize);
		this.KEY=KEYs;
		this.map=map;
		map.addHashMapListener(this);
	}
	
	
	@Override
	public void setVar(double var) {
		super.setVar(var);
		for(String s : KEY){
			map.put(s, (int) getVar());
		}
	}
	
	/**
	 * let the Knob know, that a Value Changed in the ListenerHashMap
	 * */
	public void valueChanged(String key, Integer value) {
		for(String s : KEY){
			if (key.equals(s)){
				setVar(value);
				
			}	
		}
	}
	
	
	
	
}
