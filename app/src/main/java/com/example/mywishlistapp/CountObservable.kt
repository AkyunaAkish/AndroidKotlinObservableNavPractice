package com.example.mywishlistapp

interface CounterObserver {
    fun onCountUpdated(count: Int)
}

class CountObservable {
    private val observers = mutableListOf<CounterObserver>()
    private var count = 0

    fun increment() {
        count++
        notifyObservers()
    }

    fun decrement() {
        count--
        notifyObservers()
    }

    fun registerObserver(observer: CounterObserver) {
        if (!observers.contains(observer)) {
            observers.add(observer)
            // Immediately notify new observer of current state
            observer.onCountUpdated(count)
        }
    }

    fun removeObserver(observer: CounterObserver) {
        observers.remove(observer)
    }

    private fun notifyObservers() {
        observers.forEach { observer ->
            observer.onCountUpdated(count)
        }
    }

    fun getCurrentCount(): Int = count
}

class CountDisplayObserver : CounterObserver {
    private var currentCount = 0

    override fun onCountUpdated(count: Int) {
        currentCount = count
        println("Count Display Updated: $currentCount") // For debugging
    }

    fun getCurrentCount(): Int = currentCount
}