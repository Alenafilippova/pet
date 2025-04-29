package com.example.myapplicat

import com.google.gson.annotations.SerializedName

data class Reminder(
    @SerializedName("id")
    val id: Long = 0,
    @SerializedName("description")
    val description: String,
    @SerializedName("dateTime")
    val dateTime: String,
    @SerializedName("petId")
    val petId: Long
)

data class Pet(
    @SerializedName("id")
    val id: Long = 0,
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("breed")
    val breed: String,
    @SerializedName("birthDate")
    val birthDate: String,
    @SerializedName("weight")
    val weight: Double,
    @SerializedName("ownerId")
    val ownerId: Long,
    @SerializedName("reminders")
    val reminders: List<Reminder> = emptyList()
)