package com.imdmp.youtubecompose.features.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.compose.material.Text
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.imdmp.youtubecompose.ui.theme.YoutubeComposeTheme

class HomeListFragment : Fragment(), ListScreenActions {

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
                    ListScreen(homeListViewModel, this@HomeListFragment)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        homeListViewModel.fetchVideoList()
    }

    override fun videoItemSelected(dataItem: DataItem) {
        val action = HomeListFragmentDirections.actionHomeListFragmentToPlayerFragment(dataItem)
        findNavController().navigate(action)
    }
}