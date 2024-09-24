package com.example.conversiones

import android.animation.Animator
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencias de tiempo
        val etValorTiempo = findViewById<EditText>(R.id.et_valor_tiempo)
        val spinnerOrigenTiempo = findViewById<Spinner>(R.id.spinner_origen_tiempo)
        val spinnerDestinoTiempo = findViewById<Spinner>(R.id.spinner_destino_tiempo)
        val btnConvertirTiempo = findViewById<Button>(R.id.btn_convertir_tiempo)
        val tvResultadoTiempo = findViewById<TextView>(R.id.tv_resultado_tiempo)

        // Referencias de peso
        val etValorPeso = findViewById<EditText>(R.id.et_valor_peso)
        val spinnerOrigenPeso = findViewById<Spinner>(R.id.spinner_origen_peso)
        val spinnerDestinoPeso = findViewById<Spinner>(R.id.spinner_destino_peso)
        val btnConvertirPeso = findViewById<Button>(R.id.btn_convertir_peso)
        val tvResultadoPeso = findViewById<TextView>(R.id.tv_resultado_peso)

        // Referencias de distancia
        val etValorDistancia = findViewById<EditText>(R.id.et_valor_distancia)
        val spinnerOrigenDistancia = findViewById<Spinner>(R.id.spinner_origen_distancia)
        val spinnerDestinoDistancia = findViewById<Spinner>(R.id.spinner_destino_distancia)
        val btnConvertirDistancia = findViewById<Button>(R.id.btn_convertir_distancia)
        val tvResultadoDistancia = findViewById<TextView>(R.id.tv_resultado_distancia)

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

        // Poblar los Spinners de distancia
        val adaptadorDistancia = ArrayAdapter.createFromResource(this, R.array.unidades_distancia, android.R.layout.simple_spinner_item)
        adaptadorDistancia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerOrigenDistancia.adapter = adaptadorDistancia
        spinnerDestinoDistancia.adapter = adaptadorDistancia

        // Acción al presionar el botón de conversión de tiempo
        btnConvertirTiempo.setOnClickListener {
            val valor = etValorTiempo.text.toString().toDoubleOrNull()
            if (valor != null) {
                val unidadOrigen = spinnerOrigenTiempo.selectedItem.toString()
                val unidadDestino = spinnerDestinoTiempo.selectedItem.toString()
                val resultado = convertirTiempo(valor, unidadOrigen, unidadDestino)
                mostrarAnimacion(lottieAnimation, overlay, R.raw.boost, resultado, tvResultadoTiempo)
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
                mostrarAnimacion(lottieAnimation, overlay, R.raw.boost, resultado, tvResultadoPeso)
            } else {
                Toast.makeText(this, "Por favor ingrese un valor válido", Toast.LENGTH_SHORT).show()
            }
        }

        // Acción al presionar el botón de conversión de distancia
        btnConvertirDistancia.setOnClickListener {
            val valor = etValorDistancia.text.toString().toDoubleOrNull()
            if (valor != null) {
                val unidadOrigen = spinnerOrigenDistancia.selectedItem.toString()
                val unidadDestino = spinnerDestinoDistancia.selectedItem.toString()
                val resultado = convertirDistancia(valor, unidadOrigen, unidadDestino)
                mostrarAnimacion(lottieAnimation, overlay, R.raw.boost, resultado, tvResultadoDistancia)
            } else {
                Toast.makeText(this, "Por favor ingrese un valor válido", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Función para mostrar la animación, actualizar el resultado y opacar el fondo
    private fun mostrarAnimacion(
        lottie: LottieAnimationView,
        overlay: FrameLayout,
        animRes: Int,
        resultado: Double,
        resultadoView: TextView
    ) {
        lottie.setAnimation(animRes)
        overlay.visibility = View.VISIBLE
        lottie.visibility = View.VISIBLE
        lottie.playAnimation()

        lottie.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}

            override fun onAnimationEnd(animation: Animator) {
                overlay.visibility = View.GONE
                lottie.visibility = View.GONE
                // Mostrar el resultado en el TextView correspondiente
                resultadoView.text = "Resultado: $resultado"
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

    // Función para la conversión de distancia
    fun convertirDistancia(valor: Double, deUnidad: String, aUnidad: String): Double {
        return when (deUnidad) {
            "Centímetros" -> when (aUnidad) {
                "Metros" -> valor / 100
                "Kilómetros" -> valor / 100000
                "Pulgadas" -> valor / 2.54
                "Pies" -> valor / 30.48
                "Yardas" -> valor / 91.44
                "Millas" -> valor / 160934.4
                else -> valor
            }
            "Metros" -> when (aUnidad) {
                "Centímetros" -> valor * 100
                "Kilómetros" -> valor / 1000
                "Pulgadas" -> valor * 39.3701
                "Pies" -> valor * 3.28084
                "Yardas" -> valor * 1.09361
                "Millas" -> valor / 1609.34
                else -> valor
            }
            "Kilómetros" -> when (aUnidad) {
                "Centímetros" -> valor * 100000
                "Metros" -> valor * 1000
                "Pulgadas" -> valor * 39370.1
                "Pies" -> valor * 3280.84
                "Yardas" -> valor * 1093.61
                "Millas" -> valor * 0.621371
                else -> valor
            }
            "Pulgadas" -> when (aUnidad) {
                "Centímetros" -> valor * 2.54
                "Metros" -> valor / 39.3701
                "Kilómetros" -> valor / 39370.1
                "Pies" -> valor / 12
                "Yardas" -> valor / 36
                "Millas" -> valor / 63360
                else -> valor
            }
            "Pies" -> when (aUnidad) {
                "Centímetros" -> valor * 30.48
                "Metros" -> valor / 3.28084
                "Kilómetros" -> valor / 3280.84
                "Pulgadas" -> valor * 12
                "Yardas" -> valor / 3
                "Millas" -> valor / 5280
                else -> valor
            }
            "Yardas" -> when (aUnidad) {
                "Centímetros" -> valor * 91.44
                "Metros" -> valor / 1.09361
                "Kilómetros" -> valor / 1093.61
                "Pulgadas" -> valor * 36
                "Pies" -> valor * 3
                "Millas" -> valor / 1760
                else -> valor
            }
            "Millas" -> when (aUnidad) {
                "Centímetros" -> valor * 160934.4
                "Metros" -> valor * 1609.34
                "Kilómetros" -> valor * 1.60934
                "Pulgadas" -> valor * 63360
                "Pies" -> valor * 5280
                "Yardas" -> valor * 1760
                else -> valor
            }
            else -> valor
        }
    }
}
