package com.study.multi_thread_practice.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Car(
        @Id @GeneratedValue
        var id: Long? = null,
        var manufacturer: String,
        var model: String,
        var type: String
)