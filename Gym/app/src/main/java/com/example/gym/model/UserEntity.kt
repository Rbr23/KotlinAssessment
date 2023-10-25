package com.example.gym.model

class UserEntity(
    var cliente: String? = null,
    var data: String? = null,
    var hora: String? = null
) {
    constructor() : this(null, null, null)
}