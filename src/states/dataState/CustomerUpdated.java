package states.dataState;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class CustomerUpdated implements Serializable {


    Random random = new Random();

    public String date;

    public boolean completed = false;
    public Double ID;
    public String name, email, address, phone;
    public String[] notes = new String[]{
            "Notes", "More Notes", "More Notes", "More Notes", "More Notes"
    };
    public ArrayList<MediaType> mediaTypes = new ArrayList<>();

    public CustomerUpdated(String name) {
        ID = random.nextDouble();
        this.name = name;
        email = "EMAIL: ";
        address = "ADDRESS: ";
        phone = "PHONE: ";
        date = new SimpleDateFormat("MM-dd-yyyy").format(new Date());
        generateTypes();
    }

    private void generateTypes() {
        mediaTypes.add(new MediaType("VHS"));
        mediaTypes.add(new MediaType("VHS-C"));
        mediaTypes.add(new MediaType("8MM"));
        mediaTypes.add(new MediaType("6MM"));
        mediaTypes.add(new MediaType("BETA/CAM"));
        mediaTypes.add(new MediaType("UMATIC"));
        mediaTypes.add(new MediaType("LP"));
        mediaTypes.add(new MediaType("CASSETTE"));
        mediaTypes.add(new MediaType("REEL"));
        mediaTypes.add(new MediaType("PHOTOS"));
        mediaTypes.add(new MediaType("PHOTOBOX"));
        mediaTypes.add(new MediaType("ALBUM"));
        mediaTypes.add(new MediaType("SLIDES"));
        mediaTypes.add(new MediaType("NEG"));
        mediaTypes.add(new MediaType("FILM_8MM"));
        mediaTypes.add(new MediaType("FILM_16MM"));
        mediaTypes.add(new MediaType("CELLPHONE/CAMERA"));
        mediaTypes.add(new MediaType("DVD"));
        mediaTypes.add(new MediaType("FD"));
        mediaTypes.add(new MediaType("SD"));
        mediaTypes.add(new MediaType("HD"));
    }

    public void setMediaAmount(int x, String type) {
        for (MediaType mt : mediaTypes) {
            if (mt.type.equals(type)) {
                mt.setAmount(x);
            }
        }
    }

    public int getMediaAmount(String type) {
        for (MediaType mt : mediaTypes) {
            if (mt.type.equals(type)) {
                return mt.getAmount();
            }
        }
        return -1;
    }

    public String getMediaType(String type) {
        for (MediaType mt : mediaTypes) {
            if (mt.type.equals(type)) {
                return mt.getMediaType();
            }
        }
        return null;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email.contains("EMAIL: ")) {
            this.email = "EMAIL: " + email.substring(7);
            return;
        }
        this.email = "EMAIL: " + email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (address.contains("ADDRESS: ")) {
            this.address = "ADDRESS: " + address.substring(9);
            return;
        }
        this.email = "ADDRESS: " + address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (phone.contains("PHONE: ")) {
            this.phone = "PHONE: " + phone.substring(7);
            return;
        }
        this.phone = "PHONE: " + phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

}
