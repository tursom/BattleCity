package cn.tursom.tank

import cn.tursom.Config
import cn.tursom.interfaces.Movable
import cn.tursom.interfaces.View
import cn.tursom.engine.Painter
import cn.tursom.enums.Direction

class ControlledTank(
    override var x: Int,
    override var y: Int,
    override val painter: Painter,
    override val movable: Movable.() -> Boolean
) : View, Tank, Movable {
    override var direction: Direction = Direction.UP
    override val speed: Int = 16

    override fun move(direction: Direction) {
        if (direction != this.direction) {
            this.direction = direction
        } else if (movable()) {
            when (direction) {
                Direction.UP -> y -= speed
                Direction.DOWN -> y += speed
                Direction.LEFT -> x -= speed
                Direction.RIGHT -> x += speed
            }
        }
    }

    override val width: Int = Config.blockWidth
    override val height: Int = Config.blockWidth

    override fun paint() {
        painter.drawImage(
            when (direction) {
                Direction.UP -> "blocks/tank_player1_up.png"
                Direction.DOWN -> "blocks/tank_player1_down.png"
                Direction.LEFT -> "blocks/tank_player1_left.png"
                Direction.RIGHT -> "blocks/tank_player1_right.png"
            }, x, y
        )
    }
}