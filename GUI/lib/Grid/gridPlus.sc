/*

Window.closeAll;
w=Window("window title", Rect(0, 0, 400, 400)).alwaysOnTop_(true).front;
	w.addFlowLayout;
UserView(w, w.bounds).background_(Color.red)

*/

GridPlus : UserView {

	var <rects=nil;
	var a, b;

	var posage=true;
	var <>range, <taille=64;
	var h=nil, point;
	var w, x, y, u, d, c,
	<>rect;

		
	*new{ 
		arg	w = Window.new("Grid Plus", Rect(0,0,400,400))
		.front.acceptsMouseOver_(true),
		b= w.bounds;
		^super.new(w, b).init
	}
	
	touch{		
		arg xx, yy;
		^rects.detectIndex{arg z; z.rect.contains(xx@yy)}
	}
	mouseDown{ arg x, y, mod, button, click;
		var indexRect=this.touch(x, y);
		this.mouseDownAction.value(this, x, y, mod, button, click, indexRect);
	}
	durees{
		^rects.collect({|x| [x.niveauX, x.z] })
	}
	patterns{
		^rects.collect({|x| x.pattern })
	}
	fenetre{
		^[x.minval,x.maxval]
	}
	init{  

		this.parents.last.acceptsMouseOver_(true);
		
		x = ControlSpec(0,taille,'lin',1, 0);
		y = ControlSpec(0,3,'lin', 1,0);

		rect={(
			alpha:0.5, niveauX:0, niveauY:0,z:10,
			duree:{arg self; self.use{~niveauX+~z}},
			color:Color.rand(),
			rect:Rect(),
			create:{ arg self;
				self.use{
					var tmp=Rect()
					.height_(d.tailleCouaré.height)
					.top_(d.tailleCouaré.height*~niveauY)
					.left_(~niveauX.linlin(x.minval, x.maxval, 0, this.bounds.width))
					.width_(
						((~niveauX..~niveauX+~z).asSet & (x.minval..x.maxval).asSet).size
						.linlin(0, x.maxval-x.minval, 0, this.bounds.width));
					~rect=tmp
					// TODO, intersection problem
					// if(rects.removing(a).detect{arg x; x.rect.intersects(tmp)}.notNil)
					// {~rect}{~rect=tmp};
				};
			}
		)}; 

		range= RangeSlider(this, Rect(0, this.bounds.height-20, this.bounds.width, 20))
		.lo_(0)
		.hi_(1)
		.action_{
			arg self;
			var l, h, spec=ControlSpec(0, taille, \lin, 1);
			#l, h=[self.lo, self.hi].collect(spec.map(_));
			x.maxval_(h); x.minval_(l);
			this.refresh
		};

		this.keyDownAction_{arg self, k;
			k.switch(
				$x, {
					this.parents.last.acceptsMouseOver_(true);
					posage=true;
					rects=rects.add(a=rect.())
				}
			)
		};

		this.drawFunc_{
			d = DrawGrid(this.bounds.height_(this.height-range.height), x.grid,y.grid);
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
				this.refresh;
				this.parents.last.acceptsMouseOver_(false);
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
				var regle=this.bounds.width/(x.maxval-x.minval);
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
				this.refresh;
			}
		}
		.mouseOverAction_{ arg self, xx, y;
			var step=this.bounds.width/x.maxval;
			a.niveauY=(y/ d.tailleCouaré.height).asInteger;
			a.niveauX=xx.linlin(0,this.bounds.width, x.minval, x.maxval).round;
			this.refresh;
		};
		
	}

}

/*
	GridPlus()
*/