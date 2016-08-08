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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.util.LinkedList;

import javax.swing.JPanel;

import ym2151.Utils;


/**
 * A Knob Component.
 * */
public class Knob extends JPanel implements MouseMotionListener, MouseListener{
	private double min = 0, max = 127; // min Max Value
	private double var = 0; //current value
	private double stepsize = 1; //the stepsize
	private double maxRotation = 270; //maximum Rotation in Degrees
	private LinkedList<KnobListener> listeners = new LinkedList<KnobListener>(); //Listener List
	
	/**
	 * Create a new Knob with<br>
	 * min value = 0, max value = 127, stepsize = 1
	 * */
	public Knob(){
		setOpaque(true);
		addMouseMotionListener(this);
		addMouseListener(this);
	}
	
	
	/**
	 * Create a new Knob with<br>
	 * @param min minimum Value of the knob
	 * @param max maximum Value of the knob
	 * @param stepsize the step size
	 * */
	public Knob(double min, double max, double stepsize){
		this();
		setMin(min);
		setMax(max);
		setStepsize(stepsize);
	}
	
	/**
	 * Adds a listener to this knob
	 * @param l Listener to add
	 * */
	public void addKnobListener(KnobListener l){
		listeners.add(l);
	}
	
	/**
	 * Removes a listener from this knob
	 * @param l Listener to remove
	 * */
	public void removeKnobListener(KnobListener l){
		listeners.remove(l);
	}
	
	/**
	 * Removes all Listeners from this Knob
	 * */
	public void removeAllKnobListener(){
		listeners = new LinkedList<KnobListener>();
	}
	
	/**
	 * Sends a Event to all the Listeners
	 * */
	private void dispatchEvent(){
		for(KnobListener l : listeners){
			l.knobTurned(var);
		}
	}
	
	@Override
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		//get the Middle of the Panel
		int middle = Math.min(getWidth(), getHeight())/2;
		
		if(getWidth()<getHeight()){ //width
			g2d.rotate(Math.toRadians(((maxRotation)*(1/(max-min)))*var) - Math.toRadians(90), middle, getHeight()/2); //max roatation
			
			//draw filled part of the knob
			g2d.setColor(Color.DARK_GRAY);
			Utils.fillCenteredCircle(g2d,middle,getHeight()/2,middle*2);
			
			//draw outline
			g2d.setColor(Color.black);
			Utils.drawCenteredCircle(g2d,middle,getHeight()/2,middle*2);
			
			//draw small indicator circle
			g2d.setColor(Color.black);
			Utils.fillCenteredCircle(g2d,(int)(middle-(0.6*middle)),(int)(getHeight()/2-(0.6*middle)), 10); 
		}else{
			g2d.rotate(Math.toRadians(((maxRotation)*(1/(max-min)))*var) - Math.toRadians(90), getWidth()/2, middle); //max roatation
			
			//draw filled part of the knob
			g2d.setColor(Color.DARK_GRAY);
			Utils.fillCenteredCircle(g2d,getWidth()/2,middle,middle*2);
			
			//draw outline
			g2d.setColor(Color.black);
			Utils.drawCenteredCircle(g2d,getWidth()/2,middle,middle*2);
			
			//draw small indicator circle
			g2d.setColor(Color.black);
			Utils.fillCenteredCircle(g2d,(int)(getWidth()/2-(0.6*middle)),(int)(middle-(0.6*middle)), 10); 
		}

		g2d.setTransform(new AffineTransform());
	}
	
	/**
	 * Sets the minimum value
	 * @param min the minimum value to set
	 * */
	public void setMin(double min){
		this.min=min;
		if(var<min){
			var=min;
		}
		if(min>max){
			min=max;
		}
	}
	
	/**
	 * Sets the maximum value
	 * @param min the maximum value to set
	 * */
	public void setMax(double max){
		this.max=max;
		if(var>max){
			var=max;
		}
		if(max<min){
			max=min;
		}
	}
	
	/**
	 * @return current position of the knob (min <= pos <= max)
	 * */
	public double getVar(){
		return var;
	}
	
	/**
	 * Sets the Stepsize
	 * @param the Stepsize to set.
	 * */
	public void setStepsize(double step){
		this.stepsize=step;
		setVar(var);
	}
	
	/**
	 * Sets the selected value of the Knob
	 * @param var the Value to set the knob to
	 * */
	public void setVar(double var){
		double before = this.var;
		
		//step size
		int a = (int) (var / stepsize);
		var = a * stepsize;
		
		//min and max
		this.var = Math.min(max, Math.max(min, var));
		
		//only fire a Event if the value changed
		if(this.var!=before){
			dispatchEvent();
		}
		repaint();
	}
	
	
	

	
	
	private Point start = null; //dragging start point
	private double oldVar = 0; //Value from one Event before
	
	public void mouseDragged(MouseEvent e) {
		double y = start.getY()-e.getY();
		double ny =  y/120.0; //Drag Speed , lower is faster
		
		setVar(oldVar+(ny*((max-min)/2)));
		repaint();
	}

	public void mousePressed(MouseEvent e) {
		start = e.getPoint();
		oldVar = var; 
	}
	
	
	
	
	
	//not used Event Classes
	public void mouseReleased(MouseEvent e) {
	}
	
	public void mouseMoved(MouseEvent e) {	
	}


	public void mouseClicked(MouseEvent e) {
	}


	public void mouseEntered(MouseEvent e) {
	}


	public void mouseExited(MouseEvent e) {	
	}

	
	/**
	 * A Listener for the Knob
	 * */
	public interface KnobListener{
		public void knobTurned(double var);
	}
	
}
