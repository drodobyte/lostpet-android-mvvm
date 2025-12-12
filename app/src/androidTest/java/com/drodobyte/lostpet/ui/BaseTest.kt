package com.drodobyte.lostpet.ui

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import androidx.test.espresso.Espresso
import com.drodobyte.feature.pets.helper.Semantic
import org.junit.Rule

@OptIn(ExperimentalTestApi::class)
abstract class BaseTest {

    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()

    fun pressBack() = Espresso.pressBack()
    fun Semantic.click() = node(this).performClick()
    fun Semantic.isDisplayed() = node(this).isDisplayed()
    fun Semantic.isNotDisplayed() = node(this).isNotDisplayed()
    fun Semantic.text(text: String) = node(this).performTextReplacement(text)
    fun Semantic.waitVisible(millis: Long = 3000) =
        rule.waitUntilAtLeastOneExists(hasTestTag(name), millis)

    fun node(semantic: Semantic) = rule.onNode(hasTestTag(semantic.name))
    fun nodes(semantic: Semantic, unmerged: Boolean = false) =
        rule.onAllNodesWithTag(semantic.name, unmerged)
}
