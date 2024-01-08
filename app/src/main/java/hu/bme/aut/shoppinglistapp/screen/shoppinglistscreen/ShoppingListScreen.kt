package hu.bme.aut.shoppinglistapp.screen.shoppinglistscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import hu.bme.aut.shoppinglistapp.data.SLItem
import hu.bme.aut.shoppinglistapp.data.ItemCategory
import hu.bme.aut.todoapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListScreen(
    modifier: Modifier = Modifier,
    shoppingViewModel: ShoppingListViewModel = hiltViewModel(),
    onNavigateToSummary: () -> Unit,
    // onNavigateToSummary: (Int, Int) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    val shopList by shoppingViewModel.getAllShoppingList().collectAsState(emptyList())

    var showAddItemDialog by rememberSaveable {
        mutableStateOf(false)
    }
    var itemToEdit: SLItem? by rememberSaveable {
        mutableStateOf(null)
    }

    Column {

        TopAppBar(
            title = {
                Text(stringResource(R.string.shopping_list))
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            ),
            actions = {
                IconButton(onClick = { onNavigateToSummary() }) {
                    Icon(Icons.Filled.Done, null)
                }
                IconButton(onClick = {
                    shoppingViewModel.clearAllItems()
                }) {
                    Icon(Icons.Filled.Delete, null)
                }
                IconButton(onClick = {
                    itemToEdit = null
                    showAddItemDialog = true
                }) {
                    Icon(Icons.Filled.AddCircle, null)
                }
            })

        Column(modifier = modifier.padding(10.dp)) {

            if (showAddItemDialog) {
                AddNewTodoForm(shoppingViewModel,
                    { showAddItemDialog = false },
                    itemToEdit
                )
            }

            if (shopList.isEmpty())
                Text(text = stringResource(R.string.no_items))
            else {
                LazyColumn(modifier = Modifier.fillMaxHeight()) {
                    items(shopList) {
                        TodoCard(todoItem = it,
                            onRemoveItem = { shoppingViewModel.removeItem(it) },
                            onTodoCheckChange = { checkState ->
                                shoppingViewModel.changeItemState(it, checkState)
                            },
                            onEditItem = { editedTodoItem: SLItem ->
                                itemToEdit = editedTodoItem
                                showAddItemDialog = true
                            }
                        )
                    }
                }
            }

        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun AddNewTodoForm(
    shoppingListViewModell: ShoppingListViewModel,
    onDialogDismiss: () -> Unit = {},
    todoToEdit: SLItem? = null
) {
    Dialog(
        onDismissRequest = onDialogDismiss
    ) {
        var title by rememberSaveable {
            mutableStateOf(todoToEdit?.title ?: "")
        }
        var titleValid by rememberSaveable {
            mutableStateOf(true)
        }
        var desc by rememberSaveable {
            mutableStateOf(todoToEdit?.description ?: "")
        }
        var descValid by rememberSaveable {
            mutableStateOf(true)
        }
        var category by rememberSaveable {
            mutableStateOf(ItemCategory.FOOD)
        }
        var price by rememberSaveable {
            mutableStateOf((todoToEdit?.price ?: 0).toString() )
        }
        var priceValid by rememberSaveable {
            mutableStateOf(true)
        }
        var status by rememberSaveable {
            mutableStateOf(
                false
            )
        }

        Column(
            Modifier
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(10.dp)
        ) {


            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = title,
                onValueChange = {
                    title = it
                    titleValid = !title.isEmpty()
                },
                isError = !titleValid,
                supportingText = {
                    if (!titleValid) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(R.string.please_enter_title),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                label = { Text(text = stringResource(R.string.enter_item_here)) }
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = desc,
                onValueChange = {
                    desc = it
                    descValid = !desc.isEmpty()
                },
                isError = !descValid,
                supportingText = {
                    if (!descValid) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(R.string.please_enter_item_description),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                label = { Text(text = stringResource(R.string.enter_description_here)) }
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = price,
                onValueChange = {
                    price = it
                    priceValid = !(price.toFloatOrNull() == null)
                },
                isError = !priceValid,
                supportingText = {
                    if (!priceValid) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(R.string.price_error_message),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                label = { Text(text = stringResource(R.string.enter_price)) }
            )

            /*if (!priceValid) {
                Text(text = "Please enter valid price (numeric)", color = Color.Red)
            }*/
            var spinnerOptions = listOf(stringResource(R.string.food), stringResource(R.string.tech), stringResource(
                R.string.meds
            )
            )
            SpinnerSample(
                spinnerOptions
                , preselected = stringResource(R.string.food), onSelectionChanged = {
                    if (it == spinnerOptions[0]) {
                        category = ItemCategory.FOOD
                    } else if (it == spinnerOptions[2])
                        category = ItemCategory.TECH
                    else
                        category = ItemCategory.MED },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = status, onCheckedChange = {
                    status = it
                })
                Text(text = stringResource(R.string.purchased))
            }

            Row {
                Button(onClick = {
                    titleValid = !title.isEmpty()
                    descValid = !desc.isEmpty()
                    priceValid = !(price.toFloatOrNull() == null)

                    if (titleValid && descValid && priceValid) {
                        if (todoToEdit == null) {
                            shoppingListViewModell.addItemtoList(
                                SLItem(
                                    0,
                                    title,
                                    desc,
                                    price.toFloat(),
                                    status,
                                    category
                                )
                            )
                        } else {
                            var todoEdited = todoToEdit.copy(
                                title = title,
                                description = desc,
                                price = price.toFloat(),
                                status = status,
                                category = category
                            )
                            shoppingListViewModell.editItem(todoEdited)
                        }
                        onDialogDismiss()
                    }

                }) {
                    Text(text = stringResource(R.string.save))
                }

            }
        }
    }
}

@Composable
fun TodoCard(
    todoItem: SLItem,
    onTodoCheckChange: (Boolean) -> Unit = {},
    onRemoveItem: () -> Unit = {},
    onEditItem: (SLItem) -> Unit = {}
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier.padding(5.dp)
    ) {
        var expanded by rememberSaveable {
            mutableStateOf(false)
        }

        Column {
            Row(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = todoItem.category.getIcon()),
                    contentDescription = "Category",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 10.dp)
                )

                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(0.5f)
                ) {
                    Text(todoItem.title, modifier = Modifier.fillMaxWidth(1f))
                    Text("$ " + todoItem.price.toString(), modifier = Modifier.fillMaxWidth(1f))
                    Text(todoItem.description, modifier = Modifier.fillMaxWidth(1f))
                }

                Spacer(modifier = Modifier.width(10.dp))
                Checkbox(
                    checked = todoItem.status,
                    onCheckedChange = { onTodoCheckChange(it) },
                    modifier = Modifier.padding(2.dp)
                )
                // Spacer(modifier = Modifier.width(5.dp))
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete",
                    modifier = Modifier
                        .clickable {
                            onRemoveItem()
                        }
                        .padding(2.dp),
                    tint = Color.Red
                )
                Spacer(modifier = Modifier.width(2.dp))
                Icon(
                    imageVector = Icons.Filled.Build,
                    contentDescription = "Edit",
                    modifier = Modifier
                        .clickable {
                            onEditItem(todoItem)
                        }
                        .padding(2.dp),
                    tint = Color.Blue
                )
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded)
                            Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        contentDescription = if (expanded) {
                            "Less"
                        } else {
                            "More"
                        }
                    )
                }
            }

            if (expanded) {
                Text(
                    text = todoItem.description,
                    style = TextStyle(
                        fontSize = 12.sp,
                    )
                )
                Text(
                    text = todoItem.description,
                    style = TextStyle(
                        fontSize = 12.sp,
                    )
                )
            }
        }
    }
}

@Composable
fun SpinnerSample(
    list: List<String>,
    preselected: String,
    onSelectionChanged: (myData: String) -> Unit, modifier: Modifier = Modifier
) {
    var selected by remember { mutableStateOf(preselected) }
    var expanded by remember { mutableStateOf(false) } // initial value

    OutlinedCard(
        modifier = modifier.clickable {
            expanded = !expanded
        }) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top,
        ) {
            Text(
                text = selected,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
            Icon(Icons.Outlined.ArrowDropDown, null, modifier = Modifier.padding(8.dp))
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }, modifier = Modifier.fillMaxWidth()
            ) {
                list.forEach { listEntry ->
                    DropdownMenuItem(
                        onClick = {
                            selected = listEntry
                            expanded = false
                            onSelectionChanged(selected)
                        },
                        text = {
                            Text(
                                text = listEntry,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.Start)
                            )
                        },
                    )
                }
            }
        }
    }
}
