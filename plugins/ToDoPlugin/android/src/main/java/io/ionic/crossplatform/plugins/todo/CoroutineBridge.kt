package io.ionic.crossplatform.plugins.todo

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object CoroutineBridge {
    fun fetchDataFromCoroutine(
        ioBlock: CoroutineBridgeIOBlock,
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = ioBlock.execute()
            withContext(Dispatchers.Main) {
                ioBlock.result(
                    result
                )
            }
        }
    }
}