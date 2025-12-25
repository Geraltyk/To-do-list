import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class TaskManager {
    private  Map<Integer,Task>tasks;
    public TaskManager(){
        this.tasks=new HashMap<>();
    }
    //Добавление новой задачи
    public void addTask(String title, String desc){
        Task task = new Task(title,desc);
        tasks.put(task.getId(), task);
        System.out.println("Задача ID: "+task.getId()+"Добалвена");
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
}