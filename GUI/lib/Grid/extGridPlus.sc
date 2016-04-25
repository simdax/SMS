+ GridPlus{
	*new{
		arg	w = Window.new("Grid Plus", Rect(0,0,400,400))
		.front.acceptsMouseOver_(true),
		b= w.bounds;
		var a=super.new(w, b).init;		

		a.rect= { arg x;
			var pattern=Pmel(50);
			var p=pattern.collect(_.degree).iter.nextN(20,());
			var plot=Plotter(bounds: Rect(0,0,50,50), parent:a.parent)
			.value_(p)
			.plotMode_(\levels)
			.specs_([-5,5].asSpec)
			;
			x++ (
				pattern: pattern,
				plot: plot.interactionView.acceptsMouse_(false),
				movePlot:{ arg self; self.use{
					~plot.bounds_(~rect)
				}
				},
			) } <> a.rect;
		a.drawFunc=a.drawFunc++{a.rects.do { |x|
			x.movePlot
		}};
		a.addAction({
			arg self, x, y, mod, but, nbClick, ind;
			if(nbClick>1){
				var r=a.rects[ind];
				r !? {
					var z=FlowView(nil, Rect(0,0,200,200)).front;
					r=r.pattern.patternpairs;
					EnvirGui(r.asDict, r.asDict.size, z, Rect(0,0,100,100));
					TextView(z, 100@100).string_(r.asString)
				}
			}
		}, \mouseDownAction)

		^a
	}
}