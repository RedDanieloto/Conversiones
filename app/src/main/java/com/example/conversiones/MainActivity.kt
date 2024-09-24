package com.example.conversiones

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import android.widget.EditText
import android.widget.Spinner
import android.widget.Button
import android.widget.TextView
import android.widget.ArrayAdapter
import android.widget.Toast

import android.view.View
import android.widget.*

import android.widget.*

import com.airbnb.lottie.LottieAnimationView
import android.animation.Animator

import android.widget.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencias de tiempo
        val etValorTiempo = findViewById<EditText>(R.id.et_valor_tiempo)
        val spinnerOrigenTiempo = findViewById<Spinner>(R.id.spinner_origen_tiempo)
        val spinnerDestinoTiempo = findViewById<Spinner>(R.id.spinner_destino_tiempo)
        val btnConvertirTiempo = findViewById<Button>(R.id.btn_convertir_tiempo)

        // Referencias de peso
        val etValorPeso = findViewById<EditText>(R.id.et_valor_peso)
        val spinnerOrigenPeso = findViewById<Spinner>(R.id.spinner_origen_peso)
        val spinnerDestinoPeso = findViewById<Spinner>(R.id.spinner_destino_peso)
        val btnConvertirPeso = findViewById<Button>(R.id.btn_convertir_peso)

        // Referencias a la animación y la capa de fondo
        val overlay = findViewById<FrameLayout>(R.id.overlay)
        val lottieAnimation = findViewById<LottieAnimationView>(R.id.lottie_animation)

        // Poblar los Spinners de tiempo
        val adaptadorTiempo = ArrayAdapter.createFromResource(this, R.array.unidades_tiempo, android.R.layout.simple_spinner_item)
        adaptadorTiempo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerOrigenTiempo.adapter = adaptadorTiempo
        spinnerDestinoTiempo.adapter = adaptadorTiempo

        // Poblar los Spinners de peso
        val adaptadorPeso = ArrayAdapter.createFromResource(this, R.array.unidades_peso, android.R.layout.simple_spinner_item)
        adaptadorPeso.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerOrigenPeso.adapter = adaptadorPeso
        spinnerDestinoPeso.adapter = adaptadorPeso

        // Acción al presionar el botón de conversión de tiempo
        btnConvertirTiempo.setOnClickListener {
            val valor = etValorTiempo.text.toString().toDoubleOrNull()
            if (valor != null) {
                val unidadOrigen = spinnerOrigenTiempo.selectedItem.toString()
                val unidadDestino = spinnerDestinoTiempo.selectedItem.toString()
                val resultado = convertirTiempo(valor, unidadOrigen, unidadDestino)
                Toast.makeText(this, "Resultado: $resultado", Toast.LENGTH_SHORT).show()

                // Mostrar la animación
                mostrarAnimacion(lottieAnimation, overlay, R.raw.timer)
            } else {
                Toast.makeText(this, "Por favor ingrese un valor válido", Toast.LENGTH_SHORT).show()
            }
        }

        // Acción al presionar el botón de conversión de peso
        btnConvertirPeso.setOnClickListener {
            val valor = etValorPeso.text.toString().toDoubleOrNull()
            if (valor != null) {
                val unidadOrigen = spinnerOrigenPeso.selectedItem.toString()
                val unidadDestino = spinnerDestinoPeso.selectedItem.toString()
                val resultado = convertirPeso(valor, unidadOrigen, unidadDestino)
                Toast.makeText(this, "Resultado: $resultado", Toast.LENGTH_SHORT).show()

                // Mostrar la animación
                mostrarAnimacion(lottieAnimation, overlay, R.raw.peso)
            } else {
                Toast.makeText(this, "Por favor ingrese un valor válido", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Función para mostrar la animación y opacar el fondo
    private fun mostrarAnimacion(lottie: LottieAnimationView, overlay: FrameLayout, animRes: Int) {
        lottie.setAnimation(animRes)
        overlay.visibility = View.VISIBLE
        lottie.visibility = View.VISIBLE
        lottie.playAnimation()

        lottie.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}

            override fun onAnimationEnd(animation: Animator) {
                overlay.visibility = View.GONE
                lottie.visibility = View.GONE
            }

            override fun onAnimationCancel(animation: Animator) {}

            override fun onAnimationRepeat(animation: Animator) {}
        })
    }

    // Función para la conversión de tiempo
    fun convertirTiempo(valor: Double, deUnidad: String, aUnidad: String): Double {
        return when (deUnidad) {
            "Horas" -> when (aUnidad) {
                "Minutos" -> valor * 60
                "Segundos" -> valor * 3600
                else -> valor
            }
            "Minutos" -> when (aUnidad) {
                "Horas" -> valor / 60
                "Segundos" -> valor * 60
                else -> valor
            }
            "Segundos" -> when (aUnidad) {
                "Horas" -> valor / 3600
                "Minutos" -> valor / 60
                else -> valor
            }
            else -> valor
        }
    }

    // Función para la conversión de peso
    fun convertirPeso(valor: Double, deUnidad: String, aUnidad: String): Double {
        return when (deUnidad) {
            "Kilogramos" -> when (aUnidad) {
                "Gramos" -> valor * 1000
                "Libras" -> valor * 2.20462
                else -> valor
            }
            "Gramos" -> when (aUnidad) {
                "Kilogramos" -> valor / 1000
                "Libras" -> valor / 453.592
                else -> valor
            }
            "Libras" -> when (aUnidad) {
                "Kilogramos" -> valor / 2.20462
                "Gramos" -> valor * 453.592
                else -> valor
            }
            else -> valor
        }
    }
}