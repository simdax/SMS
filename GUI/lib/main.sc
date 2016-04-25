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
	
	init{
		# buttons, save, add, grid =4.collect{View().background_(Color.rand)};
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
				gt.durees.collect({|x|
					[x[0], Pfindur(x[1], Pmel(x[1])).midi]
				}).flat
			)
		).play}
	}
}


