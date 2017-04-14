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

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.kitfox.svg.SVGDiagram;
import com.kitfox.svg.SVGException;
import com.kitfox.svg.SVGUniverse;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.net.URI;


/**
 * A  Panel which shows the Current selected Algorithm
 * */
public class AlgorithmSelector extends JPanel {

	private static final long serialVersionUID = 6752262938729908666L;
	
	private int algo = 0;
	private int max;
	
	private static SVGUniverse svgImages = new SVGUniverse();
	private static URI[] imagePaths = new URI[8];
	
	
	static{
		loadImages();
	}
	
	/**
	 * A  Panel which shows the Current selected Algorithm
	 * @param max the number of Algorithms available
	 * */
	public AlgorithmSelector(int max) {
		setBackground(Color.WHITE);
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		this.max = max;
		
	}

	private static void loadImages(){
		for(int i = 0; i<8 ; i++){
			imagePaths[i] = svgImages.loadSVG(AlgorithmSelector.class.getResource("/Images/SVG/Algorithms/"+i+".svg"));
		}
	}
	
	
	/**
	 * Sets the Algorithm to the Value
	 * @param i the Algorithm to Select
	 * 
	 * */
	public void setAlgorithm(int i){
		if(i > max){
			i = max;
		}
		if(i < 0){
			i = 0;
		}
		
		algo = i;
		repaint();
	}
	
	/**
	 * @return the current Algorithm selected
	 * */
	public int getAlgorithm(){
		return algo;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		

		try {
			Graphics2D g2d = (Graphics2D) g.create();
			
			SVGDiagram diag = svgImages.getDiagram(imagePaths[algo]);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			
			
			g2d.scale(((double)getWidth())/diag.getWidth(), ((double)getHeight())/diag.getHeight());
			diag.render(g2d);
		} catch (SVGException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}







