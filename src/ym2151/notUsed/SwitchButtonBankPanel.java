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

package ym2151.notUsed;

import javax.swing.JPanel;
import javax.swing.JToggleButton;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

public class SwitchButtonBankPanel extends JPanel implements ActionListener{

	
	private  List<JToggleButton> btns = new LinkedList<JToggleButton>();
	private LinkedList<SwitchButtonListener> listeners = new LinkedList<SwitchButtonListener>();
	private boolean onlyOne = true;
	
	public SwitchButtonBankPanel(int number, boolean onlyOne) {
		setLayout(new GridLayout(1, 1, 0, 0));
		for(int i = 0; i<number; i++){
			JToggleButton toggleButton1 = new JToggleButton(i+"");
			btns.add(toggleButton1);
			add(toggleButton1);
			toggleButton1.addActionListener(this);
			
		}
		
		this.onlyOne=onlyOne;
	}

	public void setButton(int i){
		if(i>=0 && i<btns.size()){
			if(onlyOne){
				unselectAll();
			}
			btns.get(i).setSelected(true);
			dispatchEvent(btns.get(i));
		}
	}
	
	public void setEnabledAll(boolean e){
		for(JToggleButton btn : btns){
			btn.setEnabled(e);
		}
	}
	
	
	public void actionPerformed(ActionEvent e) {
		if(onlyOne){
			if(((JToggleButton)e.getSource()).isSelected()){
				unselectAll();
				((JToggleButton)e.getSource()).setSelected(true);
			}else{
				((JToggleButton)e.getSource()).setSelected(true);
			}
		}
		dispatchEvent(((JToggleButton)e.getSource()));
	}
	
	private void unselectAll(){
		for(JToggleButton b : btns){
			if(b.isSelected()){
				dispatchEvent(b);
			}
			b.setSelected(false);
		}
	}
	
	public void addSwitchButtonListener(SwitchButtonListener l){
		listeners.add(l);
	}
	public void removeSwitchButtonListener(SwitchButtonListener l){
		listeners.remove(l);
	}
	public void removeSwitchButtonListener(){
		listeners = new LinkedList<SwitchButtonListener>();
	}
	
	private void dispatchEvent(JToggleButton selected){
		for(SwitchButtonListener l : listeners){
			l.switched(selected);
		}
	}
	
	
	
	public interface SwitchButtonListener{
		public void switched(JToggleButton var);
	}
	
	
	
	
	
}
