package View;

import Controller.IPhoneController;
import Enumerations.ButtonTag;
import Enumerations.StateTag;
import Model.IPhoneModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class PhoneViewOld implements IPhoneView {
    JFrame frame;

    JButton btnPhone, btnHangUp, btnStar, btnHash;
    JButton[] NumberButtons;
    JPanel containerPanel;
    ButtonClick buttonClickListener;

    JTextArea txtScreen;
    JPanel screenPanel;
    JLabel screenBackground;

    JPanel numericButtonGrid;
    JPanel phoneButtonGrid;

    private boolean isTxtScreenVisible = false;

    IPhoneController phoneController;
    IPhoneModel phoneModel;

    final int numberButtonWidth = 90;
    final int buttonHeight = 80;
    final int phoneButtonWidth = 130;
    final int borderValue = 20;
    final String[]  NumLabels =     {"1", "2",   "3",   "4",   "5",   "6",   "7",    "8",   "9",    "*", "0",     "#"};
    final String[]  LetterLabels =  {"",  "ABC", "DEF", "GHI", "JKL", "MNO", "PQRS", "TUV", "WXYZ", "",  "space", ""};

    public PhoneViewOld(IPhoneModel model, IPhoneController controller) {
        this.phoneModel = model;
        this.phoneController = controller;
        this.phoneModel.RegisterView(this);
        this.buttonClickListener = new ButtonClick(controller);
        InitializeView();
    }

    private void InitializeView() {
        /* set up phone frame */
        ConfigureWindow();

        /* set up screen */
        ConfigureScreen();

        /* set up text screen */
        ConfigureTextScreen();

        /* set up buttons */
        ConfigureButtons();

        frame.pack();
        frame.setVisible(true);
    }

    private void ConfigureScreen() {
        screenPanel = new JPanel();
        screenPanel.setPreferredSize(new Dimension(400, 400));
        screenPanel.setLayout(new GridLayout(1,1));

        Image screenBackgroundImage = new ImageIcon(getClass().getResource("/Chromatic color background, backgrounds textures.jpg")).getImage();
        ImageIcon screenBackgroundIcon = new ImageIcon(screenBackgroundImage.getScaledInstance(400, 400, Image.SCALE_FAST));
        screenBackground = new JLabel();
        screenBackground.setIcon(screenBackgroundIcon);
        screenPanel.add(screenBackground);

        containerPanel.add(screenPanel, BorderLayout.NORTH);
    }

    private void ConfigureTextScreen() {
        txtScreen = new JTextArea(10, 16);
        txtScreen.setFont(new Font("SansSerif", Font.BOLD, 20));
        txtScreen.setEditable(false);
        txtScreen.setLineWrap(true);
    }

    private void ConfigureWindow() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Old");
        frame.setBackground(Color.black);
        frame.setLayout(new BorderLayout());

        containerPanel = new JPanel();
        containerPanel.setBorder(BorderFactory.createEmptyBorder(borderValue, borderValue, borderValue, borderValue));
        containerPanel.setLayout(new BorderLayout());
        containerPanel.setBackground(Color.darkGray);

        frame.add(containerPanel);
    }

    private void ConfigureButtons() {
        JLabel buttonLabel1;
        JLabel buttonLabel2;
        //Enumerations.ButtonTag tempButtonLabel = Enumerations.ButtonTag.ONE;

        /* Phone and HangUp Button */
        /*************************************************************************************************************************************/
        phoneButtonGrid = new JPanel();
        phoneButtonGrid.setLayout(new GridLayout(1, 2));

        btnPhone = new JButton();
        btnPhone.setBounds(0, 0, phoneButtonWidth, buttonHeight);
        Image phoneImage = new ImageIcon(getClass().getResource("/phone-answer-green-hi.png")).getImage();
        ImageIcon phoneIcon = new ImageIcon(phoneImage.getScaledInstance(btnPhone.getWidth(), btnPhone.getSize().height, Image.SCALE_FAST));
        btnPhone.setIcon(phoneIcon);
        btnPhone.setActionCommand(ButtonTag.PHONE.name());
        btnPhone.addActionListener(buttonClickListener);
        phoneButtonGrid.add(btnPhone);

        btnHangUp = new JButton();
        btnHangUp.setBounds(0, 0, phoneButtonWidth, buttonHeight);
        Image hangUpImage = new ImageIcon(getClass().getResource("/phone-hang-up-red-horizontal-hi.png")).getImage();
        ImageIcon hangUpIcon = new ImageIcon(hangUpImage.getScaledInstance(btnHangUp.getWidth(), btnHangUp.getHeight(), Image.SCALE_FAST));
        btnHangUp.setIcon(hangUpIcon);
        btnHangUp.setActionCommand(ButtonTag.HANGUP.name());
        btnHangUp.addActionListener(buttonClickListener);
        phoneButtonGrid.add(btnHangUp);

        containerPanel.add(phoneButtonGrid, BorderLayout.CENTER);
        /*************************************************************************************************************************************/

        /* Numeric Buttons */
        /*************************************************************************************************************************************/
        NumberButtons = new JButton[12];
        numericButtonGrid = new JPanel();

        numericButtonGrid.setLayout(new GridLayout(4, 3));

        for(int i = 0; i < 12; i++) {
            NumberButtons[i] = new JButton();
            NumberButtons[i].setLayout(new BorderLayout());

            buttonLabel1 = new JLabel(NumLabels[i]);
            buttonLabel2 = new JLabel(LetterLabels[i]);
            buttonLabel1.setFont(new Font("SansSerif", Font.BOLD, 45));
            buttonLabel2.setFont(new Font("SansSerif", Font.BOLD, 15));
            buttonLabel1.setHorizontalAlignment(JLabel.HORIZONTAL);
            buttonLabel2.setHorizontalAlignment(JLabel.HORIZONTAL);

            NumberButtons[i].add(BorderLayout.NORTH,buttonLabel1);
            NumberButtons[i].add(BorderLayout.SOUTH,buttonLabel2);
            NumberButtons[i].setActionCommand(ButtonTag.values()[i+2].name());
            NumberButtons[i].addActionListener(buttonClickListener);

            numericButtonGrid.add(NumberButtons[i]);
        }

        containerPanel.add(numericButtonGrid, BorderLayout.SOUTH);
        /*************************************************************************************************************************************/
    }

    @Override
    public void UpdateView() {
        StateTag tempTag = this.phoneModel.GetState().GetStateTag();

        if(tempTag == StateTag.HOME_STATE) {
            txtScreen.setText("");
            ChangeTextScreenVisibility(false);
        }
        else if(tempTag == StateTag.ENTER_NUMBER_STATE) {
            ChangeTextScreenVisibility(true);
            txtScreen.setText(phoneModel.GetNumber());
        }
        else if(tempTag == StateTag.CALL_STATE) {

        }
        else if(tempTag == StateTag.MESSAGE_STATE) {

        }
    }

    private void ChangeTextScreenVisibility(boolean visibilityRequest) {
        if(isTxtScreenVisible == false && visibilityRequest == true) {
            screenPanel.remove(screenBackground);
            screenPanel.add(txtScreen, BorderLayout.NORTH);
            isTxtScreenVisible = true;
        }
        else if(isTxtScreenVisible == true && visibilityRequest == false) {
            screenPanel.remove(txtScreen);
            screenPanel.add(screenBackground, BorderLayout.NORTH);
            isTxtScreenVisible = false;
        }
        screenPanel.repaint();
    }
}

class ButtonClick implements ActionListener {
    IPhoneController theController;

    public ButtonClick(IPhoneController controller) {
        this.theController = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.theController.UpdateModel(e.getActionCommand());
    }
}

