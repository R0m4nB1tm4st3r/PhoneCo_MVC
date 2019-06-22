package State;

import Enumerations.ButtonTag;
import Enumerations.StateTag;
import Model.PhoneModel;

public class HomeState extends IPhoneState {
    public HomeState() {
        this.currentStateTag = StateTag.HOME_STATE;
    }

    @Override
    public void HandleState(String command, PhoneModel context) {
        if(!(   command == ButtonTag.PHONE.name() ||
                command == ButtonTag.HANGUP.name() ||
                command == ButtonTag.HASH.name() ||
                command == ButtonTag.STAR.name())   ) {
            context.currentState = new EnterNumberState();
            context.currentState.HandleState(command, context);
        }
    }
}
