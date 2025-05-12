package io.ionic.crossplatform.plugins.todo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import io.ionic.crossplatform.plugins.todo.Constants.Database.DONE_COLUMN
import io.ionic.crossplatform.plugins.todo.Constants.Database.DUE_DATE_COLUMN
import io.ionic.crossplatform.plugins.todo.Constants.Database.ID_COLUMN
import io.ionic.crossplatform.plugins.todo.Constants.Database.NAME_COLUMN
import io.ionic.crossplatform.plugins.todo.Constants.Database.TABLE_NAME
import io.ionic.crossplatform.plugins.todo.data.ToDoItem

class DataAccessObjectImpl(context: Context, name: String, version: Int) : SQLiteOpenHelper(
    context,
    name,
    null,
    version,
), DataAccessObject {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE $TABLE_NAME (
                $ID_COLUMN INTEGER PRIMARY KEY AUTOINCREMENT,
                $NAME_COLUMN TEXT,
                $DUE_DATE_COLUMN NUMERIC,
                $DONE_COLUMN NUMERIC
            )
            """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) = Unit

    override fun insert(toDoItem: ToDoItem): Int? {
        var newId: Int? = null
        runCatching {
            val values = ContentValues().apply {
                put(NAME_COLUMN, toDoItem.name)
                put(DUE_DATE_COLUMN, toDoItem.dueDate)
                put(DONE_COLUMN, if (toDoItem.done) 1 else 0)
            }
            newId = writableDatabase.insert(TABLE_NAME, null, values).toInt()
        }.onFailure {
            Log.e("*****", "Could not insert todo item $toDoItem")
        }
        return newId
    }

    override fun readAll(): List<ToDoItem> {
        val results = mutableListOf<ToDoItem>()
        runCatching {
            val cursor = readableDatabase.rawQuery(
                """
                    SELECT $ID_COLUMN, $NAME_COLUMN, $DUE_DATE_COLUMN, $DONE_COLUMN
                    FROM $TABLE_NAME
                """.trimIndent(),
                null,
            )
            while (cursor.moveToNext()) {
                results.add(
                    ToDoItem(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getLong(2),
                        cursor.getInt(3) == 1,
                    )
                )
            }
            cursor.close()
        }.onFailure {
            Log.e("*****", "Could not get all todo items")
        }
        return results.toList()
    }

    override fun update(toDoItem: ToDoItem): Boolean {
        var result = false
        runCatching {
            val values = ContentValues().apply {
                put(NAME_COLUMN, toDoItem.name)
                put(DUE_DATE_COLUMN, toDoItem.dueDate)
                put(DONE_COLUMN, if (toDoItem.done) 1 else 0)
            }
            result =
                writableDatabase.update(
                    TABLE_NAME,
                    values,
                    "$ID_COLUMN = ?",
                    arrayOf(toDoItem.id.toString())
                ) > 0
        }.onFailure {
            Log.e("*****", "Could not update todo item $toDoItem")
        }
        return result
    }

    override fun readOne(id: Int): ToDoItem? {
        var result: ToDoItem? = null
        runCatching {
            val cursor = readableDatabase.rawQuery(
                """
                    SELECT $ID_COLUMN, $NAME_COLUMN, $DUE_DATE_COLUMN, $DONE_COLUMN
                    FROM $TABLE_NAME
                    WHERE id = $id
                """.trimMargin(),
                null,
            )
            result = if (cursor.moveToFirst()) {
                val result = ToDoItem(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getLong(2),
                    cursor.getInt(3) == 1,
                )
                cursor.close()
                result
            } else {
                cursor.close()
                null
            }
        }.onFailure {
            Log.e("*****", "Could not get todo item with id $id")
        }

        return result
    }

    override fun delete(id: Int): Boolean {
        var result = false
        runCatching {
            result =
                writableDatabase.delete(
                    TABLE_NAME,
                    "$ID_COLUMN = ?",
                    arrayOf(id.toString()),
                ) > 0
        }.onFailure {
            Log.e("*****", "Could not delete todo item with id $id")
        }
        return result
    }
}