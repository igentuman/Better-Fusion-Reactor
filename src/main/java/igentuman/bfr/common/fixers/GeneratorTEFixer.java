package igentuman.bfr.common.fixers;

import mekanism.common.fixers.MekanismDataFixers.MekFixers;
import mekanism.common.fixers.TEFixer;
import igentuman.bfr.common.BFR;

public class GeneratorTEFixer extends TEFixer {

    public GeneratorTEFixer(MekFixers fixer) {
        super(BFR.MODID, fixer);
        putEntry("ReactorController", "reactor_controller");
        putEntry("ReactorFrame", "reactor_frame");
        putEntry("ReactorGlass", "reactor_glass");
        putEntry("ReactorLaserFocus", "reactor_laser_focus");
        putEntry("ReactorLogicAdapter", "reactor_logic_adapter");
        putEntry("ReactorPort", "reactor_port");
    }
}