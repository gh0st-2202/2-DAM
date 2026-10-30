package com.example.logintrujillocurro

object RepositorioUsuarios {

    private val usuarios = mutableMapOf<String, String>()

    init {
        usuarios["Manolo"] = "123456"
    }

    private fun claveReal(usuario: String): String? =
        usuarios.keys.firstOrNull { it.equals(usuario, ignoreCase = true) }

    fun existe(usuario: String): Boolean = claveReal(usuario) != null

    fun validar(usuario: String, password: String): Boolean {
        val clave = claveReal(usuario) ?: return false
        return usuarios[clave] == password
    }

    fun registrar(usuario: String, password: String): Boolean {
        if (existe(usuario)) return false
        usuarios[usuario] = password
        return true
    }

    fun obtenerPassword(usuario: String): String? {
        val clave = claveReal(usuario) ?: return null
        return usuarios[clave]
    }

    fun actualizarPassword(usuario: String, nuevaPassword: String): Boolean {
        val clave = claveReal(usuario) ?: return false
        usuarios[clave] = nuevaPassword
        return true
    }
}