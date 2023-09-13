package com.example.tipapp2

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tipapp2.ui.theme.TipApp2Theme
import java.text.NumberFormat
import java.time.temporal.TemporalAmount
import kotlin.jvm.internal.Intrinsics.Kotlin
import kotlin.math.round


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipApp2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background

                ) {
                    TipTimeLayout()
                }
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun TipTimeLayoutPreview() {
    TipApp2Theme {
        TipTimeLayout()
    }
}

@Composable
fun TipTimeLayout(){
    var amountInput by remember {mutableStateOf("")}
    var tipInput by remember { mutableStateOf("")}
    var roundUp by remember { mutableStateOf(false)}

    val amount = amountInput.toDoubleOrNull()?: 0.0
    var tipPercent = tipInput.toDoubleOrNull()?: 0.0

    val tip = calculateTip(amount ,tipPercent, roundUp)

    Column(modifier = Modifier
        .padding(40.dp)
        .verticalScroll(rememberScrollState()),
           horizontalAlignment = Alignment.CenterHorizontally,
           verticalArrangement = Arrangement.Center){

        Text(
            text = stringResource(R.string.calculate_tip),
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(alignment = Alignment.Start)
        )
        EditNumberField(label = R.string.bill_amount,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            value = amountInput,
            onValueChange ={ amountInput = it }, modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth())
        EditNumberField(label = R.string.how_was_the_service,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
            value = tipInput,onValueChange ={tipInput = it},
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth())
        RoundTheTipRow(roundUp = roundUp, onRoundUpChanged = {roundUp = it},
            modifier = Modifier.padding(bottom = 32.dp))
        Text(
            text = stringResource(R.string.tip_amount, tip),
            style = MaterialTheme.typography.displaySmall
        )
        Spacer(modifier = Modifier.height(150.dp))
    }
}

private fun calculateTip(amount: Double, tipPercent: Double=15.0, roundUp: Boolean):String {
    var tip = tipPercent/100 * amount
    if(roundUp){
        tip = kotlin.math.ceil(tip)
    }
    return NumberFormat.getCurrencyInstance().format(tip)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNumberField(@StringRes label:Int,
                    keyboardOptions: KeyboardOptions,
                    value: String, onValueChange: (String) -> Unit,
                    modifier: Modifier = Modifier){
                        TextField(
                            value = value,
                            singleLine = true,
                            modifier = modifier,
                            onValueChange = onValueChange,
                            label = { Text(stringResource(label)) },
                            keyboardOptions = keyboardOptions
                        )
}

@Composable
fun RoundTheTipRow(roundUp:Boolean,onRoundUpChanged:(Boolean)->Unit, modifier: Modifier = Modifier){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .size(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
     Text(text = stringResource(R.string.round_up_tip))
     Switch(
         modifier = modifier
             .fillMaxWidth()
             .wrapContentWidth(Alignment.End),
         checked = roundUp,
         onCheckedChange = onRoundUpChanged,
     )
    }
}