(

a=GridPlus();
a.rect= { arg x;
	var pattern=Pmel(5);
	var p=pattern.collect(_.degree).iter.nextN(20,());
	var plot=Plotter(bounds: Rect(0,0,0,0), parent:a.parent)
	.plotMode_(\levels)
	.value_(p)
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

)
