package com.study.multi_thread_practice.service

import com.study.multi_thread_practice.entity.Car
import com.study.multi_thread_practice.repository.CarRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.concurrent.CompletableFuture

@Service
open class CarService(@Autowired private val carRepository: CarRepository) {
    private final val LOGGER: Logger = LoggerFactory.getLogger(CarService::class.java)

    @Async
    open fun saveCarsAsync(file: MultipartFile): CompletableFuture<List<Car>> {
        val startTime = System.currentTimeMillis()
        val cars = parseCSVFile(file)

        LOGGER.info("Saving a list of cars of size ${cars.size}")

        val savedCars = carRepository.saveAll(cars)

        LOGGER.info("Elapsed Time : ${System.currentTimeMillis() - startTime}ms")
        return CompletableFuture.completedFuture(savedCars)
    }

    fun saveCars(file: MultipartFile): List<Car> {
        val startTime = System.currentTimeMillis()
        val cars = parseCSVFile(file)

        LOGGER.info("Saving a list of cars of size ${cars.size}")

        val savedCars = carRepository.saveAll(cars)

        LOGGER.info("Elapsed Time : ${System.currentTimeMillis() - startTime}ms")
        return savedCars
    }

    fun parseCSVFile(file: MultipartFile): List<Car> {
        val cars: MutableList<Car> = mutableListOf()
        try{
            val br = BufferedReader(InputStreamReader(file.inputStream))

            var line: String?
            line = br.readLine()

            while(line != null) {
                val data = line.split(";")
                val car = Car(null, data[0], data[1], data[2])

                cars += car
                line = br.readLine()
            }

            return cars
        } catch (e: IOException) {
            LOGGER.error("Failed to parse CSV file. $e")
            throw Exception("Failed to parse CSV file. $e")
        }
    }

    @Async
    open fun getAllCars(): CompletableFuture<List<Car>> {
        LOGGER.info("Request to get a list of cars")

        val cars = carRepository.findAll()
        return CompletableFuture.completedFuture(cars)
    }
}