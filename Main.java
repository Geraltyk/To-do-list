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
                    case 8 -> editTask(); // Добавлено: редактирование
                    case 9 -> showByStatus(); // Добавлено: фильтрация по статусу
                    case 10 -> showByPriority(); // Добавлено: фильтрация по приоритету
                    case 11 -> showByCategory(); // Добавлено: фильтрация по категори
                    case 12 -> sortTasks(); // Добавлено: сортировка
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
        System.out.println("7. Загрузить задачи из файла");
        System.out.println("8. Редактировать задачу"); // Добавлено
        System.out.println("9. Показать задачи по статусу"); // Добавлено
        System.out.println("10. Показать задачи по приоритету"); // Добавлено
        System.out.println("11. Показать задачи по категории"); // Добавлено
        System.out.println("12. Сортировать задачи"); // Добавлено
        System.out.println("0. Выход");
        System.out.println("============");
    }

    private static void addTask() {
        System.out.println("Введите название задачи:");
        String title = scanner.nextLine();
        System.out.println("Введите описание (если требуется):");
        String desc = scanner.nextLine();
        System.out.println("Введите срок выполнения задачи (ДД.ММ.ГГГГ) (если требуется): ");
        String dueDateStr = scanner.nextLine();
        System.out.println("ведите приоритет: (1-низкий 2-средний 3-высокий)");
        int priorityChoice = Integer.parseInt(scanner.nextLine());
        System.out.println("Введите категорию: ");
        String category = scanner.nextLine();
        if (title.isEmpty()) {
            System.out.println("ВВЕДИТЕ НАЗВАНИЕ!!!");
            return;
        }
        Task.Priority priority = switch(priorityChoice) {
            case 1 -> Task.Priority.LOW;
            case 2 -> Task.Priority.MEDIUM;
            case 3 -> Task.Priority.HIGH;
            default -> {
                System.out.println("Неверный приоритет, установлен средний");
                yield Task.Priority.MEDIUM;
            }
        };
        taskManager.addTask(title, desc, dueDateStr, priority, category);
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
    // Новые методы для редактирования
    private static void editTask() {
        if (taskManager.getTaskCount() == 0) {
            System.out.println("Задач нет");
            return;
        }
        System.out.println("Введите ID задачи для редактирования:");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Task task = taskManager.getTaskId(id);
            if (task == null) {
                System.out.println("Задача не найдена");
                return;
            }

            System.out.println("Текущее название: " + task.getTitle());
            System.out.println("Новое название (оставьте пустым для сохранения текущего):");
            String newTitle = scanner.nextLine();
            if (!newTitle.isEmpty()) {
                task.setTitle(newTitle);
            }

            System.out.println("Текущее описание: " + task.getDesc());
            System.out.println("Новое описание (оставьте пустым для сохранения текущего):");
            String newDesc = scanner.nextLine();
            if (!newDesc.isEmpty()) {
                task.setDesc(newDesc);
            }

            System.out.println("Текущий срок выполнения: " + task.getDueDateString());
            System.out.println("Новый срок выполнения (ДД.ММ.ГГГГ или оставьте пустым):");
            String newDueDate = scanner.nextLine();
            if (!newDueDate.isEmpty()) {
                task.setDueDate(newDueDate);
            }

            System.out.println("Текущий приоритет: " + task.getPriority());
            System.out.println("Новый приоритет (1-низкий, 2-средний, 3-высокий):");
            String priorityStr = scanner.nextLine();
            if (!priorityStr.isEmpty()) {
                int priority = Integer.parseInt(priorityStr);
                task.setPriority(Task.Priority.values()[priority - 1]);
            }

            System.out.println("Текущая категория: " + task.getCategory());
            System.out.println("Новая категория (оставьте пустым для сохранения текущей):");
            String newCategory = scanner.nextLine();
            if (!newCategory.isEmpty()) {
                task.setCategory(newCategory);
            }

            System.out.println("Задача обновлена");
        } catch (NumberFormatException e) {
            System.out.println("Введите число");
        }
    }

    private static void showByStatus() {
        System.out.println("Показать задачи: 1-активные, 2-выполненные");
        int choice = Integer.parseInt(scanner.nextLine());
        boolean completed = choice == 2;
        List<Task> tasks = taskManager.getTasksByStatus(completed);
        displayTaskList(tasks);
    }

    private static void showByPriority() {
        System.out.println("Выберите приоритет: 1-низкий, 2-средний, 3-высокий");
        int choice = Integer.parseInt(scanner.nextLine());
        Task.Priority priority = Task.Priority.values()[choice - 1];
        List<Task> tasks = taskManager.getTasksByPriority(priority);
        displayTaskList(tasks);
    }

    private static void showByCategory() {
        System.out.println("Введите категорию:");
        String category = scanner.nextLine();
        List<Task> tasks = taskManager.getTasksByCategory(category);
        displayTaskList(tasks);
    }

    private static void sortTasks() {
        System.out.println("Сортировать по:");
        System.out.println("1. Дата создания");
        System.out.println("2. Срок выполнения");
        System.out.println("3. Приоритету");
        int choice = Integer.parseInt(scanner.nextLine());
        List<Task> tasks = taskManager.getSortedTasks(choice);
        displayTaskList(tasks);
    }

    private static void displayTaskList(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("Задачи не найдены");
            return;
        }
        System.out.println("\n=== Найденные задачи ===");
        for (Task task : tasks) {
            System.out.println(task);
            System.out.println("-------------------");
        }
        System.out.println("Всего найдено: " + tasks.size() + " задач");
    }
}