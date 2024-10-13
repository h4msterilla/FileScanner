package nand.exceptions;

public class BadArgsException extends Exception {

    public boolean askForHelp = true;

    public BadArgsException(String message) {
        super(message);
    }

    public BadArgsException(String message, boolean askForHelp) {
        super(message);
        this.askForHelp = askForHelp;
    }

    public BadArgsException(String message, Throwable e) {
        super(message, e);
    }

    public BadArgsException(String message, Throwable e, Boolean askForHelp) {
        super(message, e);
        this.askForHelp = askForHelp;
    }

}
