package cn.tursom.interfaces

import cn.tursom.engine.Painter

interface View {
    val x: Int
    val y: Int

    val width: Int
    val height: Int

    val painter: Painter

    fun paint()
}