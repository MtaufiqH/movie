package com.taufiq.movies.schedulers

import io.reactivex.Scheduler

interface SchedulerProviders {
    fun computation() :Scheduler
    fun io() : Scheduler
    fun mainThread() : Scheduler
}