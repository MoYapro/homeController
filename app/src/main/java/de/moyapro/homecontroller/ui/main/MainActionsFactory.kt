package de.moyapro.homecontroller.ui.main

fun buildMainActions(mainViewModel: MainViewModel, restartMainAction: () -> Unit): MainActions {
    return MainActions(
        openSettings = { mainViewModel.selectView(ViewEnum.SETTINGS) },
        openStart = {
            mainViewModel.selectView(ViewEnum.START)
            restartMainAction()
        }
    )
}
