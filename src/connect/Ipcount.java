package connect;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Ipcount {

    private IntegerProperty ipcount;

    public final int getipcount() {
        if (ipcount != null) {
            return ipcount.get();
        }
        return 0;
    }

    public final void setipcount(int fileCount) {
        this.ipcountProperty().set(fileCount);
    }

    public final IntegerProperty ipcountProperty() {
        if (ipcount == null) {
            ipcount = new SimpleIntegerProperty(0);
        }
        return ipcount;
    }
}
