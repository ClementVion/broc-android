package clement.broc;

/**
 * Created by clement on 29/05/2017.
 */

public class ListItem {

    private String head;
    private String desc;
    private String eventId;
    private String imageUrl;

    public ListItem(String head, String desc, String eventId, String imageUrl) {
        this.head = head;
        this.desc = desc;
        this.eventId = eventId;
        this.imageUrl = imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }
}
