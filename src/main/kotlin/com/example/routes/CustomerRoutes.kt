package com.example.routes

import com.example.models.Customer
import com.example.models.customerStorage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.customerRouting() {
    route("/customer") {
        // customer 전체 조회.
        get {
            if (customerStorage.isNotEmpty()) {
                call.respond(customerStorage)
            } else {
                call.respondText(
                    text = "No Customer Found",
                    status = HttpStatusCode.OK
                )
            }
        }
        // id로 customer 조회.
        get("{id}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                text = "Missing Id",
                status = HttpStatusCode.BadRequest
            )
            val customer = customerStorage.find { it.id == id } ?: return@get call.respondText(
                text = "No Customer with id $id",
                status = HttpStatusCode.NotFound
            )
            call.respond(customer)
        }
        // customer 정보 저장.
        post {
            val customer = call.receive<Customer>()
            customerStorage.add(customer)
            call.respondText(
                text = "Customer stored correctly",
                status = HttpStatusCode.Created
            )
        }
        // id로 customer 삭제.
        delete("{id}") {
            val id = call.parameters["id"] ?: return@delete call.respondText(
                text = "Missing Id",
                status = HttpStatusCode.BadRequest
            )
            if (customerStorage.removeIf { it.id == id }) {
                call.respondText(
                    text = "Customer remove correctly",
                    status = HttpStatusCode.Accepted
                )
            } else {
                call.respondText(
                    text = "Not Found",
                    status = HttpStatusCode.NotFound
                )
            }
        }
    }
}