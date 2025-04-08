import android.util.Log  // Importação correta
import android.widget.Toast
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appaula.ui.theme.AppAulaTheme
import com.google.firebase.firestore.FirebaseFirestore

fun MandarParaOBanco(nome: String, endereco: String, bairro: String, cep: String, cidade: String, estado: String) {

    val db = FirebaseFirestore.getInstance()

    val pessoa = hashMapOf(
        "nome" to nome,
        "endereco" to endereco,
        "bairro" to bairro,
        "cep" to cep,
        "cidade" to cidade,
        "estado" to estado
    )

    db.collection("pessoas")
        .add(pessoa)
        .addOnSuccessListener { documentReference ->
            Log.d("Firebase", "Documento adicionado com ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            Log.e("Firebase", "Erro ao adicionar documento", e)
        }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppAulaTheme {
        var nome by remember { mutableStateOf("") }
        var endereco by remember { mutableStateOf("") }
        var bairro by remember { mutableStateOf("") }
        var cep by remember { mutableStateOf("") }
        var cidade by remember { mutableStateOf("") }
        var estado by remember { mutableStateOf("") }

        MainScreen(
            nome = nome,
            endereco = endereco,
            bairro = bairro,
            cep = cep,
            cidade = cidade,
            estado = estado
        )
    }
}

@Composable
fun MainScreen(
    nome: String,
    endereco: String,
    bairro: String,
    cep: String,
    cidade: String,
    estado: String
) {
    val context = LocalContext.current
    var nomeState by remember { mutableStateOf(nome) }
    var enderecoState by remember { mutableStateOf(endereco) }
    var bairroState by remember { mutableStateOf(bairro) }
    var cepState by remember { mutableStateOf(cep) }
    var cidadeState by remember { mutableStateOf(cidade) }
    var estadoState by remember { mutableStateOf(estado) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = nomeState,
                onValueChange = { nomeState = it },
                label = { Text("Nome") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = enderecoState,
                onValueChange = { enderecoState = it },
                label = { Text("Endereço") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = bairroState,
                onValueChange = { bairroState = it },
                label = { Text("Bairro") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = cepState,
                onValueChange = { cepState = it },
                label = { Text("CEP") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = cidadeState,
                onValueChange = { cidadeState = it },
                label = { Text("Cidade") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = estadoState,
                onValueChange = { estadoState = it },
                label = { Text("Estado") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                if (nomeState.isNotEmpty() && enderecoState.isNotEmpty() && bairroState.isNotEmpty() && cepState.isNotEmpty() && cidadeState.isNotEmpty() && estadoState.isNotEmpty()) {
                    MandarParaOBanco(nomeState, enderecoState, bairroState, cepState, cidadeState, estadoState)
                    Toast.makeText(context, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text("Cadastrar")
            }
        }
    }
}
