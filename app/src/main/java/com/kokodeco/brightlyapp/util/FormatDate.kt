package com.kokodeco.brightlyapp.util

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

fun formatDate(originalDate: String): String {
    // OriginalDate is in ISO 8601 format, e.g., "2020-05-20T15:23:01Z"
    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    parser.timeZone = TimeZone.getTimeZone("UTC")
    val formattedDate = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())

    return try {
        val date = parser.parse(originalDate)
        formattedDate.format(date ?: return originalDate)
    } catch (e: Exception) {
        originalDate // Return original date string if parsing fails
    }
}
