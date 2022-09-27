package edu.ucne.prestamospersonales.util

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
        return date?.time
    }
}

@ProvidedTypeConverter
class DateConverter {
    @TypeConverter
    fun toString(value: Date) : String = "${value.day}/${value.month}/${value.year}"
    @TypeConverter
    fun toDate(value:String) : Date {
        var cantSeparators = 0
        var day = "0"
        var month= "0"
        var year = "0"
        value.forEach { caracter->
            if(caracter!='/')
            {
                when(cantSeparators){
                    0->{day+=caracter}
                    1->month+=caracter
                    else-> year+=caracter
                }
            }else{
                cantSeparators++
            }
        }
        return Date(year.toInt(),month.toInt(),day.toInt())
    }
}