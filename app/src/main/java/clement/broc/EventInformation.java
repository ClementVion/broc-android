package clement.broc;

/**
 * Created by clement on 30/05/2017.
 */

public class EventInformation {

    public String name;
    public String address;
    public String imageUrl;

    public EventInformation() {

    }

    public EventInformation(String name, String address, String imageUrl) {
        this.name = name;
        this.address = address;
        this.imageUrl = imageUrl;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
