package com.imdmp.youtubecompose.features.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.imdmp.youtubecompose.player.PlayerDataSource
import com.imdmp.youtubecompose.ui.theme.YoutubeComposeTheme
import com.imdmp.youtubecompose.usecases.GetVideoStreamUrlUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PlayerFragment : Fragment() {

    val args: PlayerFragmentArgs by navArgs()

    @Inject lateinit var getVideoStreamUrlUseCase: GetVideoStreamUrlUseCase

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
                    args.dataItem?.let { VideoPlayerScreen(getVideoStreamUrlUseCase, it) }
                }
            }
        }
    }
}