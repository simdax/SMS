(
a=GridTop();
b=
PopUpMenu().items_(Pattern.subclasses.collect(_.asString))
.action_{arg self;
	a.focus !?
	{
		var pat=a.rects[a.focus].pattern;
		pat=Pbind(\degree, self.item.postln);
		EnvirGui(pat.patternpairs.asDict)
	}
}
.front;

a.addAction({
			arg self, x, y, mod, but, nbClick, ind;
			if(nbClick>1){
				// a.focus!?
				//					ListGUIPlus()
				}			
}, \mouseDownAction);

)

