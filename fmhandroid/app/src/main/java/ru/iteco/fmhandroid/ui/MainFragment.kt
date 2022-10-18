package ru.iteco.fmhandroid.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.test.espresso.IdlingResource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.iteco.fmhandroid.EspressoIdlingResources
import ru.iteco.fmhandroid.R
import ru.iteco.fmhandroid.adapter.ClaimListAdapter
import ru.iteco.fmhandroid.adapter.NewsListAdapter
import ru.iteco.fmhandroid.databinding.FragmentMainBinding
import ru.iteco.fmhandroid.utils.Utils
import ru.iteco.fmhandroid.viewmodel.AuthViewModel
import ru.iteco.fmhandroid.viewmodel.ClaimViewModel
import ru.iteco.fmhandroid.viewmodel.NewsViewModel


@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {
    private lateinit var binding: FragmentMainBinding
    private val claimViewModel: ClaimViewModel by viewModels()
    private val newsViewModel: NewsViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        lifecycleScope.launchWhenCreated {
            claimViewModel.onRefresh()
        }

        lifecycleScope.launchWhenStarted {
            EspressoIdlingResources.increment();
            claimViewModel.openClaimEvent.collectLatest {
                val action = MainFragmentDirections
                    .actionMainFragmentToOpenClaimFragment(it)
                findNavController().navigate(action)
                EspressoIdlingResources.decrement();
            }
        }

        lifecycleScope.launchWhenStarted {
            EspressoIdlingResources.increment();
            claimViewModel.claimListUpdatedEvent.collectLatest {
                newsViewModel.onRefresh()
                EspressoIdlingResources.decrement();
            }
        }

        lifecycleScope.launchWhenResumed {
            claimViewModel.claimsLoadException.collect {
                showErrorToast(R.string.error)
            }
        }

        lifecycleScope.launchWhenResumed {
            newsViewModel.loadNewsExceptionEvent.collect {
                showErrorToast(R.string.error)
            }
        }

        lifecycleScope.launchWhenResumed {
            authViewModel.userListLoadedEvent.collect {
                EspressoIdlingResources.increment();
                findNavController().navigate(R.id.action_mainFragment_to_createEditClaimFragment)
                EspressoIdlingResources.decrement();
            }
        }

    }

    @SuppressLint("Recycle")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainBinding.bind(view)

        val mainMenu = PopupMenu(
            context,
            binding.containerCustomAppBarIncludeOnFragmentMain.mainMenuImageButton
        )
        mainMenu.inflate(R.menu.menu_main)
        val menuItemMain = mainMenu.menu.getItem(0)
        menuItemMain.isEnabled = false
        binding.containerCustomAppBarIncludeOnFragmentMain.mainMenuImageButton.setOnClickListener {
            EspressoIdlingResources.increment();
            mainMenu.show()
            EspressoIdlingResources.decrement();
        }
        mainMenu.setOnMenuItemClickListener {
            //EspressoIdlingResources.increment();
            when (it.itemId) {
                R.id.menu_item_claims -> {
                    findNavController().navigate(R.id.action_mainFragment_to_claimListFragment)
                    true
                }
                R.id.menu_item_news -> {
                    findNavController().navigate(R.id.action_mainFragment_to_newsListFragment)
                    true
                }
                R.id.menu_item_about -> {
                    findNavController().navigate(R.id.action_mainFragment_to_aboutFragment)
                    true
                }
                else -> false
            }
        }



        val authorizationMenu = PopupMenu(
            context,
            binding.containerCustomAppBarIncludeOnFragmentMain.authorizationImageButton
        )
        authorizationMenu.inflate(R.menu.authorization)

        binding.containerCustomAppBarIncludeOnFragmentMain.authorizationImageButton.setOnClickListener {
            EspressoIdlingResources.increment();
            authorizationMenu.show()
            EspressoIdlingResources.decrement();
        }

        binding.containerCustomAppBarIncludeOnFragmentMain.ourMissionImageButton.setOnClickListener {
            EspressoIdlingResources.increment();
            findNavController().navigate(R.id.action_mainFragment_to_our_mission_fragment)
            EspressoIdlingResources.decrement();
        }

        authorizationMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.authorization_logout_menu_item -> {
                    authViewModel.logOut()
                    findNavController().navigate(R.id.action_mainFragment_to_authFragment)
                    true
                }
                else -> false
            }
        }

        binding.containerListClaimIncludeOnFragmentMain.apply {
            EspressoIdlingResources.increment();
            expandMaterialButton.visibility = View.VISIBLE
            allClaimsTextView.visibility = View.VISIBLE
            filtersMaterialButton.visibility = View.GONE

            addNewClaimMaterialButton.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    authViewModel.loadUserList()
                    EspressoIdlingResources.decrement();
                }
            }

            expandMaterialButton.setOnClickListener {
                EspressoIdlingResources.increment();
                when (allClaimsTextView.visibility) {
                    View.GONE -> {
                        allClaimsTextView.visibility = View.VISIBLE
                        allClaimsCardsBlockConstraintLayout.visibility = View.VISIBLE
                        expandMaterialButton.setIconResource(R.drawable.expand_less_24)
                        EspressoIdlingResources.decrement();
                    }
                    else -> {
                        allClaimsTextView.visibility = View.GONE
                        allClaimsCardsBlockConstraintLayout.visibility = View.GONE
                        expandMaterialButton.setIconResource(R.drawable.expand_more_24)
                    }
                }
            }

            allClaimsTextView.setOnClickListener {
                EspressoIdlingResources.increment();
                if (Utils.isOnline(requireContext())) {
                    findNavController().navigate(R.id.action_mainFragment_to_claimListFragment)
                    EspressoIdlingResources.decrement();
                } else {
                    showErrorToast(R.string.error)
                }
            }
        }

        val claimListAdapter = ClaimListAdapter(claimViewModel)

        binding.containerListClaimIncludeOnFragmentMain.claimListRecyclerView.adapter =
            claimListAdapter
        lifecycleScope.launchWhenCreated {
            claimViewModel.data.collectLatest { state ->
                claimListAdapter.submitList(state.take(n = 6))
            }
        }

        binding.containerListNewsIncludeOnFragmentMain.apply {
            sortNewsMaterialButton.visibility = View.GONE
            filterNewsMaterialButton.visibility = View.GONE
            editNewsMaterialButton.visibility = View.GONE

            expandMaterialButton.setOnClickListener {
                EspressoIdlingResources.increment();
                when (allNewsTextView.visibility) {
                    View.GONE -> {
                        allNewsTextView.visibility = View.VISIBLE
                        allNewsCardsBlockConstraintLayout.visibility = View.VISIBLE
                        expandMaterialButton.setIconResource(R.drawable.expand_less_24)
                        EspressoIdlingResources.decrement();
                    }
                    else -> {
                        allNewsTextView.visibility = View.GONE
                        allNewsCardsBlockConstraintLayout.visibility = View.GONE
                        expandMaterialButton.setIconResource(R.drawable.expand_more_24)
                    }
                }
            }

            allNewsTextView.setOnClickListener {
                EspressoIdlingResources.increment();
                if (Utils.isOnline(requireContext())) {
                    findNavController().navigate(R.id.action_mainFragment_to_newsListFragment)
                    EspressoIdlingResources.decrement();
                } else {
                    showErrorToast(R.string.error)
                }
            }
        }

        val newsListAdapter = NewsListAdapter(newsViewModel)
        binding.containerListNewsIncludeOnFragmentMain.newsListRecyclerView.adapter =
            newsListAdapter
        lifecycleScope.launchWhenCreated {
            EspressoIdlingResources.increment();
            newsViewModel.data.collectLatest {
                newsListAdapter.submitList(it.take(3))
                EspressoIdlingResources.decrement();
            }
        }

        lifecycleScope.launch {
            binding.mainSwipeRefresh.setOnRefreshListener {
                EspressoIdlingResources.increment();
                claimViewModel.onRefresh()
                binding.mainSwipeRefresh.isRefreshing = false
                EspressoIdlingResources.decrement();
            }
        }
        //EspressoIdlingResources.decrement();
    }

    private fun showErrorToast(text: Int) {
        Toast.makeText(
            requireContext(),
            text,
            Toast.LENGTH_LONG
        ).show()
    }

    /*@VisibleForTesting
    fun getIdlingResource(): IdlingResource {
        if (mIdlingResource == null) {
            mIdlingResource = SimpleIdlingResource()
        }
        return mIdlingResource
    }*/
}
