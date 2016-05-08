ScaledView : UserView{

	var <>roundationF;
	var <>defSize, <>spec;
	*new{ arg p, b, defSize=20@20;
		^super.new(p, b).defSize_(defSize)
	}
	roundation{ arg x, y;
		^if(roundationF.notNil)
		{roundationF.value(x, y, this.bounds.width, this.bounds.height)}
		{this.defRoundation(x, y)}
	}
	defRoundation{ 
		arg x, y;
		^[
			x.round(defSize.x),
			y.round(defSize.y) 
		]
	}
	mouseMove{ arg x, y, mod;
		^mouseMoveAction
		.value(*([this]++this.roundation(x, y)++mod))
	}
	canReceiveDrag { arg x, y;
		if( canReceiveDragHandler.notNil )
		{
			^this.canReceiveDragHandler.value
			( *[this]++this.roundation(x, y) )
		}
		{ ^(
			this.tryPerform(
				*\defaultCanReceiveDrag.asArray++ this.roundation(x, y))
			? false ) };
	}
	receiveDrag { arg x, y;
		if( receiveDragHandler.notNil )
		{ this.receiveDragHandler.value( *[this]++ this.roundation(x, y) ) }
		{ this.tryPerform( \defaultReceiveDrag, x, y ) }; //grosse flemme
	}
	
}

/*

ScaledView(nil, 200@200)
.mouseMoveAction_{arg self, x, y; 
[x, y].postln; 
self.drawFunc_{ Pen.fillRect(
Rect(x, y, self.defSize.x, self.defSize.y))};
self.refresh
}.front

	ScaledView(nil, 500@500)
	.roundationF_{ arg x, y;
	[ x.round(50), y ]
	}
	.mouseMoveAction_{
	arg self, x, y;
	[x, y].postln;
	Button(self, Rect(x, y, 150, 150))
	}.front


	a=r{ arg points;
	var now=0;
	var current=thisThread.beats;
	r=Pwhite(50,250).asStream; n=r.next;
	points=[ points[0].round(n), points[1] ].yield;
	loop{
	now=thisThread.beats;
	if(now > (current+4))
	{ n=r.next;
	current=thisThread.beats};
	points=[ points[0].round(n), points[1] ].yield;
	}
	};
	ScaledView(nil, 500@500)
	.roundationF_{ arg x, y;
	[x, y].postln ;
	a.next([x, y]).postln
	}
	.mouseMoveAction_{
	arg self, x, y;
	[x, y].postln;
	Button(self, Rect(x, y, 150, 150))
	}.front


*/

//View with a "shadow" when hover with drag
GhostView : ScaledView{

	var objects, classes;
	*new{arg p, b ... classes;
		classes.isEmpty.if{classes=[Object]};
		^super.new(p, b).init(classes)
	}
	init{ arg cl;
		classes=cl;
		this
		//.roundationF_{arg x, y, wid, hei; [x.round(wid/3), y.round(hei/3)]}
		.canReceiveDragHandler_{ arg self, x, y, mod;
			if(View.currentDrag.class.isIn(classes)){
				this.drawFunc_{
					Color.blue.alpha_(0.2).set;
					Pen.fillRect(Rect(*[x, y]++[50,50]))
				};
				this.refresh;
				true
			}
			{false}
		}
		.receiveDragHandler_{ arg self, x, y, mod;
			[x, y].postln;
			this.drawFunc_{};
			this.refresh;
			//			objects=objects.add
			//	(View.currentDrag.copy.gui(this, Rect(x, y, 50,50))
			Button(this, Rect(x, y,50,50));
		};
	}
}

/*

(
a=GhostView(nil, 200@200, String)
.defSize_(50@50);
b=DragSource().object_("io").front
)

*/