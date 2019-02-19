package cn.tursom.interfaces

import cn.tursom.enums.Direction

interface Movable : View {
    override var x: Int
    override var y: Int

    val speed: Int
    val direction: Direction

    val movable: Movable.() -> Boolean

    fun move(direction: Direction)
}