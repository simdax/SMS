
// =====================================================================
// SuperCollider Workspace
// =====================================================================

a=GridPlus()
a.go

(
var a, gt, bt;
Window.closeAll;


a=PATGUI();
bt=a.bt;
gt=a.gt;


a.parent.addAction({ arg self, k, mod, uni, other;
	//[	k, uni, mod, other].postln;
	uni.switch(
		32, {bt.doAction; gt.isPlaying.postln},
		8, { gt.x=0; }
	); 
}, \keyDownAction);
		


)

a=Pmel(5).midi

Pfindur(2, a).play