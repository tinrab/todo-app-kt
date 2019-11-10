package com.flinect.todoapp

import org.jetbrains.exposed.sql.Database
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.inject
import org.koin.dsl.module

class Application : KoinComponent {
    private val server by inject<Server>()

    fun run() {
        server.run(8001)
    }
}

fun main() {
    Database.connect(
        "jdbc:postgresql://localhost:26257/todoapp",
        driver = "org.postgresql.Driver",
        user = "root",
        password = ""
    )

    val todoModule = module {
        single { TaskRepositoryImpl() as TaskRepository }
        single { TaskService(get()) }
        single(createdAtStart = true) { Server(get()) }
    }

    startKoin {
        modules(todoModule)
    }

    Application().run()
}
