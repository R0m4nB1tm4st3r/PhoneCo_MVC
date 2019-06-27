package Controller;

import Enumerations.ButtonTag;
import Model.IPhoneModel;

import java.util.Timer;
import java.util.TimerTask;

public class PhoneController implements IPhoneController{
    private IPhoneModel theModel;

    private final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public PhoneController(IPhoneModel model) {
        this.theModel = model;
    }

    @Override
    public void UpdateModel(String actionCommand) {
        if(IsButtonActionCommand(actionCommand)) {
            theModel.UpdateState(actionCommand);
        }
    }

    private boolean IsButtonActionCommand(String actionCommand) {
        for(ButtonTag tag : ButtonTag.values()){
            if(tag.name() == actionCommand){
                return true;
            }
        }
        for(int i = 0; i < 26; i++) {
            if(alphabet.contains(actionCommand)) {
                return true;
            }
        }
        return false;
    }
}
