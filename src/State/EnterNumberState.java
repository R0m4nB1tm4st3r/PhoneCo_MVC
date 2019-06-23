package State;

import Enumerations.ButtonTag;
import Enumerations.StateTag;
import Model.PhoneModel;

public class EnterNumberState extends IPhoneState {
    private final String[] ValidEntryCommands;
    private final String NumberKeys = "0123456789";

    public EnterNumberState() {
        ValidEntryCommands = new String[10];
        this.currentStateTag = StateTag.ENTER_NUMBER_STATE;

        ValidEntryCommands[0] = ButtonTag.ZERO.name();
        for(int i = 1; i < 10; i++) {
            ValidEntryCommands[i] = ButtonTag.values()[i+1].name();
        }
    }

    @Override
    public void HandleState(String command, PhoneModel context) {
        if(!(command == ButtonTag.PHONE.name() || command == ButtonTag.HANGUP.name())) {
            for(int i = 0; i < 10; i++) {
                if(command == ValidEntryCommands[i]) {
                    context.AppendNumber(NumberKeys.charAt(i));
                }
            }
        }
        else {
            if(command == ButtonTag.PHONE.name()) {
                context.currentState = new CallState();
            }
            else if(command == ButtonTag.HANGUP.name()) {
                context.currentState = new HomeState();
                context.ClearNumber();
            }
        }
    }
}
