package io.ionic.crossplatform.plugins.todo.data

import com.getcapacitor.JSObject
import com.google.gson.Gson

data class ToDoItem(
    val id: Int?,
    val name: String,
    val dueDate: Long,
    val done: Boolean,
) {
    override fun toString(): String =
        Gson().toJson(this)

    fun toJSObject(): JSObject {
        val gson = Gson()
        val jsonStr = gson.toJson(this)
        return JSObject(jsonStr)
    }

    companion object {
        fun fromJSObject(jsonObject: JSObject): ToDoItem {
            val gson = Gson()
            val obj = gson.fromJson(
                jsonObject.toString(),
                ToDoItem::class.java,
            )
            return obj
        }
    }
}