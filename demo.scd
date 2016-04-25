// =====================================================================
// SuperCollider Workspace
// =====================================================================



(
t= SystemClock.beats+ 60 * 30;
Pspawner({arg sp;
	var port=Pseq([(..7),(8..15)], inf).asStream;
	var p=port.next;
	//	Synthetiseur(5, 4, p);
	1.wait;
	while{
		SystemClock.beats < t;
	}
	{
			TempoClock.tempo=(1, 1.05 ..4).choose;
		a=Player.mel(
			setDeg:(5.xrand.neg .. 5.xrand),
			setDur:[0.5,1,2],
			basses: [
				{[0,1,2]+[0,1,2,3].choose},
				{[0,3,6]+[1,2,4,5].choose}
			].choose.value
		).trace <> (type:\midi, midiout: MIDIOut(5), chan:p.first);
		//	{Synthetiseur(5, 6, p=port.next.postln)}.defer(4);
		z=(
			[a <> (octave:6)] ++ (a <= Pbind(\chan, 1,
				\degree, PdegreeToKey(
					Pseq([[0,1,2,1], [[0,1,2,1]]].choose, inf),
					Pstep(Pkey(\couleur),2),7),
				\dur, Pfunc{arg ev; ev.size.reciprocal}*4
			)
			) ++
			4.collect({ arg x;
				a <= Pbind(
					\chan, 2+Pkey(\chan)+x,
					\degree, PdegreeToKey(
						Pseq(Array.rand(8,0,4), inf),
						Pstep(Pkey(\couleur), 2),7),
					\dur, Pseq(
						[2 ],
						//	4*Array.rand([4,6,8].choose,1,2) / (2.pow(x)),
						inf)
				)
			})).newVite;
		sp.seq(
			Psym( 
				Pseq([ 
					[\a, \b],						
					3.collect({ [\a, \b]++(2..6).multiChoose(2).lettre}).mirror.pseq									
				]), z
			) <> (root: 6.rand2)
		);
		"et c'est reparti ! ".postln;
	}
}).play
)


TempoClock.tempo=0.5

