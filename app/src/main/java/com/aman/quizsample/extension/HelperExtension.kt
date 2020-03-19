package com.aman.quizsample.extension

inline fun <T> Array<T>.mapInPlace(mutator: (T)->T) {
    this.forEachIndexed { idx, value ->
        mutator(value).let { newValue ->
            if (newValue !== value) this[idx] = mutator(value)
        }
    }
}