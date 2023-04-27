
import UIConponents.tableView
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import service2.addCustomer
import service2.login
import service2.registrationUser

val rowSpace = 5.dp
val columnSpace = 5.dp
val rowBasicModifier = Modifier.fillMaxWidth()
val rowBasicArrangement = Arrangement.SpaceAround

enum class PagesList {
    Login, Registration, AdminPage, UserPage
}


class Authentication {
    private var emailField by mutableStateOf("")
    private var usernameField by mutableStateOf("")
    private var passwordField by mutableStateOf("")

    @Composable
    fun loginPage(screenState: MutableState<PagesList>) {
        Column(Modifier.fillMaxWidth()) {
            Row(Modifier.fillMaxHeight().align(Alignment.CenterHorizontally)) {
                Column(
                    Modifier.align(Alignment.CenterVertically),
                    verticalArrangement = Arrangement.SpaceAround
                ) {

                    Row(rowBasicModifier, rowBasicArrangement) {
                        OutlinedTextField(usernameField, { usernameField = it },
                            label = { Text("username") },
                            leadingIcon = { Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null) },
                        )
                    }
                    Spacer(Modifier.height(columnSpace))
                    Row(rowBasicModifier, rowBasicArrangement) {
                        OutlinedTextField(
                            passwordField,
                            { passwordField = it },
                            label = { Text("password") }, visualTransformation = PasswordVisualTransformation()
                        )
                    }
                    Row(Modifier.align(Alignment.CenterHorizontally)) {
                        Button({
                            when (login(usernameField, passwordField)) {
                                true to true -> {
                                    screenState.value = PagesList.AdminPage
                                }

                                true to false -> {
                                    screenState.value = PagesList.UserPage
                                }
                            }
                        }) {
                            Text("Войти")
                        }
                        Spacer(Modifier.width(rowSpace))
                        Button({ screenState.value = PagesList.Registration }) {
                            Text("Зарегистрироваться")
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun registrationPage(screenState: MutableState<PagesList>) {
        Column(Modifier.fillMaxWidth()) {
            Row(Modifier.fillMaxHeight().align(Alignment.CenterHorizontally)) {
                Column(
                    Modifier.align(Alignment.CenterVertically),
                    verticalArrangement = Arrangement.SpaceAround
                ) {

                    Row(rowBasicModifier, rowBasicArrangement) {
                        OutlinedTextField(emailField, { emailField = it },
                            label = { Text("email") },
                            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null) }
                        )
                    }
                    Spacer(Modifier.height(columnSpace))
                    Row(rowBasicModifier, rowBasicArrangement) {
                        OutlinedTextField(
                            usernameField,
                            { usernameField = it },
                            label = { Text("username") },
                            leadingIcon = { Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null) }
                        )
                    }
                    Spacer(Modifier.height(columnSpace))
                    Row(rowBasicModifier, rowBasicArrangement) {
                        OutlinedTextField(
                            passwordField,
                            { passwordField = it },
                            label = { Text("password") }, visualTransformation = PasswordVisualTransformation()
                        )
                    }
                    Row(Modifier.align(Alignment.CenterHorizontally)) {
                        Button({ screenState.value = PagesList.Login }) {
                            Text("Войти")
                        }
                        Spacer(Modifier.width(rowSpace))
                        Button({
                            if (registrationUser(emailField, usernameField, passwordField)) {
                                screenState.value = PagesList.UserPage
                            }
                        }) {
                            Text("Зарегистрироваться")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun adminPage(screenState: MutableState<PagesList>) {
    val size = 10
    val headers = List(size) { "item $it" }
    val tableData = List(50) { List(size) { "tableItem $it" } }
    val weights = List(size) { 1f }

    Column {
        tableView(headers, tableData, weights, 0.9f)
        Button({ screenState.value = PagesList.Login }) {
            Text("Выйти")
        }
    }
}

@Composable
fun userPage(screenState: MutableState<PagesList>) {
    var name by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    Column(Modifier.fillMaxWidth()) {
        Row(Modifier.align(Alignment.CenterHorizontally).fillMaxHeight()) {
            Column(Modifier.align(Alignment.CenterVertically)) {
                OutlinedTextField(name, {name = it}, label = { Text("Наименование заказчика") })
                OutlinedTextField(address, { address = it }, label = { Text("Адрес") })
                OutlinedTextField(phone, { phone = it }, label = { Text("Телефон") })
                Button({
                    addCustomer(name, address, phone)
                }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Text("сохранить")
                }
            }
        }
    }
}