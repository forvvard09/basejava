package homework01;

/**
 * Class Resume initialisation resume class.
 *
 * @author Sergei Poddubniak (forvvard09@gmail.com)
 * @version 2.0
 * @since 18.02.2019
 */
public class Resume {

    private String uuid;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String inUuid) {
        uuid = inUuid;
    }

    @Override
    public String toString() {
        return uuid;
    }
}
