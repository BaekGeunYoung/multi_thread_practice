package com.study.multi_thread_practice.controller

import com.study.multi_thread_practice.entity.Car
import com.study.multi_thread_practice.service.CarService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.concurrent.CompletableFuture

@RestController
@RequestMapping("/api/car")
class CarsController(@Autowired private val carService: CarService) {
    private final val LOGGER: Logger = LoggerFactory.getLogger(CarsController::class.java)

    @PostMapping("/async")
    fun uploadFileAsync(@RequestParam(value = "files") files: Array<MultipartFile>): ResponseEntity<Unit> {
        return try {
            for(file in files) {
                carService.saveCarsAsync(file)
            }
            ResponseEntity.status(HttpStatus.CREATED).build()
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    @PostMapping("/")
    fun uploadFile(@RequestParam(value = "files") files: Array<MultipartFile>): ResponseEntity<Unit> {
        return try {
            for(file in files) {
                carService.saveCars(file)
            }
            ResponseEntity.status(HttpStatus.CREATED).build()
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    @GetMapping("/")
    fun getAllCars(): CompletableFuture<List<Car>> {
        return carService.getAllCars().thenApply { it }
    }
}