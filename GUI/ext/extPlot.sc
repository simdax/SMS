+ Plot{
	
	bounds_ { |rect|
		plotBounds =   rect ;
		bounds = rect;
		valueCache = nil;
		drawGrid.bounds = plotBounds;
	}
}

//[0,1,2,1].plot