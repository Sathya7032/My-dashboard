package com.mydashboard.serviceimpl;

import com.mydashboard.entity.Task;
import com.mydashboard.repo.TaskRepository;
import com.mydashboard.service.TaskService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final JavaMailSender mailSender;

    @Override
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Long id, Task task) {
        Task existing = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        existing.setTitle(task.getTitle());
        existing.setDescription(task.getDescription());
        existing.setStatus(task.getStatus());
        existing.setPriority(task.getPriority());
        existing.setNotCompletedReason(task.getNotCompletedReason());
        existing.setDueDate(task.getDueDate());
        return taskRepository.save(existing);
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    /**
     * Runs every day at 9 AM to check due tasks and send reminders
     */
    @Scheduled(cron = "0 20 9 * * ?")
    @Override
    public void sendDueTaskReminders() {
        LocalDateTime now = LocalDateTime.now();
        List<Task> dueTasks = taskRepository.findByDueDate(now.toLocalDate().atStartOfDay());

        for (Task task : dueTasks) {
            sendDueTaskEmail(task, "sathichary581@gmail.com"); // TODO: Replace with task.getAssignedUser().getEmail()
        }
    }

    private void sendDueTaskEmail(Task task, String recipientEmail) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(recipientEmail);
            helper.setSubject("⏰ Task Due Reminder: " + task.getTitle());

            String htmlContent = """
                    <html>
                      <body style="font-family: Arial, sans-serif; color: #333;">
                        <h2 style="color: #2C3E50;">Task Due Reminder</h2>
                        <p><b>Title:</b> %s</p>
                        <p><b>Description:</b> %s</p>
                        <p><b>Priority:</b> %s</p>
                        <p style="color: red;"><b>Due Date:</b> %s</p>
                        <hr>
                        <p style="font-size: 12px; color: #888;">This is an automated reminder from Task Manager Dashboard.</p>
                      </body>
                    </html>
                    """.formatted(
                    task.getTitle(),
                    task.getDescription() != null ? task.getDescription() : "No description",
                    task.getPriority(),
                    task.getDueDate()
            );

            helper.setText(htmlContent, true);
            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send reminder email", e);
        }
    }
}

