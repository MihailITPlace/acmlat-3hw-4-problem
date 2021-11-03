import com.github.lipen.satlib.solver.GlucoseSolver
import com.github.lipen.satlib.solver.solve

class MinesweeperField(state: List<String>) {
    private val width = state[0].length
    private val height = state.size
    private val solver = GlucoseSolver()

    private val shifts = listOf(
        listOf(-1, -1), listOf(-1, 0), listOf(-1, 1),
        listOf(0, -1),                listOf(0, 1),
        listOf(1, -1), listOf(1, 0),  listOf(1, 1),
    )

    init {
        buildFormulaByState(state)
    }

    fun checkMineAt(i: Int, j: Int): Boolean {
        val cellVar = getVariableByCoords(i, j)
        println("NUMBER OF CLAUSES: ${solver.numberOfClauses}")

        val result = solver.solve(cellVar)
        if (result) {
            println("$i - $j SAT")
            println(solver.getModel())
        } else {
            println("$i - $j UNSAT")
        }
        return result
    }

    private fun buildFormulaByState(state: List<String>) {
        repeat((width + 2) * (height + 2)) { solver.newLiteral() }

        addBorderConstraints()

        for (r in 1..height) {
            for (c in 1..width) {
                val cellState = state[r - 1][c - 1]

                if (cellState.isDigit()) {
                    val variables = shifts.map { getVariableByCoords(r + it[0], c + it[1]) }
                    val number = cellState.digitToInt()
                    solver.exactlyN(number, variables)
                }

                if (cellState == '*') {
                    solver.addClause(getVariableByCoords(r, c))
                }
            }
        }
    }

    private fun addBorderConstraints() {
        repeat(width + 2) {
            solver.addClause(-getVariableByCoords(0, it))
            solver.addClause(-getVariableByCoords(height + 1, it))
        }

        repeat(height + 2) {
            solver.addClause(-getVariableByCoords(it, 0))
            solver.addClause(-getVariableByCoords(it, width + 1))
        }
    }

    private fun getVariableByCoords(i: Int, j: Int): Int =
        i * (width + 2) + j + 1
}