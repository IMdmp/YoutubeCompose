package com.imdmp.youtubecompose.features.videolist.topappbar

interface ToolbarActions {
    fun searchClicked()
    fun profileClicked()

    companion object {
        fun default(): ToolbarActions = object : ToolbarActions {
            override fun searchClicked() {
                TODO("Not yet implemented")
            }

            override fun profileClicked() {
                TODO("Not yet implemented")
            }

        }
    }
}
