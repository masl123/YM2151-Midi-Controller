# YM2151-Midi-Controler
A Controller Software for the YM2151 Arduino Shield

### INFO:
This is the Computer portion for the YM2151 Arduino Shield. It makes using the Shield much more simpler. Look at https://github.com/masl123/YM2151-Arduino-Software for the Arduino Part.
<br> Look at Mapping.md for the Midi CC Mapping.


### MacOS Sysex Bug
There is a bug which prevents you from Changing the Master Tune of the Synth. <b>BUT</b> there is a Workaround for this: 
</br>  - get CoreMidi4j (at: https://github.com/DerekCook/CoreMidi4J/releases)
</br>  - and put the jar file into /Library/Java/Extensions (or add the jar file to the Java Classpath in some other way)
</br> this should give you a bunch of MidiDevices with the Prefix CoreMIDI4J. Use them.

### Licenses
See [LICENSE.md](LICENSE.md)