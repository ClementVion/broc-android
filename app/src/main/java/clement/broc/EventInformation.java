package clement.broc;

/**
 * Created by clement on 30/05/2017.
 */

public class EventInformation {

    public String name;
    public String address;
    public String imageUrl;
    public String date;


    public EventInformation() {

    }

    public EventInformation(String name, String address, String imageUrl, String date) {
        this.name = name;
        this.address = address;
        this.imageUrl = imageUrl;
        this.date = date;
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

    public String getDate() {
        return date;
    }
}
