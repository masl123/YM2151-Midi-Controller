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

package ym2151.Swing;

import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JList;

import ym2151.Utils;

import java.awt.BorderLayout;
import java.util.List;

/**
 * A Swing Component to Select Items from a List (with Scrolling)
 * */
public class ListSelector<T> extends JPanel {
	private static final long serialVersionUID = -6729650486083511095L;
	
	private JList<T> list; //the JList we use
	
	/**
	 * Create a ListSelector
	 * @param data the Data to add.
	 * */
	public ListSelector(T[] data) {
		this(Utils.getListModel(data));
	}
	

	/**
	 * Create a ListSelector
	 * @param m the model to Use (this will not change the Data in the Model)
	 * */
	public ListSelector(DefaultListModel<T> m) {
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane);
		
		list = new JList<T>();
		list.setModel(m);

		if(m.size() > 0){
			list.setSelectedIndex(0);
		}
		scrollPane.setViewportView(list);
	}
	
	/**
	 * @return the selected Values
	 * */
	public List<T> getSelectedValue(){
		return list.getSelectedValuesList();
	}
	
	
	
	
	

}
