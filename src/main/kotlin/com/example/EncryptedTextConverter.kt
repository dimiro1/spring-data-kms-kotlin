package com.example

import org.springframework.security.crypto.encrypt.TextEncryptor
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class EncryptedTextConverter : AttributeConverter<String, String> {

    override fun convertToDatabaseColumn(text: String): String? {
        val encryptor = GlobalApplicationContext.context?.getBean(TextEncryptor::class.java)

        return encryptor?.encrypt(text)
    }

    override fun convertToEntityAttribute(encryptedText: String): String? {
        val encryptor = GlobalApplicationContext.context?.getBean(TextEncryptor::class.java)

        return encryptor?.decrypt(encryptedText)
    }
}