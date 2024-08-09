package com.coding.meet.thirdeye.ui.screens.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coding.meet.thirdeye.ui.screens.home.toolsDetect
import com.coding.meet.thirdeye.util.Tools
import com.coding.meet.thirdeye.viewmodels.MainViewModel
import com.coding.meet.thirdeye.ui.navigation.LocalNavControllerProvider
import com.coding.meet.thirdeye.ui.theme.boxBackgroundColor
import com.coding.meet.thirdeye.ui.theme.boxBorderColor
import com.coding.meet.thirdeye.ui.theme.textColor

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
        border = BorderStroke(5.dp, boxBorderColor),
        colors = CardDefaults.cardColors(
            containerColor = boxBackgroundColor
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = tools.title),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center, fontSize = 26.sp,
                lineHeight = 35.sp,
                color = textColor
            )
        }
    }
}