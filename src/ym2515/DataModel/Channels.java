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

public enum Channels{
	CH1("Channel 1", 0), CH2("Channel 2", 1), CH3("Channel 3",2), CH4("Channel 4",3), CH5("Channel 5",4), CH6("Channel 6",5), CH7("Channel 7",6), CH8("Channel 8", 7);
	
	private String display;
	public final int num;
	
	private Channels(String display, int num){
		this.display=display;
		this.num=num;
	}
	
	@Override
	public String toString() {
		return display;
	};
}