package zj.app.taipeizootour.state

sealed class ZooAreaState {
    object Loading: ZooAreaState()
    object Finish: ZooAreaState()
}