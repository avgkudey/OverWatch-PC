package overwatchpc;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class PermissionStates {

    private IntegerProperty state;

    public final int getState() {
        if (state != null) {
            return state.get();
        }
        return 0;
    }

    public final void setState(int state) {
        this.stateProperty().set(state);
    }

    public final IntegerProperty stateProperty() {
        if (state == null) {
            state = new SimpleIntegerProperty(0);
        }
        return state;
    }
}
