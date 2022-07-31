package com.templete.board.account.security.util

import java.io.File
import java.nio.charset.Charset
import java.nio.file.Files
import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*


class RSAKey {
    companion object
    {
        @Throws(Exception::class)
        fun readPublicKey(file: File): RSAPublicKey? {
            val key = String(Files.readAllBytes(file.toPath()), Charset.defaultCharset())
            val publicKeyPEM = key
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace(System.lineSeparator().toRegex(), "")
                .replace("-----END PUBLIC KEY-----", "")
            val encoded: ByteArray = Base64.getDecoder().decode(publicKeyPEM)
            val keyFactory: KeyFactory = KeyFactory.getInstance("RSA")
            val keySpec = X509EncodedKeySpec(encoded)
            return keyFactory.generatePublic(keySpec) as RSAPublicKey
        }

        @Throws(java.lang.Exception::class)
        fun readPrivateKey(file: File): RSAPrivateKey? {
            val key = String(Files.readAllBytes(file.toPath()), Charset.defaultCharset())
            val privateKeyPEM = key
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace(System.lineSeparator().toRegex(), "")
                .replace("-----END PRIVATE KEY-----", "")
            val encoded: ByteArray = Base64.getDecoder().decode(privateKeyPEM)
            val keyFactory = KeyFactory.getInstance("RSA")
            val keySpec = PKCS8EncodedKeySpec(encoded)
            return keyFactory.generatePrivate(keySpec) as RSAPrivateKey
        }
    }
}