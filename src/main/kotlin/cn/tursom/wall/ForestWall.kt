package cn.tursom.wall

import cn.tursom.Config
import cn.tursom.engine.Painter
import cn.tursom.interfaces.Blockable

class ForestWall(override val x: Int, override val y: Int, override val painter: Painter) : Wall {
    override val width: Int = Config.blockWidth
    override val height: Int = Config.blockHeight

    override fun paint() {
        painter.drawImage("blocks/terrain/forest.png", x, y)
    }
}