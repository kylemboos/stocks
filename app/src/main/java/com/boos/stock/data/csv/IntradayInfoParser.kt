package com.boos.stock.data.csv

import com.boos.stock.data.mapper.toIntradayInfoModel
import com.boos.stock.data.remote.dto.IntradayInfoDto
import com.boos.stock.domain.model.IntradayInfoModel
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IntradayInfoParser @Inject constructor() : CSVParser<IntradayInfoModel> {

    override suspend fun parse(stream: InputStream): List<IntradayInfoModel> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO) {
            csvReader
                .readAll()
                .drop(1)
                .mapNotNull { line ->
                    val timestamp = line.getOrNull(0)
                    val close = line.getOrNull(4)
                    IntradayInfoDto(
                        timestamp = timestamp ?: return@mapNotNull null,
                        close = close?.toDouble() ?: return@mapNotNull null
                    ).toIntradayInfoModel()
                }
                .filter {
                    val yesterday = LocalDateTime.now().minusDays(1)
                    val filter = when (yesterday.dayOfWeek.value) {
                        6 -> yesterday.minusDays(1)
                        7 -> yesterday.minusDays(2)
                        else -> yesterday
                    }
                    it.date.dayOfMonth == filter.dayOfMonth
                }
                .sortedBy {
                    it.date.hour
                }
                .also {
                    csvReader.close()
                }
        }
    }

}