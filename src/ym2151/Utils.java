/**
*	YM2151 - Midi Controller Software for Arduino Shield
*	(C) 2016  Marcel Wei�
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

package ym2151;

import java.awt.Graphics2D;
import java.io.File;
import java.util.List;

import javax.swing.DefaultListModel;

/**
 * just a Utils Class with Methods to reuse (nothing important to see here)
 * */
public class Utils {
	
	/**
	 * Get the extension of a file.
	 */  
	public static String getExtension(File f) {
	    String ext = null;
	    String s = f.getName();
	    int i = s.lastIndexOf('.');

	    if (i > 0 &&  i < s.length() - 1) {
	        ext = s.substring(i+1).toLowerCase();
	    }
	    if(ext == null) {
	    	return "";
	    }
	    return ext;
	}
	
	/**
	 * Adds all Elements from an Array to a DefaultListModel
	 * @param m the Model to add the Entries to
	 * @param o the Entries to add
	 * */
	public static <B> void addAll(DefaultListModel<B> m, B[] o){
		for(int i = 0 ; i < o.length; i++){
			m.addElement(o[i]);
		}		
	}
	
	/**
	 * Adds all Elements from a List to a DefaultListModel
	 * @param m the Model to add the Entries to
	 * @param o the Entries to add
	 * */
	public static <B> void addAll(DefaultListModel<B> m, List<B> o){
		for(int i = 0 ; i < o.size(); i++){
			m.addElement(o.get(i));
		}	
	}
	
	/**
	 * Removes all entries of the List from a DefaultListModel
	 * @param m the Model to remove the Entries from
	 * @param o the Entries to remove
	 * */
	public static <B> void removeAll(DefaultListModel<B> m, List<B> o){
		for(int i = 0 ; i < o.size(); i++){
			m.removeElement(o.get(i));
		}		
	}
	
	/**
	 * Fills a centered circle
	 * @param g the Graphics Context to draw to
	 * @param x the x-coordinate 
	 * @param y the y-coordinate
	 * @param r the Radius
	 * */
	public static void fillCenteredCircle(Graphics2D g, int x, int y, int r) {
		  x = x-(r/2);
		  y = y-(r/2);
		  g.fillOval(x,y,r,r);
	}

	/**
	 * Draws a centered circle
	 * @param g the Graphics Context to draw to
	 * @param x the x-coordinate 
	 * @param y the y-coordinate
	 * @param r the Radius
	 * */
	public static void drawCenteredCircle(Graphics2D g, int x, int y, int r) {
		  x = x-(r/2);
		  y = y-(r/2);
		  g.drawOval(x,y,r,r);
	}
	
	
	/**
	 * creates a DefaultListModel from an Array
	 * */
	public static<T>  DefaultListModel<T> getListModel(T[] data){
		DefaultListModel<T> lm = new DefaultListModel<T>();
		for(T t : data){
			lm.addElement(t);
		}
		return lm;
	}
}
