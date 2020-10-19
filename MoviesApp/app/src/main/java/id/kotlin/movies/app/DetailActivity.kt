package id.kotlin.movies.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.ContentLoadingProgressBar
import com.google.android.material.textview.MaterialTextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val progressBar = findViewById<ContentLoadingProgressBar>(R.id.progress_bar_detail)

        val dataSource = NetworkProvider.providesHttpAdapter().create(ApiService::class.java)

        val title = findViewById<MaterialTextView>(R.id.tv_detail_title)

        val overview = findViewById<MaterialTextView>(R.id.tv_detail_overview)

        dataSource.detailMovie(intent.getIntExtra(EXTRA_ID, 0)).enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                progressBar.visibility = View.GONE

                val movie = response.body()

                title.text = movie?.originalTitle
                overview.text = movie?.overview
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                Log.e(DetailActivity::class.simpleName, "${t.printStackTrace()}")
            }
        })
    }
}