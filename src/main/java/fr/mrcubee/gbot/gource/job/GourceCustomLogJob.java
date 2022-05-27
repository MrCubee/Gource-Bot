package fr.mrcubee.gbot.gource.job;

import fr.mrcubee.gbot.gource.Node;
import fr.mrcubee.gbot.gource.task.Task;

import java.io.BufferedReader;

public class GourceCustomLogJob extends GourceProcessJob {

    public GourceCustomLogJob() {
        super("GenerateCustomLog");
    }

    @Override
    public void initTask() throws Exception {
        final Process process = createProcess("gource", "--output-custom-log", "-");

    }

    @Override
    public void closeTask() throws Exception {

    }

    @Override
    public void processRun(final BufferedReader processOutput) throws Exception {
        final String line = processOutput.readLine();
        final Node node;
        final Task task;

        if (line == null) {
            taskDone();
            return;
        }
        node = Node.parseLine(processOutput.readLine());
        if (node != null) {
            task = getCurrentTask();
            task.getNodes().add(node);
        }
    }
}
