package com.imdmp.youtubecompose.features.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.imdmp.youtubecompose.ui.theme.YoutubeComposeTheme

class HomeListFragment : Fragment(), ListScreenActions {

    val args: HomeListFragmentArgs by navArgs()

    private val homeListViewModel: HomeListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                YoutubeComposeTheme() {
                    // In Compose world
                    VideoListScreen(homeListViewModel, this@HomeListFragment)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (args.query.isEmpty())
            homeListViewModel.fetchVideoList()
        else
            homeListViewModel.search(args.query)
    }

    override fun videoItemSelected(dataItem: DataItem) {
        val action = HomeListFragmentDirections.actionHomeListFragmentToPlayerFragment(dataItem)
        findNavController().navigate(action)
    }

    override fun searchClicked() {
        val action = HomeListFragmentDirections.actionHomeListFragmentToSearchFragment()
        findNavController().navigate(action)
    }
}