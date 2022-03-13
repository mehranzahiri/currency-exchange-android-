package com.paysera.currencyexchange.notificationcenter

import com.paysera.currencyexchange.utils.SingleLiveEvent

object FireCenter {
     val coroutineException= SingleLiveEvent<Throwable>()
}