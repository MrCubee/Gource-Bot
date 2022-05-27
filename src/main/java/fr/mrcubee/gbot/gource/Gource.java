package fr.mrcubee.gbot.gource;

import fr.mrcubee.gbot.gource.task.TaskError;
import fr.mrcubee.gbot.gource.task.TaskSuccess;

import java.io.File;
import java.nio.file.Path;

public class Gource {

    public static File render(final TaskSuccess onSuccess, final TaskError onError, final String... repositories) {
        return null;
    }

    public static File render(final TaskSuccess onSuccess, final TaskError onError, final Path... localRepositories) {
        return null;
    }

    public static File render(final String... repositories) {
        return render(null, null, repositories);
    }

    public static File render(final Path... localRepositories) {
        return render(null, null, localRepositories);
    }

}
