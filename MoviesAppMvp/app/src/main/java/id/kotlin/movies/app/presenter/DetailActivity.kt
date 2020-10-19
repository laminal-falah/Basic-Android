package id.kotlin.movies.app.presenter

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import dagger.android.support.DaggerAppCompatActivity
import id.kotlin.movies.app.R
import id.kotlin.movies.app.data.Movie
import id.kotlin.movies.app.data.Result
import kotlinx.android.synthetic.main.activity_detail.*
import javax.inject.Inject

class DetailActivity : DaggerAppCompatActivity(), MainBase {

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    @Inject
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        presenter.findMovie(intent.getIntExtra(EXTRA_ID, 0))
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
    }

    override fun onShowLoading() {
        progress_bar_detail.visibility = View.VISIBLE
    }

    override fun onHideLoading() {
        progress_bar_detail.visibility = View.GONE
    }

    override fun onResponse(results: List<Result>) {
        TODO("Not yet implemented")
    }

    override fun onResponse(result: Movie) {
        tv_detail_title.text = result.originalTitle
        tv_detail_overview.text = result.overview
    }

    override fun onFailure(error: Throwable) {
        Log.e(DetailActivity::class.simpleName, "${error.printStackTrace()}")
        Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
    }
}