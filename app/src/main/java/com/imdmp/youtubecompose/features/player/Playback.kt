package com.imdmp.youtubecompose.features.player

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ui.PlayerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//@Composable
//fun Playback(modifier: Modifier = Modifier, context: Context = LocalContext.current) {
//
//    val playerView = PlayerView(context)
//
////    LaunchedEffect(Unit) {
////        this.launch(Dispatchers.IO) {
////            val mediaSource = videoPlayerScreenCallbacks.getMediaSource(streamUrl)
////            withContext(Dispatchers.Main) {
////                playerView.player = player
////                player.setMediaSource(mediaSource)
////                player.prepare()
////                player.play()
////            }
////        }
////    }
//
//    Box(
//        modifier = modifier
//    ) {
//        AndroidView(
//            factory = {
//                playerView
//            })
//    }
//}
//
