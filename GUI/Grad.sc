/*
Grad().init.front
*/

Grad : UserView{

	var mel;
	var a, b;
	draw{
		mel.collect({|x, i|
			var rect=Rect(b[i]*this.width ,0,b[i]*this.width,100);
			var color=(x.switch(
				0, {Color.green}, 
				3, {Color.yellow}, 
				4, {Color.red} ))
			.alpha_(0.5);
			rect.postln;
			Pen.addRect(rect);
			Pen.fillAxialGradient(rect.leftTop+(rect.width/2), rect.rightBottom-(rect.width/2), Color.white(), color );
		})
	}
	init{ arg me=[0,3,4,3,4,3,0,4];
		mel=me;
		a=mel.collect{[1,2,3].choose}.normalizeSum;
		b=[0]++a.integrate;
		//		this.drawFunc_{ this.draw};
	}

}