package script.data;

import script.runner.ScriptRunner;

public class StandardScriptElement implements ScriptElement {

    private final ScriptAction[] scriptActions;
    private final int elementIndex;

    private StandardScriptElement(int elementIndex, ScriptAction[] scriptActions) {
        this.scriptActions = scriptActions;
        this.elementIndex = elementIndex;
    }

    @Override
    public void runElement(ScriptRunner scriptRunner) {

        runActions(scriptRunner, scriptActions);
    }


    public void runActions(ScriptRunner scriptRunner, ScriptAction[] actions) {
        scriptRunner.runActions(actions);
    }


    public static ScriptElement buildElement(ScriptSource scriptSource, int elementIndex) {

        ScriptAction[] scriptActions = scriptSource.createActions(elementIndex);
        ScriptElement scriptElement = new StandardScriptElement(elementIndex, scriptActions);

        return scriptElement;
    }


}
