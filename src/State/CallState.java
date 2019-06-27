package State;

import Enumerations.ButtonTag;
import Enumerations.StateTag;
import Model.IPhoneModel;
import Model.PhoneModel;

public class CallState extends IPhoneState {
    public CallState() {
        this.currentStateTag = StateTag.CALL_STATE;
    }

    @Override
    public void HandleState(String command, PhoneModel context) {
        if(command.equals(ButtonTag.HANGUP.name())) {
            context.currentState = new HomeState();
            context.ClearNumber();
        }
    }
}
