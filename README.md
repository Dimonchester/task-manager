# Менеджер задач



Библиотека для управления задачами на Java, реализующая разные алгоритмы планирования задач, запускаемая в виде CLI-приложения
## Особенности

- 📝 **Управление задачами**: Добавление, просмотр, изменение, удаление, построение зависимостей и обработка задач
- 🚀 **Возможность планирования при помощи Topologial Sort**: Задачи обрабатываются в порядке зависимости
- ⏰ **Возможность планирования по EDF**: Задачи обрабатываются в порядке их сроков выполнения
- 💡 **Возможность планирования при помощи Trie**: Позволяет искать задачи по заголовкам
- 📶 **Возможность сортировать по id или по имени**: Позволяет сортировать задачи по этим параметрам
- 🏷️ **Система тегов**: Организация задач с помощью пользовательских тегов
- 📅 **Проверка дат**: Обеспечение правильного формата дат (YYYY-MM-DD)
- 🔄 **Сохранение состояния**: Задачи остаются доступными до их обработки

## Начало работы

### Требования

- Java JDK 17 или новее
- Gradle 7.0+

### Установка

```bash
git clone https://github.com/Dimonchester/task-manager.git
cd task-manager
./gradlew build
```

### Запуск приложения
```bash
./gradlew run
```

## Использование

### Главное меню
```
--- Main Menu ---
1. Add new task
2. Find task by ID (cache demo)
3. Update task
4. Delete task
5. Add dependency between tasks
--- Viewing and Algorithms ---
6. Show all tasks
7. Show tasks in execution order (Topological Sort)
8. Show tasks scheduled by EDF (sorted by deadline)
9. Find tasks by title prefix (Trie demo)
10. Find tasks by tag
11. Demo QuickSort: sort by ID
12. Demo QuickSort: sort by Title
13. Exit
Select an option: 
```

## Пример работы

```
--- All Tasks ---
id: 1
title: Design architecture
deadline: 2025-07-01

id: 2
title: Implement core system
deadline: 2025-07-15

id: 3
title: Write tests
deadline: 2025-07-20

id: 4
title: Prepare documentation
deadline: 2025-07-18

id: 10
title: Requirements analysis
deadline: 2025-06-25
```
