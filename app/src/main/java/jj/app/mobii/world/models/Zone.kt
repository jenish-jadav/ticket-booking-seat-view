package jj.app.mobii.world.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class Zone {

    @SerializedName("id")
    @Expose
    val id: Int? = null
    @SerializedName("name")
    @Expose
    val name: String? = null
    @SerializedName("sort")
    @Expose
    val sort: Int? = null
    @SerializedName("startIndex")
    @Expose
    val startIndex: Int? = null
    @SerializedName("maxRows")
    @Expose
    val maxRows: Int? = null
    @SerializedName("maxColumns")
    @Expose
    val maxColumns: Int? = null
    @SerializedName("colorCode")
    @Expose
    val colorCode: String? = null
    @SerializedName("price")
    @Expose
    val price: Int? = null
    @SerializedName("totalSeats")
    @Expose
    val totalSeats: Int? = null
    @SerializedName("seatsBooked")
    @Expose
    val seatsBooked: Int? = null
    @SerializedName("seats")
    @Expose
    val seats: List<Seat>? = null
}