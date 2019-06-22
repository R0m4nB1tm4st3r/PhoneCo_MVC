package Model;

import State.IPhoneState;
import View.IPhoneView;

public interface IPhoneModel {
    void RegisterView(IPhoneView target);
    void NotifyViews();
    void UpdateState(String actionCommand);
    IPhoneState GetState();
    String GetMessage();
    String GetNumber();
    void ClearMessage();
    void ClearNumber();
}
