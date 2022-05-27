package fr.mrcubee.gbot.gource.task;

import fr.mrcubee.gbot.gource.Node;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author MrCubee
 * @since 1.0
 * @version 1.0
 */
public class Task {

    private final List<String> repositoriesUrl;
    private final Set<Node> nodes;
    private final TaskSuccess successMethod;
    private final TaskError errorMethod;
    private File workingDirectory;

    public Task(final List<String> repositoriesUrl, final TaskSuccess successMethod, final TaskError errorMethod) {
        this.repositoriesUrl = repositoriesUrl;
        this.nodes = new HashSet<Node>();
        this.successMethod = successMethod;
        this.errorMethod = errorMethod;
    }

    public Task(final TaskSuccess successMethod, final TaskError errorMethod, final String... repositoriesUrl) {
        this(Arrays.asList(repositoriesUrl), successMethod, errorMethod);
    }

    public List<String> getRepositoriesUrl() {
        return this.repositoriesUrl;
    }

    public Set<Node> getNodes() {
        return this.nodes;
    }

    public TaskSuccess getSuccessMethod() {
        return this.successMethod;
    }

    public TaskError getErrorMethod() {
        return this.errorMethod;
    }

    public void changeWorkingDirectory(final File directory) {
        this.workingDirectory = directory;
    }

    public File getWorkingDirectory() {
        return this.workingDirectory;
    }

}
