# Task Manager (Java)
Менеджер задач с ручной реализацией алгоритмов и структур данных.

## Функции
- Создание, удаление, редактирование задач.
- Сортировка (Tim Sort), поиск (бинарный + хеш-таблица).
- Зависимости между задачами (топологическая сортировка).
- Планирование (EDF + приоритетная очередь).

## Сборка
```bash
git clone https://github.com/Dimonchester/task-manager.git
cd task-manager
./gradlew build
./gradlew run
```
# Task Manager with EDF Scheduling

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)

A Java-based task management system that implements the Earliest Deadline First (EDF) scheduling algorithm to prioritize tasks based on their deadlines.

## Features

- 🚀 **EDF Scheduling**: Tasks processed in deadline order
- 📝 **Task Management**: Add, view, and process tasks
- ⏰ **Deadline Tracking**: Prioritization based on due dates
- 🏷️ **Tagging System**: Organize tasks with custom tags
- 📅 **Date Validation**: Ensures correct date format (YYYY-MM-DD)
- 🔄 **Persistent State**: Tasks remain available until processed

## Getting Started

### Prerequisites

- Java JDK 17+
- Gradle 7.0+

### Installation

```bash
git clone https://github.com/Dimonchester/task-manager.git
cd task-manager
./gradlew build
```

### Run Application
```bash
./gradlew run
```

## Usage

### Main Menu
```
Task Manager with EDF Scheduling
--------------------------------
Menu:
1. Add new task
2. Process tasks (EDF order)
3. View all tasks
4. Exit
```

### Adding a Task
1. Select option 1
2. Enter:
    - ID (integer)
    - Title
    - Deadline (YYYY-MM-DD)
    - Description (optional)
    - Tags (comma-separated, optional)

### Processing Tasks
1. Select option 2
2. Tasks display in deadline order
3. Press Enter to process each task

### Viewing All Tasks
1. Select option 3
2. All tasks display in deadline order

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   ├── org.example/Main.java
│   │   ├── algorithm/EDFScheduler.java
│   │   └── model/Task.java
│   └── resources/
build.gradle
```

## Key Classes

| Class           | Description                                                                 |
|-----------------|-----------------------------------------------------------------------------|
| `Task.java`     | Represents a task with ID, title, deadline, description, and tags           |
| `EDFScheduler.java` | Implements task queue using PriorityQueue and EDF scheduling algorithm    |
| `Main.java`     | Handles user interaction, menu system, and application flow                 |

## Example Session

```
Add New Task
------------
Enter task ID: 1
Enter task title: Complete project
Enter deadline (YYYY-MM-DD): 2023-12-15
Enter description (optional): Finish implementation
Add tags (comma separated, optional): urgent,work

Processing Tasks
----------------
ID: 1
Title: Complete project
Deadline: 2023-12-15
Description: Finish implementation
Tags: urgent, work
```

## License

Distributed under the MIT License. See `LICENSE` for more information.