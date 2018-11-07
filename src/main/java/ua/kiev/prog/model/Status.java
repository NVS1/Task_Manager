package ua.kiev.prog.model;

public enum Status {
    ACTIVE, EXECUTED, EXPIRED;

    @Override
    public String toString() {
        return name();
    }
}
