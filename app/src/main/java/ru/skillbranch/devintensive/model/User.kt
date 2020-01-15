package ru.skillbranch.devintensive.model;

import ru.skillbranch.devintensive.utils.Utils
import java.util.Date

data class User(
    val id: String,
    var firstName: String?,
    var lastName: String?,
    var avatar: String? = null,
    var rating: Int = 0,
    var respect: Int = 0,
    val lastVisit: Date? = Date(),
    val isOnline: Boolean = false
) {
    constructor(id: String, firstName: String?, lastName: String?) : this(
        id = id,
        firstName = firstName,
        lastName = lastName,
        avatar = null
    )

    constructor(builder: Builder) : this(
        id = builder.id,
        firstName = builder.firstName,
        lastName = builder.lastName,
        avatar = builder.avatar,
        rating = builder.rating,
        respect = builder.respect,
        lastVisit = builder.lastVisit,
        isOnline = builder.isOnline
    )

    var introBit: String

    constructor(id: String) : this(id, "John", "Doe")

    init {
        introBit = getIntro()
        println(
            "It's Alive!!!\n${if (lastName === "Doe") "His name is $firstName $lastName"
            else "And his name is $firstName $lastName"} \n" +
                    "${getIntro()}"
        )
    }

    private fun getIntro() = """
            ${"\n"}
        """.trimIndent()


    fun printMe() = println(
        """
id: $id 
firstName: $firstName 
lastName: $lastName
avatar: $avatar
respect: $respect
lastVisit: $lastVisit 
isOnline: $isOnline
""".trimIndent()
    )

    companion object Factory {
        private var lastId: Int = -1
        fun makeUser(fullName: String?): User {
            lastId++
            val (firstName, lastName) = Utils.parseFullName(fullName)
            return User(id = "$lastId", firstName = firstName, lastName = lastName)
        }
    }

    public object Builder {
        lateinit var id: String
        var firstName: String? = null
        var lastName: String? = null
        var avatar: String? = null
        var rating: Int = 0
        var respect: Int = 0
        var lastVisit: Date? = Date()
        var isOnline: Boolean = false

        fun id(id: String) = apply { this.id = id }

        fun firstName(firstName: String?) = apply { this.firstName = firstName }

        fun lastName(lastName: String) = apply { this.lastName = lastName }

        fun avatar(avatar: String) = apply { this.avatar = avatar }

        fun rating(rating: Int) = apply { this.rating = rating }

        fun respect(respect: Int) = apply { this.respect = respect }

        fun lastVisit(lastVisit: Date?) = apply { this.lastVisit = lastVisit }

        fun isOnline(isOnline: Boolean) = apply { this.isOnline = isOnline }

        fun build(): User {
            return User(this)
        }
    }
}



