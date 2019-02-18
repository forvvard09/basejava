package homework01;

/**
 * Class Resume initialisation resume class.
 *
 * @author Sergei Poddubniak (forvvard09@gmail.com)
 * @version 1.0
 * @since 18.02.2019
 */
public class Resume {

    /**
     * Unique identifier.
     */
    String uuid;

    /**
     * property description for resume.
     */
    String description;

    /**
     * Constructor it creates a new resume container with the specified values.
     *
     * @param uuid - id for resume
     * @param description - descripption for resume
     */
    public Resume(String uuid, String description) {
        this.uuid = uuid;
        this.description = description;
    }

    /**
     * Constructor it creates a new resume container with the specified values.
     *
     */
    public Resume() {
        this.uuid = String.valueOf(((int) (Math.random() * 999)));
        this.description = null;
    }

    /**
     * Getter for uiid resume.
     *
     */
    public String getUuid() {
        return this.uuid;
    }

    @Override
    public String toString() {
        return this.uuid;
    }
}
