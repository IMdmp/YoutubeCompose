package com.imdmp.youtubecompose.features.player.controls

interface ControlsCallback {
    fun fullScreenClicked()
    fun pauseOrPlayClicked()

    companion object{
        fun default() :ControlsCallback = object :ControlsCallback{
            override fun fullScreenClicked() {

            }

            override fun pauseOrPlayClicked() {
            }

        }
    }
}