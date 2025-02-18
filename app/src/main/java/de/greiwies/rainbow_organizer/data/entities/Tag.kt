package de.greiwies.rainbow_organizer.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "tags", foreignKeys =
[ForeignKey(entity = Book::class,
    childColumns = ["isbns"],
    parentColumns = ["isbn"])
])
data class Tag(
    @PrimaryKey val tagName: String,
    val isbns: List<String>
)
