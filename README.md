# Проект "TODO List"

## О проекте

Web-приложение для размещения списка задач. Позволяет составлять список дел и закрывать их по мере выполнения.

## Стек технологий

- **Сборка приложения в Maven 3.8**
- **Java 17**
- **Spring Boot 2.7.3**
- **Lombok 1.18.22**
- **Hibernate 5.6.11**
- **HTML 5**
- **Thymeleaf 2.7.3**
- **BOOTSTRAP 4.4.1**

## Требования к окружению

 - **Java 17**
 - **Maven 3.8**
 - **PostgresSQL 14**

## Запуск проекта

- **Создать БД ```create database todo;```**
- **Запустить проект по команде ```mvn spring-boot:run```**

## Взаимодействие с приложением

Главное окно приложения содержит список задач с указаным статусом "Active" или "Done".
Для добавления задачи воспользуемся кнопкой "Add task".

![](images/EmptyTasks.png)
![](images/AllTasks.png)

Заполняем необходимую информацию и по умолчанию ставится статус "Active" и текущее время

![](images/Addtask.png)

Для уточнения полной информации задачи нужно кликнуть на описание

![](images/TaskInfo.png)

Здесь можно сменить статус на "Done"

![](images/TaskInfoWithDone.png)

Редактировать описание задачи

![](images/UpdateTask.png)

Удалить задачу

![](images/DeleteTask.png)

Вкладка "Done tasks"

![](images/MenuDoneTasks.png)

Вкладка "Active tasks"

![](images/MenuActiveTasks.png)

## Контакты

- liana.timirgazina@yandex.ru
- <a href="https://t.me/mymomsaysimcool/" target="_blank">Телеграм</a></h1>
   