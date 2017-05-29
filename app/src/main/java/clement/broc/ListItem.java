package clement.broc;

/**
 * Created by clement on 29/05/2017.
 */

public class ListItem {

    private String head;
    private String desc;

    public ListItem(String head, String desc) {
        this.head = head;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public String getHead() {
        return head;
    }
}
