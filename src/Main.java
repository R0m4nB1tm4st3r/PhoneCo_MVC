import Controller.IPhoneController;
import Controller.PhoneController;
import Model.IPhoneModel;
import Model.PhoneModel;
import View.PhoneViewOld;

public class Main {
    public static void main(String[] args) {
        IPhoneModel model = new PhoneModel();
        IPhoneController controller = new PhoneController(model);
        PhoneViewOld testView = new PhoneViewOld(model, controller);
    }
}
