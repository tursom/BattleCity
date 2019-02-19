package cn.tursom

import cn.tursom.engine.Window
import cn.tursom.enums.Direction
import cn.tursom.interfaces.Blockable
import cn.tursom.interfaces.Movable
import cn.tursom.interfaces.View
import cn.tursom.tank.ControlledTank
import cn.tursom.wall.BrickWall
import cn.tursom.wall.ForestWall
import cn.tursom.wall.SteelWall
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.paint.Color
import java.io.File

class GameWindow : Window(
    width = Config.blockWidth * Config.widthSize,
    height = Config.blockHeight * Config.heightSize,
    title = "坦克大战",
    icon = "blocks/tank_player1_up.png",
    fps = 0L
) {
    private val backgroundView = ArrayList<View>()
    private val facegroundView = ArrayList<View>()
    private val blockList = ArrayList<Blockable>()
    private var tank: ControlledTank = ControlledTank(0, 0, painter) { x, y -> false }

    private val canMove: Movable.(x: Int, y: Int) -> Boolean = canMove@{ x, y ->
        //越界检测
        if (isOverload(x, y, width, height)) {
            return@canMove false
        }
        //碰撞检测
        blockList.forEach {
            if (isBlock(x, y, width, height, it)) {
                return@canMove false
            }
        }
        true
    }

    override fun onKeyPressed(event: KeyEvent) {
        painter.drawColor(Color.BLACK, tank.x, tank.y, tank.width, tank.height)
        when (event.code) {
            KeyCode.W -> tank.move(Direction.UP)
            KeyCode.S -> tank.move(Direction.DOWN)
            KeyCode.A -> tank.move(Direction.LEFT)
            KeyCode.D -> tank.move(Direction.RIGHT)
            else -> {
            }
        }
        uiThread(this::onDisplay)
    }

    override fun onCreate() {
        File("src/main/resources/config/default.map").readLines().forEachIndexed { line, s ->
            s.forEachIndexed { index, c ->
                when (c) {
                    '砖' -> {
                        val brickWall =
                            BrickWall(Config.blockWidth * index, Config.blockHeight * line, painter)
                        backgroundView.add(brickWall)
                        blockList.add(brickWall)
                    }
                    '铁' -> {
                        val steelWall =
                            SteelWall(Config.blockWidth * index, Config.blockHeight * line, painter)
                        backgroundView.add(steelWall)
                        blockList.add(steelWall)
                    }
                    '树' -> {
                        facegroundView.add(
                            ForestWall(
                                Config.blockWidth * index,
                                Config.blockHeight * line,
                                painter
                            )
                        )
                    }
                    '坦' -> {
                        this.tank = ControlledTank(
                            Config.blockWidth * index,
                            Config.blockHeight * line,
                            painter,
                            canMove
                        )
                    }
                    else -> {
                    }
                }
            }
        }
        uiThread {
            backgroundView.forEach(View::paint)
        }
    }

    override fun onDisplay() {
        tank.paint()
        facegroundView.forEach(View::paint)
    }

    override fun onRefresh() {
    }

    private fun isBlock(x: Int, y: Int, width: Int, height: Int, view: View): Boolean {
        return (view.x - x < width && x - view.x < view.width) &&
                (view.y - y < height && y - view.y < view.height)
    }

    private fun isOverload(x: Int, y: Int, width: Int, height: Int): Boolean {
        return (x < 0 || y < 0) ||
                (x + width > Config.widthSize * Config.blockWidth ||
                        y + height > Config.heightSize * Config.blockHeight)
    }
}