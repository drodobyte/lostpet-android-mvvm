package com.drodobyte.core.data.repository

import com.drodobyte.core.data.repository.Adapter.Local.Companion.local
import com.drodobyte.core.data.repository.Adapter.Local.Companion.model
import com.google.gson.JsonSyntaxException
import org.junit.Assert
import org.junit.Test

class AdapterTest {

    @Test
    fun `urls json adapts to strings`() {
        Assert.assertEquals(listOf("url1", "url2"), "[\"url1\",\"url2\"]".model)
        Assert.assertEquals(emptyList<String>(), "[]".model)
    }

    @Test
    fun `malformed urls json throws exception`() {
        Assert.assertThrows(JsonSyntaxException::class.java) { "[url1, url2".model }
    }

    @Test
    fun `strings adapts to urls json`() {
        Assert.assertEquals("[\"url1\",\"url2\"]", listOf("url1", "url2").local)
    }
}
