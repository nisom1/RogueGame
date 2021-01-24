package game;

public interface InputObserver {

    abstract boolean observerUpdate(char inputChar);
    // the boolean returns true to remove the observer
}
