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

package ym2151;

import themidibus.MidiBus;
import themidibus.SimpleMidiListener;


/**
 * This is a Handler, to Handle all incoming Midi Messages. The Handler just Sends all Messages to all Output Midi Devices.
 * 
 * */
public class MidiInHandler implements SimpleMidiListener{
	
	private MidiBus bus;

	/**
	 * This Handler adds itself to the bus. 
	 * @param bus the MidiBus to Use 
	 * */
	MidiInHandler(MidiBus bus){
		this.bus=bus;
		bus.addMidiListener(this);
	}

	
	/**
	 * Handles note ON Midi Messages
	 * */
	public void noteOn(int channel, int pitch, int velocity) {
		bus.sendNoteOn(channel, pitch, velocity);
	}
	
	/**
	 * Handles note OFF Midi Messages
	 * */
	public void noteOff(int channel, int pitch, int velocity) {
		bus.sendNoteOff(channel, pitch, velocity);
	}

	
	/**
	 * Handles Controller Change Messages.
	 * */
	public void controllerChange(int channel, int number, int value) {
		bus.sendControllerChange(channel, number, value);
	}
}
