
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


(
a=View().background_(Color.rand);

View(nil, 500@300).layout_(VLayout(
	[HLayout(),s:3],
	[ListGUI(), s:2]
)
).front;
)

w=View(nil, 100@100).front;
a=ListGUI(parent:w)

PopUpMenu().front