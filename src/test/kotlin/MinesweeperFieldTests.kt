import java.io.File
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MinesweeperFieldTests {
    @Test
    fun test_0() {
        val fieldState = """
            111
            1?1
            111
        """.trimIndent().split("\n")
        val field = MinesweeperField(fieldState)
        assertTrue { field.checkMineAt(2, 2) }
    }

    @Test
    fun test_1() {
        val fieldState = File("src/test/resources/test1").readLines()
        val field = MinesweeperField(fieldState)

        assertTrue { field.checkMineAt(2, 3) }
        assertFalse { field.checkMineAt(1, 3) }
    }

    @Test
    fun test_2() {
        val fieldState = """
            ???
            *1?
            ???
        """.trimIndent().split("\n")
        val field = MinesweeperField(fieldState)
        assertFalse { field.checkMineAt(2, 3) }
    }
}