package script.data;

import script.runner.ScriptRunner;

import java.util.concurrent.atomic.AtomicInteger;

public class StandardScript implements Script {

    private ScriptElement[] scriptElements;
    private AtomicInteger lastElementRun = new AtomicInteger(0);

    private StandardScript() {
    }

    @Override
    public void buildScript(ScriptSource source) {

    }

    @Override
    public void runNextElement(ScriptRunner scriptRunner) {
        int nextElementIndex = lastElementRun.getAndIncrement();
        if (nextElementIndex < scriptElements.length) {
            ScriptElement nextElement = scriptElements[nextElementIndex];
            runElement(scriptRunner, nextElement);
        }

    }

    @Override
    public void runElement(ScriptRunner scriptRunner, ScriptElement element) {


    }


    @Override
    public void handleTimeSignal() {

    }


    public static StandardScript createStandardScript(ScriptSource scriptSource) {

        StandardScript newScript = new StandardScript();
        newScript.buildScript(scriptSource);

        return newScript;

    }


}
