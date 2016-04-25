
(
a=[0,3,4,3,4,3,0,4];
b=a.collect{[1,2,3].choose}.postln.normalizeSum;
c=[0]++b.integrate;
try{w.parent} !? {w.close};
w=UserView(nil, Rect(0, 0, 400, 100)).alwaysOnTop_(true).front.background_(z=Color.rand);

w.drawFunc_{
z=a.collect({|x, i|
	var rect=Rect(c[i]*400 ,0,b[i]*400,100);
	var color=(x.switch(
		0, {Color.green}, 
		3, {Color.yellow}, 
		4, {Color.red} ))
	.alpha_(0.5);
	Pen.addRect(rect);
	Pen.fillAxialGradient(rect.leftTop+(rect.width/2), rect.rightBottom-(rect.width/2), z, color );
})
};
//w.refresh

)

