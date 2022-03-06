package com.example.beberagua_lembrete.model

class CalcularIngestaoDiaria {

    private val ML_JOVEM = 40.0
    private val ML_ADULTO = 35.0
    private val ML_IDOSO = 30.0
    private val ML_MAIS_DE_66_ANOS = 25.0

    private var resultadoML = 0.0
    private var resultado_total_ml = 0.0

    fun CalcularTotalMl(peso: Double, idade: Int){ // método que pega o peso e a idade digitado na main actyvity
        if (idade <= 17){
            resultadoML = peso * ML_JOVEM
            resultado_total_ml = resultadoML
        }else if (idade <= 55){
            resultadoML = peso * ML_ADULTO
            resultado_total_ml = resultadoML
        }else if (idade <= 75){
            resultadoML = peso * ML_IDOSO
            resultado_total_ml = resultadoML
        }else{
            resultadoML = peso * ML_MAIS_DE_66_ANOS
            resultado_total_ml = resultadoML
        }
    }

    fun ResultadoML(): Double{
        return resultado_total_ml // passando o resultado para poder ser usado pelo edit text que vai mostrar na tela o resultado
    }
}