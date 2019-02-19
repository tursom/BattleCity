package cn.tursom

import cn.tursom.engine.Window
import cn.tursom.enums.Direction
import cn.tursom.interfaces.Blockable
import cn.tursom.interfaces.Movable
import cn.tursom.tank.ControlledTank
import cn.tursom.wall.BrickWall
import cn.tursom.wall.ForestWall
import cn.tursom.wall.SteelWall
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent

class GameWindow : Window(
    width = Config.blockWidth * Config.widthSize,
    height = Config.blockHeight * Config.heightSize,
    title = "坦克大战",
    icon = "blocks/tank_player1_up.png",
    fps = 0L
) {
    private var brickWall = BrickWall(Config.blockWidth * 0, Config.blockHeight * 0, painter)
    private val forestWall = ForestWall(Config.blockWidth * 1, Config.blockHeight * 0, painter)
    private val steelWall = SteelWall(Config.blockWidth * 2, Config.blockHeight * 0, painter)
    private val blockList = ArrayList<Blockable>()
    private val tank = ControlledTank(
//        Config.blockWidth * (Config.widthSize - 1),
//        Config.blockHeight * (Config.heightSize - 1),
        Config.blockWidth * 3,
        Config.blockHeight * 0,
        painter
    ) {
        val x = when (direction) {
            Direction.LEFT -> x - speed
            Direction.RIGHT -> x + speed
            else -> x
        }
        val y = when (direction) {
            Direction.UP -> y - speed
            Direction.DOWN -> y + speed
            else -> y
        }
        if (isOverload(x, y, width, height)) {
            return@ControlledTank false
        }
        blockList.forEach {
            if (isBlock(x, y, width, height, it)) {
                return@ControlledTank false
            }
        }
        true
    }

    override fun onKeyPressed(event: KeyEvent) {
        when (event.code) {
            KeyCode.W -> tank.move(Direction.UP)
            KeyCode.S -> tank.move(Direction.DOWN)
            KeyCode.A -> tank.move(Direction.LEFT)
            KeyCode.D -> tank.move(Direction.RIGHT)
            else -> {
            }
        }
    }

    override fun onCreate() {
        blockList.add(brickWall)
        blockList.add(steelWall)
    }

    override fun onDisplay() {
        brickWall.paint()
        steelWall.paint()
        tank.paint()
        forestWall.paint()
    }

    override fun onRefresh() {
    }

    private fun isBlock(x: Int, y: Int, width: Int, height: Int, blockable: Blockable): Boolean {
        return (x - blockable.x > -width && x - blockable.x < blockable.width)
                && (y - blockable.y > -height && y - blockable.y < blockable.height)
                || (x < 0 || y < 0
                || x + width > Config.widthSize * Config.blockWidth
                || y + height > Config.heightSize * Config.blockHeight)
    }

    private fun isOverload(x: Int, y: Int, width: Int, height: Int): Boolean {
        return (x + width > Config.widthSize * Config.blockWidth
                || y + height > Config.heightSize * Config.blockHeight)
    }
}