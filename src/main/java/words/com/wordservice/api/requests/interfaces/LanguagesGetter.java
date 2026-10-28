package words.com.wordservice.api.requests.interfaces;

import words.com.wordservice.main.models.enums.Language;

public interface LanguagesGetter {
    Language getLang();
    Language getTranslateLang();
}
