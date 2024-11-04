package org.example.interfaces

interface CrudService<E> {
    fun add(entity: E): ULong
    fun delete(id: ULong)
    fun edit(entity: E)
    fun get(id: ULong): List<E>
    fun restore(id: ULong)
    fun read(): List<E>
}