package zj.app.taipeizootour.data.transformer

interface IDataTransformer<in IN, out OUT> {
    suspend fun transform(data: IN?): OUT?
}