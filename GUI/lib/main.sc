PATGUI : View{

	var canvas;
	
	//views
	var <grid, <buttons, <save, <add;
	//widgets
	var <gt,<bt;
	var pat;

	*new{ arg p=Window("PATGUI", 800@600).front, b=p.bounds.taille;
		^super.new(p, b).init;
	}
	makeButtons{
		^View().layout_(
		  	HLayout(
				[VLayout(
					View().background_(Color.rand),
					View().background_(Color.rand)
				), s:2],
				[VLayout(
					View().background_(Color.rand),
					View().background_(Color.rand),
					View().background_(Color.rand)
				),s:3]
			)
		)
	}
	init{
		# save, add, grid =3.collect{View().background_(Color.rand)};
		buttons=this.makeButtons;
		this.parent.layout_(VLayout(
			[HLayout([grid, s:6.3], [add, s:2]), s:5],
			[HLayout([buttons, s:6], [save, s:2]), s:2]
		).margins_(0)
		);
		gt=GridTop(grid);
		this.parent.addAction({ arg self, k, mod, uni;
			uni.switch(
				32 , {this.play}
			)
		},\keyDownAction
		)
		
	}
	play{
		if(pat.isPlaying)
		{pat.stop}
		{pat=
		PFF(gt.x,
			Ptpar(
				(gt.durees+++gt.patterns).collect({|x|
					x.postln;
					[x[0], Pfindur(x[1], x[2]).midi]
				}).flat
			)
		).play}
	}
}


