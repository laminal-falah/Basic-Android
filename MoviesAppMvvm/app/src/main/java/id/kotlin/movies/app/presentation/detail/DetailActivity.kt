package id.kotlin.movies.app.presentation.detail

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import dagger.android.support.DaggerAppCompatActivity
import id.kotlin.movies.app.R
import id.kotlin.movies.app.data.Movie
import id.kotlin.movies.app.databinding.ActivityDetailBinding
import javax.inject.Inject

class DetailActivity : DaggerAppCompatActivity(), DetailViewModelCallback {

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    @Inject
    lateinit var viewModel: DetailViewModel

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityDetailBinding>(this, R.layout.activity_detail).apply {
            viewModel = this@DetailActivity.viewModel

            setSupportActionBar(toolbarDetail)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
            toolbarDetail.setNavigationOnClickListener { onBackPressed() }
        }.also {
            viewModel.findByMovie(intent.getIntExtra(EXTRA_ID, 0))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDetach()
    }

    override fun onSuccess(movie: Movie) {
        binding.toolbarDetail.title = movie.originalTitle
        binding.tvDetailOverview.text = movie.overview
    }

    override fun onError(error: Throwable) {
        Log.e(DetailActivity::class.simpleName, "${error.printStackTrace()}")
        Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
    }

}