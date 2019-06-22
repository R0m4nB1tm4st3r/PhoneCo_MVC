package State;

import Enumerations.StateTag;
import Model.IPhoneModel;
import Model.PhoneModel;

public abstract class IPhoneState {
    StateTag currentStateTag;

    public abstract void HandleState(String command, PhoneModel context);

    public StateTag GetStateTag() {
        return this.currentStateTag;
    }
}
