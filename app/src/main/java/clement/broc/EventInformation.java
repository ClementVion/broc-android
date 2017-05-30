package clement.broc;

/**
 * Created by clement on 30/05/2017.
 */

public class EventInformation {

    public String name;
    public String address;

    public EventInformation() {

    }

    public EventInformation(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }
}
