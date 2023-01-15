package com.ganesh.digitaldairy.labour.listing.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ganesh.digitaldairy.AppDestination
import com.ganesh.digitaldairy.ui.theme.Shapes

data class LaborDTO(var name: String, var age: Int, var address: String)

@OptIn(ExperimentalUnitApi::class)
@Composable
fun LabourListing(laborsList1: ArrayList<LaborDTO>, navHostController: NavHostController) {
    val laborsList = remember {
        laborsList1
    }
    LazyColumn(modifier = Modifier.fillMaxWidth()) {

        laborsList.forEach { laborItem ->
            item() {
                LabourListItem(laborItem, { changedName ->
                    //
                    laborItem.name = changedName
                }){ clickedItem ->
                    navHostController.navigate(AppDestination.LABOUR_DETAIL+"/${clickedItem}")
                }
            }
        }
    }
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun LabourListItem(
    laborDTO: LaborDTO,
    onNameChange: (name: String) -> Unit,
    onItemClick: (name:String) ->Unit
) {
    val shape = Shapes.small
    Column {
       val textFieldValue =  remember {
            mutableStateOf(laborDTO.name)
        }
        TextField(value = textFieldValue.value, onValueChange = {
            textFieldValue.value = it
            onNameChange(it)
        })
        Text(
            text = laborDTO.name,
            style = TextStyle(
                color = Color.Blue,
                fontSize = TextUnit(42f, TextUnitType.Sp),
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily.Cursive,
                letterSpacing = TextUnit(0f, TextUnitType.Em)
            ),
            modifier = Modifier
                .padding(8.dp)
                .border(1.dp, MaterialTheme.colors.secondary, shape)
                .background(MaterialTheme.colors.primary, shape)
                .padding(8.dp)
                .clickable(true) {
                    onItemClick(laborDTO.name)
                },

            )
        Spacer(
            modifier = Modifier
                .padding(8.dp)
        )
    }

}
