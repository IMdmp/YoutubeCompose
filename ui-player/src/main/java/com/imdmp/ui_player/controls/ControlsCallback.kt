package com.imdmp.ui_player.controls

interface ControlsCallback {
    fun fullScreenClicked()
    fun pauseOrPlayClicked()

    companion object {
        fun default(): ControlsCallback = object : ControlsCallback {
            override fun fullScreenClicked() {
                TODO("Not yet implemented")
            }

            override fun pauseOrPlayClicked() {
                TODO("Not yet implemented")
            }

        }
    }
}