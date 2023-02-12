package com.example.common.extension

/*
    Replace item with newValue if it exist in List
 */
fun <T> List<T>.replace(newValue: T, block: (T) -> Boolean): List<T> {
    return map {
        if (block(it)) newValue else it
    }
}