package com.example.models

import org.jetbrains.exposed.sql.Table

object CustomerV2 : Table() {
    val id = integer("id").autoIncrement()
    val firstName = varchar("first_name", 10)
    val lastName = varchar("last_name", 10)

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}