package View;

import Controller.IPhoneController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ButtonClick implements ActionListener {
    IPhoneController theController;

    public ButtonClick(IPhoneController controller) {
        this.theController = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
        this.theController.UpdateModel(e.getActionCommand());
    }
}
