package com.imdmp.youtubecompose.features.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.imdmp.youtubecompose.DownloaderImpl
import com.imdmp.youtubecompose.player.PlayerDataSource
import com.imdmp.youtubecompose.ui.theme.YoutubeComposeTheme

class PlayerFragment : Fragment() {

    val args: PlayerFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataSource = PlayerDataSource(
            requireContext(), DownloaderImpl.USER_AGENT,
            DefaultBandwidthMeter.Builder(requireContext()).build()
        )
        return ComposeView(requireContext()).apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                YoutubeComposeTheme() {
                    // In Compose world
                    args.dataItem?.let { VideoPlayer(dataSource, it) }
                }
            }
        }
    }
}