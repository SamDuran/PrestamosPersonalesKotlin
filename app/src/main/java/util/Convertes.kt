package util

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.util.*

@ProvidedTypeConverter
class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}

@ProvidedTypeConverter
class DateConverter {
    @TypeConverter
    fun stringToDate(value: String?): Date? {
        return value?.let {Date(it)}
    }

    @TypeConverter
    fun dateToString(value: Date?): String? {
        return value?.time?.toString()
    }

    fun toString(value: Date?) : String {

        return "${value?.day}/${value?.month}/${value?.year}"
    }
}