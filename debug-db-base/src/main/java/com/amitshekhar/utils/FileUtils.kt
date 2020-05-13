package com.amitshekhar.utils

import android.util.Log
import com.amitshekhar.DebugDB
import java.io.File

object FileUtils {
    @JvmStatic
    fun readBytes(file: File): ByteArray {
        return file.readBytes()
    }

    @JvmStatic
    fun assetsToFile(assets: String, dir: File) {
        dir.forceDelete()
        val list = getList(assets)
        list.forEach {
            val dst = if (it.startsWith("${assets}/")) it.substring(assets.length + 1) else it
            DebugDB.context?.assets?.open(it)?.readBytes()?.let { data ->
                File(dir, dst).apply {
                    if (!parentFile.exists()) {
                        parentFile.mkdirs()
                    }
                    writeBytes(data)
                    Log.i("ASSETS","写入: $this")
                }
            }
        }
    }

    private fun getList(path: String): List<String> {
        val list = ArrayList<String>()
        DebugDB.context?.run {
            assets.list(path)?.forEach {
                val newPath = "$path/$it"
                val l = getList(newPath)
                if (l.isNotEmpty()) {
                    list.addAll(l)
                } else {
                    list.add(newPath)
                }
            }
        }
        return list
    }
}

private fun File.forceDelete() {
    if (isDirectory.not()) {
        delete()
        return
    }
    listFiles()?.forEach {
        it?.forceDelete()
    }
    delete()
}