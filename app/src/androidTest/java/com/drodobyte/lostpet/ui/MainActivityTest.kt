package com.drodobyte.lostpet.ui

import androidx.compose.ui.test.assertCountEquals
import com.drodobyte.feature.pets.helper.Semantic.All
import com.drodobyte.feature.pets.helper.Semantic.Badge
import com.drodobyte.feature.pets.helper.Semantic.EditImage
import com.drodobyte.feature.pets.helper.Semantic.EditLocation
import com.drodobyte.feature.pets.helper.Semantic.EditName
import com.drodobyte.feature.pets.helper.Semantic.Filter
import com.drodobyte.feature.pets.helper.Semantic.Found
import com.drodobyte.feature.pets.helper.Semantic.ImageChooser
import com.drodobyte.feature.pets.helper.Semantic.Lost
import com.drodobyte.feature.pets.helper.Semantic.NewPet
import com.drodobyte.feature.pets.helper.Semantic.NoPetsToShow
import com.drodobyte.feature.pets.helper.Semantic.Pet
import org.junit.Test

class MainActivityTest : BaseTest() {

    @Test
    fun initial_layout() {
        Filter.isDisplayed()
        NewPet.isDisplayed()
        NoPetsToShow.isDisplayed()
    }

    @Test
    fun pet_filters_are_shown_when_filter_clicked() {
        Filter.click()

        All.isDisplayed()
        Found.isDisplayed()
        Lost.isDisplayed()
    }

    @Test
    fun pet_is_added_when_new_pet_is_clicked() {
        NewPet.click()
        EditName.waitVisible()
        EditName.text("name")
        pressBack()
        pressBack()

        nodes(Pet).assertCountEquals(1)
    }

    @Test
    fun badge_not_shown_if_pet_lost() {
        NewPet.click()
        EditLocation.waitVisible()
        EditLocation.text("")

        Badge.isNotDisplayed()
    }

    @Test
    fun badge_shown_if_pet_found() {
        NewPet.click()
        EditLocation.waitVisible()
        EditLocation.text("place")

        Badge.isDisplayed()
    }

    @Test
    fun show_image_chooser_if_image_clicked() {
        NewPet.click()
        EditImage.waitVisible()
        EditImage.click()

        ImageChooser.isDisplayed()
    }
}
