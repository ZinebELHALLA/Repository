package com.voicelearn.app.api.responses

import com.google.gson.annotations.SerializedName

data class ResponseRegister(
    @field:SerializedName("msg")
    val msg: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)