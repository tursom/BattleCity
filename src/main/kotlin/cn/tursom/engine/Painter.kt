package cn.tursom.engine

import javafx.scene.canvas.GraphicsContext
import javafx.scene.image.Image
import javafx.scene.paint.Color
import javafx.scene.text.Font

class Painter(private val gc: GraphicsContext) {
    /**
     * 获取图片大小
     */
    fun size(imagePath: String): Array<Int> {
        val cache = imageCache[imagePath]
        if (cache != null) arrayOf(cache.width.toInt(), cache.height.toInt())

        val image = Image(imagePath)
        imageCache[imagePath] = image
        return arrayOf(image.width.toInt(), image.height.toInt())
    }

    /**
     * 绘制图片
     */
    fun drawImage(imagePath: String, x: Int, y: Int) {
        val cache = imageCache[imagePath]
        if (cache != null) gc.drawImage(cache, x.toDouble(), y.toDouble())

        val image = Image(imagePath)
        imageCache[imagePath] = image
        gc.drawImage(image, x.toDouble(), y.toDouble())
    }

    /**
     * 绘制颜色
     */
    fun drawColor(color: Color, x: Int, y: Int, width: Int, height: Int) {
        gc.fill = color
        gc.fillRect(x.toDouble(), y.toDouble(), width.toDouble(), height.toDouble())
        gc.fill = Color.BLACK
    }

    /**
     * 绘制文本
     */
    fun drawText(text: String, x: Int, y: Int, color: Color = Color.BLACK, font: Font = Font.getDefault()) {
        gc.fill = color
        gc.font = font
        gc.fillText(text, x.toDouble(), y.toDouble())
        gc.font = Font.getDefault()
        gc.fill = Color.BLACK
    }

    companion object {
        //图片缓存
        private val imageCache = object : HashMap<String, Image>() {
            override fun get(key: String): Image? {
                return super.get(key) ?: Image(key).let {
                    this[key] = it
                    it
                }
            }
        }
    }
}