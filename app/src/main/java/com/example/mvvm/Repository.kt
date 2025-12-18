package com.example.mvvm

object Repository {
    var prev = -1

    fun fetchNumber(): Int {
        var value = prev
        do {
            value = (1..100).random()
        } while (value == prev)

        return value
    }
}
