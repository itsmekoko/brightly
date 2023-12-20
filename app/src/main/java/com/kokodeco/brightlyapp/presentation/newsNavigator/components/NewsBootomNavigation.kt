package com.kokodeco.brightlyapp.presentation.newsNavigator.components

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kokodeco.brightlyapp.R

@Composable
fun NewsBottomNavigation(
    items: List<BottomNavigationItem>,
    selectedItem: Int,
    onItemClick: (Int) -> Unit
) {
    val containerBrush = if (isSystemInDarkTheme()) {
        Brush.linearGradient(
            colors = listOf(
                Color(0xFF003366).copy(alpha = 0.5f), // Semi-transparent dark blue
                Color(0xFF3399FF).copy(alpha = 0.4f)  // Semi-transparent light blue
            )
        )
    } else {
        Brush.linearGradient(
            colors = listOf(
                Color(0xFFCC8800).copy(alpha = 0.5f), // Semi-transparent dark yellow
                Color(0xFFFFFF00).copy(alpha = 0.4f)  // Semi-transparent bright yellow
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp, bottomEnd = 0.dp, bottomStart = 0.dp)) // Rounded corners on top only
            .background(containerBrush)
    ) {
        NavigationBar(
            modifier = Modifier.fillMaxWidth(),
            containerColor = Color.Transparent,
            contentColor = if (isSystemInDarkTheme()) {
                LocalContentColor.current
            } else {
                MaterialTheme.colorScheme.onPrimary
            }
        ) {
            items.forEachIndexed { index, item ->
                val isSelected = index == selectedItem
                NavigationBarItem(
                    selected = isSelected,
                    onClick = { onItemClick(index) },
                    icon = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = null,
                                modifier = Modifier.size(if (isSelected) 36.dp else 32.dp) // Slightly larger icon if selected
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = item.text,
                                fontSize = 16.sp,
                                color = if (isSystemInDarkTheme()) {
                                    Color.White
                                } else {
                                    Color.Black
                                }
                            )
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = if (isSystemInDarkTheme()) {
                            LocalContentColor.current
                        } else {
                            Color.Black
                        },
                        unselectedIconColor = if (isSystemInDarkTheme()) {
                            LocalContentColor.current.copy(alpha = 0.4f)
                        } else {
                            Color.Black.copy(alpha = 0.4f)
                        }
                    )
                )
            }
        }
    }
}

// Data class representing a bottom navigation item
data class BottomNavigationItem(
    @DrawableRes val icon: Int,
    val text: String
)

// Previews for the NewsBottomNavigation
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun NewsBottomNavigationPreview() {
    MaterialTheme {
        NewsBottomNavigation(
            items = listOf(
                BottomNavigationItem(icon = R.drawable.ic_home, text = "Home"),
                BottomNavigationItem(icon = R.drawable.ic_search, text = "Search"),
                BottomNavigationItem(icon = R.drawable.ic_bookmark, text = "Bookmark")
            ),
            selectedItem = 0,
            onItemClick = {}
        )
    }
}
