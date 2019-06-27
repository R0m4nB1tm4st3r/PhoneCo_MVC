package State;

import Draft.Draft;
import Enumerations.StateTag;
import Model.IPhoneModel;
import Model.PhoneModel;

public abstract class IPhoneState {
    StateTag currentStateTag;
    protected Draft currentLoadedDraft = null;

    public abstract void HandleState(String command, PhoneModel context);

    public StateTag GetStateTag() {
        return this.currentStateTag;
    }
}
