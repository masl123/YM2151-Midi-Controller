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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A HashMap, where you can add Listeners to
 * */
public class ListenerHashMap<K, V> extends HashMap<K, V> {


	private static final long serialVersionUID = 4468762708564247528L;
	
	private List<HashMapListener<K,V>> listeners = new ArrayList<HashMapListener<K,V>>(20); //the Listeners
	
	/**
	 * Add a Listener to this ListenerHashMap
	 * @param l the Listener to add
	 * */
	public void addHashMapListener(HashMapListener<K,V> l){
		listeners.add(l);
	}
	
	/**
	 * remove a Listener from this ListenerHashMap
	 * @param l the Listener to remove
	 * */
	public void removeHashMapListener(HashMapListener<K,V> l){
		listeners.remove(l);
	}
	
	
	/**
	 * Dispatch a Event
	 * @param k the Key of the Entry which Changed
	 * @param v the Value of the Entry which Changed
	 * */
	private void dispatch(K k , V v){
		for(HashMapListener<K,V> l : listeners){
			l.valueChanged(k, v);
		}	
	}

	
	@Override
	public V put(K k, V v) {
		boolean a = !super.containsKey(k) || !super.get(k).equals(v);
		V va = super.put(k, v);
		if(a){
			dispatch(k,v);
		}
		return va;
	}
	
	
	/**
	 * add a Element to the ListenerHashMap without dispatching a Event
	 * @param key the Key of the Entry
	 * @param value the Value of the Entry
	 * */
	public V putSilent(K key, V value) {
		return super.put(key, value);
	}
	
	
	
	/**
	 * Fires a Event to all listeners for all entries
	 * 
	 * */
	public void syncAll(){
		for(Entry<K,V> k : entrySet()){
			try {
				Thread.sleep(3);
			} catch (InterruptedException e) {}
			dispatch(k.getKey(), k.getValue());
		}
	}
	
	
	/**
	 * The Listener Interface for this ListenerHashMap
	 * */
	public interface HashMapListener<K,V>{
		public void valueChanged(K key, V value);
	}
	
}
