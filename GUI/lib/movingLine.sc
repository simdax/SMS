/*


MovingLine(w).init


Window.closeAll;
w=Window("window title", Rect(0, 0, 400, 400)).alwaysOnTop_(true).front;
y=FlowView(w, Rect(50,150,200,200));
PopUpMenu(y);
MovingLine(w).init2(y)

*/
MovingLine {
	var w;
	var view, //zone où on put le trait
	x, //décalage du trait
	a; //routine stoppable
	
	*new{ arg win=Window("window title", Rect(0, 0, 400, 400)).alwaysOnTop_(true).front;
		^super.newCopyArgs(win);
	}
	routine{
		^r{ 
			loop{
				x=x+1;
				view.refresh;
				0.2.yield;
			}
		};
	}
	bouton{ arg w, zone;
		^Button(w, zone)
		.mouseDownAction_{arg self, xx, yy, mod, but;
			switch(but,
				0, {
					if(a.isPlaying)
					{a.stop}
					{a.reset; a.play(AppClock)}
				},
				1, {x=0; view.refresh}
			)
		};
	}
	view{arg w, zone;
		^UserView(w, zone).acceptsMouse_(false)
		.drawFunc_{
			Pen.line(x@0, x@view.width);
			Pen.fillStroke
		};
	}
	init{ arg butPlace=Rect(50, 50,20,20), zone=200@200;
		x=0;
		a=this.class.routine;
		this.bouton(w, butPlace);
		view=this.view(	w, zone)	
	}
	init2{ arg butView, zone=200@200;
		x=0;
		a=this.class.routine;
		this.bouton(butView);
		view=this.view(	w, zone)	
	}
}