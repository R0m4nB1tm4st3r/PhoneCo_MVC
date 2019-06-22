package Draft;

public class Draft {
    private String draftMessage;
    private String draftNumber;

    public Draft(String message, String number) {
        this.draftMessage = message;
        this.draftNumber = number;
    }

    public String GetMessage() {
        return this.draftMessage;
    }

    public String GetNumber(){
        return this.draftNumber;
    }

    public void ChangeMessage(String message) {
        this.draftMessage = message;
    }
}
