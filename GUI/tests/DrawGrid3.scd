(

~dg=(
	x:[0,50],
	y:[0,10],
	draw:{ arg self; self.use{
		var d=DrawGrid((150@150).asRect,
			~x.asSpec.grid,
			~y.asSpec.grid);
		d.draw
	}
	}
);



a=View(nil, 150@150).front;
a.layout_(VLayout());
[Pmel].collect
{ arg x;
	DragSource(a).object_(x).background_(Color.rand)
	.beginDragAction_{"io".postln; 2};
};
b=UserView(nil, 200@200).drawFunc_{
	~dg.draw
}.front;
b.canReceiveDragHandler_{ arg self, x, y;
	View(b, Rect(x, y, 150,15) ).background_(Color.blue).alpha_(0.2);
	 true
};
b.receiveDragHandler_{ arg x;
"bingo".postln;
	//	b.pat_(	View.currentDrag.new)
}




)