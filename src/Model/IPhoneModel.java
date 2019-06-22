package Model;

import View.IPhoneView;

public interface IPhoneModel {
    void RegisterView(IPhoneView target);
    void NotifyViews();
    void UpdateState(String actionCommand);
}
