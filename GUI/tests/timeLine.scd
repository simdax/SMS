// =====================================================================
// SuperCollider Workspace
// =====================================================================

(
Window.closeAll;
w=Window("window title", Rect(1000, 450, 400, 400)).alwaysOnTop_(true).front;
w.addFlowLayout;

a=UserView(w, 500@200)
//.background_(Color.blue)
.drawFunc_({
	arg self, type;
	b.item.switch(
		"mels", {
			Pen.fillRect(Rect(0,0,20,20))
		}
	)
});
b=PopUpMenu(w).items_(["rien", "mels", "accords"]).allowsReselection_(true)
.action_{
	arg self;
	a.refresh
	//	{	a.myDraw(self.item)}.fork(AppClock)
}
)
