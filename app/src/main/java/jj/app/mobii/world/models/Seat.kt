package jj.app.mobii.world.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Seat {
    @SerializedName("id")
    @Expose
    val id: Int = 0
    @SerializedName("name")
    @Expose
    val name: String? = null
    @SerializedName("seatKey")
    @Expose
    val seatKey: String? = null
    @SerializedName("rowNumber")
    @Expose
    val rowNumber: Int? = null
    @SerializedName("columnIndex")
    @Expose
    val columnIndex: Int? = null
    @SerializedName("bookedStatus")
    @Expose
    val bookedStatus: Int? = null

    var isSelected: Int=0
}