package com.beleavemebe.chrono.repository

import com.beleavemebe.chrono.model.ChronoEntry
import java.text.SimpleDateFormat
import java.util.*

object ChronoRepository {
    val items = listOf(
        ChronoEntry(title = "Watching Serega Pirat Stream", date = parseDate("31.12.2021 23:30")),
        ChronoEntry(title = "Exploring Minestom", date = parseDate("01.01.2022 14:53")),
        ChronoEntry(title = "Programming EPAM course homework", date = parseDate("01.01.2022 18:15")),
        ChronoEntry(title = "Finishing EPAM hw", date = parseDate("02.01.2022 13:00")),
        ChronoEntry(title = "Celebrating christmas with family", date = parseDate("02.01.2022 17:01")),
        ChronoEntry(title = "Watching funny Papich moments", date = parseDate("02.01.2022 22:00")),
        ChronoEntry(title = "Trying to complete challenges from android book", date = parseDate("04.01.2022 19:00")),
        ChronoEntry(title = "Playing Dota 2", date = parseDate("04.01.2022 21:00")),
        ChronoEntry(title = "Working on Chrono", date = parseDate("05.01.2022 15:00")),
//        ChronoEntry(title = "Sample", date = parseDate("01.01.2022 00:00")),
    )

    private fun parseDate(string: String): Date {
        return SimpleDateFormat("dd.MM.yyyy HH:masm", Locale.UK).parse(string)!!
    }
}