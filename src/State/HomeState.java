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
        if(!(   command.equals(ButtonTag.PHONE.name()) ||
                command.equals(ButtonTag.HANGUP.name()) ||
                command.equals(ButtonTag.HASH.name()) ||
                command.equals(ButtonTag.STAR.name()) )) {
            context.currentState = new EnterNumberState();

            if(!command.equals(ButtonTag.ACTION.name())) {
                context.currentState.HandleState(command, context);
            }
        }
    }
}
