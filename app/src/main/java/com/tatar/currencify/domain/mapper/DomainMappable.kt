package com.tatar.currencify.domain.mapper

interface DomainMappable<T> {
    fun asDomain(): T
}
