package core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.I18NBundle;

public class Language {

    private static FileHandle baseFileHandle;
    private static I18NBundle bundle;

    public static String get(String key) {
        return bundle.get(key);
    }

    public static void setLanguage(String language) {

        String file;
        switch (language) {
            case "Slovenscina" -> {
                file = "languages/language_si";
            }

            case "Deutsch" -> {
                file = "languages/language_de";
            }

            case "English" -> {
                file = "languages/language_en";
            }

            default -> {
                file = "";
            }
        }

        baseFileHandle = Gdx.files.internal(file);
        bundle = I18NBundle.createBundle(baseFileHandle);
    }
}