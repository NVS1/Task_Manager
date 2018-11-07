package ua.kiev.prog.model;

public enum Priority {
    LOW, MEDIUM, HIGH;

    @Override
    public String toString() {
        return name();
    }
}
