
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
View(nil, 300@300).layout_(VLayout(
	[HLayout(),s:3],
	[a=View().background_(Color.rand), s:2]
)
).front;
ListGUI(parent:a)
)

Window.closeAll;
w=Window("window title", Rect(0, 0, 400, 400)).alwaysOnTop_(true).front;
w.addFlowLayout;
ListGUI(parent:w)