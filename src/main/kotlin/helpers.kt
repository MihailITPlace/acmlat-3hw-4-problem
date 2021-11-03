import com.github.lipen.satlib.core.Lit
import com.github.lipen.satlib.solver.Solver

private val table = generateTable()

fun Solver.exactlyN(n: Int, variables: List<Lit>) {
    val falseRows = table.filter { it.sum() != n }
    for (row in falseRows) {
        addClause(row.mapIndexed { i, it -> if (it == 1) -variables[i] else variables[i] })
    }
}

// Быдлокод. Но если повернуть монитор, то получится пирамидка.
private fun generateTable(): List<List<Int>> {
    val rows = mutableListOf<List<Int>>()
    for (a in 0..1) {
        for (b in 0..1) {
            for (c in 0..1) {
                for (d in 0..1) {
                    for (e in 0..1) {
                        for (f in 0..1) {
                            for (g in 0..1) {
                                for (h in 0..1) {
                                    rows.add(listOf(a, b, c, d, e, f, g, h))
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    return rows
}