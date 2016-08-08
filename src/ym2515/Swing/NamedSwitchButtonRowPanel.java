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
 * A SwitchButtonRowPanel with a key. 
 * It sets the value at the key in the provided map. 
 * */
public class NamedSwitchButtonRowPanel extends SwitchButtonRowPanel implements HashMapListener<String, Integer>, SwitchButtonRowPanel.SwitchButtonListener{

	private final String[] KEY;
	private final ListenerHashMap<String, Integer> map;
	
	/**
	 * A SwitchButtonRowPanel with a key. 
	 * It sets the value at the key in the provided map. 
	 * @param map map to set the value at the key
	 * @param KEY key to set the Value in the map
	 * */
	public NamedSwitchButtonRowPanel(ListenerHashMap<String, Integer> map, String[] KEY){
		if(KEY.length!= 4){
			throw new RuntimeException("Wrong Number of KEYs");
		}

		this.KEY = KEY;
		this.map = map;
		map.addHashMapListener(this);
		super.addSwitchButtonListener(this);
	}
	
	
	/**
	 * let the SwitchButtonRowPanel know, that a Value Changed in the ListenerHashMap
	 * */
	public void valueChanged(String key, Integer value) {
		for(int i = 0; i<KEY.length; i++){
			if(key.equals(KEY[i])){
				setButton(i, value > 64);
			}
		}
	}

	/**
	 * set the Value in the map in KEY[index]
	 * @param no KEY[no]
	 * @param state the state to set
	 * */
	public void buttonClicked(int no, int state) {
			map.put(KEY[no], state);
	}
}
