package com.example

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Person(@Id @GeneratedValue
                  var id: Long = 0,
                  @Convert(converter = EncryptedTextConverter::class)
                  var name: String = "",
                  var age: Int = 0)

@Repository
interface PersonRespository : CrudRepository<Person, Long>

@SpringBootApplication
@EnableJpaRepositories
open class Application {
    private val log: Logger = LoggerFactory.getLogger(Application::class.java)

    @Bean open fun runner(personRespository: PersonRespository) = CommandLineRunner {
        log.info("Starting")
        println(personRespository.findAll())
        personRespository.save(Person(name="Claudemiro", age=28))
        println(personRespository.findAll())
        log.info("Ending")
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}
