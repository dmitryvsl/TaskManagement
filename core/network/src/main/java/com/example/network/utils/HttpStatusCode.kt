package com.example.network.utils

enum class HttpStatusCode(val code: Int) {

    // 2.x.x
    Ok(200),
    Created(201),

    // 4.x.x
    BadRequest(400),
    Forbidden(403),
    NotAcceptable(406),
    Conflict(409)

}