package com.flinect.todoapp

class TaskRepositoryMock : TaskRepository {
    private val tasks: MutableMap<String, TaskRecord> = hashMapOf()

    override fun insertTask(task: TaskRecord) {
        tasks[task.id] = task
    }

    override fun readTaskById(id: String): TaskRecord? {
        return tasks[id]
    }
}
