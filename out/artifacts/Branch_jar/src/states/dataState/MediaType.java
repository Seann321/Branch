package states.dataState;

import java.io.Serializable;

public class MediaType implements Serializable {

    String type;
    int amount = 0;

    public MediaType(String type) {
        this.type = type;
    }

    public String getMediaType() {
        return type + " : " + amount;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
