package com.study.multi_thread_practice.repository

import com.study.multi_thread_practice.entity.Car
import org.springframework.data.jpa.repository.JpaRepository

interface CarRepository : JpaRepository<Car, Long>