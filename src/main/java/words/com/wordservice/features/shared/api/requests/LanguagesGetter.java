package words.com.wordservice.features.shared.api.requests;

import words.com.wordservice.features.shared.main.enums.Language;

public interface LanguagesGetter {
    Language getLang();
    Language getTranslateLang();
}
