package com.example.logintrujillocurro

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Enlazamos los componentes de la interfaz
        val etUsuario = findViewById<EditText>(R.id.etUsuario)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegistro = findViewById<Button>(R.id.btnRegistro)
        val tvRecuperar = findViewById<TextView>(R.id.tvRecuperar)

        btnLogin.setOnClickListener {
            val usuarioInput = etUsuario.text.toString().trim()
            val passwordInput = etPassword.text.toString().trim()

            // 2. Control de campos vacíos
            if (usuarioInput.isEmpty() || passwordInput.isEmpty()) {
                toast(getString(R.string.campos_vacios))
            }
            // 3. Acceso con éxito
            else if (RepositorioUsuarios.validar(usuarioInput, passwordInput)) {
                toast(getString(R.string.acceso_correcto))

                val intent = Intent(this, BienvenidaActivity::class.java)
                intent.putExtra("USUARIO_KEY", usuarioInput)
                startActivity(intent)

                etUsuario.text.clear()
                etPassword.text.clear()
            }
            // 4. Usuario que no existe
            else if (!RepositorioUsuarios.existe(usuarioInput)) {
                toast(getString(R.string.usuario_no_registrado))
            }
            // 5. Usuario o contraseña incorrectos
            else {
                toast(getString(R.string.credenciales_incorrectas))
            }
        }

        btnRegistro.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }

        tvRecuperar.setOnClickListener {
            startActivity(Intent(this, RecuperarActivity::class.java))
        }
    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}