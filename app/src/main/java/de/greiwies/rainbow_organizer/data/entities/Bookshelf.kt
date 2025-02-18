package de.greiwies.rainbow_organizer.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "shelves", foreignKeys =
[ForeignKey(entity = Book::class,
    childColumns = ["isbns"],
    parentColumns = ["isbn"])
])
data class Bookshelf(
    @PrimaryKey(autoGenerate = true)
    val shelfId: Int = 0,
    val name: String,
    val isbns: List<Int>,
    val description: String
)
