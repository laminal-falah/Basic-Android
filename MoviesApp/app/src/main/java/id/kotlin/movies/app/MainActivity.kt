package id.kotlin.movies.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.ContentLoadingProgressBar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val progressBar = findViewById<ContentLoadingProgressBar>(R.id.progress_bar)

        val dataSource = NetworkProvider.providesHttpAdapter().create(ApiService::class.java)

        val itemAdapter = findViewById<RecyclerView>(R.id.rv_movies)

        dataSource.discoverMovie().enqueue(object : Callback<MainResponse> {
            override fun onResponse(call: Call<MainResponse>, response: Response<MainResponse>) {
                progressBar.visibility = View.GONE

                val results = response.body()?.results
                mainAdapter = MainAdapter(results ?: emptyList())

                itemAdapter.apply {
                    setHasFixedSize(true)
                    addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
                    adapter = mainAdapter
                }

                mainAdapter.setOnItemClickCallback(object : MainAdapter.OnItemClickCallback {
                    override fun onItemClick(data: Result) {
                        startActivity(Intent(this@MainActivity, DetailActivity::class.java).apply {
                            putExtra(DetailActivity.EXTRA_ID, data.id)
                        })
                    }
                })
            }

            override fun onFailure(call: Call<MainResponse>, t: Throwable) {
                Log.e(MainActivity::class.java.simpleName, "${t.printStackTrace()}")
            }
        })


    }

}