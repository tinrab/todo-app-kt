package com.flinect.todoapp

import org.jetbrains.exposed.sql.Database

fun main() {
    Database.connect(
        "jdbc:postgresql://localhost:26257/todoapp",
        driver = "org.postgresql.Driver",
        user = "root",
        password = ""
    )

    val taskRepository = TaskRepositoryImpl()
    taskRepository.setUp()

    val taskService = TaskService(taskRepository)

    val server = Server(taskService)

    server.run(8001)

//    val snowflake = Snowflake(1, 1)
//
//    transaction {
//        addLogger(StdOutSqlLogger)
//
//        SchemaUtils.createMissingTablesAndColumns(com.flinect.todoapp.TaskTable)
//
//        for (i in 1..100) {
//            println(snowflake.nextId().toString())
////            com.flinect.todoapp.TaskTable.insert {
////                it[id] = snowflake.nextId().toString()
////                it[description] = "Task #$i"
////            }
//        }
//        for (row in com.flinect.todoapp.TaskTable.selectAll()) {
//            val task = com.flinect.todoapp.TaskTable.rowToRecord(row)
//
//            println(task)
//        }
//    }
}
