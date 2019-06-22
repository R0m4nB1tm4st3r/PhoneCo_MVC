package Model;

import Draft.Draft;
import State.HomeState;
import State.IPhoneState;
import View.IPhoneView;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class PhoneModel implements IPhoneModel {
    public IPhoneState currentState;

    private final int MESSAGE_TEXT_WIDTH = 160;
    private final int NUMBER_TEXT_WIDTH = 20;

    private LinkedList<IPhoneView> Views = new LinkedList<>();
    private String messageText = ""; // new char[MESSAGE_TEXT_WIDTH];
    private String numberText = ""; // new char[NUMBER_TEXT_WIDTH];
    private int messageTextIndex = 0;
    private int numberTextIndex = 0;
    private LinkedList<Draft> DraftList = new LinkedList<>();

    private int buttonClicked = 0;
    private String lastButtonClicked = "";
    private Timer buttonClickTimer = new Timer("ButtonClickTimer", false);
    private TimerTask buttonClickTask;
    private boolean timerRunning = false;

    public PhoneModel() {
        currentState = new HomeState();
    }

    @Override
    public void RegisterView(IPhoneView target) {
        this.Views.add(target);
    }

    @Override
    public void NotifyViews() {
        for(IPhoneView view : this.Views) {
            view.UpdateView();
        }
    }

    @Override
    public void UpdateState(String actionCommand) {
        //HandleButtonClickTimer(actionCommand);
        currentState.HandleState(actionCommand, this);

        NotifyViews();
    }

    @Override
    public IPhoneState GetState() {
        return currentState;
    }

    @Override
    public String GetMessage() {
        return messageText;
    }

    @Override
    public String GetNumber() {
        return numberText;
    }

    @Override
    public void ClearMessage() {
        messageText = "";
        messageTextIndex = 0;
    }

    @Override
    public void ClearNumber() {
        numberText = "";
        numberTextIndex = 0;
    }

    public void HandleButtonClickTimer(String actionCommand) {
        buttonClickTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Timeout!!");
                System.out.println("You clicked the same button " + buttonClicked + " times.");
                buttonClicked = 1;
                AbortTimer();
            }
        };

        if((lastButtonClicked == actionCommand) && timerRunning) {
            ResetTimer();
            buttonClicked++;
        }
        else {
            buttonClicked = 1;
            lastButtonClicked = actionCommand;

            if(timerRunning) {
                ResetTimer();

            }
            else {
                buttonClickTimer.schedule(buttonClickTask, 1000);
                timerRunning = true;
            }
        }

        System.out.println(actionCommand);
    }

    private void ResetTimer() {
        AbortTimer();
        buttonClickTimer.schedule(buttonClickTask, 1000);
        timerRunning = true;
    }

    private void AbortTimer() {
        buttonClickTimer.cancel();
        buttonClickTimer = new Timer("ButtonClickTimer");

        timerRunning = false;
    }

    public void AppendNumber(char number) {
        if(numberTextIndex != NUMBER_TEXT_WIDTH) {
            numberText += number;
            numberTextIndex++;
        }
    }

    public void AppendMessageCharacter(char character) {
        if(messageTextIndex != MESSAGE_TEXT_WIDTH) {
            messageText += character;
            messageTextIndex++;
        }
    }
}
