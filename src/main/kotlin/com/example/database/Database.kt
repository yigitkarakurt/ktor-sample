package com.example.database

import com.example.database.table.BookTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabase() {
    val dbHost="localhost"
    val dbPort=5432
    val dbName="ktordb"
    val dbUser="postgres"
    val dbPassword=//password here

    Database.connect(
        url = "jdbc:postgresql://$dbHost:$dbPort/$dbName",
        driver = "org.postgresql.Driver",
        user = dbUser,
        password = dbPassword
    )

    transaction {
        addLogger(StdOutSqlLogger)
        SchemaUtils.create(BookTable)
    }
}