package com.coding.meet.blindaiassistant.util

import com.coding.meet.blindaiassistant.R

enum class Tools(
    val title: Int,
    val icon: Int,
    val toolsType: ToolsType,
    val route: String,
    val prompt: Int
) {
    DescribeImage(
        title = R.string.describe_image,
        icon = R.drawable.ic_capture,
        toolsType = ToolsType.DescribeImage,
        route = "describe_image",
        prompt = R.string.prompt_describe_image
    ),
    ImageToText(
        title = R.string.image_to_text,
        icon = R.drawable.ic_capture,
        toolsType = ToolsType.ImageToText,
        route = "image_to_text",
        prompt = R.string.prompt_image_to_text
    ),
    Weather(
        title = R.string.weather,
        icon = R.drawable.ic_capture,
        toolsType = ToolsType.Weather,
        route = "weather",
        prompt = R.string.prompt_weather
    ),
    Talk(
        title = R.string.talk,
        icon = R.drawable.ic_capture,
        toolsType = ToolsType.Talk,
        route = "talk",
        prompt = R.string.prompt_talk
    ),
    Translate(
        title = R.string.translate,
        icon = R.drawable.ic_capture,
        toolsType = ToolsType.Translate,
        route = "translate",
        prompt = R.string.prompt_translate
    ),
    News(
        title = R.string.news,
        icon = R.drawable.ic_capture,
        toolsType = ToolsType.News,
        route = "news",
        prompt = R.string.prompt_news
    ),
    InterviewPre(
        title = R.string.interview_pre,
        icon = R.drawable.ic_capture,
        toolsType = ToolsType.InterviewPre,
        route = "interview_pre",
        prompt = R.string.prompt_interview_pre
    ),
    Joke(
        title = R.string.joke,
        icon = R.drawable.ic_capture,
        toolsType = ToolsType.Joke,
        route = "joke",
        prompt = R.string.prompt_joke
    ),
    Recipe(
        title = R.string.recipe,
        icon = R.drawable.ic_capture,
        toolsType = ToolsType.Recipe,
        route = "recipe",
        prompt = R.string.prompt_recipe
    ),
    HealthRemedy(
        title = R.string.health_remedy,
        icon = R.drawable.ic_capture,
        toolsType = ToolsType.HealthRemedy,
        route = "health_remedy",
        prompt = R.string.prompt_health_remedy
    )
}