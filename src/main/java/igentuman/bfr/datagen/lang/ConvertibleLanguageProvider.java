package igentuman.bfr.datagen.lang;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.List;

public abstract class ConvertibleLanguageProvider extends LanguageProvider {

    public ConvertibleLanguageProvider(PackOutput gen, String modid, String locale) {
        super(gen, modid, locale);
    }

    public abstract void convert(String key, List<FormatSplitter.Component> splitEnglish);

    @Override
    protected void addTranslations() {
    }
}