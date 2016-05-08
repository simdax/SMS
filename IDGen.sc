IDGen{
	classvar ids;
	classvar <last;
	*initClass{
		ids=[]
	}
	*add{
		while{
			last=10000.rand;
			ids.includes(last)
		};
		ids=ids.add(last)
	}
	*gen{
		this.add; ^this.last
	}
}

/*
IDGen.add
IDGen.last
*/