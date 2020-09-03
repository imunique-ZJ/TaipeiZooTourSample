package zj.app.taipeizootour.data.transformer

interface IDataTransformer<in IN, out OUT> {
    fun transform(data: IN?): OUT?
}