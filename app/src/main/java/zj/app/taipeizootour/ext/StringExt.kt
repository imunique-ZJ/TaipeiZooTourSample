package zj.app.taipeizootour.ext

fun String.fullWidthToHalfWidth(): String {
    val charArr = this.toCharArray()
    charArr.onEachIndexed { index, char ->
        when (val charInt = char.toInt()) {
            in 65281..65374 -> charArr[index] = (charInt - 65248).toChar()
            in 12288..12288 -> charArr[index] = 32.toChar()
            else -> { /** stay unchanged **/ }
        }
    }
    return String(charArr)
}