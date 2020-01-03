package com.example.stoclsprices

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import yahoofinance.Stock
import yahoofinance.YahooFinance
import java.math.BigDecimal
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.ThreadLocalRandom

@SpringBootApplication
class StoclsPricesApplication

fun main(args: Array<String>) {
    runApplication<StoclsPricesApplication>(*args)
}
@RestController
class RestController{

    @GetMapping(value=["/stocks/{symbol}"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun prices(@PathVariable symbol:String):Flux<StockPrice>{
    return  Flux.interval(Duration.ofSeconds(1))
             .map { StockPrice(symbol, getStockPrice(symbol), LocalDateTime.now())}
    }


    private fun getStockPrice( symbol: String):Double{
       println(YahooFinance.get(symbol).quote.price.toDouble())
        return YahooFinance.get(symbol).quote.price.toDouble()
    }
}
data class StockPrice(val symbol: String, val price:Double ,var time:LocalDateTime)
