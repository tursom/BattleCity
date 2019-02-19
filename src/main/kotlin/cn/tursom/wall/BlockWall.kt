package cn.tursom.wall

import cn.tursom.interfaces.Blockable
import cn.tursom.Config
import cn.tursom.engine.Painter

class BrickWall(
    override val x: Int,
    override val y: Int,
    override val painter: Painter
) : Wall, Blockable {
    override val width: Int = Config.blockWidth
    override val height: Int = Config.blockWidth

    override fun paint() {
        painter.drawImage("blocks/terrain/brick.png", x, y)
    }
}