package org.dyson.chat.debug

import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Controller
import java.util.concurrent.ThreadLocalRandom


@Controller
class StockTicksController(private val simpMessagingTemplate: SimpMessagingTemplate) {
    @Scheduled(fixedRate = 3000)
    fun sendTicks() {
        simpMessagingTemplate.convertAndSend("/topic/ticks", stockTicks)
    }

    private val stockTicks: Map<String, Int>
        get() {
            val ticks: MutableMap<String, Int> = HashMap()
            ticks["AAPL"] = randomTick
            ticks["GOOGL"] = randomTick
            ticks["MSFT"] = randomTick
            ticks["TSLA"] = randomTick
            ticks["AMZN"] = randomTick
            ticks["HPE"] = randomTick
            return ticks
        }
    private val randomTick: Int
        get() = ThreadLocalRandom.current().nextInt(-100, 100 + 1)
}