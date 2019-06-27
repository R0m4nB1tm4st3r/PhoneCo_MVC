package View;

import Controller.IPhoneController;
import Enumerations.ButtonTag;
import Enumerations.StateTag;
import Model.IPhoneModel;
import View.IPhoneView;

import javax.swing.*;
import java.awt.*;

public class PhoneViewModern implements IPhoneView {
    JFrame frame;
    JLabel backgroundLabel;

    JButton btnPhone, btnHangUp, btnAction, spaceKey, sendKey, draftKey, shiftKey;
    JButton[] NumberButtons, Keys;

    JPanel containerPanel;
    ButtonClick buttonClickListener;

    JTextArea txtScreen;
    JPanel screenPanel;
    JLabel screenBackground;

    JPanel numericButtonGrid;
    JPanel numericKeyboard;
    JPanel actionButtonPanel;
    JPanel keyboardPanel;
    JPanel keyboardFirstRow;
    JPanel keyboardSecondRow;
    JPanel keyboardThirdRow;
    JPanel keyboardFourthRow;


    final String[]  NumLabels = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "*", "0", "#"};
    final String[]  KeyLabelsFirstRow = {"Q","W","E","R","T","Y","U","I","O","P"};
    final String[]  KeyLabelsSecondRow = {"A","S","D","F","G","H","J","K","L"};
    final String[]  KeyLabelsThirdRow = {"Z","X","C","V","B","N","M"};

    private boolean isTxtScreenVisible = false;
    private boolean isNumPadVisible = false;
    private boolean isKeyboardVisible = false;
    private boolean isInCall = false;

    IPhoneController phoneController;
    IPhoneModel phoneModel;

    public PhoneViewModern(IPhoneModel model, IPhoneController controller) {
        this.phoneModel = model;
        this.phoneController = controller;
        this.phoneModel.RegisterView(this);
        this.buttonClickListener = new ButtonClick(controller);
        InitializeView();
    }

    private void InitializeView() {
        /* set up phone frame */
        ConfigureWindow();

        /* set up buttons */
        ConfigureButtons();

        /* set up screen */
        ConfigureScreen();

        /* set up text screen */
        ConfigureTextScreen();

        backgroundLabel.add(containerPanel);

        frame.add(backgroundLabel);
        frame.pack();
        frame.setVisible(true);
        frame.validate();
        frame.repaint();
        frame.setResizable(false);
    }

    private void ConfigureScreen() {
        screenPanel = new JPanel();
        screenPanel.setLayout(new GridLayout(1,1));

        Image screenBackgroundImage = new ImageIcon(getClass().getResource("/Chromatic color background, backgrounds textures.jpg")).getImage();
        ImageIcon screenBackgroundIcon = new ImageIcon(screenBackgroundImage.getScaledInstance(containerPanel.getWidth(), containerPanel.getHeight(), Image.SCALE_FAST));
        screenBackground = new JLabel();
        screenBackground.setIcon(screenBackgroundIcon);
        screenPanel.add(screenBackground);

        containerPanel.add(screenPanel, BorderLayout.NORTH);
    }

    private void ConfigureTextScreen() {
        txtScreen = new JTextArea(10, 16);
        txtScreen.setFont(new Font("SansSerif", Font.BOLD, 30));
        txtScreen.setEditable(false);
        txtScreen.setLineWrap(true);
    }

    private void ConfigureWindow() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Modern");
        frame.setBackground(Color.black);
        frame.setLayout(new BorderLayout());
        backgroundLabel = new JLabel(new ImageIcon(getClass().getResource("/background_smartphone.png")));
        backgroundLabel.setLayout(null);

        containerPanel = new JPanel();
        containerPanel.setLayout(new BorderLayout());
        containerPanel.setSize(new Dimension(332, 575));
        containerPanel.setLocation(20, 105);
    }

    private void ConfigureButtons() {
        JLabel buttonLabel1;
        //JLabel buttonLabel2;
        //Enumerations.ButtonTag tempButtonLabel = Enumerations.ButtonTag.ONE;

        btnPhone = new JButton();
        btnAction = new JButton();
        btnHangUp = new JButton();

        numericKeyboard = new JPanel();
        numericKeyboard.setLayout(new BoxLayout(numericKeyboard, BoxLayout.Y_AXIS));
        numericKeyboard.setPreferredSize(new Dimension(containerPanel.getWidth(), containerPanel.getHeight()/2));
        numericKeyboard.setBackground(Color.DARK_GRAY);

        /* Phone and HangUp Button */
        /*************************************************************************************************************************************/
        Image ButtonImage = new ImageIcon(getClass().getResource("/Call-Button.fw_.png")).getImage();
        ImageIcon ButtonIcon = new ImageIcon(ButtonImage.getScaledInstance(containerPanel.getWidth()/6, containerPanel.getHeight()/10, Image.SCALE_FAST));
        btnPhone.setIcon(ButtonIcon);
        btnPhone.setContentAreaFilled(false);
        btnPhone.setFocusPainted(false);
        btnPhone.setBorderPainted(false);
        btnPhone.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnPhone.setPreferredSize(new Dimension(containerPanel.getWidth()/6, containerPanel.getHeight()/10));
        btnPhone.setActionCommand(ButtonTag.PHONE.name());
        btnPhone.addActionListener(buttonClickListener);

        ButtonImage = new ImageIcon(getClass().getResource("/hangUp.png")).getImage();
        ButtonIcon = new ImageIcon(ButtonImage.getScaledInstance(containerPanel.getWidth()/6, containerPanel.getHeight()/10, Image.SCALE_FAST));
        btnHangUp.setIcon(ButtonIcon);
        btnHangUp.setContentAreaFilled(false);
        btnHangUp.setFocusPainted(false);
        btnHangUp.setBorderPainted(false);
        btnHangUp.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnHangUp.setPreferredSize(new Dimension(containerPanel.getWidth()/6, containerPanel.getHeight()/10));
        btnHangUp.setActionCommand(ButtonTag.HANGUP.name());
        btnHangUp.addActionListener(buttonClickListener);
        /*************************************************************************************************************************************/

        /* Action Button */
        /*************************************************************************************************************************************/
        ButtonImage = new ImageIcon(getClass().getResource("/homeButton.png")).getImage();
        ButtonIcon = new ImageIcon(ButtonImage.getScaledInstance(70, 70, Image.SCALE_FAST));
        btnAction.setIcon(ButtonIcon);
        btnAction.setContentAreaFilled(false);
        btnAction.setFocusPainted(false);
        btnAction.setBorderPainted(false);
        btnAction.setLocation(170, 700);
        btnAction.setPreferredSize(new Dimension(70, 70));
        btnAction.setActionCommand(ButtonTag.ACTION.name());
        btnAction.addActionListener(buttonClickListener);

        actionButtonPanel = new JPanel();
        actionButtonPanel.setLayout(new GridLayout(1, 1));
        actionButtonPanel.setSize(new Dimension(70, 70));
        actionButtonPanel.setLocation(155, 705);

        actionButtonPanel.add(btnAction);
        backgroundLabel.add(actionButtonPanel);
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
            buttonLabel1.setFont(new Font("SansSerif", Font.BOLD, 45));
            buttonLabel1.setHorizontalAlignment(JLabel.HORIZONTAL);
            NumberButtons[i].setPreferredSize(new Dimension(containerPanel.getWidth()/3, containerPanel.getHeight()/10));

            NumberButtons[i].add(BorderLayout.NORTH,buttonLabel1);
            if(i == 9) {
                NumberButtons[i].setActionCommand("*");
            }
            else if(i == 11) {
                NumberButtons[i].setActionCommand("#");
            }
            else {
                NumberButtons[i].setActionCommand(ButtonTag.values()[i+2].name());
            }
            NumberButtons[i].addActionListener(buttonClickListener);

            numericButtonGrid.add(NumberButtons[i]);
        }

        numericKeyboard.add(numericButtonGrid);
        numericKeyboard.add(btnPhone);

        //containerPanel.add(numericKeyboard, BorderLayout.SOUTH);
        /*************************************************************************************************************************************/

        /* Text Keyboard */
        /*************************************************************************************************************************************/
        spaceKey = new JButton();
        sendKey = new JButton("Send");
        draftKey = new JButton("Draft");
        shiftKey = new JButton("Shift");

        Keys = new JButton[26];

        keyboardPanel = new JPanel();
        keyboardFirstRow = new JPanel();
        keyboardSecondRow = new JPanel();
        keyboardThirdRow = new JPanel();
        keyboardFourthRow = new JPanel();

        keyboardPanel.setLayout(new BoxLayout(keyboardPanel, BoxLayout.Y_AXIS));
        keyboardPanel.setPreferredSize(new Dimension(containerPanel.getWidth(), containerPanel.getHeight()/2));

        // First Row
        keyboardFirstRow.setLayout(new GridLayout(1, 10));
        keyboardFirstRow.setAlignmentX(Component.CENTER_ALIGNMENT);

        for(int i = 0; i < 10; i++) {
            Keys[i] = new JButton(KeyLabelsFirstRow[i]);

            Keys[i].setFont(new Font("SansSerif", Font.BOLD, 25));
            Keys[i].setMargin(new Insets(0,0,0,0));
            Keys[i].setPreferredSize(new Dimension(containerPanel.getWidth()/12, containerPanel.getHeight()/8));
            Keys[i].setActionCommand(KeyLabelsFirstRow[i]);
            Keys[i].addActionListener(buttonClickListener);

            keyboardFirstRow.add(Keys[i]);
        }

        // Second Row
        keyboardSecondRow.setLayout(new GridLayout(1, 9));
        keyboardSecondRow.setAlignmentX(Component.CENTER_ALIGNMENT);

        for(int i = 10; i < 19; i++) {
            Keys[i] = new JButton(KeyLabelsSecondRow[i-10]);

            Keys[i].setFont(new Font("SansSerif", Font.BOLD, 25));
            Keys[i].setMargin(new Insets(0,0,0,0));
            Keys[i].setPreferredSize(new Dimension(containerPanel.getWidth()/10, containerPanel.getHeight()/8));
            Keys[i].setActionCommand(KeyLabelsSecondRow[i-10]);
            Keys[i].addActionListener(buttonClickListener);

            keyboardSecondRow.add(Keys[i]);
        }

        //Third Row
        keyboardThirdRow.setLayout(new GridLayout(1, 8));
        keyboardThirdRow.setAlignmentX(Component.CENTER_ALIGNMENT);

        shiftKey.setFont(new Font("SansSerif", Font.BOLD, 15));
        shiftKey.setMargin(new Insets(0,0,0,0));
        shiftKey.setPreferredSize(new Dimension(containerPanel.getWidth()/6, containerPanel.getHeight()/8));
        shiftKey.setActionCommand(ButtonTag.SHIFT.name());
        shiftKey.addActionListener(buttonClickListener);

        keyboardThirdRow.add(shiftKey);

        for(int i = 19; i < 26; i++) {
            Keys[i] = new JButton(KeyLabelsThirdRow[i-19]);

            Keys[i].setFont(new Font("SansSerif", Font.BOLD, 25));
            Keys[i].setMargin(new Insets(0,0,0,0));
            Keys[i].setPreferredSize(new Dimension(containerPanel.getWidth()/10, containerPanel.getHeight()/8));
            Keys[i].setActionCommand(KeyLabelsThirdRow[i-19]);
            Keys[i].addActionListener(buttonClickListener);

            keyboardThirdRow.add(Keys[i]);
        }

        // Fourth Row
        keyboardFourthRow.setLayout(new GridLayout(1, 3));
        keyboardFourthRow.setAlignmentX(Component.CENTER_ALIGNMENT);

        draftKey.setFont(new Font("SansSerif", Font.BOLD, 30));
        draftKey.setPreferredSize(new Dimension(containerPanel.getWidth()/6, containerPanel.getHeight()/8));
        draftKey.setActionCommand(ButtonTag.DRAFT.name());
        draftKey.addActionListener(buttonClickListener);

        sendKey.setFont(new Font("SansSerif", Font.BOLD, 30));
        sendKey.setPreferredSize(new Dimension(containerPanel.getWidth()/6, containerPanel.getHeight()/8));
        sendKey.setActionCommand(ButtonTag.SEND.name());
        sendKey.addActionListener(buttonClickListener);

        spaceKey.setPreferredSize(new Dimension(containerPanel.getWidth()/4, containerPanel.getHeight()/8));
        spaceKey.setActionCommand(" ");
        spaceKey.addActionListener(buttonClickListener);

        keyboardFourthRow.add(draftKey);
        keyboardFourthRow.add(spaceKey);
        keyboardFourthRow.add(sendKey);

        keyboardPanel.add(keyboardFirstRow);
        keyboardPanel.add(keyboardSecondRow);
        keyboardPanel.add(keyboardThirdRow);
        keyboardPanel.add(keyboardFourthRow);

        //containerPanel.add(keyboardPanel, BorderLayout.SOUTH);
        /*************************************************************************************************************************************/
    }

    @Override
    public void UpdateView() {
        StateTag tempTag = this.phoneModel.GetState().GetStateTag();

        if(tempTag == StateTag.HOME_STATE) {
            txtScreen.setText("");
            ChangeTextScreenVisibility(false);
            ChangeKeyBoardVisibility(false);
            ChangeNumPadVisibility(false);
            Change_Phone_Hangup(false);
        }
        else if(tempTag == StateTag.ENTER_NUMBER_STATE) {
            ChangeNumPadVisibility(true);
            ChangeTextScreenVisibility(true);

            txtScreen.setText(phoneModel.GetNumber());
        }
        else if(tempTag == StateTag.CALL_STATE) {
            Change_Phone_Hangup(true);
            txtScreen.setText("Calling " + phoneModel.GetNumber() + "...");
        }
        else if(tempTag == StateTag.MESSAGE_STATE) {
            txtScreen.setText(phoneModel.GetMessage());
            ChangeKeyBoardVisibility(true);
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
        frame.revalidate();
    }

    private void ChangeNumPadVisibility(boolean visibilityRequest) {
        if(!isNumPadVisible && visibilityRequest) {
            if(isKeyboardVisible) {
                containerPanel.remove(keyboardPanel);
            }
            containerPanel.remove(screenPanel);
            containerPanel.add(numericKeyboard, BorderLayout.SOUTH);
            containerPanel.add(screenPanel, BorderLayout.NORTH);
            isNumPadVisible = true;
        }
        else if(isNumPadVisible && !visibilityRequest) {
            containerPanel.remove(numericKeyboard);
            isNumPadVisible = false;
        }
        containerPanel.repaint();
        frame.revalidate();
    }

    private void ChangeKeyBoardVisibility(boolean visibilityRequest) {
        if(!isKeyboardVisible && visibilityRequest) {
            if(isNumPadVisible) {
                containerPanel.remove(numericKeyboard);
            }
            containerPanel.remove(screenPanel);
            containerPanel.add(keyboardPanel, BorderLayout.SOUTH);
            containerPanel.add(screenPanel, BorderLayout.NORTH);
            isKeyboardVisible = true;
        }
        else if(isKeyboardVisible && !visibilityRequest) {
            containerPanel.remove(keyboardPanel);
            isKeyboardVisible = false;
        }
        containerPanel.repaint();
        frame.revalidate();
    }

    private void Change_Phone_Hangup(boolean toHangUp) {
        if(!isInCall && toHangUp) {
            numericKeyboard.remove(btnPhone);
            numericKeyboard.add(btnHangUp);
            isInCall = true;
        }
        else if(isInCall && !toHangUp) {
            numericKeyboard.remove(btnHangUp);
            numericKeyboard.add(btnPhone);
            isInCall = false;
        }
        numericKeyboard.repaint();
        frame.revalidate();
    }
}
