package script.data;

public class DataBaseSource implements ScriptSource {

    @Override
    public int getNumberElements() {
        return NUMBER_ELEMENTS;
    }

    @Override
    public ScriptAction[] createActions(int elementIndex) {
        return new ScriptAction[0];
    }

    @Override
    public ScriptElement[] createElements() {
        return new ScriptElement[0];
    }
}
