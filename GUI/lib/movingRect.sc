//MovingRect().front

MovingRect : UserView{
	var dur, ratio;
	var onRect=false, rBut=false;
	var f, h, g, k;
	var <m, <l;
	*new{ arg p, b, z=[2,3,2,1];
		^super.new(p, b).init(z)
	}
	init{ arg z;
		dur=z;
		ratio=(this.bounds.width/dur.sum);
		dur.inject(0, { arg a, b, i; 
			f=f.add([Color.rand.alpha_(0.83), Rect(a*ratio, 0 , b*ratio,this.bounds.height)]);
			m=m.add(0); l=l.add(0);
			a+b
		});
		this.drawFunc={ this.draw};
	}
	mouseDown{ arg xx, y, mod;
		rBut=if(mod==1){true}{false};
		g=f.detectIndex{arg z, i;
			z[1].contains(xx@y)
		};
		if (g.notNil){
			onRect=true;
			h=f[g][1].detectPart(xx);
			k=nil;
			// if((xx - f[g][1].left) < (f[g][1].width/2)){
			// 	k=nil; \left; 
			// }{
			// 	k=nil; \right;
			// };
		}{onRect=false};
	}
	draw{
		f.do { |x|
			x[0].set;
			Pen.fillRect(x[1])
		}
	}
	mouseMove{ arg xx, y;
		k !? {k=xx-k} ?? {k=0};
		if(onRect)
		{
			if(rBut){
				// TODO
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
		this.refresh;
	}
}

/*

	w=Window("window title", Rect(0, 0, 400, 400)).alwaysOnTop_(true).front;
	w.addFlowLayout;
	a=MovingRect(w, Rect(0,0,500,100), [2,2,2]);
	
	[a.m, a.l]

*/

LegatoGUI : MovingRect {
	lag{
		^m.collect({|x, i|
			x/(dur[i]*ratio)
		})
	}
	leg{
		^l.collect({|x, i|
			x/(dur[i]*ratio)
		})	
	}
}

/*

	w=Window("window title", Rect(0, 0, 400, 400)).alwaysOnTop_(true).front;
	w.addFlowLayout;
	a=LegatoGUI(w, Rect(0,0,500,100), [2,2,2]);
	[a.lag, a.leg]

*/