// =====================================================================
// SuperCollider Workspace
// =====================================================================

(
Window.closeAll;
w=Window("window title", Rect(0, 0, 400, 400)).alwaysOnTop_(true).front;
//x=View(w, w.bounds);
a=GridPlus(w);
~stop={
	c.stop
};
~play={
	c.reset; c.play(AppClock)
};
~resume={
	y=0;x=0; b.refresh
};
~isPlaying={
	c.isPlaying
};
~refresh=		{y=x.linlin(*a.fenetre++0++a.width);
	a.refresh};
~touch={ arg xx, yy;
	a.rects.detect{arg z; z.rect.contains(xx@yy)}
};

a.range.addAction({
~refresh.()
});
x=0;y=0;
a.addAction({ arg self, xx, yy;
	if(~touch.(xx, yy).isNil){
		x=xx.linlin(*[0]++a.width++a.fenetre);
		~refresh.();
	} 
}, \mouseDownAction);
a.addAction({ arg self, k, mod, uni, other;
		[	k, uni, mod, other].postln;
	uni.switch(
		32, {		~isPlaying.().if
			{~stop.(); }
			{~play.(); }},
		8, { x=0; ~refresh.(); } 
	); 
}, \keyDownAction);

a.drawFunc=a.drawFunc ++ {
	"bah alors ??".postln;
	Color.black.set;
	Pen.line((y-0.1)@0, (y-0.1)@(a.height-a.range.height));
	Pen.fillStroke
};
c=r{
	var fps=0.2;
	loop{
		x=x+fps;
		y=x.linlin(*a.fenetre++0++a.width);
		a.refresh; 
		fps.wait;
	}
};
)


