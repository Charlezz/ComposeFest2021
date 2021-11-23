package com.example.compose.rally

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.compose.rally.ui.components.RallyTopAppBar
import org.junit.Rule
import org.junit.Test
import java.lang.Thread.sleep
import java.util.*

class TopAppBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun rallyTopAppBarTest_currentTabSelected() {
        val allScreens = RallyScreen.values().toList()
        composeTestRule.setContent {
            RallyTopAppBar(
                allScreens = allScreens,
                onTabSelected = { },
                currentScreen = RallyScreen.Accounts
            )
        }

        composeTestRule
            .onNodeWithContentDescription(RallyScreen.Accounts.name)
            .assertIsSelected()
    }

    @Test
    fun rallyTopAppBarTest_currentLabelExists() {
        val allScreens = RallyScreen.values().toList()
        composeTestRule.setContent {
            RallyTopAppBar(
                allScreens = allScreens,
                onTabSelected = { },
                currentScreen = RallyScreen.Accounts
            )
        }

//        composeTestRule.onRoot().printToLog("currentLabelExists")
//        composeTestRule
//            .onNodeWithText(RallyScreen.Accounts.name.uppercase(Locale.getDefault()))
//            .assertExists()

        composeTestRule
            .onNodeWithContentDescription(RallyScreen.Accounts.name)
            .assertExists()

        composeTestRule.onRoot(useUnmergedTree = true).printToLog("currentLabelExists")

        composeTestRule
            .onNode(
                hasText(RallyScreen.Accounts.name.uppercase(Locale.getDefault())) and
                        hasParent(
                            hasContentDescription(RallyScreen.Accounts.name)
                        ),
                useUnmergedTree = true
            )
            .assertExists()

    }

    @Test
    fun rallyTopAppBarTest_clickTabs(){
        val allScreens = RallyScreen.values().toList()
        var currentScreen:RallyScreen = RallyScreen.Overview
        composeTestRule.setContent {
            RallyTopAppBar(
                allScreens = allScreens,
                onTabSelected = { screen -> currentScreen = screen},
                currentScreen = RallyScreen.Accounts
            )
        }

//        composeTestRule.onRoot(true).printToLog("current!")

        RallyScreen.values().forEach { screen->
            composeTestRule
                .onNodeWithContentDescription(screen.name)
                .performClick()
            assert(currentScreen == screen)
        }

    }

    @Test
    fun rallyTopAppBarTest_clickTabs2(){
        var currentScreen:RallyScreen = RallyScreen.Overview
        composeTestRule.setContent {
            RallyApp(currentScreen){ screen-> currentScreen = screen }
        }

        RallyScreen.values().forEach { screen->
            composeTestRule
                .onNodeWithContentDescription(screen.name)
                .performClick()
            assert(currentScreen == screen)
        }
    }

}