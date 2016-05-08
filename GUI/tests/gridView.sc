Matrix : UserView{
	var mem;
	var cmds;
	children{
		^this.superPerform(\children)[2..]
	}
	push{
		mem=this.children.collect(_.bounds)
	}
	add{ arg view;
		mem=mem.add(view.bounds);
		
	}
	translate{ arg pt, op='+';
		this.children.do { |x, i|
			x.bounds_(x.bounds.origin_(mem[i].origin.perform(op,pt)))
		};
		cmds=cmds.add([pt, op])
	}
	zoom{ arg  pt, op='*';
		this.children.do { |x, i|
			x.bounds_(x.bounds.extent_(mem[i].extent.perform(op, pt)))
		};
		cmds=cmds.add([pt, op])
	}
	pop{
		this.children.do { |x, i|
			x.bounds_(mem[i])
		};
	}
}

/*

a=Matrix(nil, 250@250).front;
a.addFlowLayout;
15.do{Button(a,30@30);}
a.push
	a.translate( (1.1@1.5) )

*/

RangedView : Matrix{

	var rangeH, rangeV;
	var tailleRanger=20;
	var <matrice, view;
	var <zoom , <translate;
	var <>action;
	
	*new{ arg p, b;
		^super.new(p, b).init2
	}
	init2{
		matrice=[1,0,0,1,0,0];
		//		view=Matrix(this, this.trueSize);
		zoom=[1,1]; translate=[0,0];
		#rangeH, rangeV=
		(	[0, 0]+++
			[
				`Rect(0, this.bounds.height-tailleRanger, this.bounds.width-tailleRanger, tailleRanger),
				`Rect(this.bounds.width-tailleRanger,0, tailleRanger, this.bounds.height-tailleRanger)
			]
			+++ [\horizontal, \vertical]
			+++ [\width, \height]
		)
		.collect{ arg args, i;
			RangeSlider(this, args[1].value)
			.lo_(0)
			.hi_(1)
			.orientation_(args[2])
			.action_{ arg self;
				translate[i]=self.lo*this.bounds.perform(args[3]);
				zoom[i]=(1-self.hi)+1;
				matrice[0]=zoom[0];
				matrice[3]=zoom[1];
				matrice[4]=translate[0].neg;
				matrice[5]=translate[1];			
				this.action.value(this);
				this.refresh
			}
		};
		this.onResize_{
			rangeH.bounds_(
				Rect(0, this.bounds.height-tailleRanger, this.bounds.width, tailleRanger)
			);
			rangeV.bounds_(
				Rect(this.bounds.width-tailleRanger, 0, tailleRanger, this.bounds.height-tailleRanger)
			);
		};
	}
	trueSize{
		^this.bounds
		.width_(this.bounds.width-tailleRanger)
		.height_(this.bounds.height-tailleRanger)
	}
}

/*

	a=RangedView(nil, 200@200).front;
	b=View(a, a.trueSize);
	15.do{arg x; Button(b, )}

	

	a=RangedView(nil, 500@500).front;
	b=GhostView(a, a.trueSize, String)
	.drawFunc_{
	Pen.matrix=a.matrice;
	Pen.fillRect(Rect(45,45,50,50))
	};
	b.drawFunc=b.drawFunc++{};
	b.mouseMoveAction_{ arg self, x, y;
	b.drawFunc.array.last={Pen.fillRect(Rect(x, y, 20,20))};
	b.refresh
	};
	c=DragSource().object_("io").front
	


*/

GridView : RangedView{

	var <xSpec, <ySpec;
	var xRange, yRange;
	var zoom; // rapport 
	var d; // grid ref
	
	*new{ arg p, b, x=[0,10], y=[0,3];
		^super.new(p, b).init(x, y)
	}
	x_{ arg a;
		xRange=a;  xSpec=xRange.asSpec;
		rangeH.addAction(this.rangeAction(xSpec, xRange)) 
	}
	y_{ arg b;
		yRange=b; ySpec=yRange.asSpec;
		rangeV.addAction(this.rangeAction(ySpec, yRange)) 
	}

	rangeAction{ arg spec, range;
		^{arg self;
			var l, h;
			#l, h=[self.lo, self.hi].collect(range.asSpec.map(_));
			spec.maxval_(h); spec.minval_(l);
			this.refresh
		}
	}
	init{ arg a, b;
		this.x_(a); this.y_(b);
		this.grid;
		this.drawFunc_{
			this.grid.draw
		}; 
	}
	grid{
		^d=DrawGrid(
			Rect()
			.left_(0)
			.top_(0)
			.height_(this.trueSize.height)
			.width_(this.trueSize.width),
			xSpec.grid,
			ySpec.grid,
		)
	}
	h{ ^d.height}
	w{ ^d.width}
}

/*

	a=GridView(nil, 200@200, [0,70], [0,3]).front;
	a.y=[0,15] ; 
	a.x_([-20,30])
	.action_{ a.xSpec.postln}



(

w=Window('f',400@400).front;
a=GridView(w, w.bounds);
a.action_{ 
b.defSize_(a.w@a.h);
b.refresh
};
b=ScaledView(w, a.trueSize, (a.w@a.h))
.mouseMoveAction_{ arg self, x, y;
self.drawFunc_{ 
	Pen.matrix();
	Pen.fillRect(Rect(x, y,
	self.defSize.x, self.defSize.y))
	};
	self.refresh
};



	
)

*/