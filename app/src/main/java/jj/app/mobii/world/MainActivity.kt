package jj.app.mobii.world

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import jj.app.mobii.world.helpers.Messages
import jj.app.mobii.world.models.Seat
import jj.app.mobii.world.models.SeatView
import jj.app.mobii.world.models.SeatViewResponse
import jj.app.mobii.world.models.Zone
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
                    createView()
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    dialog.visibility = GONE
                    txt_status.text = Messages.onFailureMessageRetrofit(e)
                }
            })
    }

    private fun getLayoutParamBasic(): LinearLayout.LayoutParams {
        var param = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        param.gravity = Gravity.CENTER
        return param
    }

    private fun createView() {

        var root = LinearLayout(this)
        root.orientation = LinearLayout.VERTICAL
        root.layoutParams = getLayoutParamBasic()
        root.gravity = Gravity.CENTER

        var zoneList = seatView.zones

        if (zoneList != null) {
            for (zone in zoneList) {
                var title = TextView(this)
                title.text = zone.name
                var textParam = getLayoutParamBasic();
                textParam.setMargins(10, 10, 10, 10)
                title.layoutParams = textParam
                title.gravity = Gravity.CENTER
                title.textSize = 15f
                root.addView(title)

                root.addView(getZoneViewTemp(zone))
            }
        }

        ll_seat_view_holder.addView(root)
    }

    private fun getZoneView(zone: Zone, temp: ArrayList<ArrayList<Seat>>): LinearLayout {

        val totalRows = temp.size

        var row = LinearLayout(this)
        row.orientation = LinearLayout.VERTICAL
        row.gravity = Gravity.CENTER
        row.layoutParams = getLayoutParamBasic()
        for (i in 0 until totalRows) {
            var column = LinearLayout(this)
            column.orientation = LinearLayout.HORIZONTAL
            column.gravity = Gravity.CENTER
            column.layoutParams = getLayoutParamBasic()
            val totalColumns = temp[i].size

            for (j in 0 until totalColumns) {
                val seat = temp[i][j]

                if (seat.id!! > 0) {
                    var image = ImageView(this)

                    if (seat.bookedStatus == 1) {
                        image.setImageResource(R.drawable.sold_seat)
                    } else {
                        image.setImageResource(R.drawable.available_seat)
                    }

                    var imageParam = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    imageParam.setMargins(5, 5, 5, 5)
                    image.setPadding(10,10,10,10)

                    image.layoutParams = imageParam
                    image.isClickable = false
                    image.tag = Gson().toJson(seat)
                    image.setOnClickListener(object : View.OnClickListener {
                        override fun onClick(p0: View?) {
                            var selectedImage: ImageView= p0 as ImageView
                            var selectedSeat: Seat = Gson().fromJson(selectedImage?.tag.toString(), Seat::class.java)
                            if (selectedSeat != null && selectedSeat.bookedStatus == 0) {

                                if(selectedSeat.isSelected==0) {
                                    selectedImage.setImageResource(R.drawable.selected_seat)
                                    selectedSeat.isSelected = 1
                                    selectedImage.tag = Gson().toJson(selectedSeat)
                                }else{
                                    selectedImage.setImageResource(R.drawable.available_seat)
                                    selectedSeat.isSelected = 0
                                    selectedImage.tag = Gson().toJson(selectedSeat)
                                }
                            }

                        }

                    })
                    column.addView(image)
                }
            }
            row.addView(column)
        }
        return row
    }


    private fun getZoneViewTemp(zone: Zone): LinearLayout {
        var totalRows = zone.maxRows
        if (totalRows == null) totalRows = 0
        var totalColumns = zone.maxColumns
        if (totalColumns == null) totalColumns = 0

        val temp: ArrayList<ArrayList<Seat>> =
            ArrayList()

        for (i in 0 until totalRows) {
            val columnsSeats: ArrayList<Seat> = ArrayList()
            for (j in 0 until totalColumns) {
                val seat = Seat()
                columnsSeats.add(seat)
            }
            temp.add(columnsSeats)
        }

        val seatList: List<Seat> = zone.seats!!
        for (i in seatList.indices) {
            try {
                val seat = seatList[i]
                Messages.log("Seat: " + Gson().toJson(seat))
                temp[seat.columnIndex!! - 1][seat.rowNumber!! - 1] = seat
            } catch (e: Exception) {
                Messages.log("Error: $e")
            }
        }
        Messages.log("Data: " + Gson().toJson(temp))
        return getZoneView(zone, temp)
    }
}
