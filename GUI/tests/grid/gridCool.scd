// =====================================================================
// GridPlus test
// =====================================================================

(

var rects=nil, a, b;

var grosseur, depart;
var posage=true, point;
var range, taille=64;
var h=nil; 

Window.closeAll;

w = Window.new("bob", Rect(350,150,400,400)).acceptsMouseOver_(true).front;
u = UserView(w, Rect(0,0,w.width, w.height););

~touch={ arg xx, yy;
	rects.detect{arg z; z.rect.contains(xx@yy)}
};


~rect={(
	alpha:0.5, niveauX:0, niveauY:0,z:10,
	color:Color.rand(),
	rect:Rect(),
	create:{ arg self;
		self.use{
			var tmp=
			Rect()
			.height_(d.tailleCouaré.height)
			.top_(d.tailleCouaré.height*~niveauY)
			.left_(~niveauX.linlin(x.minval, x.maxval, 0, u.bounds.width))
			.width_(
				((~niveauX..~niveauX+~z).asSet & (x.minval..x.maxval).asSet).size
				.linlin(0, x.maxval-x.minval, 0, u.bounds.width))
			.postln;
			~rect=tmp;
			// if(rects.removing(a).detect{arg x; x.rect.intersects(tmp)}.notNil)
			// {~rect}{~rect=tmp};
		}
	}
)}; 
rects=[a=~rect.()];


range= RangeSlider(u, Rect(0, u.bounds.height-20, u.bounds.width, 20))
.lo_(0)
.hi_(1)
.action_{
	arg self;
	var l, h, spec=ControlSpec(0, taille, \lin, 1);
	#l, h=[self.lo, self.hi].collect(spec.map(_));
	x.maxval_(h); x.minval_(l);
	u.refresh
};

// the Spec can define its preferred grid system
x = ControlSpec(0,taille,'lin',1, 0);
y = ControlSpec(0,3,'lin', 1,0);


u.keyDownAction_{arg self, k;
	k.switch(
		$x, {
			w.acceptsMouseOver_(true);
			posage=true;
			rects=rects.add(a=~rect.())
		}
	)
};

u.drawFunc_{
	d = DrawGrid(u.bounds.height_(u.height-20), x.grid,y.grid);
	d.draw;
	rects.do { |x|
		x.color.alpha_(x.alpha).set;
		Pen.fillRect(x.create)
	};
}
.mouseDownAction_{ arg self, xx, y;
	if(posage)
	{
		a.alpha=1;
		u.refresh;
		w.acceptsMouseOver_(false);
		posage=false;
	}
	{
		rects.detect{arg x,i;
			h=if(x.rect.contains(xx@y))
			{
				a=x; 
				x.rect.detectPart(xx);
			};
			h !? true ?? false
		};
	};
	c=r{
		arg xx;
		var point=xx;
		var regle=u.bounds.width/(x.maxval-x.minval);
		var dist=0;
		loop{
			var niou=((xx-point)/regle).asInteger;
			xx= if(niou>dist){dist=niou; 1.yield}{
				if(niou<dist){dist=niou; -1.yield}
				{0.yield}
			}
		}
	}
}
.mouseMoveAction_{ arg self, xx, y;
	if(h.notNil){
		var dist=c.next(xx);
		h.switch(
			\right,  {
				a.z=a.z+dist;
				if(a.z < 2){a.z=2};
			},
			\left,
			{
				a.z=a.z-dist;
				if(a.z < 2){a.z=2}{a.niveauX=a.niveauX+dist};
			}
		);
		u.refresh;
	}
}
.mouseOverAction_{ arg self, xx, y;
	var step=u.bounds.width/x.maxval;
	a.niveauY=(y/ d.tailleCouaré.height).asInteger;
	a.niveauX=xx.linlin(0,u.bounds.width, x.minval, x.maxval).round;
	u.refresh;
};
//
)


