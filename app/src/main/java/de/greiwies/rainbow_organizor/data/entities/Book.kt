package de.greiwies.rainbow_organizor.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "Books")
data class Book(
    @PrimaryKey val isbn: Int, //TODO: evtl. eigene Klasse anlegen, um die Infos aus der ISBN bereits rausziehen zu können (bspw. Sprache), Prüfziffer
    val title: String,
    val authors: String,//TODO: in Datenbank integrieren: List<String>,
    val description: String,
    val pageAmount: Int,
    val language: String,
    val genres: String,//TODO: in Datenbank integrieren: List<String>,
    //TODO: val publishDateFormat: LocalDate,
    val edition: String,
    val favorite: Boolean
    ) {
}