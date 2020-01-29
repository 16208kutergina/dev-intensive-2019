package ru.skillbranch.devintensive.models

import androidx.core.text.isDigitsOnly

class Bender(
    var status: Status = Status.NORMAL,
    var question: Question = Question.NAME
) {
    var counterErrors: Int = 0

    fun askQuestion(): String = when (question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIALS -> Question.MATERIALS.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> {
        val aboutValidAnswer = checkValidAnswer(answer)
        val answerToLowerCase = answer.toLowerCase()
        val pair = if (aboutValidAnswer != "OK") {
            notValidAnswer(aboutValidAnswer)
        } else if (question.answers.contains(answerToLowerCase)) {
            trueAnswer()
        } else {
            errorAnswer()
        }
        return pair
    }

    private fun notValidAnswer(aboutValidAnswer: String) =
        "$aboutValidAnswer\n${question.question}" to status.color

    private fun trueAnswer(): Pair<String, Triple<Int, Int, Int>> {
        question = question.nextQuestion()
        return "Отлично - ты справился\n${question.question}" to status.color
    }

    private fun errorAnswer(): Pair<String, Triple<Int, Int, Int>> {
        counterErrors++
        return if (counterErrors == 3) {
            status = Status.NORMAL
            question = Question.NAME
            "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
        } else {
            status = status.nextStatus()
            "Это неправильный ответ\n" +
                    "${question.question}" to status.color
        }
    }

    private fun checkValidAnswer(answer: String): String {
        return when {
            (question == Question.NAME) && answer[0].isLowerCase() ->
                "Имя должно начинаться с заглавной буквы"
            (question == Question.PROFESSION) && answer[0].isUpperCase() ->
                "Профессия должна начинаться со строчной буквы"
            (question == Question.MATERIALS) && answer.contains(Regex("\\d")) ->
                "Материал не должен содержать цифр"
            (question == Question.BDAY) && !answer.isDigitsOnly() ->
                "Год моего рождения должен содержать только цифры"
            (question == Question.SERIAL) && (!answer.isDigitsOnly() || answer.length != 7) ->
                "Серийный номер содержит только цифры, и их 7"
            else -> "OK"
        }
    }


    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus(): Status {
            return if (this.ordinal < values().lastIndex) {
                values()[this.ordinal + 1]
            } else {
                values()[0]
            }
        }
    }

    enum class Question(val question: String, val answers: List<String>) {
        NAME("Как меня зовут?", listOf("бендер", "bender")) {
            override fun nextQuestion(): Question = PROFESSION
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun nextQuestion(): Question = MATERIALS
        },
        MATERIALS("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun nextQuestion(): Question = BDAY
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun nextQuestion(): Question = SERIAL
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun nextQuestion(): Question = IDLE
        },
        IDLE("Отлично - ты справился\nНа этом все, вопросов больше нет", listOf()) {
            override fun nextQuestion(): Question = IDLE
        };

        abstract fun nextQuestion(): Question
    }
}