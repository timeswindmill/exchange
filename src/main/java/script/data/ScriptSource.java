package script.data;

public interface ScriptSource {

    public static final int NUMBER_ELEMENTS = 200;

    public int getNumberElements();

    public ScriptAction[] createActions(int elementIndex);

    public ScriptElement[] createElements();


}
