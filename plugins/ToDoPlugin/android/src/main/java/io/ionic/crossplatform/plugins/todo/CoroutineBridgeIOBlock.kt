package io.ionic.crossplatform.plugins.todo

interface CoroutineBridgeIOBlock {
    fun execute(): Any?
    fun result(vararg data: Any?)
}
