package ua.kiev.prog.service;

import ua.kiev.prog.model.Task;

public interface Notifier {
    void notify (String to,  Task task, String name);
}
