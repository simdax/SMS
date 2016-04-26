
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

#b, a=2.collect{View().background_(Color.rand)};
View(nil, 500@300).layout_(VLayout(
	[HLayout([a, s:2], [b, s:1]),s:3],
	[c=ListGUI(), s:2]
)
).front;
([a, b]+++
[[
	[["scramble"]],
	[["reverse?"],["reverse!"]],
	[["rotate"]]
],[
	[["+"]],
	[["-"]]
]])
.collect({|x|
x[0].layout_(
	VLayout
	(*	x[1..].postln.collect({|x|
		Button().states_(x)
	})
	)
)
})

)

w=View(nil, 100@100).front;
a=ListGUI(parent:w)

PopUpMenu().front