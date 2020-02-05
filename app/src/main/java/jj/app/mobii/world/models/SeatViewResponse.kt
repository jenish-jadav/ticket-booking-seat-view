package jj.app.mobii.world.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SeatViewResponse {

    @SerializedName("StatusCode")
    @Expose
    val statusCode: Int? = null
    @SerializedName("Status")
    @Expose
    val status: String? = null
    @SerializedName("Data")
    @Expose
    val seatView: SeatView? = null

}

