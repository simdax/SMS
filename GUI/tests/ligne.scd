(
Window.closeAll;
w=Window("window title", Rect(800, 0, 400, 400)).alwaysOnTop_(true).front;
y=UserView(w, 60@20).canFocus_(false);
Button(w, Rect(50, 50,20,20))
.mouseDownAction_{arg self, xx, yy, mod, but;
	switch(but,
		0, {
			if(a.isPlaying.postln)
			{a.stop}
			{a.reset; a.play(AppClock)}
		},
		1, {x=0; y.refresh}
	)
};
x=0;
a=r{ 
	loop{
		x=x+1;
		y.refresh;
		0.2.yield;
	}
};
y.drawFunc_{
	x.postln;
	Pen.line(x@0, x@w.width);
	Pen.fillStroke
};
)
