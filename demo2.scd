// =====================================================================
// exemple cool
// =====================================================================

a=Pmel();
b=Pbind(\type, \rest,
	\couleur, Pstep(Pchords(Pseries(0,3)),  4)
);
(a<>b).midi(5,0).trace.play;
(Pbind(\type, \midi, \degree, Pkey(\couleur), \dur, 4) <> b).midi(5,1).trace.play;

TempoClock.tempo=1



