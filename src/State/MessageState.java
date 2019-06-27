package State;

import Controller.PhoneController;
import Draft.Draft;
import Enumerations.ButtonTag;
import Enumerations.StateTag;
import Model.IPhoneModel;
import Model.PhoneModel;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class MessageState extends IPhoneState {
    private final String[] ValidEntryCommands;
    private final String[] LowerCaseCharacters = {"1@", "2abc", "3def", "4ghi", "5jkl", "6mno", "7pqrs", "8tuv", "9wxyz", "*", "0 "};
    private final String[] UpperCaseCharacters = {"1@", "2ABC", "3DEF", "4GHI", "5JKL", "6MNO", "7PQRS", "8TUV", "9WXYZ", "*", "0 "};

    private boolean isUpperCase = false;
    private int characterIndex = 0;

    private int buttonClicked = 0;
    private String lastButtonClicked = "";
    private Timer buttonClickTimer = new Timer("ButtonClickTimer", false);
    private TimerTask buttonClickTask;
    private boolean timerRunning = false;

    public MessageState() {
        this.currentStateTag = StateTag.MESSAGE_STATE;
        ValidEntryCommands = new String[11];

        for(int i = 0; i < 11; i++) {
            ValidEntryCommands[i] = ButtonTag.values()[i+2].name();
        }
    }

    @Override
    public void HandleState(String command, PhoneModel context) {
        if(!(   command.equals(ButtonTag.PHONE.name()) || command.equals(ButtonTag.HANGUP.name()) || command.equals(ButtonTag.HASH.name()) ||
                command.equals(ButtonTag.SHIFT.name()) || command.equals(ButtonTag.DRAFT.name()) || command.equals(ButtonTag.SEND.name()) || command.equals(ButtonTag.ACTION.name()))) {
            for(int i = 0; i < 11; i++) {
                if(command.equals(ValidEntryCommands[i])) {
                    if(!(lastButtonClicked.equals(command))) {
                        characterIndex = 0;
                    }

                    if(isUpperCase) {
                        HandleButtonClickTimer(command, UpperCaseCharacters[i].charAt(characterIndex), context);
                    }
                    else {
                        HandleButtonClickTimer(command, LowerCaseCharacters[i].charAt(characterIndex), context);
                    }
                    characterIndex = (characterIndex+1) % LowerCaseCharacters[i].length();
                    break;
                }
            }

            if(command.length() == 1) {
                if (isUpperCase) {
                    context.AppendMessageCharacter(command.charAt(0));
                } else {
                    context.AppendMessageCharacter(command.toLowerCase().charAt(0));
                }
            }
        }
        else {
            if(command.equals(ButtonTag.PHONE.name()) || command.equals(ButtonTag.SEND.name())) {
                context.PrintSendingMessage();
                context.DeleteDraft(context.GetNumber());

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        context.currentState = new HomeState();
                        context.ClearMessage();
                        context.ClearNumber();
                        context.NotifyViews();
                    }
                }, 1000);
            }
            else if(command.equals(ButtonTag.HANGUP.name()) || command.equals(ButtonTag.DRAFT.name())) {
                if(currentLoadedDraft == null) {
                    context.SaveDraft(new Draft(context.GetMessage(), context.GetNumber()));
                }
                else {
                    currentLoadedDraft.ChangeMessage(context.GetMessage());
                }
                context.PrintSavingDraftMessage();

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        context.currentState = new HomeState();
                        context.ClearMessage();
                        context.ClearNumber();
                        context.NotifyViews();
                    }
                }, 1000);
            }
            else if(command.equals(ButtonTag.HASH.name()) || command.equals(ButtonTag.SHIFT.name())) {
                isUpperCase = !isUpperCase;
            }
            else if(command.equals(ButtonTag.ACTION.name())) {
                context.DeleteDraft(context.GetNumber());
                context.ClearMessage();
            }
        }
    }

    public void HandleButtonClickTimer(String actionCommand, char character, PhoneModel context) {
        buttonClickTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Timeout!!");
                System.out.println("You clicked the same button " + buttonClicked + " times.");
                buttonClicked = 0;
                characterIndex = 0;
                AbortTimer();
            }
        };

        if((lastButtonClicked.equals(actionCommand)) && timerRunning) {  // if clicked more than one time
            ResetTimer();
            buttonClicked++;

            context.ReplaceLastCharacter(character);
        }
        else {                                                      // if clicked first
            buttonClicked = 1;
            lastButtonClicked = actionCommand;
            characterIndex = 0;

            context.AppendMessageCharacter(character);

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
}
