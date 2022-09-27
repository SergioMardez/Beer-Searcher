package com.sergiom.data.mappers

interface Mapper<T, R> {
    fun map(input: T): R
}