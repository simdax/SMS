+ GridPlus{
	*new{
		arg	w = Window.new("Grid Plus", Rect(0,0,400,400))
		.front.acceptsMouseOver_(true)//.plotMode_(\points)
		,
		b= w.bounds;
		var a=super.new(w, b).init;		
		a.rect= { arg x;
			var plot=Plotter(bounds: Rect(0,0,50,50), parent:a.parent).value_([0,1,2]);
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
		^a
	}
}