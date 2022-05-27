package fr.mrcubee.gbot.gource;

import java.io.File;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Node {

    private static final Pattern NODE_PATTERN = Pattern.compile("([0-9]{1,21})\\|(.+)\\|([MDA])\\|([^\\|\\n]+)");

    public enum Action {
        CREATE('A'),
        EDIT('M'),
        DELETE('D');

        public final char character;

        Action(final char value) {
            this.character = value;
        }

        public static Action fromChar(final char value) {
            for (Action action : Action.values())
                if (action.character == value)
                    return action;
            return null;
        }

        public static Action fromString(final String value) {
            if (value == null || value.length() != 1)
                return null;
            return fromChar(value.charAt(0));
        }
    }

    private final long timestamp;
    private final String author;
    private final Action action;
    private final File file;

    public Node(final long timestamp, final String author, final Action action, final File file) {
        this.timestamp = timestamp;
        this.author = author;
        this.action = action;
        this.file = file;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public String getAuthor() {
        return this.author;
    }

    public Action getAction() {
        return this.action;
    }

    public File getFile() {
        return this.file;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.timestamp, this.author, this.action, this.file);
    }

    @Override
    public boolean equals(final Object object) {
        return object instanceof Node && object.hashCode() == hashCode();
    }

    @Override
    public String toString() {
        return this.timestamp + "|" + this.author + "|" + this.action.character + "|" + this.file.getPath();
    }

    public static Node parseLine(final String line) {
        final Matcher matcher;
        final Action action;
        final Node node;

        if (line == null)
            return null;
        matcher = NODE_PATTERN.matcher(line);
        if (!matcher.find() || matcher.groupCount() != 4)
            return null;
        action = Action.fromString(matcher.group(3));
        if (action == null)
            return null;
        node =  new Node(Long.parseLong(matcher.group(1)), matcher.group(2), action, new File(matcher.group(4)));
        if (matcher.find())
            return null;
        return node;
    }

    public static Set<Node> parse(final String str) {
        final String[] lines;
        final Set<Node> nodes;
        Node node;

        if (str == null)
            return null;
        lines = str.split("\n");
        if (lines.length < 1)
            return null;
        nodes = new HashSet<Node>();
        for (String line : lines) {
            node = parseLine(line);
            if (node != null)
                nodes.add(node);
        }
        return nodes;
    }
}
