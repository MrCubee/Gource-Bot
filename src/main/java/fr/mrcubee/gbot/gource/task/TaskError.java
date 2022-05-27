package fr.mrcubee.gbot.gource.task;

@FunctionalInterface
public interface TaskError {

    public void error(final Task task, final Exception exception);

}
