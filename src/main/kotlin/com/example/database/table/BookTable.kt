package com.example.database.table

import com.example.model.Book
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

object BookTable : Table("book") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 50)
    val pageNumber = integer("page_number")

    fun getBooks(): List<Book> {
        return transaction {
            BookTable.selectAll().map { it.toBook() }
        }
    }

    fun getBookById(id: Int): Book? {
        return transaction {
            BookTable.selectAll()
                .where {
                    BookTable.id eq id
                }
                .map {
                    it.toBook()
                }
                .firstOrNull()
        }
    }

    fun addBook(book: Book) {
        transaction {
            BookTable.insert {
                it[name] = book.name
                it[pageNumber] = book.pageNumber
            }
        }
    }

    fun deleteById(id: Int) {
        transaction {
            BookTable.deleteWhere { BookTable.id eq id }
        }
    }

    fun updateBook(book: Book) {
        transaction {
            BookTable.update(
                { BookTable.id eq book.id }
            ) {
                it[name] = book.name
                it[pageNumber] = book.pageNumber
            }
        }
    }

    private fun ResultRow.toBook() = Book(
        id = this[id], name = this[name], pageNumber = this[pageNumber]
    )
}