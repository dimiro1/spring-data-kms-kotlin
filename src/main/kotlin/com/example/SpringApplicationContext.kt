package com.example

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component

/**
 * Intercept the application initialization and set the context in a campanion object
 * So We can access the context anywhere.
 *
 * This is a mess, its is used only to get the encryption bean in the EncryptedTextConverter.
 */
@Component
open class SpringApplicationContext : ApplicationContextAware {

    override fun setApplicationContext(applicationContext: ApplicationContext?) {
        context = applicationContext
    }

    companion object {
        private var context: ApplicationContext? = null

        fun <T> getBean(clazz: Class<T>): T? = context?.getBean(clazz)
    }
}