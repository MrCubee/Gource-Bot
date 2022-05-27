package fr.mrcubee.gbot.gource.task;

/**
 * @author MrCubee
 *
 */
public enum TaskStatus {

    IN_QUEUE,
    DOWNLOAD_AND_EXTRACT,
    EXTRACT_USERS,
    WAITING_USER_RESPONSE,
    RENDERING,
    DONE,
    ERROR;

}
