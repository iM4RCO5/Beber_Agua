package com.example.beberagua_lembrete

import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.inputmethod.InputContentInfoCompat
import com.example.beberagua_lembrete.model.CalcularIngestaoDiaria
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var id_peso: EditText
    private lateinit var id_idade: EditText
    private lateinit var bt_calcular: Button
    private lateinit var txt_resultado_ml: TextView
    private lateinit var ic_redefinir_dados: ImageView
    private lateinit var bt_lembrete: Button
    private lateinit var bt_alarme: Button
    private lateinit var txt_hora: TextView
    private lateinit var txt_minutos: TextView



    private lateinit var calcularIngestaoDiaria: CalcularIngestaoDiaria //está herdando a classe CalcularIngestaoDiaria.kt
    private var resultadoML = 0.0

    lateinit var timePickerDialog: TimePickerDialog
    lateinit var calendario: Calendar
    var horaAtual = 0
    var minutos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar!!.hide()
        IniciarComponentes() // iniciou a classe Iniciar componentes
        calcularIngestaoDiaria = CalcularIngestaoDiaria()//inicia a classe Calcular Ingestao Diaria


        bt_calcular.setOnClickListener {


            if (id_peso.text.toString().isEmpty()) {
                Toast.makeText(this, R.string.toast_informe_peso, Toast.LENGTH_SHORT).show() // verificando se o campo está fazio, se sim imprime a menssagem da variavel toast_informe_peso
            } else if (id_idade.text.toString().isEmpty()) {
                Toast.makeText(this, R.string.toast_informe_idade, Toast.LENGTH_SHORT).show() //verificando se o campo está fazio, se sim imprime a menssagem da variavel toast_informe_idade
            }else{
                val peso = id_peso.text.toString().toDouble() //capturando texto digitado no peso e convertendo de String para Double
                val idade = id_idade.text.toString().toInt() //capturando texto digitado na idade e convertendo de string para Int
                calcularIngestaoDiaria.CalcularTotalMl(peso, idade) // passando a informação peso e idade digitadas para a classe Calcular IngestaoDiaria
                val formatar = NumberFormat.getNumberInstance(Locale("pt","BR")) // convertendo o ponto em virgula de acordo com a localidade
                formatar.isGroupingUsed = false
                resultadoML = calcularIngestaoDiaria.ResultadoML() // recupera o resultado da classe CalcularIngestaoDiaria e armazena na variavel criada aqui em cima resultadoML
                txt_resultado_ml.text = formatar.format(resultadoML) + "" + "ml"
            }
        }

        ic_redefinir_dados.setOnClickListener {
            val alerDialog = AlertDialog.Builder(this) // criando um alerta para o botão de redefinir dados
            alerDialog.setTitle(R.string.dialog_titulo)
                .setMessage(R.string.dialog_desc)
                .setPositiveButton("OK", {dialogInterface, i -> // cria uma caixa de alerta ao clicar no botão redefinir com um botao OK para limpar dados
                    id_peso.setText("")
                    id_idade.setText("") // limpa os dados
                    txt_resultado_ml.text = ""

                })
            alerDialog.setNegativeButton("Cancelar", {dialogInterface, i -> // outro botao cancelar se não quiser limpar os dadsos

            })
            val dialog = alerDialog.create() // cria a caixa de alerta e chama a variavel com o tratamento acima
            dialog.show()
        }

        bt_lembrete.setOnClickListener {
            calendario = Calendar.getInstance() // esse bloco cria uma janela que vai ser acionada pelo botão lembrete, pedindo para você setar o horario e os minutos do alarme
            horaAtual = calendario.get(Calendar.HOUR_OF_DAY)
            minutos = calendario.get(Calendar.MINUTE)
            timePickerDialog = TimePickerDialog(this, {timePicker: TimePicker, hourOfDay: Int, minutes: Int ->
                txt_hora.text = String.format("%02d", hourOfDay)
                txt_minutos.text = String.format("%02d", hourOfDay)
            }, horaAtual, minutos,true)
            timePickerDialog.show()
        }

        bt_alarme.setOnClickListener {
            if (!txt_hora.text.toString().isEmpty()&& !txt_minutos.text.toString().isEmpty()){
                val intent = Intent(AlarmClock.ACTION_SET_ALARM)
                intent.putExtra(AlarmClock.EXTRA_HOUR, txt_hora.text.toString().toInt())
                intent.putExtra(AlarmClock.EXTRA_MINUTES, txt_minutos.text.toString().toInt())
                intent.putExtra(AlarmClock.EXTRA_MESSAGE, getString(R.string.alarme_menssagem))
                startActivity(intent)

                    if (intent.resolveActivity(packageManager)!= null){
                        startActivity(intent)
                    }
            }
        }
    }
        private fun IniciarComponentes() { // capturou as id criadas no ducumento activity main
            id_peso = findViewById(R.id.id_peso)
            id_idade = findViewById(R.id.id_idade)
            bt_calcular = findViewById(R.id.bt_calcular)
            txt_resultado_ml = findViewById(R.id.txt_resultado_ml)
            ic_redefinir_dados = findViewById(R.id.ic_redefinir_dados)
            bt_lembrete = findViewById(R.id.bt_definir_lembrete)
            bt_alarme = findViewById(R.id.bt_definir_alarme)
            txt_hora = findViewById(R.id.txt_hora)
            txt_minutos = findViewById(R.id.txt_minutos)
        }
    }
