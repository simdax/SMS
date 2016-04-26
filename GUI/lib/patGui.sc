// =====================================================================
// SuperCollider Workspace
// =====================================================================

PatGui{

	*new{
		arg p, rect;
		var w=FlowView(parent: p, bounds:200@100);
		StaticText(w, 50@20).string_("degree");
		ListView(w, 50@50).items_(["Prand", "Pseq"]);
		Button(w, 50@50).action_{
			rect.pattern_(
				Pbind(
					\degree, Array.rand(10, 1,4).pseq,
					\dur, Array.rand(10, 1,4).pseq
				)	
			);
			rect.patternVal
		};
		^w
	}
}

