package com.example

import com.amazonaws.services.kms.AWSKMSClient
import com.amazonaws.services.kms.model.DecryptRequest
import com.amazonaws.services.kms.model.EncryptRequest
import com.amazonaws.util.Base64
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.encrypt.TextEncryptor
import org.springframework.stereotype.Component
import java.nio.ByteBuffer

fun ByteBuffer.extractString() = String(array())

@Component
open class KmsTextEncryptor : TextEncryptor {
    private val kms = AWSKMSClient()

    @Value("\${aws.kms.keyId}")
    private var keyId: String = ""


    override fun encrypt(text: String): String {
        val encryptRequest = EncryptRequest().withKeyId(keyId)
                .withPlaintext(ByteBuffer.wrap(text.toByteArray()))

        val encryptedBytes = kms.encrypt(encryptRequest).ciphertextBlob

        return ByteBuffer.wrap(Base64.encode(encryptedBytes.array())).extractString()
    }

    override fun decrypt(encryptedText: String): String {
        val encryptedBytes = ByteBuffer.wrap(Base64.decode(encryptedText.toByteArray()))

        val decryptRequest = DecryptRequest().withCiphertextBlob(encryptedBytes)

        return kms.decrypt(decryptRequest).plaintext.extractString()
    }

}

