package jj.app.mobii.world.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class SeatView{
    @SerializedName("zones")
    @Expose
    val zones: List<Zone>? = null
}