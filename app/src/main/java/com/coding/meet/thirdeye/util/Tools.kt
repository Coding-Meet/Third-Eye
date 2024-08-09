package com.coding.meet.thirdeye.util

import com.coding.meet.thirdeye.R

enum class Tools(
    val title: Int,
    val prompt: Int
) {
    CustomPrompt(
        title = R.string.custom_prompt,
        prompt = R.string.image_prompt
    ),
    CustomPromptImage(
        title = R.string.custom_prompt_image,
        prompt = R.string.image_prompt
    ),
    DescribeImage(
        title = R.string.describe_image,
        prompt = R.string.prompt_describe_image
    ),
    ImageToText(
        title = R.string.image_to_text,
        prompt = R.string.prompt_image_to_text
    ),
}