package com.digitaldairy

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.digitaldairy.compose.appcomponents.AppText
import com.digitaldairy.compose.appcomponents.theme.DigitalDairyTheme
import com.digitaldairy.labour.LabourActivity

class MainActivity : ComponentActivity() {
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DigitalDairyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

//val menuItems = listOf(
//    MenuItem("Labour Details", Icons.Default.Groups, Color(0xFF4CAF50)),
//    MenuItem("Schedule", Icons.Default.Event, Color(0xFFFFC107)),
//    MenuItem("Records", Icons.Default.Folder, Color(0xFF4E342E)),
//    MenuItem("Todo", Icons.Default.Checklist, Color(0xFFFF7043))
//)

data class MenuItem(
    val imageVector: ImageVector,
    val menuText: String
)

@Composable
fun Greeting() {
    val context = LocalContext.current

    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // 2 columns
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(
            listOf(
                MenuItem(Icons.Default.Person, "Labour"),
                MenuItem(Icons.Default.DateRange, "Schedule")
            )
        ) { item ->
            MenuItemCard(context as MainActivity, item)
        }
    }

}

@Composable
fun MenuItemCard(activity: MainActivity, menuItem: MenuItem) {
    Card(
        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary),
        modifier = Modifier
            // .weight(1f)
            .height(100.dp)
            .padding(10.dp)
            .background(color = MaterialTheme.colorScheme.secondary)
//            .align(alignment = Alignment.CenterVertically)
            .clickable {
                val intent = Intent(activity, LabourActivity::class.java)
                activity.startActivity(intent)
            }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Image(
                imageVector = menuItem.imageVector, menuItem.menuText
//                        imageVector = Icons.Default.Person ,""
            )
            AppText(
                menuItem.menuText,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                textAlign = TextAlign.Center,
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onSecondary)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DigitalDairyTheme {
        Greeting()
    }
}