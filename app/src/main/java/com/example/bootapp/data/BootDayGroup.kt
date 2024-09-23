package com.example.bootapp.data

import com.example.bootapp.domain.BootDayGroupModel
import com.example.bootapp.domain.BootModel

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