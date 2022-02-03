package com.imdmp.youtubecompose.features.search

import SearchScreen
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.imdmp.youtubecompose.features.home.ListScreen
import com.imdmp.youtubecompose.ui.theme.YoutubeComposeTheme

class SearchFragment : Fragment(), SearchScreenActions {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                YoutubeComposeTheme {
                    // In Compose world
                    SearchScreen(this@SearchFragment)
                }
            }
        }
    }

    override fun onSearchClicked(query: String) {
        val action = SearchFragmentDirections.actionSearchFragmentToHomeListFragment(query = query)
        findNavController().navigate(action)
    }
}