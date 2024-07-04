package com.example.plugins

import com.example.database.table.BookTable
import com.example.model.Book
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

val books = mutableListOf(
    Book(1, "The Great Gatsby", 180)
)

fun Application.configureRouting() {
    routing {

        route("/book") {
            get {
                val books = BookTable.getBooks()
                call.respond(books)
            }

            get("/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond("Invalid id")
                    return@get
                }
                val book = BookTable.getBookById(id)
                if (book == null) {
                    call.respond("Book not found")
                } else {
                    call.respond(book)
                }
            }

            post {
                val book = call.receive<Book>()
                BookTable.addBook(book)
                call.respond(BookTable.getBooks())
            }

            delete("/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond("Invalid id")
                    return@delete
                }
                BookTable.deleteById(id)
                call.respond(BookTable.getBooks())
            }

            put {
                val book = call.receive<Book>()
                BookTable.updateBook(book)
                call.respond(BookTable.getBooks())
            }
        }

    }
}
