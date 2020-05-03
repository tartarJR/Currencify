package com.tatar.currencify.data.mapper

interface DataMappable<T> {
    fun asData(): T
}
