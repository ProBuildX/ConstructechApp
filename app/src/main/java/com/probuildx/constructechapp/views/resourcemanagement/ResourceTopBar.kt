package com.probuildx.constructechapp.views.resourcemanagement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ResourceTopBar(navController: NavController, projectId: Int, index: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Resource Management",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        val tabs = mapOf(
            "resource-management" to "Summary",
            "materials" to "Materials",
            "machinery" to "Machinery"
        )

        TabRow(
            selectedTabIndex = index,
            modifier = Modifier.fillMaxWidth()
        ) {
            tabs.entries.forEachIndexed { i, entry ->
                Tab(
                    selected = (i == index),
                    onClick = { navController.navigate("${entry.key}/${projectId}") },
                    text = {
                        Text(
                            text = entry.value,
                            color = if (i == index) Color.Black else Color.Gray,
                            fontSize = 14.sp
                        )
                    }
                )
            }
        }
    }
}