// =====================================================================
// SuperCollider Workspace
// =====================================================================

a=Pbind( \degree, Pkey(\degree)+.x Pseq([0,1,2]) ,
	\dur, Pkey(\dur)/3
);
b=Pbind(\degree, Pseq(Array.fill(15, {10.rand}))).trace;
c= a<=b;
b.play;
c.play

Pbind(
	\degree, Pwhite(),
	\dur, Prand([1], inf),
	\timingOffset, Pseq([-0.5, 0, 0.5], inf),
	//	\legato, Pseq([1,0.5], inf)
).trace.play;


(
var onRect=false, io; var rBut=false;
Window.closeAll;
f=nil; l=nil; m=nil; 
w=Window("window title", Rect(1000, 0, 400, 400)).alwaysOnTop_(true).front;
io=UserView(w, Rect(0,0,400,50));
z=[2,3,2,1]; r=(io.bounds.width/z.sum);
z.inject(0, { arg a, b, i; 
	f=f.add([Color.rand.alpha_(0.83), Rect(a*r, 0 , b*r,io.bounds.height)]);
	m=m.add(0); l=l.add(0);
	a+b
}); 

io.mouseDownAction_{ arg self, xx, y, mod;
	rBut=if(mod==1){true}{false};
	g=f.detectIndex{arg z, i; (xx@y).postln;z[1].postln.contains(xx@y)};
	if (g.notNil){
		onRect=true;
		h=if((xx - f[g][1].left) < (f[g][1].width/2)){
			k=nil; \left; 
		}{
			k=nil; \right;
		};
	}{onRect=false};
	onRect.postln;
}
.drawFunc_{
	f.do { |x|
		x[0].set;
		Pen.fillRect(x[1])
	}
}
.mouseMoveAction_{ arg self, xx, y;
	k !? {k=xx-k} ?? {k=0};
	if(onRect)
	{
		if(rBut){
			
		}
		{
			h.switch(
				\left, {
					f[g][1]=f[g][1].moveBy(k, 0);
					f[g][1]=f[g][1].resizeBy(k.neg, 0);
					m[g]=m[g]-k
				},
				\right, {f[g][1]=f[g][1].resizeBy(k, 0);
					l[g]=l[g]+k;}
			)
		}
	};
	k=xx;
	io.refresh;
};
)


