import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
public class Task{
    private static int idC=1;
    private int id;
    private String desc;
    private boolean isCompl;
    private LocalDateTime createDate;
    private String title;
    private LocalDate dueDate;
    private Priority priority;
    private String category;

    public enum Priority{
        LOW,MEDIUM,HIGH
    }

    public Task(String title, String desc, String dueDateStr, Priority priority, String category) {
        this.id = idC++;
        this.title = title;
        this.desc = desc;
        this.isCompl = false;
        this.createDate = LocalDateTime.now();
        this.priority = priority;
        this.category = category;
        setDueDate(dueDateStr);
    }

    public Task(int id, String title, String desc, boolean isCompl, String dueDateStr, Priority priority, String category) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.isCompl = isCompl;
        this.createDate = LocalDateTime.now();
        this.priority = priority;
        this.category = category;
        setDueDate(dueDateStr);

        if (id >= idC) {
            idC = id + 1;
        }
    }

    public int getId(){
        return id;
    }
    public String getDesc(){
        return desc;
    }
    public boolean isCompl(){
        return isCompl;
    }
    public LocalDateTime getCreateDate(){
        return createDate;
    }
    public String getTitle(){
        return title;
    }
    public LocalDate getDueDate() { return dueDate; }
    public Priority getPriority() { return priority; }
    public String getCategory() { return category; }

    public void setTitle(String title){
        this.title = title;
    }

    public void setDesc(String desc){
        this.desc = desc;
    }

    public void setCompl(boolean compl){
        this.isCompl = compl;
    }

    public void setPriority(Priority priority) { this.priority = priority; }

    public void setCategory(String category) { this.category = category; }

    public void setDueDate(String dueDateStr) {
        if (dueDateStr != null && !dueDateStr.isEmpty()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                this.dueDate = LocalDate.parse(dueDateStr, formatter);
            } catch (DateTimeParseException e) {
                System.err.println("Неверный формат даты: " + dueDateStr);
                this.dueDate = null;
            }
        } else {
            this.dueDate = null;
        }
    }

    public String getDueDateString() {
        if (dueDate == null) return "не установлен";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return dueDate.format(formatter);
    }

    @Override
    public String toString() {
        DateTimeFormatter form = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String status = isCompl ? "Выполнена" : "Выполняется";
        String dueDateInfo = dueDate != null ?
                "\nСрок выполнения: " + getDueDateString() : "";
        String priorityStr = String.format("\nПриоритет: %s", priority);
        String categoryStr = String.format("\nКатегория: %s", category);

        return String.format("ID: %d" + "\nНазвание: %s" + "\nОписание: %s" + dueDateInfo + priorityStr + categoryStr + "\nСоздано: %s" + "\nСтатус: %s", id, title, desc, createDate.format(form), status);
    }
}
