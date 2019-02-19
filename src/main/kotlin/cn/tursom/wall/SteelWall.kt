package cn.tursom.wall

import cn.tursom.Config
import cn.tursom.engine.Painter
import cn.tursom.interfaces.Blockable

class SteelWall(override val x: Int, override val y: Int, override val painter: Painter) : Wall, Blockable {
    override val width: Int = Config.blockWidth
    override val height: Int = Config.blockHeight

    override fun paint() {
        painter.drawImage("blocks/terrain/steel.png", x, y)
    }
}