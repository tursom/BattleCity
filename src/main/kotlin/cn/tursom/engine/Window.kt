package cn.tursom.engine

import javafx.application.Application
import javafx.event.EventHandler
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.image.Image
import javafx.scene.input.KeyEvent
import javafx.scene.paint.Color
import javafx.stage.Stage
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

abstract class Window(
    val title: String = "Tursom Game Engine",
    val icon: String? = null,
    val width: Int = 800,
    val height: Int = 600,
    private val fps: Long = 15L
) : Application() {
    private val canvas = Canvas(width.toDouble(), height.toDouble())
    private val uiThread = Executors.newSingleThreadScheduledExecutor()

//    private val keyRecorder = mutableMapOf<KeyCode, KeyEvent>()
//    private var currentKey: KeyCode? = null

    private val gc = canvas.graphicsContext2D!!

    //画笔
    protected val painter = Painter(gc)

    override fun start(primaryStage: Stage?) {
        val group = Group()
        group.children.add(canvas)

        val scene = Scene(group, width.toDouble(), height.toDouble())

        primaryStage?.let {
            with(primaryStage) {
                this.title = this@Window.title
                this.scene = scene
                this.isResizable = false
                icon?.let { this.icons.add(Image(it)) }

                this.setOnCloseRequest {
                    System.exit(0)
                }

                show()
                onCreate()
                display()
            }
        }

//        scene.onKeyPressed = EventHandler { event ->
//            Thread.currentThread().name = "key-scanner"
//            currentKey = event.code
//            //记录
//            keyRecorder[event.code] = event
//            this@Window.onKeyPressed(event)
//        }
        scene.onKeyPressed = EventHandler(this::onKeyPressed)
//        scene.onKeyReleased = EventHandler { event ->
//            keyRecorder.remove(event.code)
//            if (currentKey == event.code) {
//                currentKey = null
//            }
//        }

        if (fps > 0L) uiThread.scheduleAtFixedRate(this::display, 0, 1000 / fps, TimeUnit.MILLISECONDS)

//        Thread {
//            Thread.sleep(200)
//            while (true) {
//                Thread.sleep(100)
//                keyRecorder.filter { entry ->
//                    entry.key != currentKey
//                }.forEach { _, u ->
//                    onKeyPressed(u)
//                }
//            }
//        }.start()

        Thread {
            while (true) {
                Thread.sleep(if (fps > 0) 1000 / fps else 100)
                this@Window.onRefresh()
            }
        }.let {
            it.isDaemon = true
            it.priority = Thread.MAX_PRIORITY
            it.start()
        }

    }

    private fun display() {
        //清屏
        gc.fill = Color.BLACK
        gc.fillRect(0.0, 0.0, width.toDouble(), height.toDouble())
        onDisplay()
    }

    protected fun uiThread(runnable: () -> Unit) = uiThread.execute(runnable)

    abstract fun onCreate()

    abstract fun onDisplay()

    abstract fun onRefresh()

    abstract fun onKeyPressed(event: KeyEvent)
}