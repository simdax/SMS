(

a=GridPlus();
a.rect= { arg x;
	var pattern= Pmel(),
	var plot=Plotter(bounds: Rect(0,0,50,50), parent:a.parent).value_(
		Pfindur(8, pattern).collect(_.degree).iter.nextN(50, ())
	).plotMode_(\points);
	x++ (
		pattern: Pmel(),
		plot: plot.interactionView.acceptsMouse_(false),
		movePlot:{ arg self; self.use{
			~plot.bounds_(~rect)
		}
		},
	) } <> a.rect;
a.drawFunc=a.drawFunc++{a.rects.do { |x|
    x.movePlot
}}
)