package de.greiwies.rainbow_organizor.data

import android.app.Application

class BookApplication : Application() {
    lateinit var container: BookContainer

    override fun onCreate() {
        super.onCreate()
        container = BookContainer(this)
    }
}