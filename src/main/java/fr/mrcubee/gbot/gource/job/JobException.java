package fr.mrcubee.gbot.gource.job;

import fr.mrcubee.gbot.gource.task.Task;

public class JobException extends Exception {

    private final GourceJob job;
    private final Task task;

    public JobException(final GourceJob job, final Task task, final String message) {
        super(message);
        this.job = job;
        this.task = task;
    }

    public JobException(final GourceJob job, final Task task, final Throwable throwable) {
        super(throwable);
        this.job = job;
        this.task = task;
    }

    public JobException(final GourceJob job, final Task task, final String message, final Throwable throwable) {
        super(message, throwable);
        this.job = job;
        this.task = task;
    }

    public GourceJob getJob() {
        return this.job;
    }

    public Task getTask() {
        return this.task;
    }

}
