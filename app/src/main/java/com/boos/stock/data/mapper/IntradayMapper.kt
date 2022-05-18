package com.boos.stock.data.mapper

import com.boos.stock.data.remote.dto.IntradayInfoDto
import com.boos.stock.domain.model.IntradayInfoModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun IntradayInfoDto.toIntradayInfoModel(): IntradayInfoModel {
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())

    return IntradayInfoModel(
        date = LocalDateTime.parse(timestamp, formatter),
        close = close
    )
}

fun IntradayInfoModel.toInfradayInfoDto(): IntradayInfoDto {
    val timestamp = date.toString()
    return IntradayInfoDto(
        timestamp = timestamp,
        close = close
    )
}