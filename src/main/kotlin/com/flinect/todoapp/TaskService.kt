package com.flinect.todoapp

import xyz.downgoon.snowflake.Snowflake

class TaskService(private val taskRepository: TaskRepository) {
    private val snowflake: Snowflake = Snowflake(1, 1)

    fun createTask(description: String): Result<Task, String> {
        val cleanDescription = description.trim()
        if (cleanDescription.isEmpty()) {
            return Failure("empty description")
        }

        val task = Task(snowflake.nextId().toString(), cleanDescription)

        try {
            taskRepository.insertTask(
                TaskRecord(
                    task.id,
                    task.description
                )
            )
        } catch (t: Throwable) {
            return Failure(t.localizedMessage)
        }

        return Success(task)
    }

    fun getTask(id: String): Result<Task, String> {
        val task = taskRepository.readTaskById(id)
        return if (task != null) {
            Success(Task(task.id, task.description))
        } else {
            Failure("Not found")
        }
    }
}
