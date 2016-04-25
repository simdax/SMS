(

Window.closeAll;
w=Window("window title", Rect(0, 0, 400, 400)).alwaysOnTop_(true).front;
w.addFlowLayout;

a= ~dg.(w);

w.drawFunc_{
	a.draw
};

)
(
~dg={ arg w;
	(
		w:w,
		x:[0]++Array.rand(10, 1,4).integrate,
		y:Array.rand(11, 0,4),
		offset:0,
		draw:{ arg self; self.use{
			var d=DrawGrid(~w.view.bounds.taille.asRect, [0,50].asSpec.grid, [0,10].asSpec.grid).draw;
			(~x+++~y).collect({|x|
				Pen.fillRect(Rect(
					d.width * x[0],
					d.height * x[1],
					d.width,
					d.height/2
				).postln)
			})
		}
		}
	)
}
)

[0,10].asSpec.range

Warp(-4)