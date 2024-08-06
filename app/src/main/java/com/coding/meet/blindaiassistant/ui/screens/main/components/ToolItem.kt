package com.coding.meet.blindaiassistant.ui.screens.main.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.coding.meet.blindaiassistant.ui.screens.main.toolsDetect
import com.coding.meet.blindaiassistant.util.Tools
import com.coding.meet.blindaiassistant.viewmodels.MainViewModel
import com.coding.meet.blindaiassistant.ui.navigation.LocalNavControllerProvider

/**
 * Created 28-07-2024 at 06:56 pm
 */

@Composable
fun RowScope.ToolItem(tools: Tools, mainViewModel: MainViewModel,modifier: Modifier = Modifier) {
    val navController = LocalNavControllerProvider.current
    Card(
        modifier = modifier
            .weight(1f)
            .fillMaxSize(),
        onClick = {
            toolsDetect(mainViewModel = mainViewModel, toolsSelect = tools,navController = navController)
        },
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = tools.title), fontSize = 20.sp)
        }

    }
}