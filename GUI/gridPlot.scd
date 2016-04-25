(

a=GridPlus();
a.rect= { arg x;
var pattern=Pmel(5);
var p=pattern.collect(_.degree).iter.nextN(20,());
	var plot=Plotter(bounds: Rect(0,0,50,50), parent:a.parent)
	.plotMode_(\levels)
	.value_(p)
	;
	x++ (
		plot: plot.interactionView.acceptsMouse_(false),
		movePlot:{ arg self; self.use{
			~plot.bounds_(~rect)
		}
		},
	) } <> a.rect;
a.drawFunc=a.drawFunc++{a.rects.do { |x|
    x.movePlot
}};
p.postln
)