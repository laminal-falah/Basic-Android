package id.kotlin.movies.app.presenter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import dagger.android.support.DaggerAppCompatActivity
import id.kotlin.movies.app.R
import id.kotlin.movies.app.data.Movie
import id.kotlin.movies.app.data.Result
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), MainBase {

    @Inject
    lateinit var presenter: MainPresenter

    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.discoverMovie()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
    }

    override fun onShowLoading() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun onHideLoading() {
        progress_bar.visibility = View.GONE
    }

    override fun onResponse(results: List<Result>) {
        mainAdapter = MainAdapter(results)

        rv_movies.apply {
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

    override fun onResponse(result: Movie) {
        TODO("Not yet implemented")
    }

    override fun onFailure(error: Throwable) {
        Log.e(MainActivity::class.java.simpleName, "${error.printStackTrace()}")
        Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
    }

}