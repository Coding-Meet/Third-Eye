package com.coding.meet.thirdeye.ui.screens.result.components

import android.graphics.Bitmap
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.coding.meet.thirdeye.ui.theme.boxBackgroundColor
import com.coding.meet.thirdeye.ui.theme.boxBorderColor
import com.coding.meet.thirdeye.ui.theme.textColor
import com.coding.meet.thirdeye.util.Tools
import dev.jeziellago.compose.markdowntext.MarkdownText

@Composable
fun ChatBubbleItem(currentTools: Tools, currentPrompt: String,bitmaps: Bitmap?, answerTxt: String?) {
    Column(
        horizontalAlignment = if (answerTxt != null) Alignment.Start else Alignment.End,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
    ) {
        Card(
            border = BorderStroke(5.dp, boxBorderColor),
            colors = CardDefaults.cardColors(
                containerColor = boxBackgroundColor
            ),
            shape = if (answerTxt != null) {
                RoundedCornerShape(4.dp, 20.dp, 20.dp, 20.dp)
            } else {
                RoundedCornerShape(20.dp, 4.dp, 20.dp, 20.dp)
            },
            modifier = Modifier
                .padding(start = if (answerTxt == null) 16.dp else 0.dp)
        ) {
            if (answerTxt != null) {

                TypewriterTextEffect(text = answerTxt) { displayedText ->
                    MarkdownText(
                        modifier = Modifier.padding(16.dp),
                        markdown = displayedText,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            color = textColor
                        )
                    )
                }
            } else {
                if (bitmaps != null) {
                    Image(
                        bitmaps.asImageBitmap(),
                        contentDescription = "Attachment",
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(8.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .wrapContentSize()
                            .aspectRatio(1f),
                        contentScale = ContentScale.Crop,
                    )
                    Text(
                        if(currentTools == Tools.CustomPromptImage || currentTools == Tools.CustomPrompt) currentPrompt else stringResource(id = currentTools.prompt),
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )
                } else {
                    Text(
                        text = if(currentTools == Tools.CustomPromptImage || currentTools == Tools.CustomPrompt) currentPrompt else stringResource(id = currentTools.prompt),
                        modifier = Modifier.padding(16.dp),
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )
                }
            }
        }
    }
}
