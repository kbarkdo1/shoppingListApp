package hu.bme.aut.shoppinglistapp.screen.summaryscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import hu.bme.aut.shoppinglistapp.data.ItemCategory
import hu.bme.aut.shoppinglistapp.screen.shoppinglistscreen.ShoppingListViewModel
import hu.bme.aut.todoapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SummaryScreen(
    modifier: Modifier = Modifier,
    shoppingListViewModel: ShoppingListViewModel = hiltViewModel(),
    onNavigateToMain: () -> Unit

    ) {
    val categorySummaryList by shoppingListViewModel.categorySummary().collectAsState(emptyList())
    val total by shoppingListViewModel.total().collectAsState(emptyList())


    Column(
        modifier = Modifier.padding(100.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(onClick = { onNavigateToMain() }) {
            Text(text = stringResource(R.string.main))
        }

        LazyColumn(modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            items(total) {
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center) {
                    Text(text =  stringResource(R.string.total) + " $" + total.toFloatArray()[0].toString())
                }
            }
            items(categorySummaryList){
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center) {
                    Text(text = if (it.category == ItemCategory.FOOD) {
                        stringResource(R.string.food)
                    } else if (it.category == ItemCategory.TECH)
                        stringResource(R.string.tech)
                    else stringResource(R.string.meds),
                        modifier = Modifier.fillMaxWidth(0.5f)
                    )
                    Text(text = it.price.toString(),
                        modifier = Modifier.fillMaxWidth(0.5f)
                    )
                }
            }


        }



    }



}