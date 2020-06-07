package com.techustle.afitest

import com.techustle.afitest.utils.FinanceSeed
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.ExecutionException
import java.util.function.Consumer


@Component
class  ApplicationRestartHandler(@Autowired private  val financeSeed: FinanceSeed ){
    // A context refresh event listener
    @EventListener(ContextRefreshedEvent::class)
    fun contextRefreshedEvent() {
  println("Application restarted")
        financeSeed.insert()
    }

}