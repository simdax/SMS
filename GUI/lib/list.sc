ListGUI{
	var <list, <parent,  <setBouton, <focusAction, <>actionAdd, <>mwa;
	var <window, <view;// <handler;
	var <bouton, <>boutons;

	*new{arg list=[\a, \b, \c, \d], setBouton=[], focusAction={}, actionAdd={}, mwa={}, parent;
		^super.newCopyArgs(list, parent, setBouton.asArray, focusAction, actionAdd, mwa).init(parent)
	}
	handler{
			arg obj;
			list.swap(
				list.indexOf(View.currentDrag),
				list.indexOf(obj.object)
			);
			("new list = "++list).postln;
			this.update
	}
	init{
		arg window=Window("listGUI", Rect(0, 0, 400, 40)).alwaysOnTop_(true).front;
		view=View(
			window, window.bounds.width-60@40
		)
		.background_(Color.rand)
		.mouseDownAction_{arg self;
			focusAction.value; self.background_(Color.rand)
		}
		.mouseWheelAction_{
			arg self, x, y;
			view.children.select{ arg rect;
				rect.bounds.contains(x@y)
			}.postln
		};
		view.addFlowLayout;
		PopUpMenu(window, Rect(view.bounds.width, 0, 40, 40) )
		.items_(setBouton);
	
		this.draw;
	}
	add{ arg item;
		list=list.add(item);
		this.update;
		actionAdd.value;
	}
	remove{arg index;
		index=if(index.isSymbol, {
			list.perform(index)
		}, index);
		list.removeAt(index);
		view.children[index].remove
	}
	removeFocus{
		var index=this.focus;
		index !? {this.remove(index)}
	}
	focus{
		^view.children.detectIndex(_.hasFocus);
	}
	replace{arg index, item;
		list[index]=item; this.update;
	}
	mouseDownAction_{ arg func;
		view.mouseDownAction_
		(view.mouseDownAction.addFunc({
			arg self, x, y;
			view.children.select{ arg rect;
				rect.bounds.contains(x@y)
			}.postln
		}));
		view.mouseDownAction.class.postln
	}
	draw{ 
		view.layout_(
			HLayout(
				*boutons=list.collect({
					arg sym, i;
					DragBoth()
					.object_(sym)
					.mouseWheelAction_(mwa.(sym, i))
					.receiveDragHandler_({arg self;
						this.handler(self)
					})
				
				})
			))
	}
	update { 
		{
			view.removeAll;
			view.decorator.reset;
			{this.draw}.defer(0.05)
		}.defer(0.05)
	}
	close{
		view.remove
	}
}

/*

a=ListGUI([], focusAction:{"prout".postln});
a.add(3);

a=ListGUI([0,1,2], mwa:{ arg obj, i;
{arg  self, x, y, mod, xD, yD; 
 [self, x, y, mod, xD, yD, obj, i].postln
}});


a=ListGUI([0,1,2]);
a.remove(\lastIndex)

a.action_({"a".postln});
a.action_({"b".postln});


b=ListGUI(3.collect{Pbind(\degree, Pseq(15.rand.asArray.postln))});
Pdef(\bob, Pn(Pseq(b.list)).midi(5)).play;
b.action_{
	Pdef(\bob).source.patterns[0].pattern.list=b.list
	}

r{
	loop{
		defer{
			a.hasFofo.postln
		};
		1.wait
	}
}.play



*/


// (

// var nb=3;

// var w=View(nil, Rect(0, 0, 400, (40*nb)+10));
// var w2=View(nil, w.bounds+Rect(0,0,50,0))
// .layout_(
// 	HLayout(VLayout(*nb.collect{Button()}))
// )
// .background_(Color.rand());
// w.addFlowLayout;
// nb.do{ListGUI(parent:w)};
// w2.layout.add(w);
// w2.front

// )


// // add button

// MultiListGUI{

// 	*new
	
// }