package agent.agent;

import java.util.ArrayList;
import java.util.List;

public class AgentConfig {

    //TODO load from file

    List<String> getExchangeInputFiles() {
        List<String> inputFiles = new ArrayList<>();
        inputFiles.add("/home/laurence/Work/Dev/Java/MatchingProjects/Exchange/data/x1");
        return inputFiles;
    }

}
