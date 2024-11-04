package org.example.service

import org.example.data.Notes
import org.example.interfaces.CrudService
import org.example.data.Comment

object NoteService : CrudService<Notes> {

    private var count = 0uL;
    private var notes = emptyArray<Notes>()

    override fun add(entity: Notes): ULong {
        count++
        notes += entity.copy(id = count)
        return count
    }

    fun add(title: String, text: String): ULong {

        val note = Notes(title = title, text = text)
        return add(note)

    }

    fun addComment(noteID: ULong, message: String): ULong {
        return CommentService.add(noteID, message)
    }

    override fun delete(id: ULong) {

        for ((index, currentNode) in notes.withIndex()) {
            if (currentNode.id == id && !currentNode.deleteMark) {
                notes[index] = currentNode.copy(deleteMark = true)
                deleteComment(nodeId = id)
            }
        }

    }

    fun deleteComment(commentId: ULong = 0uL, nodeId: ULong = 0uL) {
        CommentService.delete(commentId, nodeId)
    }

    override fun get(id: ULong): List<Notes> {
        return notes.filter { it.id == id && !it.deleteMark }
    }

    fun getByID(noteId: ULong): Notes? =
        try {
            get(noteId).last()
        } catch (e: NoSuchElementException) {
            null
        }

    fun get(array: Array<ULong>, offset: Int, count: Int): List<Notes> {
        val list = notes.filter { (it.id in array || array.isEmpty()) && !it.deleteMark }.drop(offset).take(count)
        return list
    }

    fun getComment(noteID: ULong, offset: Int = 0, count: Int = 1): List<Comment>
    {
       return CommentService.get(noteID, offset, count)
    }

    override fun restore(id: ULong) {

    }

    override fun read(): List<Notes> {
        return notes.filter { !it.deleteMark }
    }

    override fun edit(entity: Notes) {

        for ((index, currentNode) in notes.withIndex()) {
            if (currentNode.id == entity.id && !currentNode.deleteMark) {
                notes[index] = entity
            }
        }

    }

    fun editComment(commentId: ULong, ownerId: ULong, message: String)
    {
        CommentService.edit(commentId, ownerId, message)
    }

    fun edit(noteID: ULong, title: String, text: String) {
        val note = Notes(noteID, title, text)
        edit(note)
    }

    fun clear() {
        count = 0uL;
        notes = emptyArray<Notes>()
    }


}

object CommentService : CrudService<Comment> {

    private var count = 0uL;
    private var comments = emptyArray<Comment>()

    override fun add(entity: Comment): ULong {
        count++
        comments += entity.copy(id = count)
        return count
    }

    fun add(noteId: ULong, message: String): ULong {

        val comment = Comment(noteId = noteId, message = message)
        return CommentService.add(comment)

    }

    override fun delete(id: ULong) {

        for ((index, currentComment) in comments.withIndex()) {
            if (currentComment.id == id && !currentComment.deleteMark) {
                comments[index] = currentComment.copy(deleteMark = true)
            }
        }

    }

    fun delete(id: ULong = 0uL, noteID: ULong = 0uL) {

        for ((index, currentComment) in comments.withIndex()) {
            if (((id != 0uL && currentComment.id == id) ||
                        (noteID != 0uL && currentComment.noteId == noteID))
                && !currentComment.deleteMark
            ) {
                comments[index] = currentComment.copy(deleteMark = true)
            }
        }

    }

    override fun edit(entity: Comment) {

        for ((index, currentComment) in comments.withIndex()) {
            if (currentComment.id == entity.id && currentComment.noteId == entity.noteId && !currentComment.deleteMark) {
                comments[index] = entity
            }
        }

    }

    fun edit(commentId: ULong, noteID: ULong, message: String) {
        val comment = Comment(commentId, noteID, message)
        edit(comment)
    }

    override fun get(id: ULong): List<Comment> {
        return comments.filter { it.id == id && !it.deleteMark }
    }

    fun get(noteId: ULong, offset: Int, count: Int): List<Comment> {
        return comments.filter { it.noteId == noteId && it.noteId == noteId && !it.deleteMark }.drop(offset)
            .take(count)
    }

    override fun restore(id: ULong) {

        for ((index, currentNode) in comments.withIndex()) {
            if (currentNode.id == id && currentNode.deleteMark) {
                if (NoteService.getByID(currentNode.noteId) != null) {
                    comments[index] = currentNode.copy(deleteMark = false)
                }

            }
        }
    }

    override fun read(): List<Comment> {
        return comments.filter { !it.deleteMark }
    }

    fun clear() {
        count = 0uL;
        comments = emptyArray<Comment>()
    }

}