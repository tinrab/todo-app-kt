package com.flinect.todoapp

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

data class TaskRecord(
    val id: String,
    val description: String
)

private object TaskTable : Table("tasks") {
    val id = varchar("id", 24).primaryKey()
    val description = text("description")

    fun rowToRecord(row: ResultRow) = TaskRecord(
        id = row[id],
        description = row[description]
    )
}

interface TaskRepository {
    fun insertTask(task: TaskRecord)
    fun readTaskById(id: String): TaskRecord?
}

class TaskRepositoryImpl : TaskRepository {
    fun setUp() {
        transaction {
            SchemaUtils.createMissingTablesAndColumns(TaskTable)
        }
    }

    override fun insertTask(task: TaskRecord) {
        transaction {
            TaskTable.insert {
                it[id] = task.id
                it[description] = task.description
            }
        }
    }

    override fun readTaskById(id: String): TaskRecord? {
        return transaction {
            TaskTable.select { TaskTable.id eq id }
                .map { TaskTable.rowToRecord(it) }
                .firstOrNull()
        }
    }
}
