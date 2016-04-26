+ GridPlus{
	*new{
		arg	w = Window.new("Grid Plus", Rect(0,0,400,400))
		.front.acceptsMouseOver_(true),
		b= w.bounds;
		var a=super.new(w, b).init;		

		a.rect= { arg x;
			var pattern=Pbind(\degree, Array.rand(10, 1,4).pseq,
				\dur, Array.rand(10, 1,4).pseq 
			);
			var p=pattern.collect(_.degree).iter.nextN(20,());
			var plot=Plotter(bounds: Rect(0,0,0,0), parent:a.parent)
			.plotMode_(\levels)
			//	.value_(p)
			;
			x=x++ (
 				pattern: pattern,
				patternVal:{ arg self; self.use{
					var a, b, x;
					a=Pfindur(~z, ~pattern).asStream;
					while{x=a.next(()); x.notNil}
					{
						b=b.add(x.degree ! x.dur)
					};
					~plot.value_(b.flat);
					~plot.specs_([-10,10].asSpec)
				}},
				plot: plot,
				plotV: plot.interactionView.acceptsMouse_(false),
				movePlot:{ arg self; self.use{
					~plotV.bounds_(~rect);
				}
				},
			);
			//	x
		} <> a.rect;

		a.drawFunc=a.drawFunc++{a.rects.do { |x|
			x.movePlot
		}};

		a.addAction({
			arg self, x, y, mod, but, nbClick, ind;
			if(nbClick>1){
				var r=a.rects[ind];
				r !? {
					var z=FlowView(nil, Rect(0,0,200,200)).front;
					var dict=r.pattern.patternpairs.asDict;
					EnvirGui(dict, dict.size, z, Rect(0,0,100,100));
					PatGui(z, r)
				}
			}
		}, \mouseDownAction);

		a.addAction({
			arg s, x, y, mod, ind;
			if(ind.notNil){
				a.rects[ind].patternVal;
			}
		}, \mouseMoveAction);

		a.addAction({
			arg s, x, y, mod, but, ind;
			ind.postln;
			if(ind.notNil){
				a.rects[ind].patternVal;
			}
		}, \mouseUpAction)

		^a
	}
}