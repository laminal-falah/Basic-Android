package id.kotlin.movies.app.presentation.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import dagger.android.support.DaggerAppCompatActivity
import id.kotlin.movies.app.R
import id.kotlin.movies.app.data.Result
import id.kotlin.movies.app.databinding.ActivityMainBinding
import id.kotlin.movies.app.presentation.OnItemClickCallback
import id.kotlin.movies.app.presentation.detail.DetailActivity
import id.kotlin.movies.app.presentation.paging.PagingActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)
            .get(MainViewModel::class.java)
    }

    private lateinit var binding: ActivityMainBinding

    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).apply {
            viewModel = this@MainActivity.viewModel

            setSupportActionBar(toolbarMain)
        }.also {
            viewModel.discoverMovie()
        }

        viewModel.states.observe(this, { state ->
            when (state) {
                is MainViewState.Loading -> binding.progressBar.visibility = View.VISIBLE
                is MainViewState.Success -> {
                    mainAdapter = state.response.results?.let { MainAdapter(it) }!!
                    binding.progressBar.visibility = View.GONE
                    binding.rvMovies.apply {
                        setHasFixedSize(true)
                        addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
                        adapter = mainAdapter
                    }
                    mainAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW
                    mainAdapter.setOnItemClickCallback(object: OnItemClickCallback<Result> {
                        override fun onItemClick(data: Result) {
                            startActivity(Intent(this@MainActivity, DetailActivity::class.java).apply {
                                putExtra(DetailActivity.EXTRA_ID, data.id)
                            })
                        }
                    })
                }
                is MainViewState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Log.e(MainActivity::class.java.simpleName, "${state.error.printStackTrace()}")
                    Toast.makeText(this, state.error.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.pagingPage -> {
            startActivity(Intent(this@MainActivity, PagingActivity::class.java))
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}