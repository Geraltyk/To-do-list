import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.io.*;

public class TaskManager {
    private  Map<Integer,Task>tasks;
    public TaskManager(){
        this.tasks=new HashMap<>();
    }
    //Добавление новой задачи
    public void addTask(String title, String desc){
        Task task = new Task(title,desc);
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

    //Сохранение задач в файл
    public boolean saveToFile(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))){
            for (Task task : tasks.values()){
                String line = String.format("%d;%s;%s;%b",
                    task.getId(),
                    task.getTitle(),
                    task.getDesc(),
                    task.isCompl());
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
                    
                    Task task = new Task(id, title, description, isCompleted);
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