package com.flinect.todoapp

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class TaskServiceTest {

    @Test
    fun createTask() {
        val description = "sleep"

        val taskService = TaskService(TaskRepositoryMock())

        val createTaskResult = taskService.createTask(description)
        assert(createTaskResult is Success)
        val task = (createTaskResult as Success).value
        assertNotEquals("", task.id)
        assertNotEquals("", task.description)

        val getTaskResult = taskService.getTask(task.id)
        assert(getTaskResult is Success)
        val returnedTask = (getTaskResult as Success).value
        assertEquals(task.id, returnedTask.id)
        assertEquals(task.description, returnedTask.description)

        assert(taskService.getTask("1") is Failure)
    }

}
