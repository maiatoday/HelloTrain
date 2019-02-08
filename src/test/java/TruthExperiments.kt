import com.google.common.collect.ImmutableList
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import java.util.HashMap

//https://www.baeldung.com/google-truth
class TruthExperiments {
    var result: Result<Answer, ErrorCode> = Fail(ErrorCode.GENERAL_ERROR)
    @Before
    fun setUp() {

    }

    @Test
    fun `playing with truth`() {
        //  val abc = ImmutableList.of("a", "b", "c", "c")
        val abc = ImmutableList.of("a", "b", "c")

        Truth.assertThat(abc).contains("c")
        Truth.assertThat(abc).containsNoneOf("x", "y", "z")
        Truth.assertThat(abc).containsAllOf("c", "a", "b")             // passes
        Truth.assertThat(abc).containsExactly("c", "a", "b")           // passes

        Truth.assertThat(abc).containsAllOf("a", "b").inOrder()        // passes

        // assertThat(abc).containsAllOf("c", "a", "b").inOrder()   // fails
        // assertThat(abc).containsExactly("c", "a", "b").inOrder() // fails

        // assertThat(abc).containsNoDuplicates()

        val actualDouble: Double = 1.03
        val tolerance = 0.01
        val expectedDouble = 1.035
        Truth.assertThat(actualDouble).isWithin(tolerance).of(expectedDouble);

    }

    @Test
    fun `test object comparisons`() {
        val thing1 = User("thing1", "hello")
        val thing2 = User("thing1", "hello")
        val thing3 = User("thing1", "hello blah blah")
        val sameThing1 = thing1

        Truth.assertThat(thing1 == thing2).isTrue() //becaue Kotlin
        Truth.assertThat(thing1 === thing2).isFalse() //because Kotlin
        Truth.assertThat(thing1 == thing3).isFalse() //because Kotlin
        Truth.assertThat(thing1).isEqualTo(thing2) // these also test values but give better error message
        Truth.assertThat(thing1).isNotSameAs(thing2) // these also test values but give better error message
        Truth.assertThat(thing1).isSameAs(sameThing1) // these also test values but give better error message
        Truth.assertThat(thing1).isNotEqualTo(thing3)  // these also test values but give better error message

    }

    @Test
    fun `messing with maps`() {

        val aMap = HashMap<String, Any>()
        aMap["one"] = 1L
        Truth.assertThat(aMap).containsEntry("one", 1L)

        aMap["first"] = 1L
        aMap["second"] = 2.0
        aMap["third"] = 3f

        val anotherMap = HashMap(aMap)

        Truth.assertThat(aMap).containsExactlyEntriesIn(anotherMap)
    }

    @Test
    fun `Accepting exceptions`() {
        val anException = IllegalArgumentException(NumberFormatException("Bad number"))

        Truth.assertThat(anException)
            .hasCauseThat()
            .isInstanceOf(NumberFormatException::class.java)

        val anotherException = IllegalArgumentException("Bad value")
        Truth.assertThat(anotherException)
            .hasMessageThat()
            .startsWith("Bad");
    }
}

data class User(val name: String, val message: String)
