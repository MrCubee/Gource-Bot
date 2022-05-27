package fr.mrcubee.gbot.gource.job;

import fr.mrcubee.gbot.gource.task.Task;
import fr.mrcubee.gbot.gource.task.TaskError;
import fr.mrcubee.gbot.gource.task.TaskSuccess;

import java.util.*;

/**
 * @author MrCubee
 * @since 1.0
 * @version 1.0
 */
public abstract class GourceJob implements Runnable {

    private final String jobName;
    private final LinkedList<Task> tasks;
    private GourceJob nextJob;
    private Task currentTask;

    protected GourceJob(final String jobName) {
        this.jobName = jobName;
        this.tasks = new LinkedList<Task>();
    }

    public int taskCount() {
        return this.tasks.size();
    }

    public void setNextJob(GourceJob nextJob) {
        this.nextJob = nextJob;
    }

    public GourceJob getNextJob() {
        return this.nextJob;
    }

    public boolean containTask(final Task task) {
        if (task == null)
            return false;
        return this.tasks.contains(task);
    }

    public boolean addTask(final Task task) {
        if (task == null)
            return false;
        return this.tasks.add(task);
    }

    public Task getCurrentTask() {
        return this.tasks.getFirst();
    }

    protected void taskDone() {
        final Task task = getCurrentTask();
        final TaskSuccess taskSuccess;

        if (task == null)
            return;
        this.tasks.remove(task);
        this.currentTask = null;
        try {
            closeTask();
        } catch (Exception exception) {
            taskError("Error to close task.");
            return;
        }
        if (this.nextJob == null) {
            taskSuccess = task.getSuccessMethod();
            if (taskSuccess != null)
                taskSuccess.success(task);
        }
        this.nextJob.addTask(task);
    }

    protected void taskError(final JobException exception) {
        final Task task;
        final TaskError taskError;

        if (exception == null)
            return;
        task = getCurrentTask();
        if (task == null)
            return;
        this.tasks.remove(task);
        taskError = task.getErrorMethod();
        if (taskError != null)
            taskError.error(task, exception);
    }

    protected void taskError(final String message) {
        final Task task;
        final TaskError taskError;

        task = getCurrentTask();
        if (task == null)
            return;
        this.tasks.remove(task);
        taskError = task.getErrorMethod();
        if (taskError != null)
            taskError.error(task, new JobException(this, task, message));
    }

    protected void taskError(final String message, final Throwable throwable) {
        final Task task;
        final TaskError taskError;

        task = getCurrentTask();
        if (task == null)
            return;
        this.tasks.remove(task);
        taskError = task.getErrorMethod();
        if (taskError != null)
            taskError.error(task, new JobException(this, task, message, throwable));
    }

    public String getJobName() {
        return this.jobName;
    }

    @Override
    public void run() {
        final Task task = getCurrentTask();

        if (task == null) {
            this.currentTask = null;
            return;
        }
        if (this.currentTask == null || !this.currentTask.equals(task)) {
            try {
                initTask();
            } catch (Exception exception) {
                taskError("Error to init task.");
                return;
            }
            this.currentTask = task;
        }
        try {
            runTask();
        } catch (JobException exception) {
            taskError(exception);
        }
    }

    public abstract void initTask() throws Exception;
    public abstract void runTask() throws JobException;
    public abstract void closeTask() throws Exception;

}
