package com.example.bootapp.data

import com.example.bootapp.domain.BootDayGroupModel

data class BootDayGroup(
    val day: String,
    val bootCount: Int
)

fun BootDayGroup.toDomain(): BootDayGroupModel {
    return BootDayGroupModel(
        count = bootCount,
        date = day,
    )
}