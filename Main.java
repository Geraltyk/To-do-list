import java.util.List;
import java.util.Scanner;

public class Main {
    private static TaskManager taskManager;
    private static Scanner scanner;

    public static void main(String[] args) {
        taskManager = new TaskManager();
        scanner = new Scanner(System.in);
        System.out.println("--TO-DO-LIST--");
        boolean bom = true;
        
        // Предлагаем загрузить задачи из файла при запуске
        System.out.println("Хотите загрузить задачи из файла? (y/n): ");
        String answer = scanner.nextLine();
        if (answer.equalsIgnoreCase("y")) {
            System.out.println("Введите имя файла (например, tasks.txt): ");
            String fileName = scanner.nextLine();
            taskManager.loadFromFile(fileName);
        }
        
        while (bom) {
            printMenu();
            System.out.println("Выберите действие: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> addTask();
                    case 2 -> deleteTask();
                    case 3 -> markCompl();
                    case 4 -> ShowAllTasks();
                    case 5 -> getTaskId();
                    case 6 -> saveToFile(); // Добавлено: сохранение в файл
                    case 7 -> loadFromFile(); // Добавлено: загрузка из файла
                    case 0 -> {
                        bom = false;
                        System.out.println("Выход.");
                    }
                    default -> System.out.println("Выберите из предложенных действий!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Введите число");
            }
            if (bom) {
                System.out.println("Enter для продолжения");
                scanner.nextLine();
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n=== МЕНЮ ===");
        System.out.println("Всего задач: " + taskManager.getTaskCount());
        System.out.println("1. Добавить новую задачу");
        System.out.println("2. Удалить задачу");
        System.out.println("3. Отметить задачу выполненной");
        System.out.println("4. Показать все задачи");
        System.out.println("5. Найти по ID");
        System.out.println("6. Сохранить задачи в файл"); // Добавлено
        System.out.println("7. Загрузить задачи из файла"); // Добавлено
        System.out.println("0. Выход");
        System.out.println("============");
    }

    private static void addTask() {
        System.out.println("Введите название задачи:");
        String title = scanner.nextLine();
        System.out.println("Введите описание (если требуется):");
        String desc = scanner.nextLine();
        if (title.isEmpty()) {
            System.out.println("ВВЕДИТЕ НАЗВАНИЕ!!!");
            return;
        }
        taskManager.addTask(title, desc);
    }

    private static void deleteTask() {
        if (taskManager.getTaskCount() == 0) {
            System.out.println("Задач нет");
            return;
        }
        System.out.println("Введите ID задачи для удаления:");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            taskManager.deleteTask(id);
        } catch (NumberFormatException e) {
            System.out.println("Введите число");
        }
    }

    private static void markCompl() {
        if (taskManager.getTaskCount() == 0) {
            System.out.println("Задач нет");
            return;
        }
        System.out.println("Введите ID задачи для выполнения:");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            taskManager.markCompl(id);
        } catch (NumberFormatException e) {
            System.out.println("Введите число");
        }
    }

    private static void ShowAllTasks() {
        List<Task> tasks = taskManager.getAllTasks();
        if (tasks.isEmpty()) {
            System.out.println("Список пуст");
            return;
        }
        System.out.println("\n=== Список задач ===");
        for (Task task : tasks) {
            System.out.println(task);
            System.out.println("-------------------");
        }
    }

    private static void getTaskId() {
        if (taskManager.getTaskCount() == 0) {
            System.out.println("Задач нет");
            return;
        }
        System.out.println("Введите ID задачи для ее поиска:");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Task task = taskManager.getTaskId(id);
            if (task != null) {
                System.out.println("\n=== Найденная задача ===");
                System.out.println(task);
            } else {
                System.out.println("Задача с ID " + id + " не найдена");
            }
        } catch (NumberFormatException e) {
            System.out.println("Введите число");
        }
    }

    // Новые методы для работы с файлами
    private static void saveToFile() {
        System.out.println("Введите имя файла для сохранения (например, tasks.txt): ");
        String fileName = scanner.nextLine();
        if (fileName.isEmpty()) {
            fileName = "tasks.txt";
        }
        taskManager.saveToFile(fileName);
    }

    private static void loadFromFile() {
        System.out.println("Введите имя файла для загрузки (например, tasks.txt): ");
        String fileName = scanner.nextLine();
        if (fileName.isEmpty()) {
            fileName = "tasks.txt";
        }
        taskManager.loadFromFile(fileName);
    }
}