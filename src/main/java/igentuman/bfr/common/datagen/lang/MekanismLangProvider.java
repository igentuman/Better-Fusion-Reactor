package igentuman.bfr.common.datagen.lang;


import mekanism.common.Mekanism;

import net.minecraft.data.DataGenerator;

public class MekanismLangProvider extends BaseLanguageProvider {

    public MekanismLangProvider(DataGenerator gen) {
        super(gen, Mekanism.MODID);
    }

    @Override
    protected void addTranslations() {


    }


    private static String formatAndCapitalize(String s) {
        boolean isFirst = true;
        StringBuilder ret = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (c == '_') {
                isFirst = true;
                ret.append(' ');
            } else {
                ret.append(isFirst ? Character.toUpperCase(c) : c);
                isFirst = false;
            }
        }
        return ret.toString();
    }

}
