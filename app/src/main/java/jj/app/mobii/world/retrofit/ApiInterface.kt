package jj.app.mobii.world.retrofit

import io.reactivex.Single
import jj.app.mobii.world.models.SeatViewResponse
import retrofit2.http.GET

interface ApiInterface {

    @GET(ApiUrl.SEATING_PLAN)
    fun getSeatingPlan():Single<SeatViewResponse>

}