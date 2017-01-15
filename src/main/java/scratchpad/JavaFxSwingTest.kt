package scratchpad

import javafx.application.Platform
import javafx.beans.property.SimpleStringProperty
import javafx.embed.swing.JFXPanel
import javafx.scene.Scene
import javafx.scene.layout.BorderPane
import javafx.scene.text.Text
import org.almibe.multipage.MultiPageDisplay
import org.almibe.multipage.NewPageAction
import org.almibe.multipage.Page
import javax.swing.JFrame
import javax.swing.SwingUtilities

fun main(args: Array<String>) {
    val developerConsole = DeveloperConsole()
    developerConsole.start()
}

class DeveloperConsole {
    private val frame = JFrame("Library Weasel Developer Console")
    private val jfxPanel = JFXPanel()
    //private val multiPageDisplay = MultiPageDisplay { DeveloperConsole::addPage }

    fun start() {
        SwingUtilities.invokeLater {
            frame.add(jfxPanel)
            frame.setSize(1000, 800)
            frame.isVisible = true
            Platform.runLater { initFx() }
        }
        //TODO add onClose check for window and ask if user wants to shut down entire application or not
        //TODO support tracking developer entry points
        //TODO support showing start page with all new tabs + show when all tabs are closed
    }

    private fun stop() {}

    private fun initFx() {
        val scene = createScene()
        jfxPanel.scene = scene
    }

    private fun createScene(): Scene {
        val root = BorderPane()
        val scene = Scene(root)
        root.center = MultiPageDisplay(NewPageAction { display ->
            display.addPage(Page(SimpleStringProperty("Test"), Text(""), Text("Body")))
        })
        return scene

//        return MultiPageDisplay(NewPageAction { display ->
//            display.addPage(Page(SimpleStringProperty("Test"), Text(""), Text("Body")))
//        })

    }

//    private fun addPage(page: Page) {
//
//    }
}
