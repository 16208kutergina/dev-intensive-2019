package ru.skillbranch.devintensive.utils

import java.lang.StringBuilder

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val parts: List<String>? = fullName?.split(" ")
        var firstName = parts?.getOrNull(0)
        var lastName = parts?.getOrNull(1)
        if (firstName.isNullOrEmpty()) firstName = null
        if (lastName.isNullOrEmpty()) lastName = null
        return firstName to lastName
    }

    val translit: HashMap<Char, String> = hashMapOf(
        'а' to "a",
        'А' to "A",
        'б' to "b",
        'Б' to "B",
        'в' to "v",
        'В' to "V",
        'г' to "g",
        'Г' to "G",
        'д' to "d",
        'Д' to "D",
        'е' to "e",
        'Е' to "E",
        'ё' to "e",
        'Ё' to "E",
        'ж' to "zh",
        'Ж' to "Zh",
        'з' to "z",
        'З' to "Z",
        'и' to "i",
        'И' to "I",
        'й' to "i",
        'Й' to "I",
        'к' to "k",
        'К' to "K",
        'л' to "l",
        'Л' to "L",
        'м' to "m",
        'М' to "M",
        'н' to "n",
        'Н' to "N",
        'о' to "o",
        'О' to "O",
        'п' to "p",
        'П' to "P",
        'р' to "r",
        'Р' to "R",
        'с' to "s",
        'С' to "S",
        'т' to "t",
        'Т' to "T",
        'у' to "u",
        'У' to "U",
        'ф' to "f",
        'Ф' to "F",
        'х' to "h",
        'Х' to "H",
        'ц' to "c",
        'Ц' to "C",
        'ч' to "ch",
        'Ч' to "Ch",
        'ш' to "sh",
        'Ш' to "Sh",
        'щ' to "sh",
        'Щ' to "Sh",
        'ъ' to "",
        'ы' to "i",
        'ь' to "",
        'э' to "e",
        'Э' to "E",
        'ю' to "yu",
        'Ю' to "Yu",
        'я' to "ya",
        'Я' to "Ya"
    )

    fun transliteration(payload: String?, divider: String = " "): String {
        val split = payload?.split(" ")
        val translitPhrase = StringBuilder()
        if (split != null) {
            for (word in split) {
                for (sign in word) {
                    translitPhrase.append(translit[sign])
                }
                translitPhrase.append(divider)
            }
        }
        translitPhrase.deleteCharAt(translitPhrase.lastIndex)
        return translitPhrase.toString()
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        var initials: String? = ""
        val firstNameTrim = firstName?.trim()
        val lastNameTrim = lastName?.trim()
        if (!firstNameTrim.isNullOrEmpty()) {
            initials += firstNameTrim.first().toTitleCase()
        }
        if (!lastNameTrim.isNullOrEmpty()) {
            initials += lastNameTrim.first().toTitleCase()
        }
        if (initials.isNullOrEmpty()) {
            initials = null
        }
        return initials
    }
}
