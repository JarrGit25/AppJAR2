package com.curso.AppJAR.basedatos.converter

import androidx.room.TypeConverter
import com.curso.AppJAR.basedatos.entity.Empleo
import com.curso.AppJAR.basedatos.entity.Empleo.TipoContrato
import java.util.Date

class Conversor {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = value?.let { Date(it) }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time

    @TypeConverter
    fun fromTipoContrato(value: String?): TipoContrato? = value?.let { TipoContrato.valueOf(it) }

    @TypeConverter
    fun tipoContratoToString(tipo: TipoContrato?): String? = tipo?.name

}