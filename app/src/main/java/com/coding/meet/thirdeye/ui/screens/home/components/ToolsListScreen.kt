package com.coding.meet.thirdeye.ui.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.coding.meet.thirdeye.util.Tools
import com.coding.meet.thirdeye.viewmodels.MainViewModel

/**
 * Created 29-07-2024 at 12:13 pm
 */


@Composable
fun ColumnScope.ToolsListScreen(mainViewModel: MainViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .weight(0.7f)
            .padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (index in Tools.entries.indices step 2) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ToolItem(
                    tools = Tools.entries[index],
                    mainViewModel = mainViewModel
                )
                if (index + 1 < Tools.entries.size) {
                    ToolItem(
                        tools = Tools.entries[index + 1],
                        mainViewModel = mainViewModel
                    )
                }
            }
        }
    }
}