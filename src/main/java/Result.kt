/** Sealed class to handle sucess and error cases. Taken from this article:
 * https://medium.com/@its_damo/error-handling-in-kotlin-a07c2ee0e06f
 */
sealed class Result<A, B> {
    abstract fun <C> map(mapping: (A) -> C): Result<C, B>
    abstract fun <C> flatMap(mapping: (A) -> Result<C, B>): Result<C, B>
    abstract fun <C> mapFailure(mapping: (B) -> C): Result<A, C>
    abstract fun <C> flatMapFailure(mapping: (B) -> Result<A, C>): Result<A, C>
    abstract fun orElse(other: A): A
    abstract fun orElse(function: (B) -> A): A

    abstract suspend fun <C> flatMapSuspend(mapping: suspend (A) -> Result<C, B>): Result<C, B>
}

data class Success<A, B>(val value: A) : Result<A, B>() {
    override fun <C> map(mapping: (A) -> C): Result<C, B> = Success(mapping(value))
    override fun <C> flatMap(mapping: (A) -> Result<C, B>): Result<C, B> = mapping(value)
    override fun <C> mapFailure(mapping: (B) -> C): Result<A, C> = Success(value)
    override fun <C> flatMapFailure(mapping: (B) -> Result<A, C>): Result<A, C> = Success(value)
    override fun orElse(other: A): A = value
    override fun orElse(function: (B) -> A): A = value

    override suspend fun <C> flatMapSuspend(mapping: suspend (A) -> Result<C, B>): Result<C, B> = mapping(value)
}

data class Fail<A, B>(val value: B) : Result<A, B>() {
    override fun <C> map(mapping: (A) -> C): Result<C, B> = Fail(value)
    override fun <C> flatMap(mapping: (A) -> Result<C, B>): Result<C, B> = Fail(value)
    override fun <C> mapFailure(mapping: (B) -> C): Result<A, C> = Fail(mapping(value))
    override fun <C> flatMapFailure(mapping: (B) -> Result<A, C>): Result<A, C> = mapping(value)
    override fun orElse(other: A): A = other
    override fun orElse(function: (B) -> A): A = function(value)

    override suspend fun <C> flatMapSuspend(mapping: suspend (A) -> Result<C, B>): Result<C, B> = Fail(value)
}
