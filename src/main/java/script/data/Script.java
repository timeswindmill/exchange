package script.data;

import script.runner.ScriptRunner;

public interface Script {

    public void buildScript(ScriptSource source);

    public void runNextElement(ScriptRunner scriptRunner);

    public void runElement(ScriptRunner scriptRunner, ScriptElement element);

    public void handleTimeSignal();

}
