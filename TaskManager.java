import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.io.*;

public class TaskManager {
    private  Map<Integer,Task>tasks;
    public TaskManager(){
        this.tasks=new HashMap<>();
    }
    //Добавление новой задачи
    public void addTask(String title, String desc, String dueDateStr, Task.Priority priority, String category){
        Task task = new Task(title,desc, dueDateStr, priority, category);
        tasks.put(task.getId(), task);
        System.out.println("Задача ID: "+task.getId()+"Добавлена");
    }
    //Удаление задачи
    public boolean deleteTask(int id){
        if(tasks.containsKey(id)){
            tasks.remove(id);
            System.out.println("Задача ID: "+id+"Удалена");
            return true;
        }else{
            System.out.println("Задача ID: "+id+"Не найдена");
            return false;
        }
    }
    //Отметка выполнения задачи
    public boolean markCompl(int id){
        Task task=tasks.get(id);
        if(task!=null){
            task.setCompl(true);
            System.out.println("Задача ID: "+id+"Выполнена");
            return true;
        }else{
            System.out.println("Задача ID: "+id+"Не найдена");
            return false;
        }
    }
    //Поиск всех задач
    public List<Task> getAllTasks(){
        return new ArrayList<>(tasks.values());
    }
    //Поиск задачи по ID
    public Task getTaskId(int id){
        return tasks.get(id);
    }

    public int getTaskCount(){
        return tasks.size();
    }

    //Задачи по статусу
    public List<Task> getTasksByStatus(boolean completed) {
        return tasks.values().stream()
                .filter(task -> task.isCompl() == completed)
                .collect(Collectors.toList());
    }
    //Задачи по приоритету
    public List<Task> getTasksByPriority(Task.Priority priority) {
        return tasks.values().stream()
                .filter(task -> task.getPriority() == priority)
                .collect(Collectors.toList());
    }
    //Задачи по категориям
    public List<Task> getTasksByCategory(String category) {
        return tasks.values().stream()
                .filter(task -> task.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }
    //ортировка по параметрам
    public List<Task> getSortedTasks(int sortType) {
        List<Task> sortedList = new ArrayList<>(tasks.values());
        switch (sortType) {
            case 1: // По дате
                sortedList.sort(Comparator.comparing(Task::getCreateDate));
                break;
            case 2: // По сроку
                sortedList.sort((t1, t2) -> {
                    if (t1.getDueDate() == null && t2.getDueDate() == null) return 0;
                    if (t1.getDueDate() == null) return 1;
                    if (t2.getDueDate() == null) return -1;
                    return t1.getDueDate().compareTo(t2.getDueDate());
                });
                break;
            case 3: // По приоритету
                sortedList.sort(Comparator.comparing(Task::getPriority).reversed());
                break;
            default:
                System.out.println("Неверный тип сортировки");
                break;
        }

        return sortedList;
    }

    //Сохранение задач в файл
    public boolean saveToFile(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))){
            for (Task task : tasks.values()){
                String line = String.format("%d;%s;%s;%b;%s;%s;%s",
                        task.getId(),
                        task.getTitle(),
                        task.getDesc(),
                        task.isCompl(),
                        task.getDueDateString(),
                        task.getPriority().name(),
                        task.getCategory());
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Задачи сохранены в файл: " + filePath);
            return true;
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении в файл: " + e.getMessage());
            return false;
        }
    }
    //Загрузка задач из файла
    public boolean loadFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            tasks.clear();
            String line;
            int loadedCount = 0;

            while ((line = reader.readLine()) != null){
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(";", 4);
                if (parts.length != 4) {
                    System.err.println("Неверный формат строки: " + line);
                    continue;
                }

                try {
                    int id = Integer.parseInt(parts[0]);
                    String title = parts[1];
                    String description = parts[2];
                    boolean isCompleted = Boolean.parseBoolean(parts[3]);
                    String dueDate = parts[4];
                    Task.Priority priority = Task.Priority.valueOf(parts[5]);
                    String category = parts[6];

                    Task task = new Task(id, title, description, isCompleted, dueDate, priority, category);
                    tasks.put(id, task);
                    loadedCount++;
                } catch (NumberFormatException e) {
                    System.err.println("Неверный формат ID в строке: " + line);
                } catch (Exception e) {
                    System.err.println("Ошибка при создании задачи: " + e.getMessage());
                }
            }

            System.out.println("Загружено задач: " + loadedCount + " из файла: " + filePath);
            return true;
        } catch (FileNotFoundException e) {
            System.err.println("Файл не найден: " + filePath);
            return false;
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
            return false;
        }
    }
}