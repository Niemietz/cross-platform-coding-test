package io.ionic.crossplatform.plugins.todo

object Constants {
    object Ionic {
        const val GET_ALL_RESPONSE_KEY = "todos"
        const val GET_ONE_RESPONSE_KEY = "todo"
        const val ID_PARAMETER_KEY = "id"
    }

    object Database {
        const val DATABASE_NAME = "todos.db"
        const val TABLE_NAME = "todos"
        const val ID_COLUMN = "id"
        const val NAME_COLUMN = "name"
        const val DUE_DATE_COLUMN = "dueDate"
        const val DONE_COLUMN = "done"
    }
}