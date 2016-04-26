// =====================================================================
// SuperCollider Workspace
// =====================================================================


(
Window.closeAll;
~patGui={ arg p, pat;
	w=FlowView(parent: p, bounds:200@100);
	StaticText(w, 50@20).string_("degree");
	ListView(w, 50@50).items_(["Prand", "Pseq"]);
	Button(w, 50@50).action_{
		NotificationCenter.notify(~patGui, \changed, 	
				Pbind(\degree, Array.rand(10, 1,4).pseq,
					\dur, Array.rand(10, 1,4).pseq  )	
		)
	}
};
)

~patGui.()

NotificationCenter.unregister(~patGui, \changed, 0)