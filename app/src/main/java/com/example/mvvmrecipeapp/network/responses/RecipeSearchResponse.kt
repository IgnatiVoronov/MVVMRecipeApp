package com.example.mvvmrecipeapp.network.responses

import com.example.mvvmrecipeapp.network.model.RecipeDto
import com.google.gson.annotations.SerializedName

data class RecipeSearchResponse (
    @SerializedName("count")
    var count: Int,

    @SerializedName("result")
    var recipes: List<RecipeDto>
)