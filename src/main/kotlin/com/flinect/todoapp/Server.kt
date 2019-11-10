package com.flinect.todoapp

import io.javalin.Javalin
import io.javalin.http.Context
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.servlet.http.HttpServletResponse

data class CreateTaskRequest(
    val description: String
)

data class ErrorResponse(
    val message: String,
    val error: Boolean = true
)

class Server(
    private val taskService: TaskService
) {
    private val log: Logger = LoggerFactory.getLogger(Server::class.java)

    fun run(port: Int) {
        val app = Javalin.create().start(port)
        app.get("/tasks/:id", this::handleTasksGet)
        app.post("/tasks", this::handleTasksPost)
    }

    private fun handleTasksGet(ctx: Context) {
        val id = ctx.pathParam("id")

        when (val result = taskService.getTask(id)) {
            is Success -> {
                ctx.json(result.value)
                ctx.status(HttpServletResponse.SC_OK)
            }
            is Failure -> {
                ctx.json(ErrorResponse(result.value))
                ctx.status(HttpServletResponse.SC_BAD_REQUEST)
            }
        }
    }

    private fun handleTasksPost(ctx: Context) {
        val req = ctx.body<CreateTaskRequest>()

        when (val result = taskService.createTask(req.description)) {
            is Success -> {
                log.info("Created a task: {}", result.value)

                ctx.json(result.value)
                ctx.status(HttpServletResponse.SC_OK)
            }
            is Failure -> {
                ctx.json(ErrorResponse(result.value))
                ctx.status(HttpServletResponse.SC_BAD_REQUEST)
            }
        }
    }
}
