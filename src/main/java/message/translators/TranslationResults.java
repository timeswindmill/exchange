package message.translators;

public class TranslationResults<T> {


    private final T translatedObject;
    private final TranslationError translationError;

    public TranslationResults(T translatedObject, TranslationError translationError) {
        this.translatedObject = translatedObject;
        this.translationError = translationError;
    }

    public T getTranslation() {
        return translatedObject;
    }

    public TranslationError getError() {
        return translationError;
    }

    private class TranslationError {
        String errorText;
        int tagNumber;
        String tagValue;
    }

}
