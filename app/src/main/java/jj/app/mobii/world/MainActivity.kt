package jj.app.mobii.world

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import jj.app.mobii.world.helpers.Messages
import jj.app.mobii.world.models.SeatView
import jj.app.mobii.world.models.SeatViewResponse
import jj.app.mobii.world.retrofit.ApiClient
import jj.app.mobii.world.retrofit.ApiInterface
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var seatView: SeatView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getSeatViewData()
    }


    @SuppressLint("CheckResult")
    private fun getSeatViewData() {
        dialog.visibility = VISIBLE
        val apiInterface: ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)

        apiInterface.getSeatingPlan()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<SeatViewResponse> {
                override fun onSuccess(t: SeatViewResponse) {
                    dialog.visibility = GONE

                    if (t.seatView != null) {
                        seatView = t.seatView
                    }
                    txt_status.text = "Success"
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    dialog.visibility = GONE
                    txt_status.text = Messages.onFailureMessageRetrofit(e)
                }
            })
    }


}
