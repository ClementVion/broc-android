package clement.broc;

/**
 * Created by clement on 29/05/2017.
 */

public class ListItem {

    private String head;
    private String desc;
    private String eventId;

    public ListItem(String head, String desc, String eventId) {
        this.head = head;
        this.desc = desc;
        this.eventId = eventId;
    }

    public String getDesc() {
        return desc;
    }

    public String getHead() {
        return head;
    }

    public String getEventId() {
        return eventId;
    }
}
