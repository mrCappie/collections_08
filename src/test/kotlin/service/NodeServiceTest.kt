package service

import org.example.service.CommentService
import org.example.service.NoteService
import org.junit.Assert
import org.junit.Test
import org.junit.Before

class NoteServiceTest {

    @Before
    fun setUp() {
        NoteService.clear()
        CommentService.clear()
    }

    @Test
    fun add() {
        NoteService.add("Первая запись", "Какая-то запись о чём-то");
        NoteService.add("Вторая запись", "Какая-то запись о чём-то");
        NoteService.add("Третья запись", "Какая-то запись о чём-то");
        val result = NoteService.add("Четвертая запись", "Какая-то запись о чём-то")
        Assert.assertEquals(result, 4uL)
    }

    @Test
    fun addComment() {
        val id = NoteService.add("Первая запись", "Какая-то запись о чём-то");
        NoteService.addComment(id, "1-й комментарий")
        NoteService.addComment(id, "2-й комментарий")
        val result = NoteService.addComment(id, "3-й комментарий")
        Assert.assertEquals(result, 3uL)
    }

    @Test
    fun delete() {
        NoteService.add("Первая запись", "Какая-то запись о чём-то");
        var id = NoteService.add("Вторая запись", "Какая-то запись о чём-то");
        NoteService.add("Третья запись", "Какая-то запись о чём-то");
        NoteService.delete(id)

        var list = NoteService.read()
        Assert.assertEquals(2, list.size)
    }

    @Test
    fun deleteComment() {
        val id = NoteService.add("Первая запись", "Какая-то запись о чём-то");
        NoteService.addComment(id, "1-й комментарий")
        NoteService.addComment(id, "2-й комментарий")
        NoteService.addComment(id, "3-й комментарий")
        NoteService.deleteComment(2uL)

        var list = CommentService.read()
        Assert.assertEquals(2, list.size)

    }

    @Test
    fun edit() {
        NoteService.add("Первая запись", "Какая-то запись о чём-то");
        NoteService.add("Вторая запись", "Какая-то запись о чём-то");
        val id = NoteService.add("Третья запись", "Какая-то запись о чём-то");
        val newText = "Новая запись о чём-то"
        NoteService.edit(id, "Третья запись", newText)

        Assert.assertEquals(newText, NoteService.getByID(id)!!.text)
    }

    @Test
    fun editComment() {

        val noteId = NoteService.add("Первая запись", "Какая-то запись о чём-то");
        val commentID = NoteService.addComment(noteId, "1-й комментарий")
        NoteService.addComment(noteId, "2-й комментарий")
        NoteService.addComment(noteId, "3-й комментарий")

        val newMessage = "Обновленный 1-й комментарий"
        NoteService.editComment(commentID, noteId, newMessage)

        var list = NoteService.getComment(commentID)
        list.last()

        Assert.assertEquals(newMessage, list.last().message)
    }

    @Test
    fun get() {

        NoteService.add("Первая запись", "Какая-то запись о чём-то");
        val id = NoteService.add("Вторая запись", "Какая-то запись о чём-то");
        NoteService.add("Третья запись", "Какая-то запись о чём-то");

        NoteService.delete(id)

        val list = NoteService.get(emptyArray(), 1, 1)
        Assert.assertEquals("Третья запись", list.last().title)
    }

    @Test
    fun getById() {

        NoteService.add("Первая запись", "Какая-то запись о чём-то");
        val id = NoteService.add("Вторая запись", "Какая-то запись о чём-то");
        NoteService.add("Третья запись", "Какая-то запись о чём-то");

        NoteService.delete(id)

        val note = NoteService.getByID(1uL)
        Assert.assertEquals("Первая запись", note!!.title)
    }

    @Test
    fun getComments() {
        val noteId = NoteService.add("Первая запись", "Какая-то запись о чём-то");
        var id = NoteService.addComment(noteId, "1-й комментарий")
        NoteService.delete(id)
        id = NoteService.addComment(noteId, "2-й комментарий")
        NoteService.delete(id)
        NoteService.addComment(noteId, "3-й комментарий")
        NoteService.addComment(noteId, "4-й комментарий")

        var list = NoteService.getComment(noteId, 0, 5)
        Assert.assertEquals(3, list.size)
        Assert.assertEquals("4-й комментарий", list[0].message)
    }
}