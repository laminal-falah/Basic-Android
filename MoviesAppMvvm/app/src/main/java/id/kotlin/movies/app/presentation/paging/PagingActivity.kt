package id.kotlin.movies.app.presentation.paging

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.android.support.DaggerAppCompatActivity
import id.kotlin.movies.app.R
import id.kotlin.movies.app.databinding.ActivityPagingBinding
import id.kotlin.movies.app.domain.MainEntity
import id.kotlin.movies.app.domain.MainResult
import id.kotlin.movies.app.presentation.OnItemClickCallback
import id.kotlin.movies.app.presentation.detail.DetailActivity
import javax.inject.Inject

class PagingActivity : DaggerAppCompatActivity(), PagingView {

    @Inject
    lateinit var pagingPresenter: PagingPresenter

    private var binding: ActivityPagingBinding? = null

    private lateinit var pagingAdapter: PagingAdapter

    private var isLoading = false

    private var currentPage = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityPagingBinding>(this, R.layout.activity_paging).apply {
            toolbarPaging.title = getString(R.string.paging_name)
            setSupportActionBar(toolbarPaging)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
            toolbarPaging.setNavigationOnClickListener { onBackPressed() }
        }.also {
            pagingPresenter.discoverMovie()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        pagingPresenter.onDetach()
    }

    override fun onShowLoading() {
        binding?.progressBar?.visibility = View.VISIBLE
    }

    override fun onHideLoading() {
        binding?.progressBar?.visibility = View.GONE
    }

    override fun onSuccess(entity: MainEntity) {
        pagingAdapter = PagingAdapter(entity.results.toMutableList())
        currentPage = entity.page

        binding?.rvMovies?.apply {
            addItemDecoration(DividerItemDecoration(this@PagingActivity, DividerItemDecoration.VERTICAL))
            adapter = pagingAdapter

            pagingAdapter.setOnItemClickCallback(object : OnItemClickCallback<MainResult> {
                override fun onItemClick(data: MainResult) {
                    startActivity(Intent(this@PagingActivity, DetailActivity::class.java).apply {
                        putExtra(DetailActivity.EXTRA_ID, data.id)
                    })
                }
            })
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (currentPage >= entity.totalPages || isLoading) return

                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount

                    if (visibleItemCount.plus(firstVisibleItemPosition) >= totalItemCount) {
                        pagingAdapter.onShowLoading()
                        isLoading = true
                        currentPage++
                        pagingPresenter.loadMore(currentPage)
                    }
                }
            })
        }
        pagingAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    override fun onError(error: Throwable) {
        Log.e(this.javaClass.simpleName, "${error.printStackTrace()}")
    }

    override fun onPaginationSuccess(entity: MainEntity) {
        currentPage = entity.page
        hideLoading()
        pagingAdapter.loadMore(entity.results.toMutableList())
    }

    override fun onPaginationError(error: Throwable) {
        onError(error)
        currentPage--
        hideLoading()
    }

    private fun hideLoading() {
        pagingAdapter.onHideLoading()
        isLoading = false
    }
}