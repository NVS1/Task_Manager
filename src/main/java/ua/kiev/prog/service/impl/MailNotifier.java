package ua.kiev.prog.service.impl;

import org.springframework.stereotype.Service;
import ua.kiev.prog.model.Task;
import ua.kiev.prog.service.Notifier;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class MailNotifier implements Notifier {
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);


    @Override
    public void notify(String to, Task task, String name) {
        String message = String.format("Hello %s, complete task - %s !!", name, task.getTitle());
        Sender sender = new Sender(to, message);
        if (task.getNotifyDate()!=null){
            LocalDateTime date = LocalDateTime.from(task.getNotifyDate().toInstant());
            long delay = LocalDateTime.now().until(date, ChronoUnit.MILLIS);
            scheduler.schedule(sender, delay, TimeUnit.MILLISECONDS);
        }
    }
}
