package fr.mrcubee.gbot.gource.job;

import fr.mrcubee.gbot.gource.task.Task;
import fr.mrcubee.gbot.gource.task.TaskError;

import java.io.*;
import java.nio.file.Files;

public abstract class GourceProcessJob extends GourceJob {

    private final Runtime runtime;
    private Process process;
    private BufferedReader bufferedReader;

    public GourceProcessJob(final String name) {
        super(name);
        this.runtime = Runtime.getRuntime();
    }

    protected Process createProcess(final String binary, final String... args) {
        final Task currentTask = getCurrentTask();
        File workingDirectory;

        if (currentTask == null)
            return null;
        workingDirectory = currentTask.getWorkingDirectory();
        if (workingDirectory == null) {
            try {
                workingDirectory = Files.createTempDirectory(Integer.toString(currentTask.hashCode())).toFile();
                currentTask.changeWorkingDirectory(workingDirectory);
            } catch (IOException exception) {
                taskError("Can't create temp task directory.");
                return null;
            }
        }
        try {
            this.process = this.runtime.exec(binary, args, workingDirectory);
        } catch (IOException exception) {
            taskError("Can't execute process.", exception);
            return null;
        }
        this.bufferedReader = new BufferedReader(new InputStreamReader(this.process.getInputStream()));
        return this.process;
    }

    @Override
    public void runTask() throws JobException {
        final Task task = getCurrentTask();

        if (this.process == null)
            return;
        if (this.process.isAlive()) {
            try {
                processRun(this.bufferedReader);
            } catch (Exception exception) {
                taskError("Error during process run.", exception);
            }
            return;
        }
        if (this.process.exitValue() != 0)
            throw new JobException(this, task, "Invalid exit value.");
        taskDone();
    }

    public abstract void processRun(final BufferedReader processOutput) throws Exception;

}
