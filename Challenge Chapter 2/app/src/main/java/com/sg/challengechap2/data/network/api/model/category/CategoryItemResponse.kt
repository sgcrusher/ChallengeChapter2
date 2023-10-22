package com.sg.challengechap2.data.network.api.model.category


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import com.sg.challengechap2.model.CategoryFood

@Keep
data class CategoryItemResponse(
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("nama")
    val name: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("slug")
    val slug: String?
)

fun CategoryItemResponse.toCategory() = CategoryFood(
    categoryImg = this.imageUrl.orEmpty(),
    categoryName = this.name.orEmpty(),
    id = this.id ?: 0,
    slug = this.slug.orEmpty()
)

fun Collection<CategoryItemResponse>.toCategoryList() = this.map {
    it.toCategory()
}