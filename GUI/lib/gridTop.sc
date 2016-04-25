GridTop : GridPlus{
	var xTrait,yTrait, // endroit du trait
	routine;//routine
	
	*new{
		arg w=
		Window("window title", Rect(0, 0, 400, 400))
		.alwaysOnTop_(true).front,
		b=w.bounds;
		^super.new(w, b).init2;
	}
	go{ 
		this.keyDownAction.value(nil, nil,nil,32)
	}
	x{ // compatibility
		^xTrait
	}
	x_{
		arg val;
		xTrait=val;
		this.refres
	}
	// private
	refres{
		yTrait=xTrait.linlin(*this.fenetre++0++this.width);
		this.refresh
	}
	init2{
		xTrait=0; yTrait=0;
		// overriding..
		range.addAction({
			this.refres
		});
		this.addAction({ arg self, xx, yy;
			if(rects.detect{arg z; z.rect.contains(xx@yy)}.isNil){
				xTrait=xx.linlin(*[0]++this.width++this.fenetre);
				this.refres;
			} 
		}, \mouseDownAction);
		this.addAction({arg self, xx, yy;
			rects.detect{arg z; z.rect.contains(xx@yy)}.notNil
		}, \mouseDownAction);
		this.addAction({ arg self, k, mod, uni, other;
			//	[	k, uni, mod, other].postln;
			uni.switch(
				32, {		this.isPlaying.if
					{this.stop; }
					{this.play; }},
				8, { xTrait=0; this.refres; } 
			); 
		}, \keyDownAction);
		this.drawFunc = this.drawFunc++{
			Color.black.set;
			Pen.line((yTrait-0.1)@0, (yTrait-0.1)@(this.height-this.range.height));
			Pen.fillStroke
		};
		routine=r{
			var fps=0.2;
			loop{
				xTrait=xTrait+fps;
				this.refres;
				fps.wait;
			}
		}
	}
	stop{
		routine.stop
	}
	play{
		routine.reset; routine.play(AppClock)
	}
	resume{
		yTrait=0;xTrait=0; this.refres
	}
	isPlaying{
		^routine.isPlaying
	}
}

