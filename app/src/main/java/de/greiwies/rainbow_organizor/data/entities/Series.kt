package de.greiwies.rainbow_organizor.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "series",
    foreignKeys =
    [ForeignKey(entity = Book::class,
        childColumns = ["isbns"],
        parentColumns = ["isbn"])
    ])
data class Series(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val isbns: List<Int>,
    val ongoing: Boolean
)
